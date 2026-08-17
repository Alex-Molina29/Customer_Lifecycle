# customersKata - Ciclo de vida de una aplicación

El proyecto está desarrollado bajo una arquitectura de monolito modular, exponiendo una API REST.

El backend está organizado mediante Vertical Slicing, agrupando el código por funcionalidades de negocio. Donde dentro de cada funcionalidad se aplica una separación basada en principios de la arquitectura hexagonal.

El frontend está desarrollado de forma independiente y se comunica con el backend mediante HTTP/REST.

## Stack tecnologico

- **Backend**: Java 21 + Spring Boot.
- **Frontend**: React + Vite, en producción con Nginx.
- **Base de datos**: PostgreSQL (local vía Docker, en la nube vía Neon)
- **Autenticación**: JWT
- **Contenedores**: Docker + docker compose
- **CI/CD**: GitHub Actions
- **Cloud**: Google Cloud Run + Google Artifact Registry

## Arquitectura de ambientes
La aplicación cuenta con dos ambientes configurados para ejecución local: desarrollo (dev) y producción (prod), cada uno con su propio puerto, base de datos, y configuración:
 
| | Desarrollo (dev) | Producción (prod) |
|---|---|---|
| Puerto backend | 8080 | 9090 |
| Nombre de la app | customers-dev | customers-prod |
| Base de datos | customersdb | customersdb_prod |
| ddl-auto | update | validate |
| Logging SQL | activo | desactivado |
| Frontend local | http://localhost:4200 | http://localhost:4201 |
| Frontend Web | https://frontend-dev-875809812914.us-central1.run.app/ | https://frontend-prod-875809812914.us-central1.run.app |

## Estructura del repositorio

```
kataBancoBogota/
├── docker-compose.yml       # Orquesta los 5 servicios en local
├── init-db.sql              # Crea la base customersdb_prod al primer arranque
├── schema.sql               # Dump del esquema
├── seed-data.sql            # Datos de prueba
├── .github/workflows/       # Pipeline de CI/CD
├── Backend/customersKata/   # API Spring Boot
└── Frontend/frontend/       # React
```

### Capas dentro de cada funcionalidad

```
customers/
├── application/
│   ├── CustomerService.java
│   └── dto/
│       ├── request/
│       └── response/
│
├── domain/
│   ├── Customer.java
│   └── exception/
│
└── infrastructure/
    ├── CustomerController.java
    ├── CustomerRepository.java
    └── specification/
```

## Variables de entorno
 
Antes de levantar el proyecto, es necesario crear un archivo `.env` en la raíz (mismo nivel que `docker-compose.yml`) con la siguiente estructura:
 
```env
POSTGRES_PASSWORD=tu_password_local
JWT_SECRET=tu_secreto_jwt_local
```
 
Vale aclarar que ninguna credencial real vive en el código fuente. Los archivos `application-*.properties` usan la sintaxis `${VARIABLE:valor_por_defecto}`.

## Ejecución del proyecto 

### Con docker-compose

La forma recomendada para ejecutar el proyecto completo es mediante docker-compose, ya que así se asegura de contar con todo lo necesario.

Desde la carpeta raíz del proyecto, ejecutar:

```bash
docker-compose up --build -d
```

Y tenga en cuenta que para detener la ejecución lo puede hacer con el siguiente comando: 

```bash
docker-compose down
```

### Levantar únicamente la Base de Datos

Si se desea ejecutar el backend manualmente desde tu IDE (fuera de Docker) para depurar el código, se puede levantar exclusivamente el contenedor de PostgreSQL sin iniciar el resto de los servicios:

```bash
docker-compose up -d db
```

(Esto iniciará solo la base de datos en segundo plano, dejándola lista en el puerto 5433 como se definió en el compose).

#### Inicializar la estructura de la base de datos (local)

Si estás levantando el proyecto por primera vez y necesitas crear las tablas manualmente a partir del dump, asegúrate de que el contenedor de Docker esté corriendo y ejecuta el comando correspondiente a tu terminal desde la raíz del proyecto:

Si usas Bash, CMD o terminales de Linux/Mac:

```bash
docker exec -i customers-postgres-db psql -U admin -d customersdb_prod < schema.sql
```
Si usas PowerShell (Windows):
```bash
Get-Content schema.sql | docker exec -i customers-postgres-db psql -U admin -d customersdb_prod
``` 

### Ejecución local desde consola

#### Backend

