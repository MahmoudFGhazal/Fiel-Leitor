describe('Books - Delete', () => {
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

  it('deve excluir um livro com sucesso', () => {
    // 1) Intercept antes do clique e cobrindo query (?bookId=1)
    cy.intercept(
      { method: 'PUT', url: '**/book/delete*' },
      (req) => {
        // debug opcional
        console.log('DELETE URL:', req.url, 'QS:', req.query);
        // você pode validar:
        // expect(req.query.bookId).to.eq('1');

        req.reply({
          statusCode: 200,
          body: { data: { entity: { id: 1 } } },
        });
      }
    ).as('deleteBook');

    // 2) Captura nº de linhas
    cy.get('tbody tr').then(($rowsBefore) => {
      const countBefore = $rowsBefore.length;

      // 3) Dispara o clique
      cy.get('[data-cy="delete-button"]').first().click();

      // 4) Espera a requisição
      cy.wait('@deleteBook', { timeout: 10000 });

      // 5) Verifica que removeu 1 linha
      cy.get('tbody tr').should('have.length', countBefore - 1);
      // ajuste se sua mensagem de sucesso for diferente
      // cy.get('@alert').should('have.been.calledWith', 'Exclusão concluído com sucesso!');
    });
  });

});
