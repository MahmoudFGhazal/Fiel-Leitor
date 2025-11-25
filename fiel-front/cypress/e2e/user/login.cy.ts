// cypress/e2e/login.cy.ts

describe("LoginComponent", () => {

    beforeEach(() => {
        cy.visit("/login");
    });

    it("deve logar com sucesso", () => {

        // mocka a resposta da API /user/login
        cy.intercept("POST", "**/user/login", {
            statusCode: 200,
            body: {
                data: {
                    entity: {
                        id: 123,
                        email: "renata@example.com",
                        active: true,
                    }
                }
            }
        }).as("loginRequest");

        // mock do alert()
        cy.window().then((win) => {
            cy.stub(win, "alert").as("alerta");
        });

        cy.get('[data-cy=email-text]').type("renata@example.com");
        cy.get('[data-cy=password-text]').type("$2a$10$hashFake1");
        cy.get('[data-cy=refresh-button]').click();  // marcar checkbox
        cy.get('[data-cy=submit-button]').click();

        cy.wait("@loginRequest");

        cy.get("@alerta").should("have.been.calledWith", "Login efetuado com sucesso!");

        // garante que foi redirecionado pra home
        cy.url().should('match', /\/$/);
    });

    it("deve exibir erro com credenciais inválidas", () => {
        // mock da API retornando erro
        cy.intercept("POST", "**/user/login", {
            statusCode: 401,
            body: { message: "Email ou senha incorretos." }
        }).as("loginErro");

        // mock do alert()
        cy.window().then((win) => {
            cy.stub(win, "alert").as("alerta");
        });

        cy.get('[data-cy=email-text]').type("invalido@exemplo.com");
        cy.get('[data-cy=password-text]').type("senhaerrada");
        cy.get('[data-cy=submit-button]').click();

        cy.wait("@loginErro");

        cy.get("@alerta").should("have.been.calledWith", "Email ou senha incorretos.");
    });

});
