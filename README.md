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

## API 안내

> ## 관리자 권한 획득

아래의 API들은 모두 관리자 권한으로 API에 접근하는 것을 가정합니다.

따라서 Request에 BearerToken에 비밀키(XgEzXpJLnwVwYaJk)를 넣어서 API 호출을 진행해야합니다.

해당 비밀키를 BearToken에 넣지 않고 API 호출을 할 때는 서버는 아무 응답도 하지 않는다.

> ## 사내 팀 관리

사내 팀 관리 관련해서는 다음과 같은 기능을 지원합니다.

1. 사내 팀 등록

    - 요청 URL : ```POST teams/create```
    - Request Json
    ```JavaScript
        {
            "name" : Team Name
        }
    ```
    - Response Json

    |Http Code|설명|
    |------|---|
    |200|정상적으로 팀 생성이 완료됨|
    |400|생성하려는 팀 이름이 이미 존재함|
    
    <정상적인 응답 예시>

    ```JavaScript
    {
        "error": null,
        "statusCode": 200,
        "data": [
            {
                "id" : Team Id,
                "name" : Team Name
            }
        ]
    }
    ```
    
    
2. 팀 전체 조회

    - 요청 URL : ```GET teams/totalsearch```
    - Response Json

    |Http Code|설명|
    |------|---|
    |200|정상적으로 모든 팀 정보가 반환되었음|

    <정상적인 응답 예시>

    ```JavaScript
    {
        "error": null,
        "statusCode": 200,
        "data": [
            {
                "id": Team 1 Id,
                "name": Team 1 Name
            },
            {
                "id": Team 2 Id,
                "name": Team 2 Name
            },
            ...
        ]
        
    }
    ```

3. 팀 조건부 조회

    - 요청 URL : ```GET teams/selectsearch```
    - Request Json
    ```JavaScript
    {
        "option" : option value to search
    }
    ```

    |option|설명|
    |------|---|
    |id|팀의 아이디로 팀을 검색할 때 사용한다.|
    |team_name|팀의 이름으로 팀을 검색할 때 사용한다.|
    
    - Response Json

    |Http Code|설명|
    |------|---|
    |200|정상적으로 조건에 맞는 팀을 검색했을 때|
    |204|조건에 맞는 팀을 찾지 못했을 때|
    |400|검색 option이 잘못된 경우|

    <정상적인 응답 예시>
    ```JavaScript
    {
        "error": null,
        "statusCode": 200,
        "data": [
            {
                "id" : Team Id
                "name" : Team Name
            }
        ]
    }
        
    ```

4. 팀 멤버 조회
    - 요청 URL : ```GET teams/membersearch```
    - Request Json
    ```JavaScript
    {
        "name" : Team Name to search
    }
    ```
    
    - Response Json

    |Http Code|설명|
    |------|---|
    |200|정상적으로 조건에 맞는 팀을 검색했을 때|
    |204|조건에 맞는 팀을 찾지 못했을 때|
    |400|검색 option이 잘못된 경우|

    <정상적인 응답 예시>
    ```JavaScript
    {
        "error" : null,
        "statusCode : 200,
        "data" : [
            {
                "id": "member 1 id",
                "teamName" : "member 1 team name",
                "name": "member 1 Name",
                "age": "member 1 Age",
                "gender": "member 1 Gender"
                
            },
            {
                "id": "member 2 id",
                "teamName" : "member 2 team name",
                "name": "member 2 Name",
                "age": "member 2 Age",
                "gender": "member 2 Gender",
                
            }
            ...,
        ]
    }   
        
    ```

> ## 사내 구성원 관리
사내 구성원 관리 관련해서는 다음과 같은 기능을 지원합니다.

