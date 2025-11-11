describe('Gestão de Endereços', () => {
    beforeEach(() => {
        cy.window().then(win => {
            win.localStorage.setItem('currentUser', '1');
        });

        cy.intercept('GET', '**/address/user*').as('getAddresses');
        cy.visit('/config?tab=addresses');
        cy.wait('@getAddresses');
    });

    it('Deve editar um endereço existente com sucesso', () => {
        // Garante que tem pelo menos 1 endereço carregado
        cy.get('[data-cy="view-edit-button"]', { timeout: 10000 }).first().click();
        cy.get('[data-cy="edit-button"]').click();

        // Atualiza alguns campos
        cy.get('[data-cy="street-text"]').clear().type('Rua Editada');
        cy.get('[data-cy="number-text"]').clear().type('222');
        cy.get('[data-cy="neighbor-text"]').clear().type('Bairro Novo');

        cy.get('[data-cy="save-button"]').click();

        // Valida o toast
        cy.on('window:alert', (text) => {
            expect(text).to.contains('Endereço Atualizado com Sucesso');
        });
    });
});
