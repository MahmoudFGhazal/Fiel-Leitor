// cypress/e2e/trade_admin.cy.ts

describe('Controle de trocas - aceitar e recusar', () => {
  const saleId = 555;

  beforeEach(() => {
    // GET da aba de trocas
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

    // deve mostrar a tabela e o id
    cy.contains('Pedidos de Troca').should('be.visible');
    cy.contains(String(saleId)).should('be.visible');
  });

  it('deve aceitar a solicitação de troca (fluxo atual do componente → vira DECLINED)', () => {
    // intercepta o PUT de status com confirm=true
    cy.intercept(
      {
        method: 'PUT',
        url: '**/sale/trade/status*',
        query: {
          saleId: String(saleId),
          confirm: 'true',
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
    ).as('putTradeStatusAccept');

    // no status EXCHANGE_REQUESTED aparecem os dois botões
    cy.contains('Aceitar').should('be.visible').click();

    cy.wait('@putTradeStatusAccept');

    // o componente faz: setStatus('DECLINED');
    cy.contains('DECLINED').should('be.visible');
  });

  it('deve recusar a solicitação de troca (fluxo atual do componente → vira EXCHANGE_AUTHORIZED)', () => {
    // precisamos recarregar o estado inicial porque o beforeEach já mudou a página
    // mas o intercept inicial já está feito no beforeEach
    // intercepta o PUT de status com confirm=false
    cy.intercept(
      {
        method: 'PUT',
        url: '**/sale/trade/status*',
        query: {
          saleId: String(saleId),
          confirm: 'false',
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
    ).as('putTradeStatusReject');

    cy.contains('Rejeitar').should('be.visible').click();

    cy.wait('@putTradeStatusReject');

    // o componente faz: setStatus('EXCHANGE_AUTHORIZED');
    cy.contains('EXCHANGE_AUTHORIZED').should('be.visible');
  });
});