Debe abrir una terminal y ubicarse en el directorio del backend (tenga presente que es necesario tener la base de datos lista para que se pueda conectar):
```bash
cd Backend/customersKata
```

```bash
./mvnw spring-boot:run 
```

#### Frontend

Debe abrir una segunda terminal y ubicarse en el directorio del frontend:
```bash
cd Frontend/frontend
```

Si es la primera ejecución o se modificaron las dependencias:
```bash
pnpm install
``` 
Para iniciar el servidor de desarrollo:

```bash
pnpm dev
```

### Ejecutando el jar directamente

#### Entorno de desarrollo
 
```bash
cd Backend/customersKata
java -jar target/customersKata-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
 
Al arrancar, la consola muestra:
```
=== Ejecutando en DEV === (puerto: 8080)
```

## Construcción del proyecto sin Docker
 
### Requisitos previos

- Docker y docker-compose
- Java 21 y Maven, Node 20 y pnpm

### Backend — build manual
 
```bash
cd Backend/customersKata
./mvnw clean package
```

Genera el ejecutable en `target/customersKata-0.0.1-SNAPSHOT.jar`.

### Frontend — build manual
 
```bash
cd Frontend/frontend
pnpm install
pnpm run build
```

Genera los estáticos en `Frontend/frontend/dist/`. 
 
#### Entorno de producción (al ser local una simulación)
 
```bash
cd Backend/customersKata
java -jar target/customersKata-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```
 
Al arrancar, la consola muestra:
```
=== Ejecutando en PROD === (puerto: 9090)
```

### Diferencia visible entre ambientes
 
| Aspecto | DEV | PROD |
|---|---|---|
| Puerto | 8080 | 9090 |
| Mensaje de log al arrancar | "Ejecutando en DEV" | "Ejecutando en PROD" |
| Nombre de la aplicación | customers-dev | customers-prod |
| Base de datos | customersdb (aislada) | customersdb_prod (aislada) |
| Comportamiento del esquema | Hibernate lo actualiza automáticamente | Hibernate solo valida, nunca modifica |


## Endpoints principales
 
### Autenticación
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/auth/register` | Registra un nuevo usuario |
| POST | `/auth/login` | Autentica y retorna un JWT |
 
### Clientes (requieren JWT válido en header `Authorization: Bearer <token>`)
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/customers/` | Crea uno o más clientes |
| GET | `/api/customers/` | Lista clientes, con filtros y paginación opcional |
| GET | `/api/customers/{id}` | Consulta un cliente por ID |
| PUT | `/api/customers/{id}` | Actualiza un cliente |
| DELETE | `/api/customers/{id}` | Elimina un cliente |

 
## Pruebas unitarias
 
El backend incluye pruebas unitarias sobre la capa de servicios (`UserService`, `JwtService`, `CustomerService`), usando JUnit 5 y Mockito, sin dependencia de una base de datos real.
 
```bash
cd Backend/customersKata
./mvnw test
```
 
Las pruebas corren automáticamente como parte del pipeline de CI/CD antes de cada despliegue — si alguna falla, el despliegue se cancela automáticamente y la versión en producción no se ve afectada.

## Despliegue en la nube (Google Cloud Run)
 
La aplicación se despliega automáticamente mediante GitHub Actions (`.github/workflows/main.yml`):
 
- **Push a `develop`** → despliega `backend-dev` y `frontend-dev` a Cloud Run
- **Push a `main`** (vía Pull Request desde `develop`) → despliega `backend-prod` y `frontend-prod`

### Flujo del pipeline
 
1. Checkout del código
2. Configuración de Java 21 y ejecución de `mvn test` (gate de calidad)
3. Autenticación contra Google Cloud usando una Service Account
4. Build y push de la imagen del backend a Artifact Registry
5. Deploy del backend a Cloud Run (puerto y variables de entorno según el ambiente)
6. Build de la imagen del frontend, inyectando la URL real del backend recién desplegado (`VITE_API_URL`)
7. Deploy del frontend a Cloud Run

### Protección de rama
 
La rama `main` está protegida: todo cambio debe pasar primero por `develop` y ser incorporado mediante un Pull Request — no es posible desplegar a producción sin pasar antes por el ambiente de desarrollo.

## Seguridad

- CORS configurado explícitamente por ambiente — en producción solo el dominio real del frontend puede consumir la API
- El repositorio es personal, no vinculado a la organización, siguiendo las recomendaciones de seguridad de la Kata
