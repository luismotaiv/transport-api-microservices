# 🚚 Transport Microservices API

Arquitectura basada en microservicios para la gestión de órdenes de transporte, conductores, autenticación y manejo de archivos.

---

## 🧠 Arquitectura

El sistema está dividido en los siguientes microservicios:

* **auth-service** → Registro y autenticación (JWT)
* **driver-service** → Gestión de conductores
* **order-service** → Gestión de órdenes
* **file-service** → Subida y consulta de archivos (PDF, imágenes)

Cada microservicio es independiente y cuenta con su propia base de datos (principio de desacoplamiento).

---

## 🏗️ Tecnologías

* Java 17
* Spring Boot
* Spring Security (JWT)
* Spring Data JPA (Hibernate)
* MySQL 8
* Docker & Docker Compose
* Swagger (OpenAPI)
* JUnit 5 & Mockito

---

## 📂 Estructura del proyecto

```
transport-microservices/
│
├── auth-service/
├── driver-service/
├── order-service/
├── file-service/
└── docker-compose.yml
```

---

## ⚙️ Variables de entorno

Los microservicios utilizan variables de entorno para la configuración de base de datos:

```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Incluyen valores por defecto para ejecución local.

---

## 🐳 Cómo ejecutar el proyecto

### 1. Clonar repositorio

```
git clone https://github.com/luismotaiv/transport-microservices.git
cd transport-microservices
```

---

### 2. Generar los JARs

Ejecutar en cada microservicio:

```
mvn clean package -DskipTests
```

---

### 3. Levantar con Docker

```
docker-compose up --build
```

---

## 🚀 Servicios disponibles

| Servicio       | URL                   | Puerto |
| -------------- | --------------------- | ------ |
| Auth Service   | http://localhost:8080 | 8080   |
| Driver Service | http://localhost:8081 | 8081   |
| Order Service  | http://localhost:8082 | 8082   |
| File Service   | http://localhost:8083 | 8083   |

---

## 📌 Endpoints principales

### 🔐 Auth Service

**POST /auth/register**

```
{
  "username": "user",
  "password": "1234"
}
```

**POST /auth/login**

→ Devuelve JWT

---

### 🚗 Driver Service

**POST /drivers**

* Crear conductor

**GET /drivers/active**

* Validar conductor activo

---

### 📦 Order Service

**POST /orders**

* Crear orden

```
{
  "origin": "CDMX",
  "destination": "Guadalajara"
}
```

---

**PATCH /orders/{id}/driver**

* Asignar conductor

Headers:

```
Authorization: Bearer <token>
```

Body:

```
{
  "driverId": "uuid"
}
```
ó opcional con el pdf y image:
```
{
  "driverId": "uuid",
  "pdfUrl": "nombrepdf.pdf",
  "imageUrl": "nombrepng.png"
}
```
---

### 📁 File Service

**POST /files/upload**

* Subir archivos (multipart/form-data)

Campos:

* file (pdf / imagen)

---

**GET /files/{filename}**

***SE DEBE REALIZAR DESDE LA WEB PARA PODER DESCAGARLO***

* Descargar archivo

---

## 🔄 Flujo del sistema

1. Usuario se registra/login → obtiene JWT
2. Crea una orden
3. Asigna un conductor (validado vía driver-service)
4. Sube archivos (file-service)
5. Consulta la orden

---

## 🔐 Seguridad

* Autenticación basada en JWT
* Filtro personalizado (`JwtAuthFilter`)
* Protección de endpoints
* Swagger expuesto sin autenticación

---

## 🧪 Pruebas

Se implementaron pruebas unitarias en:

* `order-service`

Tecnologías:

* JUnit 5
* Mockito

Ejecutar:

```
mvn test
```

---

## 📊 Documentación (Swagger)

Los microservicios que exponen Swagger son el Auth-service(PORT:8080) y Order-service(PORT:8082):

```
http://localhost:{puerto}/swagger-ui.html
```

Ejemplo:

```
http://localhost:8082/swagger-ui.html
```

---

## 🧠 Decisiones técnicas

* ✔ Base de datos por microservicio → desacoplamiento
* ✔ Comunicación REST entre servicios
* ✔ Uso de WebClient para llamadas internas
* ✔ Docker para portabilidad
* ✔ Variables de entorno para configuración

---

## ⚠️ Notas

* No se incluyen credenciales reales en el repositorio
* El sistema está diseñado para entorno local con Docker
* Se recomienda usar Postman para pruebas

---

## 👨‍💻 Autor [@luismotaiv](https://github.com/luismotaiv)

Proyecto desarrollado como prueba técnica backend enfocada en arquitectura de microservicios.

---

## 📌 Posibles mejoras

* API Gateway
* Service Discovery (Eureka)
* Config Server
* Logs centralizados
* CI/CD pipeline

---
