# Monitoring & Observability

- Request logging middleware on backend
- Metrics via Spring Boot Actuator (`/actuator/prometheus`)
- Basic tracing hooks ready for OpenTelemetry
- Performance notes: use pagination, avoid N+1 queries, index frequently filtered fields

## Quick Access

- App: `http://localhost:5173/`
- API Swagger: `http://localhost:8080/swagger-ui/index.html`
- Health: `http://localhost:8080/api/health`

## Demo Credentials

- Email: `admin@demo.test`
- Password: `Admin123!`

Use these for local demos and to access ADMIN-only endpoints (e.g., `POST /api/demo/reset`).

## Swagger Authorization

- Click "Authorize" and paste `Bearer <ACCESS_TOKEN>` (include the `Bearer ` prefix).
- Use uppercase enums for `status`: `OPEN`, `IN_PROGRESS`, `COMPLETED`.
