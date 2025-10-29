describe('Books - Create', () => {
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

  it('deve criar um novo livro com sucesso', () => {
    cy.intercept('POST', '**/book', (req) => {
      req.reply({
        statusCode: 200,
        body: {
          data: {
            entity: {
              id: 3,
              name: 'Design Patterns',
              category: null,
              price: 199.9,
              stock: 10,
              active: true,
            },
          },
        },
      });
    }).as('createBook');

    cy.get('[data-cy="create-button"]').click();
    cy.get('form').should('exist');

    cy.get(nameInputSelector).first().clear().type('Design Patterns');

    cy.get('[data-cy="book-form"]').should('exist').within(() => {
      cy.get('input[type="number"]').eq(0).clear().type('199.9');
      cy.get(stockInputSelector).clear().type('10');
      cy.get('[data-cy="save-button"]').click();
    });

    cy.wait('@createBook');
    cy.contains('table td', 'Design Patterns').should('be.visible');
    cy.contains('table td', '10').should('be.visible');
  });
});
