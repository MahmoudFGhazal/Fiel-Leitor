// cypress/e2e/trade_admin.cy.ts

describe('Controle de trocas - delivered', () => {
  const saleId = 555;

  beforeEach(() => {
    // já vem autorizado pra troca
    cy.intercept('GET', /sale\/trade/i, {
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

    cy.contains('Pedidos de Troca').should('be.visible');
    cy.contains(String(saleId)).should('be.visible');
    cy.contains('Entregue').should('be.visible');
  });

  it('deve marcar a troca como entregue', () => {
    // o teu componente manda params -> vira querystring
    cy.intercept(
      {
        method: 'PUT',
        url: '**/sale/trade/delivered*',
        query: {
          saleId: String(saleId),
        },
      },
      (req) => {
        req.reply({
          statusCode: 200,
          body: {
            data: {},
          },
        });
      }
    ).as('putTradeDelivered');

    cy.contains('Entregue').click();

    cy.wait('@putTradeDelivered');

    // o componente faz: setStatus('EXCHANGED');
    cy.contains('EXCHANGED').should('be.visible');
  });
});
