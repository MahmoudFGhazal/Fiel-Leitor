describe("SignInComponent", () => {
    beforeEach(() => {
        cy.visit("/sign"); // ajuste o path conforme sua rota real
    });

    it("deve completar o cadastro com sucesso", () => {
        // Step 1 - Usuário
        cy.get('[data-cy=email-text]').type("teste2@exemplo.com");
        cy.get('[data-cy=password-text]').first().type("123@Pass");
        cy.get('[data-cy=newPassword-text]').type("123@Pass");

        cy.get('[data-cy=next-button]').should('be.visible').click();




        // Step 2 - Perfil
        cy.get('[data-cy=name-text]').type("Usuário Teste");
        cy.get('[data-cy=gender-select]').select(1);
        cy.get('[data-cy=birth-date]').type("2000-01-01");
        cy.get('[data-cy=cpf-text]').type("79666000172");
        cy.get('[data-cy=phone-text]').type("11999999999");

        cy.get('[data-cy=next-button]').should('be.visible').click();




        // Step 3 - Endereço
        cy.get('[data-cy=nickname-text]').type("Casa Teste");
        cy.get('[data-cy=cep-text]').type("12345678");
        cy.get('[data-cy=residence-select]').select(1);
        cy.get('[data-cy=street-select]').select(1);
        cy.get('[data-cy=street-text]').type("Rua das Flores");
        cy.get('[data-cy=number-text]').type("123");
        cy.get('[data-cy=neighbor-text]').type("Centro");
        cy.get('[data-cy=city-text]').type("São Paulo");
        cy.get('[data-cy=state-text]').type("SP");
        cy.get('[data-cy=country-text]').type("Brasil");
        cy.get('[data-cy=complement-text]').type("Apto 10");

        // Intercepta a chamada da API
        cy.intercept("POST", "/api/user", {
            statusCode: 200,
            body: { data: { entity: { id: 1, email: "teste@exemplo.com" } } }
            }).as("createUser");

            cy.intercept("POST", "/api/address", {
            statusCode: 200,
            body: { message: "Endereço salvo com sucesso" }
        }).as("createAddress");

        cy.contains("Finalizar").click();

        // Verifica chamadas
        cy.wait("@createUser").its("request.body.data").should("have.property", "email", "teste@exemplo.com");
        cy.wait("@createAddress");

        // Verifica redirecionamento
        cy.url().should("include", "/login");
    });
});