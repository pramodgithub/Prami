# Prami — One Stop Shopping (Frontend)

## Overview

Prami is a comprehensive e-commerce and curated shopping platform consisting of:

- **Frontend (`prami/`)**: A responsive HTML5 web application providing the user-facing storefront with product discovery, store browsing, collections, search, and user profile management.
- **Backend (`com.prami/`)**: Java-based REST API services handling product catalogs, store data, user authentication, collections, pricing, and analytics.

The frontend communicates with backend APIs to deliver a dynamic shopping experience while maintaining SEO optimization and third-party integrations.

## Architecture Overview

```
┌─────────────────────────────────┐
│   Frontend (prami/)             │
│  - HTML5/CSS/JavaScript Pages   │
│  - SEO & Meta Tags              │
│  - Analytics & Social Pixels    │
│  - Static Assets                │
└──────────────┬──────────────────┘
               │ (HTTP/REST API)
               ▼
┌─────────────────────────────────┐
│  Backend (com.prami/)           │
│  - Product Management APIs      │
│  - Store & Category Services    │
│  - User & Auth Services         │
│  - Collection & Search Engine   │
│  - Database Layer (DAO)         │
└─────────────────────────────────┘
```

## Frontend Key Features

- Multi-page storefront with curated product and store pages
- SEO/Open Graph meta tags and analytics integrations (Google Analytics, Facebook Pixel)
- Client-side search/typeahead and responsive layout
- User authentication and profile management
- Custom 404 handling via `error/custom_404.html`

## Frontend Project Structure

- `WebContent/` — site root containing HTML pages, assets, and server config
  - `index.html` — home / landing page
  - `product.html`, `product2.html` — product detail pages with pricing and reviews
  - `store.html`, `teststore.html`, `addstore.html` — store listing and management pages
  - `collection.html`, `designers.html`, `shop.html` — curated collections and category pages
  - `privacy.html`, `search.html`, `tv.html` — supporting pages
  - `error/custom_404.html` — 404 error page
  - `WEB-INF/web.xml` — Java webapp descriptor (welcome files, error-page mapping, CORS)
  - `META-INF/MANIFEST.MF`
  - `js/jquery.min.js` — jQuery library for DOM manipulation and AJAX

## Backend Project Structure

- `src/` — Java source code
  - `com/prami/bean/` — Domain objects (Product, Store, User, Category, etc.)
  - `com/prami/dao/` — Data Access Objects for database operations
  - `com/prami/db/` — Database connection and utility classes
  - `com/prami/filter/` — Request filters (auth, CORS, etc.)
  - `com/prami/util/` — Helper utilities and common functions
  - `com/prami/resources/` — Configuration files
- `build/classes/` — Compiled bytecode
- `WebContent/WEB-INF/lib/` — Third-party JAR dependencies

### Backend Key Components

- **Beans**: Product, Store, User, Category, Collection, LocalStore, OnlineStore, PriceHistory, Activity, etc.
- **DAOs**: ProductDao, StoreDao, UserDao, CategoryDao, CollectionDao, PriceHistoryDao, AcitivityDao, etc.
- **Services**: Product search, pricing, store management, user authentication, activity tracking

## Activity Engine

The Activity Engine is a core backend component that tracks user interactions with products and stores, enabling personalized recommendations and analytics.

### Purpose

- **User Behavior Tracking**: Records product views, clicks, and purchases with geolocation and device info
- **Analytics**: Aggregates activity data for product popularity, regional trends, and user engagement metrics
- **Personalization**: Enables recommendation engines based on user activity history
- **Purchase History**: Tracks user purchase patterns for order history and recommendations

### Activity Data Captured

- **User ID**: Identifier of the user performing the action
- **Activity Type**: Category of action (view, click, purchase, wishlist, etc.)
- **Product ID**: Reference to the product being interacted with
- **Location Data**: Country, region, city, ZIP code, latitude, longitude
- **User IP**: IP address for geo-targeting and fraud detection
- **Timestamp**: Precise time of the activity

### Activity Engine Components