1. 사내 구성원 등록

    - 요청 URL : ```POST members/create```
    - Request Json
    ```JavaScript
    {
        "name" : "Member Name",
        "age" : "Member Age",
        "teamName" : "Member Team Name",
        "gender" : "Member Gender"
    }
    ```
    - Response Json

    |Http Code|설명|
    |------|---|
    |200|정상적으로 구성원이 생성됨|
    |400|구성원 생성 관련 정보에 문제가 있음|

    <정상 응답 예시>

    ```JavaScript
    {
        "error": null,
        "statusCode": 200,
        "data": [
            {
                "id" : "Member Id",
                "name" : "Member Name",
                "age" : "Member Age",
                "gender" : "Member Gender",
                "teamName" : "Member Team Name"
            }
        ]
    }
    ```

    <오류 응답 예시>

    - 잘못된 요청

        - Response Json
        ```JavaScript
            {
                "error": 에러 메세지,
                "statusCode": 400
                "data" : null
            }
        ```
        
        |에러메세지|내용|
        |------|---|
        |Invalid Information|필요한 정보가 모두 들어오지 않음|
        |Invalid Gender|성별이 올바르게 입력되지 않음|
        |We can't find the team that meets requirements|구성원의 팀이 존재하는 팀임|
        

2. 구성원 전체 조회

    - 요청 URL : ```GET members/totalsearch```
    - Response Json
    ```JavaScript
    {
        "error" : null,
        "statusCode" : 200,
        "data" : [
            {
                "id" : "Member 1 Id",
                "teamName" : "Member 1 Team Name",
                "name" : "Member 1 Name",
                "age" : "Member 1 Age",
                "gender" : "Member 1 Gender",
                
            }
            {
                "id" : "Member 2 Id",
                "teamName" : "Member 2 Team Name",
                "name" : "Member 2 Name",
                "age" : "Member 2 Age",
                "gender" : "Member 2 Gender"
            }
            ...
        ]
    }
        
    ```

3. 구성원 조건부 조회

    - 요청 URL : ```GET members/selectsearch```
    - Request Json
    ```JavaScript
    {
        "option" : option value to search
    }
    ```

    |option|설명|
    |------|---|
    |name|이름으로 구성원들을 검색할 때 사용한다.|
    |team_name|팀의 이름으로 구성원들을 검색할 때 사용한다.|
    |age|나이로 구성원들을 검색할 때 사용한다.|
    |gender|성별로 구성원을 검색할 때 사용한다.|


    - Response Json

    |Http Code|설명|
    |------|---|
    |200|정상적으로 구성원 검색이 이뤄짐|
    |204|검색 조건에 맞는 구성원이 존재하지 않음|
    |400|구성원 검색 조건에 문제가 있음|

    <정상 응답 예시>

    ```JavaScript
    {
        "error": null,
        "statusCode": 200,
        "data": [
            {
                "id" : "Member 1 Id",
                "teamName" : "Member 1 Team Name",
                "name" : "Member 1 Name",
                "age" : "Member 1 Age",
                "gender" : "Member 1 Gender",
                
            }
            {
                "id" : "Member 2 Id",
                "teamName" : "Member 2 Team Name",
                "name" : "Member 2 Name",
                "age" : "Member 2 Age",
                "gender" : "Member 2 Gender"
            }
            ...
        ]
    }
        
    ```
    

4. 구성원 정보 수정

    - 요청 URL : ```POST members/modifyinfo```
    - Request Json
    ```JavaScript
    {
        "id" : 수정하려는 구성원의 ID
        "info" : 수정하려는 구성원의 정보
        "value" : 수정 정보
    }
    ```
    - Response Json

    <정상 응답 예시>

    |Http Code|설명|
    |------|---|
    |200|정상적으로 구성원 검색이 이뤄짐|
    |400|수정하려는 구성원의 정보에 문제가 있음|

    ```JavaScript
        {
            "error": null,
            "statusCode": 200,
            "data": [
                    {
                        "id" : "Member 1 Id",
                        "teamName" : "Reivse Member 1 Team Name",
                        "name" : "Reivse Member 1 Name",
                        "age" : "Reivse Member 1 Age",
                        "gender" : "Reivse Member 1 Gender",
                        
                    }
                ]
        }
        
    ```

    <오류 응답 예시>

    - Response Json
        ```JavaScript
        {
            "error": 에러 메세지,
            "statusCode": 400
            "data" : null
        }
        ```
        
        |에러메세지|내용|
        |------|---|
        |We can't find the member that meets requirements|수정하려는 구성원이 존재하지 않음|
        |We can't find the team that meets requirements|구성원의 팀이 존재하는 팀임|