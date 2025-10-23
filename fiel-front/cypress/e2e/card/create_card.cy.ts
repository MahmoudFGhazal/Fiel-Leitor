describe('Gestão de Cartões', () => {
    beforeEach(() => {
        cy.window().then((win) => {
            win.localStorage.setItem('currentUser', '20');
        });

        // lista inicial vazia
        cy.intercept(
            { method: 'GET', url: '**/card/user*' },
            { statusCode: 200, body: { data: { entities: [] } } }
        ).as('getCards');

        // cria cartão
        cy.intercept(
            { method: 'POST', url: '**/card' },
            (req) => {
                // você pode validar o payload aqui se quiser
                // expect(req.body?.data?.user).to.eq(20)
                req.reply({
                    statusCode: 200,
                    body: {
                        data: {
                            entity: {
                                id: 100,
                                user: { id: 20 },
                                last4: '1234',
                                bin: '4111',
                                holder: 'RENATA TESTE',
                                principal: false,
                                expMonth: '12',
                                expYear: '2030',
                            },
                        },
                    },
                });
            }
        ).as('createCard');

        cy.visit('/config?tab=cards');
        cy.wait('@getCards');
    });

    it('Deve criar um novo cartão com sucesso', () => {
        cy.get('[data-cy="create-card-button"]').click();

        // preenche modal
        cy.get('[data-cy="card-modal"]').should('be.visible');

        cy.get('[data-cy="holder-text"]').type('RENATA TESTE');
        cy.get('[data-cy="pan-text"]').type('4111111111111234'); // será mascarado no display
        cy.get('[data-cy="exp-text"]').type('1230'); // 12/30

        cy.get('[data-cy="save-card-button"]').click();

        cy.wait('@createCard');

        // modal fecha e linha aparece com **** 1234
        cy.get('[data-cy="card-modal"]').should('not.exist');
        cy.contains('**** **** **** 1234').should('be.visible');
    });
});