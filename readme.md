# goose-auth-api-server

## 빌드 방법

<details>
<summary style="font-size: 17px">로컬 빌드 방법</summary>
<div>
<p>1. postgres, redis container 실행</p>
<p>2. configuration 설정</p>

Run/Debug Configurations

Override configuration properties

| Name | Value                                      |
|------|--------------------------------------------|
| spring.datasource.url | jdbc:postgresql://localhost:5431/gooseauth |
| spring.datasource.username | gooseauth                                  |
| spring.datasource.password | gooseauth                                  |
| spring.jpa.hibernate.ddl-auto | create                                     |
</div>
</details>

<details>
<summary style="font-size: 17px">배포 시 빌드 방법</summary>
<div>
<p>1. postgres, redis container 실행</p>
<p>2. configuration 설정</p>

Run/Debug Configurations

Override configuration properties

| Name | Value                                      |
|------|--------------------------------------------|
| spring.datasource.url | jdbc:postgresql://postgres:5432/gooseauth |
| spring.datasource.username | gooseauth                                  |
| spring.datasource.password | gooseauth                                  |
| spring.jpa.hibernate.ddl-auto | create                                     |
</div>
</details>

**혹은**

## vault 설정하기
```yml
  cloud:
    vault:
      uri: http://localhost:8200
      token: insert-your-TOKEN
```
### vault 내부 (로컬환경)
```json
{
  "spring.datasource.password": "gooseauth",
  "spring.datasource.url": "jdbc:postgresql://localhost:5431/gooseauth",
  "spring.datasource.username": "gooseauth",
  "spring.jpa.hibernate.ddl-auto": "update"
}
```

### 활용 기술
![tech-stack](https://user-images.githubusercontent.com/26823834/187427754-d4fb3bde-ebe0-487c-9420-5c7fdb8d9b3e.png)
