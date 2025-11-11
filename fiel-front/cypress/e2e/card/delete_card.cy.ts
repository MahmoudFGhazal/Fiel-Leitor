describe('Gestão de Cartões', () => {
  beforeEach(() => {
    cy.window().then((win) => {
      win.localStorage.setItem('currentUser', '20');
    });

    // Mock da listagem
    cy.intercept(
      { method: 'GET', url: '**/card/user*' },
      {
        statusCode: 200,
        body: {
          data: {
            entities: [
              {
                id: 7,
                user: { id: 20 },
                last4: '4321',
                bin: '5555',
                holder: 'RENATA',
                principal: false,
                expMonth: '11',
                expYear: '2031',
              },
            ],
          },
        },
      }
    ).as('getCards');

    // Mock da exclusão (igual ao endereço)
    cy.intercept(
      { method: 'PUT', url: '**/card/delete*' },
      (req) => {
        const url = new URL(req.url);
        expect(url.searchParams.get('cardId')).to.eq('7');

        req.reply({
          statusCode: 200,
          body: { data: { entity: { id: 7 } }, message: null },
        });
      }
    ).as('deleteCard');

    cy.visit('/config?tab=cards');
    cy.wait('@getCards');
  });

  it('Deve apagar um cartão existente com sucesso', () => {
    cy.on('window:confirm', () => true);
    cy.contains('**** **** **** 4321').should('be.visible');

    cy.get('[data-cy="delete-card-button"]').first().click();

    cy.wait('@deleteCard');
    cy.on('window:alert', (text) => {
      expect(text).to.contains('Cartão removido com sucesso');
    });

    cy.contains('**** **** **** 4321').should('not.exist');
  });
});
