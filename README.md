# ClassFlow 프로젝트

## 프로젝트 소개
ClassFlow는 학생 및 예약 관리를 위한 백엔드 애플리케이션입니다. Spring Boot를 기반으로 RESTful API를 제공하며, JPA를 통해 PostgreSQL 데이터베이스와 연동됩니다.

## 주요 기능
- **학생(Student) CRUD**: 학생 정보 생성, 조회, 수정, 삭제
- **예약(Reservation) CRUD**: 예약 정보 생성, 조회, 수정, 삭제
- **학생-예약 연관관계**: 한 학생이 여러 예약을 가질 수 있는 1:N 관계 설정
- **RESTful API 문서화**: SpringDoc OpenAPI (Swagger UI)를 통한 자동 문서화
- **예외 처리**: 리소스(학생, 예약)를 찾지 못했을 때의 사용자 정의 예외 처리

## 기술 스택
- **백엔드**: Spring Boot 3.x, Spring Data JPA
- **데이터베이스**: PostgreSQL
- **빌드 도구**: Gradle
- **언어**: Java 17
- **API 문서**: SpringDoc OpenAPI (Swagger UI)
- **기타**: Lombok

## 개발 환경 설정
1. **Java Development Kit (JDK)**: OpenJDK 17 이상
2. **Gradle**: 7.x 이상
3. **PostgreSQL**: 데이터베이스 서버 설치 및 실행
4. **IDE**: IntelliJ IDEA 또는 Spring Tool Suite (STS) 권장

### 데이터베이스 설정
`src/main/resources/application.properties` 파일에 PostgreSQL 데이터베이스 연결 정보를 설정합니다.
```properties
spring.datasource.url=jdbc:postgresql://your-database-host:5432/your-database-name?sslmode=require
spring.datasource.username=your-username
spring.datasource.password=${DB_PASSWORD} # 환경 변수 사용 권장
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```
`DB_PASSWORD`는 시스템 환경 변수로 설정하거나, `application.properties`에 직접 비밀번호를 입력할 수 있습니다.

## 프로젝트 실행
1. 프로젝트 클론: `git clone [프로젝트_레포지토리_URL]`
2. 프로젝트 디렉토리로 이동: `cd ClassFlow`
3. Gradle 빌드: `./gradlew build` (Windows의 경우 `gradlew build`)
4. 애플리케이션 실행: `./gradlew bootRun` (Windows의 경우 `gradlew bootRun`)
   또는 IDE에서 `ClassFlowApplication.java`를 실행합니다.

## API 문서 (Swagger UI)
애플리케이션 실행 후 다음 URL로 접속하여 API 문서를 확인할 수 있습니다.
`http://localhost:8080/swagger-ui.html`
또는
`http://localhost:8080/swagger-ui/index.html`

## 프론트엔드 테스트 페이지
간단한 CRUD 테스트를 위한 정적 HTML 페이지가 제공됩니다.
- 학생 관리: `http://localhost:8080/index.html`
- 예약 관리: `http://localhost:8080/index-reservation.html`

## API 엔드포인트 및 사용 예시

### 1. 학생 (Student) API
**엔티티 필드:** `id` (Long), `name` (String), `phone` (String), `memo` (String)

#### 1.1. 학생 생성 (POST)
- **URL**: `/api/students`
- **Request Body**: `application/json`
```json
{
  "name": "김철수",
  "phone": "010-1111-2222",
  "memo": "수학 심화반"
}
```
- **cURL 예시**:
```bash
curl -X POST "http://localhost:8080/api/students" \
-H "Content-Type: application/json" \
-d '{
  "name": "김철수",
  "phone": "010-1111-2222",
  "memo": "수학 심화반"
}'
```

#### 1.2. 모든 학생 조회 (GET)
- **URL**: `/api/students`
- **cURL 예시**:
```bash
curl -X GET "http://localhost:8080/api/students"
```

