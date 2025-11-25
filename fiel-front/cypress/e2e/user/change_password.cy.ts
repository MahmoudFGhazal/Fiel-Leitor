describe('Change Password', () => {
    beforeEach(() => {
        // Simula usuário logado
        cy.window().then(win => {
            win.localStorage.setItem('currentUser', '1');
        });

        cy.visit('/config?tab=password');
    });

    it('atualiza a senha com sucesso', () => {
        // Preenche os campos
        cy.get('[data-cy=current-password-text]').type('$2a$10$hashFake1');
        cy.get('[data-cy=new-password-text]').type('NovaSenha123!');
        cy.get('[data-cy=confirm-password-text]').type('NovaSenha123!');

        // Clica no botão
        cy.get('[data-cy=update-password-button]').click();

        // Verifica se o alert aparece
        cy.on('window:alert', (text) => {
            expect(text).to.contains('Senha atualizada com sucesso!');
        });
    });
});
