// cypress/e2e/sale/new_sale.cy.ts

describe('Fluxo completo de compra', () => {
  const userId = 1;
  const saleId = 9001;
  const addressId = 1;
  const cardId = 7;

  const itemsParam = encodeURIComponent(JSON.stringify({
    items: [{ bookId: 1, quantity: 2 }],
    fromCart: false,
  }));

  beforeEach(() => {

    // ------------------------------------------------------
    // GARANTE USUÁRIO LOGADO ANTES DO LOAD
    // ------------------------------------------------------
    cy.visit(`/sale?items=${itemsParam}`, {
      onBeforeLoad(win) {
        win.localStorage.setItem('currentUser', String(userId));
      }
    });

    // ------------------------------------------------------
    // MOCK: Criação da venda
    // ------------------------------------------------------
    cy.intercept('POST', '**/sale', {
      statusCode: 200,
      body: { data: { entity: { id: saleId } } }
    }).as('createSale');

    // ------------------------------------------------------
    // MOCK: Lista dos livros
    // ------------------------------------------------------
    cy.intercept('GET', '**/book/list*', {
      statusCode: 200,
      body: {
        data: {
          entities: [{ id: 1, name: 'Livro A', price: 30 }]
        }
      }
    }).as('getBooks');

    // ------------------------------------------------------
    // MOCK: Endereços
    // ------------------------------------------------------
    cy.intercept('GET', '**/address/user*', {
      statusCode: 200,
      body: {
        data: {
          entities: [
            { id: 1, nickname: 'Casa', principal: true },
            { id: 2, nickname: 'Trabalho', principal: false }
          ]
        }
      }
    }).as('getAddresses');

    // ------------------------------------------------------
    // MOCK: Cartões
    // ------------------------------------------------------
    cy.intercept('GET', '**/card/user*', {
      statusCode: 200,
      body: {
        data: {
          entities: [
            { id: cardId, last4: '1234', principal: true }
          ]
        }
      }
    }).as('getCards');

    // ------------------------------------------------------
    // MOCK: Trader coupon (permissivo)
    // ------------------------------------------------------
    cy.intercept('GET', '**/traderCoupon/check*', {
      statusCode: 200,
      body: { data: { entity: null } }
    }).as('checkTrader');

    // ------------------------------------------------------
    // MOCK: Promotional coupon
    // ------------------------------------------------------
    cy.intercept('GET', '**/promotionalCoupon/check*', {
      statusCode: 200,
      body: {
        data: {
          entity: { id: 10, code: 'PROMO10', value: 10, used: false }
        }
      }
    }).as('checkPromo');

    // ------------------------------------------------------
    // MOCK: Pagamento final
    // ------------------------------------------------------
    cy.intercept('PUT', '**/sale/payment', {
      statusCode: 200,
      body: {
        data: { entity: { id: saleId } },
        message: null
      }
    }).as('pay');

    // Esperar carregamento inicial
    cy.wait('@createSale');
    cy.wait('@getBooks');
    cy.wait('@getAddresses');
    cy.wait('@getCards');
  });

  it('Deve finalizar toda compra com sucesso', () => {

    // 1️⃣ Seleciona endereço
    cy.contains('Escolher outro').click();
    cy.contains('button', 'Casa').click();

    // 2️⃣ Seleciona cartão
    cy.contains('Selecionar Cartões').click();
    cy.get('input[type="number"]').clear().type('100');
    cy.contains('button', 'Fechar').click();

    // 3️⃣ Aplicar cupom
    cy.get('[data-cy="coupon-text"]').type('PROMO10{enter}');
    cy.wait('@checkTrader');
    cy.wait('@checkPromo');

    // 4️⃣ Finalizar compra
    cy.window().then(win => cy.stub(win, 'alert').as('alert'));

    cy.get('[data-cy="finalize-purchase-button"]').click();

    // Aguarda o PUT
    cy.wait('@pay');

    // ✔ FINAL: confirmar redirecionamento para HOME
    cy.url().should('match', /\/$/);

    // ✔ alerta exibido
    cy.get('@alert').should('have.been.calledWith', 'Pedido Enviado com Sucesso');
  });

});
