// cypress/e2e/trade_pedido.cy.ts

describe('Pedido - solicitar troca', () => {
  const userId = 20;
  const saleId = 9001;
  const createdAtISO = '2025-10-21T18:30:00.000Z';

  beforeEach(() => {
    // simula login no localStorage (teu ListSales usa o contexto, mas isso costuma bastar no teste)
    cy.window().then((win) => {
      win.localStorage.setItem('currentUser', String(userId));
    });

    // mock da lista de pedidos do usuário
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
                statusSale: { status: 'DELIVERED' }, // libera o botão
              },
            ],
          },
        },
      });
    }).as('getSalesByUser');
  });

  it('deve pedir troca e atualizar o status para EXCHANGE_REQUESTED', () => {
    // IMPORTANTE: não mandar "message" senão o componente dá return antes do setState
    cy.intercept(
      {
        method: 'PUT',
        url: '**/sale/trade/request*',
        query: { saleId: String(saleId) },
      },
      (req) => {
        req.reply({
          statusCode: 200,
          body: {
            data: {}, // sem message!
          },
        });
      }
    ).as('putTradeRequest');

    cy.visit('/historical');
    cy.wait('@getSalesByUser');

    // botão com data-cy que você colocou
    cy.get('[data-cy="request-button"]').should('be.visible').click();

    cy.wait('@putTradeRequest');

    // agora o componente deve ter feito setCurrentStatus('EXCHANGE_REQUESTED')
    // o <p className={styles.statusLine}> é o pai, então dá pra checar nele
    cy.get('p.orderCard_statusLine__eYM2_')
      .should('contain.text', 'Troca solicitada');
  });
});
