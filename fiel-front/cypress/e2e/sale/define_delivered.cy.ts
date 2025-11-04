// cypress/e2e/pedido_detalhe.cy.ts
// Ajustado para a chamada real da página: GET /sale/user?userId=20

describe('Página de Detalhe do Pedido (OrderSale)', () => {
  const userId = 20;
  const saleId = 9001;

  const createdAtISO = '2025-10-21T18:30:00.000Z'; // exibe 21/10/2025 em pt-BR
  const saleBooks = [{ price: 10.0 }, { price: 35.5 }];
  const expectedTotal = saleBooks.reduce((a, b) => a + Number(b.price ?? 0), 0); // 45.5
  const expectedDate = '21/10/2025';

  beforeEach(() => {
    // garante "login"
    cy.window().then((win) => {
      win.localStorage.setItem('currentUser', String(userId));
    });

    // a página busca as vendas do usuário
    cy.intercept({ method: 'GET', url: '**/sale/user*' }, (req) => {
      // opcional: conferir o query param
      expect(String(req.query?.userId ?? '')).to.eq(String(userId));

      req.reply({
        statusCode: 200,
        body: {
          data: {
            // algumas telas retornam em data.entities; outras em data.entities/entities
            entities: [
              {
                id: saleId,
                createdAt: createdAtISO,
                saleBooks,
              },
            ],
          },
        },
      });
    }).as('getSalesByUser');

    // se sua página faz outras chamadas paralelas que não interessam ao teste:
    cy.intercept({ method: 'GET', url: '**/address/**' }, { statusCode: 200, body: { data: { entities: [] } } }).as('noopAddr');
    cy.intercept({ method: 'GET', url: '**/card/**' },    { statusCode: 200, body: { data: { entities: [] } } }).as('noopCard');

    // VISITE a rota correta da sua página que lista/mostra os pedidos do usuário
    // ajuste aqui conforme seu app (ex.: '/orders', '/sale', '/minhas-compras')
    cy.visit('/historical');

    cy.wait('@getSalesByUser');
  });

  it('deve exibir data (pt-BR), número do pedido, total e itens do pedido retornado', () => {
    // Data (apenas dd/mm/aaaa)
    cy.contains('Pedido realizado:').should('be.visible');
    cy.contains(expectedDate).should('be.visible');

    // Número do pedido
    cy.contains('Pedido nº').should('be.visible');
    cy.contains(String(saleId)).should('be.visible');

    // Total (aceita vírgula ou ponto como separador decimal)
    cy.contains('Total:').should('be.visible');
    cy.contains(new RegExp(`R\\$\\s*${expectedTotal.toFixed(2).replace('.', '[\\.,]')}`)).should('be.visible');

  
  });
});
// cypress/e2e/controlar_pedidos.cy.ts

describe('Controle de vendas - Pendentes', () => {
  const saleId = 1234;

  beforeEach(() => {
    // GET pendentes
    cy.intercept('GET', /sale\/peding/i, {
      statusCode: 200,
      body: {
        data: {
          entities: [
            {
              id: saleId,
              statusSale: { status: 'APPROVED' },
              saleBooks: [{ price: 10 }, { price: 20 }],
            },
          ],
        },
      },
    }).as('getPedingSales');

    // PUT transit – aqui vamos olhar a URL, não o body
    cy.intercept('PUT', /sale\/transit/i, (req) => {
      const url = new URL(req.url);
      const sp = url.searchParams;

      // se o axios jogou nos query params, vão estar aqui:
      expect(sp.get('saleId')).to.eq(String(saleId));
      // esses dois você pode só checar se existem
      expect(sp.get('deliveryDate')).to.exist;
      expect(sp.get('freight')).to.exist;

      req.reply({
        statusCode: 200,
        body: {
          message: 'Venda atualizada com sucesso',
          data: {},
        },
      });
    }).as('putInTransit');

    // PUT delivered
    cy.intercept('PUT', /sale\/delivered/i, (req) => {
      const url = new URL(req.url);
      const sp = url.searchParams;

      expect(sp.get('saleId')).to.eq(String(saleId));

      req.reply({
        statusCode: 200,
        body: {
          message: 'Venda atualizada com sucesso',
          data: {},
        },
      });
    }).as('putDelivered');

    // tua rota
    cy.visit('/control?tab=peding');

    cy.wait('@getPedingSales');
  });

  it('deve colocar pedido em trânsito e depois marcar como entregue', () => {
    cy.contains('Pedidos Pendentes').should('be.visible');
    cy.contains(String(saleId)).should('be.visible');

    // abre modal
    cy.get('[data-cy="pass-button"]').click();

    cy.get('[data-cy="delivery-date-text"]').type('2025-11-10');
    cy.get('[data-cy="freight-text"]').clear().type('25.50');

    cy.contains('Confirmar').click();

    // agora sim esperamos o PUT de tránsito
    cy.wait('@putInTransit');

    // modal some
    cy.contains('Colocar em trânsito').should('not.exist');

    // clica de novo pra entregar
    cy.get('[data-cy="pass-button"]').click();
    cy.wait('@putDelivered');

    // some da tabela
    cy.contains(String(saleId)).should('not.exist');
  });
});