describe('Carrinho - Limpar Carrinho', () => {
    beforeEach(() => {
        cy.window().then((win) => {
            win.localStorage.setItem('currentUser', '1');
        });

        cy.visit('/'); // abre a home; o carrinho abre pelo ícone
    });

    it('Deve limpar todos os itens do carrinho com sucesso', () => {
        // abre o carrinho
        cy.get('[data-cy="open-cart-button"]').click();

        // itens carregados (ajuste os nomes conforme seu stub/seed)
        cy.contains('Seu Carrinho').should('be.visible');

        // aciona limpar
        cy.get('[data-cy="clear-button"]').should('not.be.disabled').click();

        // estado final esperado
        cy.contains('Seu carrinho está vazio.').should('be.visible');
        cy.get('[data-cy="clear-button"]').should('be.disabled');
    });
});