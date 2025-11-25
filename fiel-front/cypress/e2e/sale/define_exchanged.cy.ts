describe('Controle de trocas - delivered', () => {
  const saleId = 555;

  beforeEach(() => {
    // Mock da listagem inicial
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

    cy.contains('Pedidos de Troca', { matchCase: false }).should('be.visible');
    cy.contains(String(saleId)).should('be.visible');
    cy.contains(/troca autorizada/i).should('be.visible');
  });

  it('deve marcar a troca como entregue', () => {
    // Intercept do PUT da troca
    cy.intercept('PUT', '**/sale/trade/delivered*', {
      statusCode: 200,
      body: { data: {} },
    }).as('putTradeDelivered');

    // Usa o botão com data-cy
    cy.get('[data-cy="trade-button"]').click({ force: true });

    // Aguarda o PUT ser chamado
    cy.wait('@putTradeDelivered').its('request.url').should('include', '/sale/trade/delivered');

    // Confirma que mudou o status no UI
    cy.contains(/Trocado/i).should('be.visible');
  });
});
