describe('Gestão de Endereços', () => {
    beforeEach(() => {
        // Define currentUser como 20
        cy.window().then(win => {
            win.localStorage.setItem('currentUser', '20');
        });

        cy.visit('/config?tab=addresses'); 
    });

    it('Deve criar um novo endereço com sucesso', () => {
        // Printando currentUser
        cy.window().then(win => {
            const currentUser = win.localStorage.getItem('currentUser');
            cy.log('Current User:', currentUser); // aparece no log do Cypress
        });

        cy.get('[data-cy="create-button"]').click();

        cy.get('[data-cy="nickname-text"]').type('Casa Teste');
        cy.get('[data-cy="cep-text"]').type('12345678');

        cy.get('[data-cy="residence-select"]').should('exist').select('1');
        cy.get('[data-cy="street-select"]').should('exist').select('1');

        cy.get('[data-cy="street-text"]').type('Rua Cypress');
        cy.get('[data-cy="number-text"]').type('123');
        cy.get('[data-cy="neighbor-text"]').type('Centro');
        cy.get('[data-cy="city-text"]').type('São Paulo');
        cy.get('[data-cy="state-text"]').type('SP');
        cy.get('[data-cy="country-text"]').type('Brasil');
        cy.get('[data-cy="complement-text"]').type('Apto 101');

        cy.get('[data-cy="save-button"]').click();

        cy.on('window:alert', (text) => {
            expect(text).to.contains('Endereço Criado com Sucesso');
        });
    });
});
