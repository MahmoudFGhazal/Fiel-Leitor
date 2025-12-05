describe('Perfil do Usuário', () => {
    beforeEach(() => {
        cy.window().then(win => {
            win.localStorage.setItem('currentUser', '1');
        });

        cy.intercept('GET', '**/gender/all', {
            statusCode: 200,
            body: {
                data: {
                    entities: [
                        { id: 1, gender: "MALE" },
                        { id: 2, gender: "FEMALE" }
                    ]
                }
            }
        }).as('getGenders');

        cy.intercept('GET', '**/user*').as('getUser');

        cy.visit('/config?tab=profile');

        cy.wait('@getGenders');
        cy.wait('@getUser');
    });

    it('Deve editar os dados do usuário com sucesso', () => {
        cy.get('[data-cy="edit-button"]').click();

        cy.get('[data-cy="name-text"]').clear().type('Nome Teste');
        cy.get('[data-cy="gender-select"]').select('1');   // agora funciona
        cy.get('[data-cy="phonuNumber-text"]').clear().type('11999999999');
        cy.get('[data-cy="birth-date"]').clear().type('1990-01-01');

        cy.get('[data-cy="save-button"]').click();

        cy.on('window:alert', (text) => {
            expect(text).to.contains('Dados atualizados com sucesso!');
        });
    });
});
