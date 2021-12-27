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
    ```JavaSript
        {
            "name" : Team Name
        }
    ```
    - Response Json
    ```JavaScript
        {
            "id" : Team Id
            "name" : Team Name
        }
    ```
    - 잘못된 요청

        <이미 있는 팀 이름으로 팀을 생성하려고 하는 경우>
        - Response Json
        ```JavaScript
            {
                "error": "Team name is already exist.",
                "statusCode": 400,
                "data": null
            }
        ```
    
    
2. 팀 전체 조회

    - 요청 URL : ```GET teams/totalsearch```
    - Response Json
    ```JavaScript
    [
        {
            "id" : Team 1 Id 
            "name" : Team 1 Name 
        }
        {
            "id" : Team 2 Id 
            "name" : Team 2 Name 
        }
        ...
    ]
        
    ```

3. 팀 조건부 조회

    - 요청 URL : ```GET teams/selectsearch```
    - Request Json
    ```JavaScript
    {
        "option" : option value to search
    }
    ```
    - Response Json
    ```JavaScript
        {
            "id" : Team Id
            "name" : Team Name
        }
    ```
    - 잘못된 요청

        <검색하려는 팀이 존재하지 않는 경우>
        - Response Json
        ```JavaScript
            {
                "error": "Requested team doesn't exist.",
                "statusCode": 400,
                "data": null
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
    ```JavaScript
        [
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
        ]
        ...
    ```
    - 잘못된 요청

        <검색하려는 팀이 존재하지 않는 경우>
        - Response Json
        ```JavaScript
            {
                "error": "Requested team doesn't exist.",
                "statusCode": 400,
                "data": null
            }
        ```

> ## 사내 구성원 관리
사내 구성원 관리 관련해서는 다음과 같은 기능을 지원합니다.

1. 사내 팀 등록

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
    ```JavaScript
        {
            "id" : "Member Id",
            "name" : "Member Name",
            "age" : "Member Age",
            "gender" : "Member Gender",
            "teamName" : "Member Team Name"
            
        }
    ```

    - 잘못된 요청

        <생성하려고 하는 사람의 정보를 모두 전달받지 못한 경우>
        - Response Json
        ```JavaScript
            {
                "error": "Invalid Information.",
                "statusCode": 400,
                "data": null
            }
        ```

2. 구성원 전체 조회

    - 요청 URL : ```GET members/totalsearch```
    - Response Json
    ```JavaScript
    [
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
        
    ```

3. 구성원 조건부 조회

    - 요청 URL : ```GET members/selectsearch```
    - Request Json
    ```JavaScript
    {
        "option" : option value to search
    }
    ```
    - Response Json
    ```JavaScript
        [
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
    ```
    - 잘못된 요청

        <검색하려는 구성원이 존재하지 않는 경우>
        - Response Json
        ```JavaScript
            {
                "error": "There's no member who meets the requirements.",
                "statusCode": 400,
                "data": null
            }
        ```

4. 구성원 정보 수정

    - 요청 URL : ```POST members/modifyinfo```
    - Request Json
    ```JavaScript
    {
        "id" : "Member Id"
        "info" : "Type of Information that you want to change"
        "value" : "Revise Value"
    }
    ```
    - Response Json
    ```JavaScript
        [
        {
            "id" : "Member 1 Id",
            "teamName" : "Reivse Member 1 Team Name",
            "name" : "Reivse Member 1 Name",
            "age" : "Reivse Member 1 Age",
            "gender" : "Reivse Member 1 Gender",
            
        }
        
        ...
    ]
    ```

    - 잘못된 요청

        <정보를 수정하려는 구성원이 존재하지 않는 경우>
        - Response Json
        ```JavaScript
            {
                "error": "Request Member doesn't exist.",
                "statusCode": 400,
                "data": null
            }
        ```

        <바꾸려는 팀이 존재하지 않는 경우>
        - Response Json
        ```JavaScript
            {
                "error": "Requested Team is not existed.",
                "statusCode": 400,
                "data": null
            }
        ```