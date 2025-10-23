// cypress/e2e/fechamento_compra.cy.ts

describe('Fechamento de Compra', () => {
  const userId = 20;
  const saleId = 9001;
  const addressId = 77;
  const cardId1 = 201;
  const cardId2 = 202;

  const itemsParam = encodeURIComponent(
    JSON.stringify({
      items: [{ bookId: 1, quantity: 2 }],
      fromCart: false,
    })
  );

  beforeEach(() => {
    // usuário logado
    cy.window().then((win) => {
      win.localStorage.setItem('currentUser', String(userId));
    });

    // cria a venda a partir da URL ?items=
    cy.intercept({ method: 'POST', url: '**/sale' }, (req) => {
      const body = req.body?.data;
      expect(body?.user).to.eq(userId);
      expect(body?.books).to.have.length(1);
      expect(body?.books[0]).to.deep.include({ book: 1, quantity: 2 });

      req.reply({
        statusCode: 200,
        body: {
          data: {
            entity: { id: saleId },
          },
        },
      });
    }).as('createSale');

    // detalhes dos livros
    cy.intercept(
      { method: 'GET', url: '**/book/list*' },
      {
        statusCode: 200,
        body: {
          data: {
            entities: [{ id: 1, name: 'Livro A', price: 30.0 }], // 2 x 30 = 60
          },
        },
      }
    ).as('getBooks');

    // endereços do usuário
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

    // cartões do usuário
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

    // evita fluxo de cupons nesse teste
    cy.intercept(
      { method: 'GET', url: '**/traderCoupon/check*' },
      { statusCode: 200, body: { data: { entity: null } } }
    ).as('checkTrader');
    cy.intercept(
      { method: 'GET', url: '**/promotionalCoupon/check*' },
      { statusCode: 200, body: { data: { entity: null } } }
    ).as('checkPromotional');

    // pagamento
    cy.intercept({ method: 'PUT', url: '**/sale/payment' }, (req) => {
      const payload = req.body?.data;

      expect(payload?.id).to.eq(saleId);
      expect(payload?.user).to.eq(userId);

      // endereço selecionado
      expect(payload?.address).to.eq(addressId);

      // split 70/30 como fração (0.7/0.3)
      expect(payload?.cards).to.have.length(2);
      const byCard = Object.fromEntries(payload.cards.map((c: any) => [c.card, c]));
      expect(byCard[cardId1].percent).to.be.closeTo(0.7, 1e-6);
      expect(byCard[cardId2].percent).to.be.closeTo(0.3, 1e-6);

      // campos que o front envia nulos no fechamento
      expect(payload?.books).to.be.null;
      expect(payload?.freight).to.be.null;
      expect(payload?.deliveryDate).to.be.null;
      expect(payload?.status).to.be.null;

      // sem cupons neste cenário
      expect(payload?.traderCoupons ?? []).to.have.length(0);
      expect(payload?.promotinalCoupon).to.be.null;

      req.reply({
        statusCode: 200,
        body: { data: { entity: { id: saleId } } },
      });
    }).as('pay');

    // cancelamento defensivo (ao sair)
    cy.intercept(
      { method: 'PUT', url: '**/sale/cancel' },
      { statusCode: 200, body: { data: { entity: null } } }
    ).as('cancelSale');

    // visita a página com os itens
    cy.visit(`/sale?items=${itemsParam}`);

    // espera inicialização
    cy.wait(['@createSale', '@getBooks', '@getAddresses', '@getCards']);
  });

  it('Deve finalizar a compra com dois cartões (70%/30%) e endereço selecionado', () => {
    // seleciona endereço
    cy.get(`[data-cy="address-option-${addressId}"]`).click();

    // seleciona cartões e percentuais
    cy.get(`[data-cy="card-row-${cardId1}"] [data-cy="card-select"]`).click();
    cy.get(`[data-cy="percent-input-${cardId1}"]`).clear().type('70');

    cy.get(`[data-cy="card-row-${cardId2}"] [data-cy="card-select"]`).click();
    cy.get(`[data-cy="percent-input-${cardId2}"]`).clear().type('30');

    // finaliza
    cy.contains('button', 'Finalizar Compra').click();

    // valida chamada /sale/payment
    cy.wait('@pay');

    // redireciona para home
    cy.url().should('match', /\/$/);
  });
});
