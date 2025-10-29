describe('Books - Toggle Active', () => {
  beforeEach(() => {
    cy.window().then((win) => {
      cy.stub(win, 'alert').as('alert');
      win.localStorage.setItem('currentUser', '20');
    });

    cy.intercept('GET', '**/book/all*', {
      statusCode: 200,
      body: {
        data: {
          entities: [
            { id: 1, name: 'Clean Code', category: { category: 'TECH' }, price: 120.5, stock: 7, active: true },
            { id: 2, name: 'Dom Casmurro', category: { category: 'ROMANCE' }, price: 45, stock: 3, active: false },
          ],
        },
      },
    }).as('getBooks');

    cy.visit('/crud/book');
    cy.wait('@getBooks');
  });

  it('deve alternar ativo/desativado com sucesso', () => {
    cy.intercept(
      { method: 'PUT', url: '**/book/active*' }, // note o * no final
      (req) => {
        req.reply({
          statusCode: 200,
          body: {
            data: { entity: { id: 2, active: true } },
          },
        });
      }
    ).as('toggleActive');

    cy.contains('tr', 'Dom Casmurro').within(() => {
      cy.get('[data-cy="active-button"]').click();
    });

    cy.wait('@toggleActive');

    cy.contains('tr', 'Dom Casmurro').within(() => {
      cy.get('[data-cy="active-button"]').should('contain.text', 'Desativar');
    });
  });
});
