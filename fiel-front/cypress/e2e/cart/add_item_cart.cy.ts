describe('Carrinho - adicionar item a partir da página do livro', () => {
    const BOOK_ID = 1;

    beforeEach(() => {
        cy.visit('/', {
            onBeforeLoad(win) {
                win.localStorage.setItem('currentUser', '1');
            },
        });

        cy.intercept(
            { method: 'GET', url: /\/book(\?.*)?$/ },
            (req) => {
                if (req.headers.accept?.includes('text/html')) return req.continue();

                const url = new URL(req.url);
                expect(url.searchParams.get('bookId')).to.eq(String(BOOK_ID));

                req.reply({
                    statusCode: 200,
                    body: { data: { entity: { id: BOOK_ID, name: 'Livro Cypress — Teste', price: 99.9 } } },
                });
            }
        ).as('getBook');

        cy.intercept('POST', '/cart/add', { body: { data: { ok: true } } }).as('addToCart');
    });

    it('adiciona o livro ao carrinho', () => {
        cy.visit(`/book?bookId=${BOOK_ID}`);

        cy.wait('@getBook', { timeout: 15000 });

        cy.contains('h1', 'Livro Cypress — Teste').should('be.visible');
        cy.contains('Preço:').should('contain', 'R$');

        // stub do alert
        cy.window().then((win) => cy.stub(win, 'alert').as('alert'));

        cy.get('[data-cy="add-button"]').click();

        cy.wait('@addToCart');
        cy.get('@alert').should('have.been.calledWith', 'Livro adicionado ao carrinho!');
    });
});