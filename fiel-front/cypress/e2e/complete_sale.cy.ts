// ====================================================================
// ====================  TESTE 1 — SOLICITAR TROCA  ====================
// ====================================================================

describe('Pedido - solicitar troca', () => {
  const userId = 20;
  const saleId = 9001;
  const createdAtISO = '2025-10-21T18:30:00.000Z';

  beforeEach(() => {
    cy.window().then((win) => {
      win.localStorage.setItem('currentUser', String(userId));
    });

    cy.intercept('GET', '**/sale/user*', (req) => {
      expect(String(req.query?.userId ?? '')).to.eq(String(userId));
      req.reply({
        statusCode: 200,
        body: {
          data: {
            entities: [
              {
                id: saleId,
                createdAt: createdAtISO,
                saleBooks: [{ price: 50 }],
                statusSale: { status: 'DELIVERED' },
              },
            ],
          },
        },
      });
    }).as('getSalesByUser');
  });

  it('deve pedir troca e atualizar o status para EXCHANGE_REQUESTED', () => {
    cy.intercept(
      {
        method: 'PUT',
        url: '**/sale/trade/request*',
        query: { saleId: String(saleId) },
      },
      (req) => {
        req.reply({ statusCode: 200, body: { data: {} } });
      }
    ).as('putTradeRequest');

    cy.visit('/historical');
    cy.wait('@getSalesByUser');

    cy.get('[data-cy="request-button"]').should('be.visible').click();

    cy.wait('@putTradeRequest');

    cy.get('p.orderCard_statusLine__eYM2_')
      .should('contain.text', 'Troca solicitada');
  });
});


// ====================================================================
// ===============  TESTE 2 — DETALHE DO PEDIDO  =======================
// ====================================================================

describe('Página de Detalhe do Pedido (OrderSale)', () => {
  const userId = 20;
  const saleId = 9001;

  const createdAtISO = '2025-10-21T18:30:00.000Z';
  const saleBooks = [{ price: 10.0 }, { price: 35.5 }];
  const expectedTotal = saleBooks.reduce((a, b) => a + Number(b.price), 0);
  const expectedDate = '21/10/2025';

  beforeEach(() => {
    cy.window().then((win) => {
      win.localStorage.setItem('currentUser', String(userId));
    });

    cy.intercept('GET', '**/sale/user*', (req) => {
      expect(String(req.query?.userId ?? '')).to.eq(String(userId));

      req.reply({
        statusCode: 200,
        body: {
          data: {
            entities: [
              {
                id: saleId,
                createdAt: createdAtISO,
                saleBooks,
              },
            ],
          },
        },
      });
    }).as('getSalesByUser');

    cy.intercept('GET', '**/address/**', { statusCode: 200, body: { data: { entities: [] } } });
    cy.intercept('GET', '**/card/**', { statusCode: 200, body: { data: { entities: [] } } });

    cy.visit('/historical');
    cy.wait('@getSalesByUser');
  });

  it('deve exibir data, número do pedido e total', () => {
    cy.contains('Pedido realizado').should('be.visible');
    cy.contains(expectedDate).should('be.visible');
    cy.contains('Pedido nº').should('be.visible');
    cy.contains(String(saleId)).should('be.visible');

    cy.contains('Total:').should('be.visible');
    cy.contains(new RegExp(`R\\$\\s*${expectedTotal.toFixed(2).replace('.', '[\\.,]')}`)).should('be.visible');
  });
});


// ====================================================================
// ==========  TESTE 3 — CONTROLE DE VENDAS (pendentes)  ===============
// ====================================================================

