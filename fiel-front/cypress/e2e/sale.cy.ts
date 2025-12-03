describe('Fluxo completo de compra (E2E)', () => {
  beforeEach(() => {
    cy.window().then(win => {
      win.localStorage.setItem('currentUser', '1'); // mocka usuário logado
    });

    // 🧠 Mock do livro — só intercepta JSON, deixa o HTML passar
    cy.intercept({ method: 'GET', url: /\/book(\?.*)?$/ }, (req) => {
      if (req.headers.accept?.includes('text/html')) return req.continue();
      req.reply({
        statusCode: 200,
        body: {
          data: {
            entity: {
              id: 1,
              name: 'O Senhor dos Anéis',
              price: 59.9,
              author: 'Tolkien',
            },
          },
        },
      });
    }).as('getBook');

    // 🧠 Mock do carrinho
    cy.intercept('POST', '**/cart/add', {
      statusCode: 200,
      body: { data: { entity: { id: 99 } }, message: null },
    }).as('addCart');

    // 🧠 Mock do carrinho quando abrir
    cy.intercept('GET', '**/cart*', {
      statusCode: 200,
      body: {
        data: {
          entities: [
            {
              id: 50,
              quantity: 1,
              book: { id: 1, name: 'O Senhor dos Anéis', price: 59.9 },
            },
          ],
        },
      },
    }).as('getCart');

    // 🧠 Mock da criação da venda
    cy.intercept('POST', '**/sale', {
      statusCode: 200,
      body: { data: { entity: { id: 123 } }, message: null },
    }).as('createSale');

    // 🧠 Mock da lista de livros na venda
    cy.intercept('GET', '**/book/list*', {
      statusCode: 200,
      body: {
        data: {
          entities: [
            { id: 1, name: 'O Senhor dos Anéis', price: 59.9 },
          ],
        },
      },
    }).as('getBookList');

    // 🧠 Mock dos endereços
    cy.intercept('GET', '**/address/user*', {
      statusCode: 200,
      body: {
        data: {
          entities: [
            { id: 1, nickname: 'Casa', principal: true },
            { id: 2, nickname: 'Trabalho', principal: false },
          ],
        },
      },
    }).as('getAddresses');

    // 🧠 Mock dos cartões
    cy.intercept('GET', '**/card/user*', {
      statusCode: 200,
      body: {
        data: {
          entities: [
            { id: 7, last4: '1234', principal: true, bin: '555555', holder: 'RENATA' },
          ],
        },
      },
    }).as('getCards');

    // 🧠 Mock do cupom promocional
    cy.intercept('GET', '**/promotionalCoupon/check*', {
      statusCode: 200,
      body: {
        data: {
          entity: { id: 10, code: 'PROMO10', value: 10, used: false },
        },
      },
    }).as('checkPromo');

    // 🧠 Mock da finalização do pagamento
    cy.intercept('PUT', '**/sale/payment', {
      statusCode: 200,
      body: { data: { entity: { id: 123 } }, message: null },
    }).as('finalizeSale');
  });

  it('Deve realizar uma compra completa com sucesso', () => {

    // 🔹 Acessa a página do livro
    cy.visit('/book?bookId=1');
    cy.wait('@getBook');

    // 🔹 Adiciona ao carrinho
    cy.window().then((win) => cy.stub(win, 'alert').as('alertAdd'));
    cy.get('[data-cy="add-button"]').click();
    cy.wait('@addCart');
    cy.get('@alertAdd').should('have.been.calledWith', 'Livro adicionado ao carrinho!');

    // 🔹 Abre o carrinho
    cy.get('[data-cy="open-cart-button"]').click();
    cy.wait('@getCart');

    // 🔹 Vai para a tela de compra
    cy.get('[data-cy="sale-cart-button"]').click();
    cy.wait('@createSale');

    // 🔹 Confere que está na tela de venda
    cy.url().should('include', '/sale');
    cy.wait('@getBookList');
    cy.wait('@getAddresses');
    cy.wait('@getCards');

    // ============================================================
    // 🔹 TROCA O ENDEREÇO
    // ============================================================
    cy.contains('Escolher outro').click();
    cy.contains('Trabalho').click();

    // ============================================================
    // 🔹 TROCA O CARTÃO E AJUSTA PERCENTUAL
    // ============================================================
    cy.contains('Selecionar Cartões').click();
    cy.get('input[type="checkbox"]').check();
    cy.get('input[type="number"]').clear().type('10');
    cy.contains('Fechar').click();

    // ============================================================
    // 🔹 FINALIZA A COMPRA (APENAS UMA VEZ)
    // ============================================================
    cy.window().then(win => cy.stub(win, 'alert').as('alertFinish'));
    cy.get('[data-cy="finalize-purchase-button"]').click();
    cy.wait('@finalizeSale');
    cy.get('@alertFinish').should('have.been.calledWith', 'Pedido Enviado com Sucesso');
  });
});
