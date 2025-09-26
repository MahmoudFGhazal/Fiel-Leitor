describe("LoginComponent", () => {
    beforeEach(() => {
        cy.visit("/login"); // ajuste conforme sua rota real
    });

    it("deve logar com sucesso", () => {
        cy.window().then((win) => {
            cy.stub(win, 'alert').as('alerta');
        });

        cy.get('[data-cy=email-text]').type("teste2@exemplo.com");
        cy.get('[data-cy=password-text]').type("123@Pass");
        cy.get('[data-cy=refresh-button]').click();
        cy.get('[data-cy=submit-button]').click();

        cy.get('@alerta').should('have.been.calledWith', 'Login efetuado com sucesso!');
    });

    it("deve exibir erro com credenciais inválidas", () => {
        cy.get('[data-cy=email-text]').type("invalido@exemplo.com");
        cy.get('[data-cy=password-text]').type("senhaerrada");
        cy.get('[data-cy=submit-button]').click();

        cy.contains("Email ou senha incorretos.", { timeout: 5000 }).should("exist");
    });
});