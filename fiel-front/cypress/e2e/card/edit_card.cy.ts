describe('Gestão de Cartões', () => {
    beforeEach(() => {
        cy.window().then((win) => {
            win.localStorage.setItem('currentUser', '20');
        });

        cy.intercept(
            { method: 'GET', url: '**/card/user*' },
            {
                statusCode: 200,
                body: {
                    data: {
                        entities: [
                            { id: 9, user: { id: 20 }, last4: '9000', bin: '4000', holder: 'RENATA', principal: false, expMonth: '07', expYear: '2032' },
                        ],
                    },
                },
            }
        ).as('getCards');

        cy.visit('/config?tab=cards');
        cy.wait('@getCards');
    });

    it('Deve abrir modal de visualização com campos bloqueados', () => {
        cy.get('[data-cy="view-card-button"]').first().click();
        cy.get('[data-cy="card-modal"]').should('be.visible');

        // Inputs desabilitados
        cy.get('[data-cy="pan-text"]').should('be.disabled');
        cy.get('[data-cy="exp-text"]').should('be.disabled');

        // Botão de salvar não deve existir em modo view
        cy.get('[data-cy="save-card-button"]').should('not.exist');

        cy.get('[data-cy="close-card-button"]').click();
        cy.get('[data-cy="card-modal"]').should('not.exist');
    });
});