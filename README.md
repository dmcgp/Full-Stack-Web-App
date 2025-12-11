## Demo Credentials

- Email: `admin@demo.test`
- Password: `Admin123!`

Use these credentials to log in on the frontend and access ADMIN-only features like `POST /api/demo/reset`.

## Quick Links

- App: `http://localhost:5173/`
- API Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Health: `http://localhost:8080/api/health`

## Swagger Authorization

- Click "Authorize" in Swagger UI and paste `Bearer <ACCESS_TOKEN>` (include the `Bearer ` prefix).
- To obtain a token locally:
	```powershell
	# Login
	$login = Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/auth/login -ContentType 'application/json' -Body '{"email":"admin@demo.test","password":"Admin123!"}'
	$token = $login.accessToken
	```
- Use uppercase status values when filtering tasks: `OPEN`, `IN_PROGRESS`, `COMPLETED`.
# Task Manager — Full-Stack Web App

A CRUD app with authentication, REST API, PostgreSQL, Docker, CI/CD, and monitoring.

## Problem & Impact
Managing small team projects gets messy across chats and spreadsheets. This app centralizes projects, tasks, and comments with auth and role-based access, improving visibility and accountability.

## Tech Stack
- Frontend: React + TypeScript + Vite, React Router, Zustand, Axios, TailwindCSS, ESLint + Prettier, Jest + Testing Library, Cypress
- Backend: Java 17 Spring Boot, Maven, Spring Security (JWT), JPA/Hibernate, Flyway, Lombok, MapStruct, OpenAPI, Testcontainers
- Database: PostgreSQL (Docker Compose)
- CI/CD: GitHub Actions
- Deployment: Frontend (Netlify/Vercel), Backend (Render/Fly.io via Docker)

## Quick Start (Windows PowerShell)

### Prerequisites
- Node.js 18+
- Java 17 (JDK)
- Docker Desktop

### Run Database
```powershell
# From repo root
docker compose -f ./infra/docker-compose.yml up -d
```

### Run Backend
```powershell
# From repo root
Push-Location ./backend
# Ensure Java 17 for compatibility
# If needed: choco install temurin17 -y; refreshenv
mvn -q -DskipTests=false test
mvn spring-boot:run -Dspring-boot.run.profiles=local
Pop-Location
```

Environment variables (for non-local profiles):
- `JWT_SECRET`: Base secret for signing tokens
- `JWT_ACCESS_TTL_SECONDS`: Access token TTL (default 3600)
- `JWT_REFRESH_TTL_SECONDS`: Refresh token TTL (default 604800)

### Run Frontend
```powershell
# From repo root
Push-Location ./frontend
npm install
npm run dev
Pop-Location
```

### Test & Lint
```powershell
# Frontend
Push-Location ./frontend
npm run lint
npm test
npx cypress run
Pop-Location

# Backend
Push-Location ./backend
mvn -q -DskipTests=false test
Pop-Location

### Demo Data
```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/demo/seed
```

Admin-only reset (idempotent):
```powershell
# Register admin and login to get a token, then:
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/demo/reset -Headers @{ Authorization = "Bearer <ADMIN_ACCESS_TOKEN>" }
```

## Observability
- Request logging middleware (backend)
- `/actuator/prometheus` metrics (backend, optional)
- Basic performance notes in `docs/monitoring.md`

## CI/CD
## Security Notes
- **Auth**: Email/password with BCrypt hashing, JWT (HS256). Access token + refresh token. Routes under `/api/auth/*` are public; `/api/demo/reset` requires `ADMIN`.
- **RBAC**: Roles `USER` and `ADMIN` via `ROLE_USER`/`ROLE_ADMIN` authorities.
- **Best Practices**: In production, rotate `JWT_SECRET`, enforce HTTPS, and consider storing refresh tokens (DB) with revocation.
- GitHub Actions: build, lint, test, Docker image, deploy
- Environment setup documented in `docs/deployment.md`

## GitHub Repo
Public repository: https://github.com/dmcgp/Full-Stack-Web-App

## Collaboration
- Clear commits, issues, PR template, CONTRIBUTING

## License
MIT
