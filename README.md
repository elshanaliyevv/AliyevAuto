# 🚗 AliyevAuto — Backend Documentation

> **Java Spring Boot** ilə yazılmış avtomobil satış idarəetmə sistemi.  
> 🌐 Live Demo: [aliyev-auto.vercel.app](https://aliyev-auto.vercel.app) &nbsp;|&nbsp; 📦 Repo: [github.com/elshanaliyevv/AliyevAuto](https://github.com/elshanaliyevv/AliyevAuto)

---

## 📋 Mündəricat

- [Layihə Haqqında](#-layihə-haqqında)
- [Texnologiyalar](#-texnologiyalar)
- [Arxitektura](#-arxitektura)
- [Qovluq Strukturu](#-qovluq-strukturu)
- [Təhlükəsizlik (Security)](#-təhlükəsizlik--security-)
- [JWT Mexanizmi](#-jwt-mexanizmi)
- [Verilənlər Bazası](#-verilənlər-bazası)
- [API Endpointlər](#-api-endpointlər)
- [Konfiqurasiya](#-konfiqurasiya)
- [Docker ilə İşə Salma](#-docker-ilə-işə-salma)
- [Lokal İşə Salma](#-lokal-işə-salma)
- [Build Prosesi](#-build-prosesi)
- [Test](#-test)

---

## 📌 Layihə Haqqında

**AliyevAuto** — avtomobil dealerlik sistemi üçün hazırlanmış RESTful backend API-dir. Sistem aşağıdakı əsas funksionallığı əhatə edir:

- İstifadəçi qeydiyyatı və autentifikasiyası (JWT)
- Avtomobil elanlarının idarə edilməsi (CRUD)
- Rol əsaslı giriş nəzarəti (RBAC)
- Token yenilənməsi (Refresh Token mexanizmi)
- Məlumatların validasiyası

---

## 🛠️ Texnologiyalar

| Texnologiya | Versiya | İstifadə Məqsədi |
|---|---|---|
| **Java** | 21 (LTS) | Əsas proqramlaşdırma dili |
| **Spring Boot** | 4.0.3 | Əsas framework |
| **Spring Web MVC** | Spring Boot ilə | REST Controller, DispatcherServlet |
| **Spring Data JPA** | Spring Boot ilə | ORM, verilənlər bazası əməliyyatları |
| **Spring Security** | Spring Boot ilə | Autentifikasiya, avtorizasiya |
| **Spring Validation** | Spring Boot ilə | Input validasiyası |
| **PostgreSQL** | 15 | Əsas verilənlər bazası |
| **Lombok** | Ən son | Boilerplate kodun azaldılması |
| **JJWT** | 0.13.0 | JWT token yaratma/doğrulama |
| **Gradle** | Wrapper | Build alət |

---

## 🏛️ Arxitektura

Layihə klassik **Layered Architecture** (Qatlı Arxitektura) prinsipinə əsaslanır:

```
┌─────────────────────────────────────────────────┐
│              CLIENT (Frontend / Postman)        │
└──────────────────────┬──────────────────────────┘
                       │ HTTP Request
┌──────────────────────▼──────────────────────────┐
│             SECURITY LAYER                      │
│    JwtAuthFilter → SecurityFilterChain          │
└──────────────────────┬──────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────┐
│           CONTROLLER LAYER                      │
│    @RestController — HTTP metodlarını idarə edir│
└──────────────────────┬──────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────┐
│            SERVICE LAYER                        │
│    @Service — İş məntiqi (Business Logic)       │
└──────────────────────┬──────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────┐
│          REPOSITORY LAYER                       │
│    @Repository / JpaRepository — DB əməliyyatları│
└──────────────────────┬──────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────┐
│            DATABASE (PostgreSQL)                │
└─────────────────────────────────────────────────┘
```

### Qatların Vəzifəsi

**Controller Layer** — HTTP sorğularını qəbul edir, parametrləri yoxlayır, Service-ə ötürür və cavabı (`ResponseEntity`) qaytarır. İş məntiqi saxlamır.

**Service Layer** — Əsas iş məntiqi burada yerləşir. Repository-lərlə işləyir, DTO-Entity çevrilmələrini idarə edir, exception-lar atır.

**Repository Layer** — `JpaRepository`-dən extend olunur. Verilənlər bazası ilə bütün CRUD əməliyyatları burada həyata keçirilir. Xüsusi sorğular üçün JPQL/Native Query istifadə edilir.

**Entity Layer** — JPA annotasiyaları ilə işarələnmiş model sinifləri. Verilənlər bazası cədvəllərinin Java təsviri.

**DTO Layer** — Controller ilə Client arasında data transferi üçün. Entity-nin birbaşa expose edilməsinin qarşısını alır.

---

## 📁 Qovluq Strukturu

```
src/
└── main/
    └── java/
        └── com/
            └── elshanaliyev/
                └── aliyevauto/
                    ├── AliyevAutoApplication.java     # Entry point (@SpringBootApplication)
                    │
                    ├── config/
                    │   ├── SecurityConfig.java         # Spring Security konfiqurasiyası
                    │   └── ApplicationConfig.java      # Bean-lər (PasswordEncoder, AuthManager)
                    │
                    ├── security/
                    │   ├── JwtService.java             # Token yaratma, doğrulama, parse
                    │   ├── JwtAuthFilter.java          # OncePerRequestFilter — hər sorğu üçün
                    │   └── UserDetailsServiceImpl.java # İstifadəçi yükləmə
                    │
                    ├── controller/
                    │   ├── AuthController.java         # /api/auth/** — login, register, refresh
                    │   ├── CarController.java          # /api/cars/** — avtomobil CRUD
                    │   └── UserController.java         # /api/users/** — istifadəçi əməliyyatları
                    │
                    ├── service/
                    │   ├── AuthService.java            # Autentifikasiya məntiqi
                    │   ├── CarService.java             # Avtomobil iş məntiqi
                    │   └── UserService.java            # İstifadəçi iş məntiqi
                    │
                    ├── repository/
                    │   ├── CarRepository.java          # JpaRepository<Car, Long>
                    │   └── UserRepository.java         # JpaRepository<User, Long>
                    │
                    ├── entity/
                    │   ├── Car.java                    # @Entity — avtomobil modeli
                    │   └── User.java                   # @Entity — istifadəçi modeli
                    │
                    ├── dto/
                    │   ├── request/
                    │   │   ├── LoginRequest.java
                    │   │   ├── RegisterRequest.java
                    │   │   └── CarRequest.java
                    │   └── response/
                    │       ├── AuthResponse.java
                    │       ├── CarResponse.java
                    │       └── UserResponse.java
                    │
                    └── exception/
                        ├── GlobalExceptionHandler.java  # @ControllerAdvice
                        └── custom/
                            ├── ResourceNotFoundException.java
                            └── UnauthorizedException.java

└── test/
    └── java/
        └── com/elshanaliyev/aliyevauto/
            ├── controller/                              # Controller testləri (MockMvc)
            ├── service/                                 # Service unit testləri
            └── repository/                              # Repository inteqrasiya testləri
```

---

## 🔐 Təhlükəsizlik ( Security )

Layihədə **Spring Security + JWT (Stateless)** autentifikasiya modeli istifadə edilir. Session saxlanılmır — hər sorğu ilə birlikdə `Authorization: Bearer <token>` başlığı göndərilir.

### Açıq Endpointlər (Authentication tələb etmir)

```
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh-token
```

### Qorunan Endpointlər (Token tələb edir)

```
GET    /api/cars/**
POST   /api/cars/**
PUT    /api/cars/**
DELETE /api/cars/**
GET    /api/users/**
```

### SecurityFilterChain Axışı

```
HTTP Sorğu
    │
    ▼
JwtAuthFilter (OncePerRequestFilter)
    │── Token yoxdur → anonim istifadəçi kimi davam et
    │── Token var    → JwtService.validateToken()
    │       ├── Etibarsız / sürəşmiş → 401 Unauthorized
    │       └── Etibarlı → SecurityContext-ə istifadəçini yüklə
    │
    ▼
SecurityFilterChain
    │── Public endpoint  → icazə ver
    └── Qorunan endpoint → autentifikasiya var? → davam et / 403 Forbidden
```

---

## 🔑 JWT Mexanizmi

Sistemdə **iki tokenli** model istifadə edilir:

```
┌─────────────────────────────────────────────┐
│              TOKEN NÖVLƏRİ                  │
│                                             │
│  Access Token                               │
│  ├── Müddət  : 15 dəqiqə (900,000 ms)       │
│  ├── Məqsəd  : API endpointlərə giriş       │
│  └── Başlıq  : Authorization: Bearer <jwt>  │
│                                             │
│  Refresh Token                              │
│  ├── Müddət  : 7 gün (604,800,000 ms)       │
│  ├── Məqsəd  : Yeni access token almaq      │
│  └── Endpoint: POST /api/auth/refresh-token │
└─────────────────────────────────────────────┘
```

### Token Axışı (Flow)

```
1. İstifadəçi login edir
         │
         ▼
2. Server → Access Token (15 dəq) + Refresh Token (7 gün) qaytarır
         │
         ▼
3. Client hər sorğuya Access Token əlavə edir
         │
         ▼
4. Access Token sürəşir (15 dəq sonra)
         │
         ▼
5. Client → POST /api/auth/refresh-token  (Refresh Token göndərir)
         │
         ▼
6. Server → Yeni Access Token + Yeni Refresh Token qaytarır
```

### JWT Konfiqurasiyası

```properties
# application.properties
security.jwt.secret=<Base64-encoded-256bit-secret>
security.jwt.access-expiration=900000      # 15 dəqiqə (millisaniyə)
security.jwt.refresh-expiration=604800000  # 7 gün (millisaniyə)
```

> ⚠️ **Vacib:** `security.jwt.secret` produksiyada mütləq environment variable kimi verilməlidir. Heç vaxt Git-ə commit etməyin.

---

## 🗄️ Verilənlər Bazası

**PostgreSQL 15** istifadə edilir. Spring Data JPA / Hibernate ORM vasitəsilə idarə olunur.

### Əlaqə Parametrləri (Docker)

```
Host     : db  (docker network daxilində servis adı)
Port     : 5432
Database : aliyev_auto
Username : postgres
Password : postgres
```

### Əsas Cədvəllər

**`users` cədvəli**

| Sütun | Tip | Açıqlama |
|---|---|---|
| `id` | BIGINT (PK) | Auto-generated |
| `username` | VARCHAR | Unikal istifadəçi adı |
| `email` | VARCHAR | Unikal email |
| `password` | VARCHAR | BCrypt şifrəli |
| `role` | VARCHAR | `USER` / `ADMIN` |
| `created_at` | TIMESTAMP | Qeydiyyat tarixi |

**`cars` cədvəli**

| Sütun | Tip | Açıqlama |
|---|---|---|
| `id` | BIGINT (PK) | Auto-generated |
| `brand` | VARCHAR | Marka (BMW, Mercedes...) |
| `model` | VARCHAR | Model adı |
| `year` | INTEGER | İstehsal ili |
| `price` | DECIMAL | Qiymət |
| `mileage` | INTEGER | Yürüş (km) |
| `description` | TEXT | Ətraflı təsvir |
| `user_id` | BIGINT (FK) | Elan sahibi → `users.id` |
| `created_at` | TIMESTAMP | Yaradılma tarixi |

---

## 📡 API Endpointlər

### Auth

| Metod | URL | Token | Açıqlama |
|---|---|---|---|
| `POST` | `/api/auth/register` | ❌ | Yeni istifadəçi qeydiyyatı |
| `POST` | `/api/auth/login` | ❌ | Giriş — token qaytarır |
| `POST` | `/api/auth/refresh-token` | ❌ | Yeni access token al |

**Register Request:**
```json
{
  "username": "elshan",
  "email": "elshan@example.com",
  "password": "Password123!"
}
```

**Login Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR...",
  "tokenType": "Bearer"
}
```

---

### Cars

| Metod | URL | Token | Açıqlama |
|---|---|---|---|
| `GET` | `/api/cars` | ✅ | Bütün elanları gətir |
| `GET` | `/api/cars/{id}` | ✅ | ID-yə görə elan gətir |
| `POST` | `/api/cars` | ✅ | Yeni elan yarat |
| `PUT` | `/api/cars/{id}` | ✅ | Elanı yenilə |
| `DELETE` | `/api/cars/{id}` | ✅ | Elanı sil |

**Car Request (POST / PUT):**
```json
{
  "brand": "BMW",
  "model": "5 Series",
  "year": 2021,
  "price": 45000.00,
  "mileage": 32000,
  "description": "Əla vəziyyətdə, tam komplektli"
}
```

---

### İstifadə Nümunəsi (cURL)

```bash
# 1. Qeydiyyat
curl -X POST http://localhost:9090/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"elshan","email":"elshan@example.com","password":"Password123!"}'

# 2. Login
curl -X POST http://localhost:9090/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"elshan@example.com","password":"Password123!"}'

# 3. Avtomobilləri gətir (token ilə)
curl -X GET http://localhost:9090/api/cars \
  -H "Authorization: Bearer <access_token>"

# 4. Yeni avtomobil əlavə et
curl -X POST http://localhost:9090/api/cars \
  -H "Authorization: Bearer <access_token>" \
  -H "Content-Type: application/json" \
  -d '{"brand":"Mercedes","model":"C200","year":2022,"price":52000,"mileage":15000}'

# 5. Token yenilə
curl -X POST http://localhost:9090/api/auth/refresh-token \
  -H "Content-Type: application/json" \
  -d '{"refreshToken":"<refresh_token>"}'
```

---

## ⚙️ Konfiqurasiya

### `application.properties`

```properties
# Tətbiq adı
spring.application.name=AliyevAuto

# JWT parametrləri
security.jwt.secret=V2VUaGltSXM0QmFzZTY0U2VjcmV0Rm9yQWxpeWV2QXV0b0...
security.jwt.access-expiration=900000
security.jwt.refresh-expiration=604800000
```

> **Qeyd:** Verilənlər bazası bağlantısı Docker Compose mühit dəyişənləri vasitəsilə ötürülür. Lokal işə salanda aşağıdakıları əlavə edin:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/aliyev_auto
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🐳 Docker ilə İşə Salma

### Servis Strukturu

```
┌──────────────────────────────────────────────────┐
│                 Docker Network                   │
│                                                  │
│  ┌──────────────────┐   ┌──────────────────────┐ │
│  │  aliyevauto-db   │◄──│ aliyevauto-backend   │ │
│  │  (PostgreSQL 15) │   │ (Spring Boot :9090)  │ │
│  │  host port: 8080 │   │ host port: 9090      │ │
│  └──────────────────┘   └──────────────────────┘ │
│                                                  │
│                   ┌──────────────────────┐       │
│                   │  aliyevauto-frontend │       │
│                   │  (Nginx :80)         │       │
│                   │  host port: 3000     │       │
│                   └──────────────────────┘       │
└──────────────────────────────────────────────────┘
```

### Portlar

| Servis | Container adı | Daxili Port | Xarici Port |
|---|---|---|---|
| PostgreSQL | `aliyevauto-db` | 5432 | 8080 |
| Spring Boot | `aliyevauto-backend` | 9090 | 9090 |
| Nginx | `aliyevauto-frontend` | 80 | 3000 |

### Əmrlər

```bash
# Bütün servisleri build edib başlat
docker-compose up --build

# Arxa planda başlat
docker-compose up -d --build

# Yalnız backend loglarını izlə
docker-compose logs -f backend

# Servisleri dayandır
docker-compose down

# Volume-larla birlikdə sil (DB məlumatları da silinir)
docker-compose down -v

# Yalnız backend-i yenidən başlat
docker-compose restart backend
```

---

## 🔨 Dockerfile (Multi-Stage Build)

```dockerfile
# ---- Build Mərhələsi (JDK) ----
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN ./gradlew build -x test

# ---- Runtime Mərhələsi (yalnız JRE) ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Multi-Stage build-in üstünlükləri:**
- Final image-də yalnız JRE var, JDK yox → daha kiçik ölçü
- Build alətləri (Gradle, source kod) final image-ə daxil olmur → daha təhlükəsiz
- `alpine` bazası istifadəsi image ölçüsünü minimuma endirir

---

## 🖥️ Lokal İşə Salma (Docker olmadan)

### Tələblər

- Java 21+
- PostgreSQL 15+
- Gradle (və ya `./gradlew` wrapper)

### Addımlar

```bash
# 1. Repoyu klonla
git clone https://github.com/elshanaliyevv/AliyevAuto.git
cd AliyevAuto

# 2. PostgreSQL-də verilənlər bazası yarat
psql -U postgres -c "CREATE DATABASE aliyev_auto;"

# 3. application.properties-ə DB konfiqurasyonu əlavə et
echo "
spring.datasource.url=jdbc:postgresql://localhost:5432/aliyev_auto
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
" >> application.properties

# 4. Tətbiqi başlat
./gradlew bootRun

# Backend hazırdır → http://localhost:9090
```

---

## 📦 Build Prosesi

```bash
# Tam build (test daxil)
./gradlew build

# Test-siz sürətli build
./gradlew build -x test

# Yalnız executable JAR yarat
./gradlew bootJar

# Çıxış fayllarını təmizlə
./gradlew clean

# Asılılıqları göstər
./gradlew dependencies

# Spring Boot tətbiqini birbaşa başlat
./gradlew bootRun
```

Build nəticəsində `build/libs/AliyevAuto-0.0.1-SNAPSHOT.jar` faylı yaranır.

---

## 🧪 Test

```bash
# Bütün testləri işə sal
./gradlew test

# Test hesabatını bax (HTML)
open build/reports/tests/test/index.html
```

### Test Kateqoriyaları

| Kateqoriya | Alətlər | Açıqlama |
|---|---|---|
| Unit Test | JUnit 5, Mockito | Service qatını izolyasiyada test edir |
| Integration Test | `@DataJpaTest` | Repository qatı — JPA/DB testləri |
| Controller Test | MockMvc | HTTP sorğu/cavab axışının testi |
| Security Test | `spring-security-test` | JWT filter, endpoint qorumasının testi |

---

## 🌍 Produksiya Mühit Dəyişənləri

Produksiyada həssas məlumatları mühit dəyişənləri ilə idarə edin:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/aliyev_auto
export SPRING_DATASOURCE_USERNAME=<username>
export SPRING_DATASOURCE_PASSWORD=<güclü_şifrə>
export SECURITY_JWT_SECRET=<base64_encoded_256bit_key>
export SECURITY_JWT_ACCESS_EXPIRATION=900000
export SECURITY_JWT_REFRESH_EXPIRATION=604800000
```

---

## 👤 Müəllif

**Elshan Aliyev**  
GitHub: [@elshanaliyevv](https://github.com/elshanaliyevv)

---

*Bu layihə özünü inkişaf etdirmək məqsədi ilə hazırlanmışdır.*
