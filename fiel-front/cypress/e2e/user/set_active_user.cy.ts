describe('CRUD de Usuários', () => {
    beforeEach(() => {
        // Intercepta requisição de todos os usuários
        cy.intercept('GET', '**/user/all').as('getUsers');

        cy.visit('/crud/client');

        cy.wait('@getUsers');
    });

    it('Deve ativar um usuário inativo', () => {
        cy.get('[data-cy="active-button"]').first().click();

        cy.on('window:alert', (text) => {
            expect(text).to.contains('Usuário ativado com sucesso');
        });
    });

    it('Deve desativar um usuário ativo', () => {
        cy.get('[data-cy="desactive-button"]').first().click();

        cy.on('window:alert', (text) => {
            expect(text).to.contains('Usuário inativado com sucesso');
        });
    });
});
