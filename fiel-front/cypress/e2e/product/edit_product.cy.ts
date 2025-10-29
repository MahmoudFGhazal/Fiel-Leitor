describe('Books - Edit', () => {
  const nameInputSelector =
    '[data-cy="name-text"] input, ' +
    '[data-cy="name-text"] textarea, ' +
    'input[data-cy="name-text"], ' +
    'textarea[data-cy="name-text"]';

  const stockInputSelector =
    '[data-cy="stock-text"] input, ' +
    '[data-cy="stock-text"] textarea, ' +
    'input[data-cy="stock-text"], ' +
    'textarea[data-cy="stock-text"]';

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
          ],
        },
      },
    }).as('getBooks');

    cy.intercept('GET', '**/category/**', { statusCode: 200, body: { data: { entities: [] } } }).as('getCategories');

    cy.visit('/crud/book');
    cy.wait('@getBooks');
  });

  it('deve editar um livro existente com sucesso', () => {
    cy.intercept('PUT', '**/book', (req) => {
      // opcional: validar req.body.data.id === 1
      req.reply({
        statusCode: 200,
        body: {
          data: {
            entity: {
              id: 1,
              name: 'Clean Code (2ª Ed.)',
              category: { category: 'TECH' },
              price: 130.0,
              stock: 9,
              active: true,
            },
          },
        },
      });
    }).as('editBook');

    cy.get('[data-cy="edit-button"]').first().click();
    cy.get(nameInputSelector).first().clear().type('Clean Code (2ª Ed.)');

    cy.get('[data-cy="book-form"]').should('exist').within(() => {
      cy.get('input[type="number"]').eq(0).clear().type('130.00'); // preço
      cy.get(stockInputSelector).clear().type('9');                // estoque
      cy.get('[data-cy="save-button"]').click();
    });

    cy.wait('@editBook');
    cy.contains('table td', 'Clean Code (2ª Ed.)').should('be.visible');
    cy.contains('table td', '9').should('be.visible');
  });
});
