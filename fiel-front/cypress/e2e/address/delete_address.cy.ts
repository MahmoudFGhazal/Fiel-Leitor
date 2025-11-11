describe('Gestão de Endereços', () => {
  beforeEach(() => {
    cy.window().then((win) => {
      win.localStorage.setItem('currentUser', '1');
    });

    // Mock da listagem de endereços
    cy.intercept(
      { method: 'GET', url: '**/address/user*' },
      {
        statusCode: 200,
        body: {
          data: {
            entities: [
              {
                id: 1,
                user: { id: 1 },
                nickname: 'Casa',
                street: 'Rua das Flores',
                number: '123',
                city: 'São Paulo',
                state: 'SP',
                zip: '01000-000',
                principal: true,
              },
            ],
          },
        },
      }
    ).as('getAddresses');

    // Mock da exclusão de endereço
    cy.intercept(
      { method: 'PUT', url: '**/address/delete*' },
      (req) => {
        // Validação opcional do parâmetro
        const url = new URL(req.url);
        expect(url.searchParams.get('addressId')).to.eq('1');

        req.reply({
          statusCode: 200,
          body: { data: { entity: { id: 1 } }, message: null },
        });
      }
    ).as('deleteAddress');

    cy.visit('/config?tab=addresses');
    cy.wait('@getAddresses');
  });

  it('Deve apagar um endereço existente com sucesso', () => {
    cy.on('window:confirm', () => true);

    // Garante que o endereço mockado apareceu
    cy.contains('Casa').should('be.visible');

    // Clica no botão de apagar
    cy.get('[data-cy="delete-button"]').first().click();

    // Espera a requisição mockada
    cy.wait('@deleteAddress');

    // Confirma o alert de sucesso
    cy.on('window:alert', (text) => {
      expect(text).to.contains('Endereço removido com sucesso');
    });

    // Garante que o endereço sumiu da tela
    cy.contains('Casa').should('not.exist');
  });
});