- **Activity Bean** (`Activity.java`): Data model for activity records
- **ActivityDAO** (`AcitivityDao.java`): Database operations for activity tracking
  - `insertActivity()`: Log new user activities
  - `getProductViews()`: Count total product views
  - `getPurchaseHistory()`: Retrieve user purchase history with auth validation

### API Endpoints for Activity

- `POST /api/activities` — log user activity (view, click, purchase)
- `GET /api/activities/product/{id}` — get product view count
- `GET /api/users/{id}/purchase-history` — retrieve user's purchase history (auth required)
- `GET /api/analytics/trending` — get trending products by activity

### Activity Tracking Example

```javascript
// Frontend: Track product view
async function trackProductView(productId, userId) {
  const activity = {
    activityType: 1,  // 1 = view
    userId: userId,
    productId: productId,
    userIP: getClientIP(),
    country: getUserCountry(),
    region: getUserRegion(),
    city: getUserCity(),
    zip: getUserZip(),
    latitude: getLatitude(),
    longitude: getLongitude()
  };
  
  const res = await fetch(`${API_BASE}/api/activities`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(activity)
  });
  return res.json();
}
```

## How to Run / Deploy

### Frontend Only (Static Preview)

Open `WebContent/index.html` directly in a browser for a static preview (limited to frontend UI only).

### Frontend + Backend (Local Development)

1. Start the backend API service locally (e.g., `http://localhost:8080/api`)
2. Update `API_BASE` in frontend scripts to point to your backend instance
3. Serve the frontend via:
   - Simple HTTP server: `python -m http.server --directory WebContent`
   - Java webapp (Tomcat/Jetty): Package both frontend and backend into a single WAR

### Production Deployment

- **Frontend**: Package `WebContent/` as a WAR or deploy to a CDN/static hosting
- **Backend**: Deploy the Java application to a servlet container (Tomcat, Jetty, etc.)
- **Config**: Set environment variables for database, auth secrets, and API endpoints

## API Integration

### Base URLs

- Development: `http://localhost:8080/api`
- Production: `https://api.prami.com` (replace with your actual host)

### Common Endpoints

- `GET /api/search?q={query}` — search products and stores
- `GET /api/products/{id}` — get product details with pricing and reviews
- `GET /api/stores/{id}` — get store details and services
- `GET /api/collections` — list curated collections
- `GET /api/categories` — list product categories
- `POST /api/auth/login` — authenticate and receive auth token
- `POST /api/auth/register` — create user account
- `GET /api/users/me` — get current user profile (auth required)

### Authentication

- Public endpoints (search, product listings, store info) do not require authentication
- User/profile endpoints require bearer token: `Authorization: Bearer <token>`
- Tokens should be stored securely (httpOnly cookies or secure localStorage)

### CORS Configuration

The API must enable CORS for frontend origins:

```
Access-Control-Allow-Origin: https://prami.in, http://localhost:8080
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: Content-Type, Authorization
```

### Example Frontend API Calls

Search products (public):

```javascript
const API_BASE = 'https://api.prami.com';
async function search(q){
  const res = await fetch(`${API_BASE}/api/search?q=${encodeURIComponent(q)}`);
  return res.json();
}
```

Get user profile (authenticated):

```javascript
async function getProfile(){
  const token = localStorage.getItem('prami_token');
  const res = await fetch(`${API_BASE}/api/users/me`, {
    headers: { 'Authorization': `Bearer ${token}` }
  });
  return res.json();
}
```

## Notes for Developers

### Frontend

- Assets are referenced under `/static/` and `/images/` — ensure these remain available when serving
- Third-party integrations (Google Sign-In, analytics, social links) are embedded in HTML pages
- Update API base URL for different environments (dev/staging/production)
- Include CSRF tokens for POST/PUT/DELETE requests to protected endpoints

### Backend

- Database connections configured via `application.properties` or environment variables
- DAO classes handle all database operations; services orchestrate business logic
- Filter classes enforce authentication and CORS policies
- Price history and product similarity tracking for analytics and recommendations

## License & Contact

Use and distribution depend on project owners. For questions contact the original project maintainer (see `index.html` footer contact: contactus@prami.in).
