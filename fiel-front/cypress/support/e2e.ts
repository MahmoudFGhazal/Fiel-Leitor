// ***********************************************************
// This example support/e2e.ts is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import './commands';

Cypress.on('uncaught:exception', (err) => {
  // Ignora erros de SSR/hidratação relacionados ao document/window
  if (err.message.includes("document") || err.message.includes("window")) {
    return false; // impede que o teste falhe
  }
});

Cypress.on('uncaught:exception', (err) => {
  if (
    err.message.includes("document") ||
    err.message.includes("window") ||
    err.message.includes("hydration")
  ) {
    return false;
  }
});