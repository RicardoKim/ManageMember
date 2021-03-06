# ManageMember

## 프로그램 목적

Spring을 활용하여 사내의 직원 관리 시스템을 만드는 실습 프로젝트입니다.

---

## 프로그램 실행 및 테스트 환경
1. Java 8
2. Spring Boot 2.6.1
---
## 프로그램 실행 방법

```shell
git clone https://github.com/RicardoKim/ManageMember.git
cd ManageMember
./gradlew bootRun
```
---

## API 문서
https://documenter.getpostman.com/view/18776635/UVRGDPHq

## 로깅 정보

[로그 설정 정보](./src/main/resources/logback.xml)

1. [서비스 로그](./logs/ServiceLog.log)

    시스템의 flow에 따른 log를 보여준다.

2. [권한 오류 로그](./logs/securityCheckFail.log)

    토큰 오류로 request 권한 거부 된 client의 정보를 담아둔 로그 파일