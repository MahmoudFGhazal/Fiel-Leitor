describe('Perfil do Usuário', () => {
    beforeEach(() => {
        // Define currentUser
        cy.window().then(win => {
            win.localStorage.setItem('currentUser', '1');
        });

        cy.intercept('GET', '**/user*').as('getUser');

        cy.visit('/config?tab=profile');

        cy.wait('@getUser');
    });

    it('Deve editar os dados do usuário com sucesso', () => {
        // Clica no botão de editar
        cy.get('[data-cy="edit-button"]').click();

        // Preenche novos valores
        cy.get('[data-cy="name-text"]').clear().type('Nome Teste');
        cy.get('[data-cy="gender-select"]').select('1'); // ou outro valor válido
        cy.get('[data-cy="phonuNumber-text"]').clear().type('11999999999');
        cy.get('[data-cy="birth-date"]').clear().type('1990-01-01');

        // Clica em salvar
        cy.get('[data-cy="save-button"]').click();

        // Valida o alert de sucesso
        cy.on('window:alert', (text) => {
            expect(text).to.contains('Dados atualizados com sucesso!');
        });
    });
});
