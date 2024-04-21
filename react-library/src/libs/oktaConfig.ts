export const oktaConfig = {
  clientId: "0oagl316zsbiByGvS5d7",
  issuer: "https://dev-76650645.okta.com/oauth2/default",
  redirectUri: "http://localhost:3000/login/callback",
  scopes: ['openid', 'profile', 'email'],
  pkce: true,
  disableHttpsCheck: true
}