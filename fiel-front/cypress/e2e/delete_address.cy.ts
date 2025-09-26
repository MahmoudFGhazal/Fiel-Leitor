describe('Gestão de Endereços', () => {
    beforeEach(() => {
        // Define currentUser
        cy.window().then(win => {
            win.localStorage.setItem('currentUser', '20');
        });

        // Intercepta requisição de endereços
        cy.intercept('GET', '**/address/user*').as('getAddresses');

        cy.visit('/config?tab=addresses');

        cy.wait('@getAddresses');
    });

    it('Deve apagar um endereço existente com sucesso', () => {
        // Configura o alert do delete
        cy.on('window:confirm', () => true);
        cy.on('window:alert', (text) => {
            expect(text).to.contains('Endereço removido com sucesso');
        });

        // Clica no primeiro botão de deletar
        cy.get('[data-cy="delete-button"]').first().click();
    });
});
