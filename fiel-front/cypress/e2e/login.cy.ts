describe("LoginComponent", () => {
    beforeEach(() => {
        cy.visit("/login"); // ajuste conforme sua rota real
    });

    it("deve logar com sucesso", () => {
        cy.get('[data-cy=email-text]').type("teste2@exemplo.com");
        cy.get('[data-cy=password-text]').type("123@Pass");
        cy.get('[data-cy=submit-button]').click();

        cy.contains("Login efetuado com sucesso!", { timeout: 5000 }).should("exist");
    });

    it("deve exibir erro com credenciais inválidas", () => {
        cy.get('[data-cy=email-text]').type("invalido@exemplo.com");
        cy.get('[data-cy=password-text]').type("senhaerrada");
        cy.get('[data-cy=submit-button]').click();

        cy.contains("Email ou senha incorretos.", { timeout: 5000 }).should("exist");
    });
});