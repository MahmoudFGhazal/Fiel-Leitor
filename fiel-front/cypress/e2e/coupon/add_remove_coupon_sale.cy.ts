describe('Gestão de Cupons', () => {
  beforeEach(() => {
    cy.window().then((win) => {
      win.localStorage.setItem('currentUser', '20');
    });

    // Evita 500 e alertas de "Usuário não encontrado" em outras partes da página
    cy.intercept({ method: 'GET', url: '**/address/user*' }, { statusCode: 200, body: { data: { entities: [] } } }).as('getAddresses');
    cy.intercept({ method: 'GET', url: '**/card/user*' },    { statusCode: 200, body: { data: { entities: [] } } }).as('getCards');

    // verifica cupom trader: usa o seu código real
    cy.intercept(
      { method: 'GET', url: '**/traderCoupon/check*' },
      (req) => {
        const code = String(req.query?.code ?? '');
        if (code === 'PCO000003') {
          req.reply({
            statusCode: 200,
            body: {
              data: {
                entity: {
                  id: 101,
                  code: 'PCO000003',
                  value: 10,     // ajuste o valor do desconto conforme sua regra
                  used: false,
                },
              },
            },
          });
        } else {
          // força o componente a tentar o promocional em seguida
          req.reply({ statusCode: 200, body: { data: { entity: null } } });
        }
      }
    ).as('checkTrader');

    // verifica cupom promocional (fallback caso não seja trader)
    cy.intercept(
      { method: 'GET', url: '**/promotionalCoupon/check*' },
      { statusCode: 200, body: { data: { entity: null } } }
    ).as('checkPromotional');

    cy.visit('/sale'); // <-- ajuste para a sua rota real
    cy.wait(['@getAddresses', '@getCards']); // garante que não haverá alert bloqueando a UI
  });

  it('Deve aplicar e remover um cupom trader com sucesso', () => {
    // seletor robusto: funciona se o data-cy estiver no wrapper OU no input
    const couponInput =
      '[data-cy="coupon-text"] input, ' +
      '[data-cy="coupon-text"] textarea, ' +
      'input[data-cy="coupon-text"], ' +
      'textarea[data-cy="coupon-text"]';

    // garante que o campo existe antes de digitar
    cy.get(couponInput).should('exist').first().type('PCO000003{enter}');

    // primeiro tenta trader
    cy.wait('@checkTrader');

    // linha do cupom aparece
    cy.get('[data-cy="trader-coupon-row"]').should('exist');
    cy.contains('PCO000003').should('be.visible');

    // ajuste o formato conforme sua UI: 'R$ 10.00' ou 'R$ 10,00'
    cy.contains(/R\$ 10([.,]?)0{2}/).should('be.visible');

    // clica no X para remover
    cy.get('[data-cy="remove-coupon"]').click();

    // some a linha e aparece o hint de vazio
    cy.get('[data-cy="trader-coupon-row"]').should('not.exist');
    cy.get('[data-cy="empty-coupons-hint"]').should('be.visible');
  });
});