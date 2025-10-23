describe('Fechamento de Compra', () => {
    const userId = 20;
    const saleId = 9001;
    const addressId = 77;
    const cardId1 = 201;
    const cardId2 = 202;

    // Compra: 2 unidades do livro 1, preço 30 => subtotal 60 (sem descontos)
    const itemsParam = encodeURIComponent(
      JSON.stringify({
        items: [{ bookId: 1, quantity: 2 }],
        fromCart: false,
      })
    );

    beforeEach(() => {
      // Simula usuário logado (mesmo padrão que você já usa)
      cy.window().then((win) => {
        win.localStorage.setItem('currentUser', String(userId));
      });

      // Intercepta criação da venda a partir dos items da URL
      cy.intercept({ method: 'POST', url: '**/sale' }, (req) => {
        // Valida payload de criação
        const body = req.body?.data;
        expect(body?.user).to.eq(userId);
        expect(body?.books).to.have.length(1);
        expect(body?.books[0]).to.deep.include({ book: 1, quantity: 2 });

        req.reply({
          statusCode: 200,
          body: {
            data: {
              entity: { id: saleId }, // id da venda criada
            },
          },
        });
      }).as('createSale');

      // Livros pedidos (detalhes)
      cy.intercept(
        { method: 'GET', url: '**/book/list*' },
        {
          statusCode: 200,
          body: {
            data: {
              entities: [
                { id: 1, name: 'Livro A', price: 30.0 }, // 2 x 30 = 60
              ],
            },
          },
        }
      ).as('getBooks');

      // Endereços do usuário (deixe um para seleção)
      cy.intercept(
        { method: 'GET', url: '**/address/user*' },
        {
          statusCode: 200,
          body: {
            data: {
              entities: [
                {
                  id: addressId,
                  nickname: 'Casa',
                  street: 'Rua 1',
                  number: '123',
                  city: 'SP',
                },
              ],
            },
          },
        }
      ).as('getAddresses');

      // Cartões do usuário (dois cartões para split 70/30)
      cy.intercept(
        { method: 'GET', url: '**/card/user*' },
        {
          statusCode: 200,
          body: {
            data: {
              entities: [
                { id: cardId1, brand: 'VISA', last4: '1111', principal: true },
                { id: cardId2, brand: 'MC', last4: '2222', principal: false },
              ],
            },
          },
        }
      ).as('getCards');

      // Evita interferência dos fluxos de cupom neste teste
      cy.intercept({ method: 'GET', url: '**/traderCoupon/check*' }, { statusCode: 200, body: { data: { entity: null } } }).as('checkTrader');
      cy.intercept({ method: 'GET', url: '**/promotionalCoupon/check*' }, { statusCode: 200, body: { data: { entity: null } } }).as('checkPromotional');

      // Intercepta o fechamento/pagamento para validar o payload
      cy.intercept({ method: 'PUT', url: '**/sale/payment' }, (req) => {
        const payload = req.body?.data;

        // ----- Asserts do payload gerado pelo front -----
        expect(payload?.id).to.eq(saleId);
        expect(payload?.user).to.eq(userId);

        // endereço selecionado
        expect(payload?.address).to.eq(addressId);

        // cartões: percent enviado como fração (0.7 e 0.3)
        expect(payload?.cards).to.have.length(2);
        const byCard = Object.fromEntries(payload.cards.map((c: any) => [c.card, c]));
        expect(byCard[cardId1].percent).to.be.closeTo(0.7, 1e-6);
        expect(byCard[cardId2].percent).to.be.closeTo(0.3, 1e-6);

        // sem livros (no fechamento), sem frete/entrega/status no payload do pagamento
        expect(payload?.books).to.be.null;
        expect(payload?.freight).to.be.null;
        expect(payload?.deliveryDate).to.be.null;
        expect(payload?.status).to.be.null;

        // sem cupons neste teste
        expect(payload?.traderCoupons ?? []).to.have.length(0);
        expect(payload?.promotinalCoupon).to.be.null;

        req.reply({
          statusCode: 200,
          body: { data: { entity: { id: saleId } } },
        });
      }).as('pay');

      // Intercepta cancelamento defensivo (saída de página)
      cy.intercept({ method: 'PUT', url: '**/sale/cancel' }, { statusCode: 200, body: { data: { entity: null } } }).as('cancelSale');

      // Visita a página já com o parâmetro items (como o componente espera)
      cy.visit(`/sale?items=${itemsParam}`);

      // Garante que a página inicializou o fluxo
      cy.wait(['@createSale', '@getBooks', '@getAddresses', '@getCards']);
    });

    it('Deve finalizar a compra com dois cartões (70%/30%) e endereço selecionado', () => {
      // ---- Seleciona endereço ----
      // Ajuste o seletor abaixo para o seu componente de endereço
      cy.get(`[data-cy="address-option-${addressId}"]`).click();

      // ---- Seleciona cartões e percentuais ----
      // Ajuste os seletores para o seu componente de pagamento:
      // exemplo: marcar os dois cartões e digitar 70 e 30 nas porcentagens
      cy.get(`[data-cy="card-row-${cardId1}"] [data-cy="card-select"]`).click();
      cy.get(`[data-cy="percent-input-${cardId1}"]`).clear().type('70');

      cy.get(`[data-cy="card-row-${cardId2}"] [data-cy="card-select"]`).click();
      cy.get(`[data-cy="percent-input-${cardId2}"]`).clear().type('30');

      // ---- Finalizar compra ----
      cy.contains('button', 'Finalizar Compra').click();

      // Valida que o front chamou a API com o payload correto
      cy.wait('@pay');

      // Se a sua app redireciona para '/', valide o redirect:
      cy.url().should('match', /\/$/);
    });
});