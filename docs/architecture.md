# Architecture Overview

- Frontend: React + Vite (TS), routing, state, API client
- Backend: Spring Boot (Java 17), REST controllers, services, repositories
- DB: PostgreSQL via Docker Compose
- Auth: JWT Access + Refresh tokens
- CI/CD: GitHub Actions pipelines for both apps
- Deployment: Static frontend to Netlify/Vercel; containerized backend to Render/Fly.io
