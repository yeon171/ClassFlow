# ClassFlow 프로젝트

## 1. 프로젝트 소개
이 프로젝트는 회원 관리 및 예약 기능을 제공하는 백엔드 시스템입니다. Spring Boot를 사용하여 개발되었으며, RESTful API를 통해 기능을 노출합니다. 간단한 프론트엔드(HTML, CSS, JavaScript)를 포함하여 API 테스트 및 시연을 용이하게 합니다.

## 2. 기술 스택
- **백엔드**: Spring Boot, Spring Data JPA
- **데이터베이스**: H2 Database (개발 및 테스트용)
- **빌드 도구**: Gradle
- **언어**: Java 17
- **의존성 관리**: Lombok
- **프론트엔드 (간단한 테스트용)**: HTML, CSS, JavaScript (Fetch API)

## 3. ERD (Entity-Relationship Diagram)

**Entity 목록:**
- **Member**: 회원 정보
  - `id` (PK, Long)
  - `name` (String)
  - `email` (String, Unique)
  - `password` (String)
- **Reservation**: 예약 정보
  - `id` (PK, Long)
  - `member_id` (FK, Member.id)
  - `dateTime` (LocalDateTime)
  - `status` (Enum: PENDING, CONFIRMED, CANCELLED)

**관계:**
- `Member` (1) : `Reservation` (N) - 한 회원은 여러 예약을 가질 수 있습니다.

## 4. API 명세

### 4.1. 회원 (Member) API

#### 1. 회원 가입
- **URL**: `POST /api/members/register`
- **Request Body**: `application/json`
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string"
  }
  ```
- **Response**: `application/json`
  - **201 Created**: `Member registered with ID: {memberId}`
  - **409 Conflict**: `Email already exists: {email}`
  - **500 Internal Server Error**: `Error registering member: {errorMessage}`

#### 2. 로그인
- **URL**: `POST /api/members/login`
- **Request Body**: `application/json`
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response**: `application/json`
  - **200 OK**: 로그인한 회원 정보 (예: `{"id":1,"name":"test","email":"test@example.com","password":"testpassword"}`)
  - **401 Unauthorized**: `이메일 또는 비밀번호가 일치하지 않습니다.`
  - **500 Internal Server Error**: `로그인 중 오류가 발생했습니다: {errorMessage}`

#### 3. 회원 ID로 조회
- **URL**: `GET /api/members/{id}`
- **Response**: `application/json`
  - **200 OK**: 회원 정보
  - **404 Not Found**: 회원을 찾을 수 없음

#### 4. 회원 이메일로 조회
- **URL**: `GET /api/members/email/{email}`
- **Response**: `application/json`
  - **200 OK**: 회원 정보
  - **404 Not Found**: 회원을 찾을 수 없음

### 4.2. 예약 (Reservation) API

#### 1. 예약 생성
- **URL**: `POST /api/reservations`
- **Request Body**: `application/json`
  ```json
  {
    "memberId": 1,
    "dateTime": "2026-06-10T14:30:00"
  }
  ```
- **Response**: `application/json`
  - **201 Created**: 생성된 예약 정보
  - **404 Not Found**: `Member not found` (memberId에 해당하는 회원이 없을 경우)
  - **500 Internal Server Error**: 서버 오류

#### 2. 회원 ID로 예약 목록 조회
- **URL**: `GET /api/reservations/member/{memberId}`
- **Response**: `application/json`
  - **200 OK**: 해당 회원의 예약 목록 (배열)
  - **404 Not Found**: 회원을 찾을 수 없음 (현재 구현에서는 빈 배열 반환)

#### 3. 예약 ID로 단일 예약 조회
- **URL**: `GET /api/reservations/{id}`
- **Response**: `application/json`
  - **200 OK**: 예약 정보
  - **404 Not Found**: 예약을 찾을 수 없음

#### 4. 예약 취소
- **URL**: `PUT /api/reservations/{id}/cancel`
- **Response**: `application/json`
  - **200 OK**: 취소된 예약 정보
  - **404 Not Found**: 예약을 찾을 수 없음
  - **409 Conflict**: `이미 취소된 예약입니다.`
  - **500 Internal Server Error**: 서버 오류
