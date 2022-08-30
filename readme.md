# goose-auth-api-server

### 로컬 빌드 방법

1. postgres, redis container 실행
2. configuration 설정

Run/Debug Configurations

Override configuration properties

| Name | Value                                      |
|------|--------------------------------------------|
| spring.datasource.url | jdbc:postgresql://localhost:5431/gooseauth |
| spring.datasource.username | gooseauth                                  |
| spring.datasource.password | gooseauth                                  |
| spring.jpa.hibernate.ddl-auto | update                                  |
 
혹은
## vault 설정하기
```yml
  cloud:
    vault:
      uri: http://localhost:8200
      token: insert-your-TOKEN
```
## vault 내부
```json
{
  "spring.datasource.password": "your-password",
  "spring.datasource.url": "jdbc:postgresql://localhost:5432/your-schema",
  "spring.datasource.username": "your-password",
  "spring.jpa.hibernate.ddl-auto": "create"
}
```
 
### Workflow 추가
 
### 활용 기술
![tech-stack](https://user-images.githubusercontent.com/26823834/187427754-d4fb3bde-ebe0-487c-9420-5c7fdb8d9b3e.png)