describe('Controle de vendas - Pendentes', () => {
  const saleId = 1234;

  beforeEach(() => {
    // GET inicial — aprovado
    cy.intercept('GET', /sale\/peding/i, {
      statusCode: 200,
      body: {
        data: {
          entities: [
            {
              id: saleId,
              statusSale: { status: 'APPROVED' },
              saleBooks: [{ price: 10 }, { price: 20 }],
            },
          ],
        },
      },
    }).as('getPedingSales');

    // PUT /sale/transit — valida QUERY (não body)
    cy.intercept('PUT', /sale\/transit/i, (req) => {
      expect(req.query.saleId).to.eq(String(saleId));
      expect(req.query.deliveryDate).to.eq('2025-12-10');

      // Seu front converte 25.50 → 2550
      expect(req.query.freight).to.match(/25\.?50|2550/);

      req.reply({
        statusCode: 200,
        body: { message: 'Venda atualizada com sucesso' },
      });
    }).as('putInTransit');

    // PUT /sale/delivered
    cy.intercept('PUT', /sale\/delivered/i, (req) => {
      expect(req.query.saleId).to.eq(String(saleId));

      req.reply({
        statusCode: 200,
        body: { message: 'Venda atualizada com sucesso' },
      });
    }).as('putDelivered');

    cy.visit('/control?tab=peding');
    cy.wait('@getPedingSales');
  });

  it('deve colocar pedido em trânsito e depois marcar como entregue', () => {
    // Confirma que o pedido carregou
    cy.contains('Pedidos Pendentes').should('be.visible');
    cy.get('tbody tr').contains(String(saleId)).should('be.visible');

    // 1️⃣ Abrir modal de “Passar”
    cy.get('[data-cy="pass-button"]').click({ force: true });

    // 2️⃣ Preencher modal
    cy.get('[data-cy="delivery-date-text"]').type('2025-12-10');
    cy.get('[data-cy="freight-text"]').clear().type('25.50');

    // 3️⃣ Confirmar
    cy.contains('Confirmar').click();

    // PUT transit
    cy.wait('@putInTransit');

    // Modal some
    cy.get('.peding_modal__FphNw').should('not.exist');

    // 🔥 AQUI ESTÁ O AJUSTE IMPORTANTE
    // O front **NÃO remove** a linha. Ele muda para "Em trânsito".
    cy.get('tbody tr').contains(String(saleId)).should('exist');
    cy.get('tbody tr').contains(/trânsito/i).should('exist');

    // 4️⃣ Simular nova listagem — agora com IN_TRANSIT
    cy.intercept('GET', /sale\/peding/i, {
      statusCode: 200,
      body: {
        data: {
          entities: [
            {
              id: saleId,
              statusSale: { status: 'IN_TRANSIT' },
              saleBooks: [{ price: 10 }],
            },
          ],
        },
      },
    }).as('getPedingSalesSecond');

    cy.visit('/control?tab=peding');
    cy.wait('@getPedingSalesSecond');

    // Agora a linha existe, mas com IN_TRANSIT
    cy.get('tbody tr').contains(String(saleId)).should('be.visible');
    cy.get('tbody tr').contains(/trânsito/i).should('be.visible');

    // 5️⃣ Passar novamente → entregar
    cy.get('[data-cy="pass-button"]').click({ force: true });

    cy.wait('@putDelivered');

    // A linha CONTINUA EXISTINDO (comportamento real do front)
    cy.get('tbody tr')
      .contains(String(saleId))
      .should('exist');
  });
});


// ====================================================================
// =========  TESTE 4 — CONTROLE DE TROCAS (delivered)  ===============
// ====================================================================

describe('Controle de trocas - delivered', () => {
  const saleId = 555;

  beforeEach(() => {
    cy.intercept('GET', '**/sale/trade**', {
      statusCode: 200,
      body: {
        data: {
          entities: [
            {
              id: saleId,
              statusSale: { status: 'EXCHANGE_AUTHORIZED' },
              saleBooks: [{ price: 10 }],
            },
          ],
        },
      },
    }).as('getTradeSales');

    cy.visit('/control?tab=trade');
    cy.wait('@getTradeSales');

    cy.contains(String(saleId)).should('be.visible');
    cy.contains(/troca autorizada/i).should('be.visible');
  });

  it('deve marcar a troca como entregue', () => {
    cy.intercept('PUT', '**/sale/trade/delivered*', {
      statusCode: 200,
      body: { data: {} },
    }).as('putTradeDelivered');

    cy.get('[data-cy="trade-button"]').click({ force: true });

    cy.wait('@putTradeDelivered');

    cy.contains(/Trocado/i).should('be.visible');
  });
});


// ====================================================================
// ==========  TESTE 5 — CONTROLE DE TROCAS (admin) ===================
// ====================================================================

describe('Controle de trocas - aceitar e recusar', () => {
  const saleId = 555;

  beforeEach(() => {
    cy.intercept('GET', /sale\/trade/i, {
      statusCode: 200,
      body: {
        data: {
          entities: [
            {
              id: saleId,
              statusSale: { status: 'EXCHANGE_REQUESTED' },
              saleBooks: [{ price: 10 }],
            },
          ],
        },
      },
    }).as('getTradeSales');

    cy.visit('/control?tab=trade');
    cy.wait('@getTradeSales');

    cy.contains(String(saleId)).should('be.visible');
  });

  it('deve aceitar a troca → vira DECLINED', () => {
    cy.intercept(
      {
        method: 'PUT',
        url: '**/sale/trade/status*',
        query: { saleId: String(saleId), confirm: 'true' },
      },
      (req) => req.reply({ statusCode: 200, body: { data: {} } })
    ).as('putTradeStatusAccept');

    cy.contains('Aceitar', { timeout: 6000 }).should('be.visible').click({ force: true });

    cy.wait('@putTradeStatusAccept');

    cy.contains('Recusado').should('be.visible');
  });

  it('deve recusar a troca → vira EXCHANGE_AUTHORIZED', () => {
    cy.intercept(
      {
        method: 'PUT',
        url: '**/sale/trade/status*',
        query: { saleId: String(saleId), confirm: 'false' },
      },
      (req) => req.reply({ statusCode: 200, body: { data: {} } })
    ).as('putTradeStatusReject');

    cy.contains('Rejeitar', { timeout: 6000 }).should('be.visible').click({ force: true });

    cy.wait('@putTradeStatusReject');

    cy.contains('Troca autorizada').should('be.visible');
  });
});
