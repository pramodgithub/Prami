# com.prami API Services

## Overview

`com.prami` provides backend HTTP APIs used by the Prami frontend for search, product, store and user management. This document describes the service purpose, how to run it locally, and example requests your frontend can call.

## Base URL

- Development (example): `http://localhost:8080/api`
- Production: `https://api.prami.com` (replace with your actual host)

## Authentication

- The API supports token-based authentication (e.g., JWT or session token). Include the token in the `Authorization` header: `Authorization: Bearer <token>`.
- Public endpoints (search, product listings) do not require auth; user/profile endpoints do.

## Common Endpoints (examples)

- `GET /api/search?q={query}` — search products/stores
- `GET /api/products/{id}` — get product details
- `GET /api/stores/{id}` — get store details
- `GET /api/collections` — list collections
- `POST /api/auth/login` — authenticate and receive token
- `POST /api/users` — create user account

Update these to match your actual routes if they differ.

## CORS

Ensure the API enables CORS for the frontend origin(s). Example Access-Control headers:

- `Access-Control-Allow-Origin: https://prami.in`
- `Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS`

## Example AJAX (fetch) calls

Search (public):

```javascript
const base = 'https://api.prami.com';
async function search(q){
  const res = await fetch(`${base}/api/search?q=${encodeURIComponent(q)}`);
  return res.json();
}
```

Authenticated request (token stored in `localStorage`):

```javascript
async function getProfile(){
  const token = localStorage.getItem('prami_token');
  const res = await fetch(`${base}/api/users/me`, {
    headers: { 'Authorization': `Bearer ${token}` }
  });
  return res.json();
}
```

jQuery example:

```javascript
$.ajax({
  url: `${base}/api/products/123`,
  method: 'GET',
  success: function(data){ console.log(data); }
});
```

## Sample responses

Product (GET /api/products/{id}):

```json
{
  "id":123,
  "title":"Leather Formal Shoes",
  "price":2499.00,
  "currency":"INR",
  "images":["/images/products/123/1.jpg"],
  "store": { "id":42, "name":"ShoeStore" }
}
```

Search (GET /api/search?q=shoe):

```json
{
  "query":"shoe",
  "results": [ { "type":"product", "id":123, "title":"..." } ]
}
```

## Running locally

1. Set the application properties (port, DB, auth secrets) via environment variables or `application.properties`.
2. Start the service (example for a Spring Boot app):

```bash
mvn spring-boot:run
# or
./gradlew bootRun
```

3. Verify the API via `curl` or Postman.

## API spec / Postman

- If available, publish an OpenAPI/Swagger spec or a Postman collection and add the link here. This makes integration and testing much easier.

## Contact

For questions about endpoints, auth, or sample data contact the backend maintainer or team.
