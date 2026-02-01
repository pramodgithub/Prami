# Prami — One Stop Shopping (Frontend)

## Overview

Prami is a static frontend for an e-commerce/curated shopping experience. The repository contains the website's HTML, assets and configuration intended to be deployed as a Java web application (WAR) or served as static files.

## Key Features

- Multi-page storefront with curated product and store pages
- SEO/Open Graph meta tags and analytics integrations (Google Analytics, Facebook Pixel)
- Client-side search/typeahead and responsive layout
- Custom 404 handling via `error/custom_404.html`

## Project structure

- `WebContent/` — site root containing HTML pages, assets, and server config
  - `index.html` — home / landing page
  - `product.html`, `product2.html` — product pages
  - `store.html`, `teststore.html`, `addstore.html` — store-related pages
  - `privacy.html`, `search.html`, `collection.html`, `designers.html`, `shop.html`, `tv.html` — supporting pages
  - `error/custom_404.html` — 404 error page
  - `WEB-INF/web.xml` — Java webapp descriptor (welcome files, error-page mapping)
  - `META-INF/MANIFEST.MF`

## How to run / deploy

- Static preview: open `WebContent/index.html` directly in a browser for a quick static preview.
- Java webapp (Tomcat / Jetty): package the contents of `WebContent/` into a WAR or configure your build to place `WebContent` as the webapp root, then deploy to your servlet container. `WEB-INF/web.xml` configures welcome pages and a 404 mapping.

## Notes for developers

- Many assets are referenced under `/static/` and `/images/` — ensure those directories remain available when serving the site.
- Third-party integrations (Google Sign-In, analytics, social links) are embedded in `index.html`.

## API Integration

This frontend calls backend APIs implemented in the `com.prami` services. See the API documentation added at `com.prami README.md` for endpoints, auth, CORS and examples.

Example `fetch` usage (search):

```javascript
const API_BASE = 'https://api.prami.com'; // set to your dev/prod base URL
async function search(q){
  const res = await fetch(`${API_BASE}/api/search?q=${encodeURIComponent(q)}`);
  return res.json();
}
```

Notes:
- Ensure the API sets appropriate CORS headers for the frontend origin.
- Store auth tokens securely (e.g., `localStorage` or secure cookies) and include `Authorization` header when calling protected endpoints.

## License & Contact

Use and distribution depend on project owners. For questions contact the original project maintainer (see `index.html` footer contact: contactus@prami.in).