#### 1.3. 특정 학생 조회 (GET)
- **URL**: `/api/students/{id}` (예: `/api/students/1`)
- **cURL 예시**:
```bash
curl -X GET "http://localhost:8080/api/students/1"
```

#### 1.4. 학생 정보 업데이트 (PUT)
- **URL**: `/api/students/{id}` (예: `/api/students/1`)
- **Request Body**: `application/json`
```json
{
  "name": "김철수",
  "phone": "010-3333-4444",
  "memo": "영어 회화반"
}
```
- **cURL 예시**:
```bash
curl -X PUT "http://localhost:8080/api/students/1" \
-H "Content-Type: application/json" \
-d '{
  "name": "김철수",
  "phone": "010-3333-4444",
  "memo": "영어 회화반"
}'
```

#### 1.5. 학생 삭제 (DELETE)
- **URL**: `/api/students/{id}` (예: `/api/students/1`)
- **cURL 예시**:
```bash
curl -X DELETE "http://localhost:8080/api/students/1"
```

### 2. 예약 (Reservation) API
**엔티티 필드:** `id` (Long), `lessonTime` (LocalDateTime), `duration` (Integer), `memo` (String), `status` (String), `student` (Student 엔티티)

#### 2.1. 예약 생성 (POST)
- **URL**: `/api/reservations?studentId={studentId}` (예: `/api/reservations?studentId=1`)
- **Request Body**: `application/json`
```json
{
  "lessonTime": "2024-07-01T10:00:00",
  "duration": 60,
  "memo": "첫 수업",
  "status": "PENDING"
}
```
- **cURL 예시**:
```bash
curl -X POST "http://localhost:8080/api/reservations?studentId=1" \
-H "Content-Type: application/json" \
-d '{
  "lessonTime": "2024-07-01T10:00:00",
  "duration": 60,
  "memo": "첫 수업",
  "status": "PENDING"
}'
```

#### 2.2. 모든 예약 조회 (GET)
- **URL**: `/api/reservations`
- **cURL 예시**:
```bash
curl -X GET "http://localhost:8080/api/reservations"
```

#### 2.3. 특정 예약 조회 (GET)
- **URL**: `/api/reservations/{id}` (예: `/api/reservations/1`)
- **cURL 예시**:
```bash
curl -X GET "http://localhost:8080/api/reservations/1"
```

#### 2.4. 예약 정보 업데이트 (PUT)
- **URL**: `/api/reservations/{id}?studentId={studentId}` (예: `/api/reservations/1?studentId=1`)
- **Request Body**: `application/json`
```json
{
  "lessonTime": "2024-07-01T11:00:00",
  "duration": 90,
  "memo": "두 번째 수업",
  "status": "CONFIRMED"
}
```
- **cURL 예시**:
```bash
curl -X PUT "http://localhost:8080/api/reservations/1?studentId=1" \
-H "Content-Type: application/json" \
-d '{
  "lessonTime": "2024-07-01T11:00:00",
  "duration": 90,
  "memo": "두 번째 수업",
  "status": "CONFIRMED"
}'
```

#### 2.5. 예약 삭제 (DELETE)
- **URL**: `/api/reservations/{id}` (예: `/api/reservations/1`)
- **cURL 예시**:
```bash
curl -X DELETE "http://localhost:8080/api/reservations/1"
```

## 예외 처리
애플리케이션은 리소스를 찾지 못했을 때 `ResourceNotFoundException`을 발생시킵니다. 이 예외는 `@ResponseStatus(HttpStatus.NOT_FOUND)` 어노테이션이 적용되어 있어, 클라이언트에게 HTTP 404 Not Found 상태 코드와 함께 오류 메시지를 반환합니다.

**예시**: 존재하지 않는 학생 ID로 학생 정보를 조회하거나 업데이트/삭제를 시도할 경우
```json
{
  "timestamp": "2024-06-12T15:30:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found for this id :: 999",
  "path": "/api/students/999"
}
```