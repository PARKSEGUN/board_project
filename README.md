# Spring Boot를 이용해서 게시판 만들기[![Build Status](https://app.travis-ci.com/PARKSEGUN/board_project.svg?branch=main)](https://app.travis-ci.com/PARKSEGUN/board_project)


## 개발환경
- IDE: IntelliJ IDEA Community
- Spring Boot 2.7.12
- JDK 1.8


## Dependencies
- Mysql
- Spring Data JPA
- Thymeleaf
- Spring Web
- Spring Security
- Lombok

## 주요 기능
- 회원관리
    - 회원가입
    - 로그인(spring security, kakaoLoginAPI 사용)
    - 상세조회
    - 수정
    - 삭제
- 게시판
   - 게시글 작성
  - 게시글 수정
  - 게시글 삭제
  - 댓글 작성
  - 댓글 삭제
  - 댓글 수정
  - 게시글 검색
  - 게시글 페이징 처리
  - 관리자 기능
  - 카테고리 기능
  - 대댓글 기능
  

## application.yml 설정

```
# 서버 포트 설정
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: UTF-8
      enabled: true
      force: true

# database 연동 설정
spring:
  mvc:
#    view:
#      prefix: WEB-INF/views/
#      suffix: .html

  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/blogproject?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: root
    password: qkrtprjs0505
  thymeleaf:
    cache: false

  # spring data jpa 설정
  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: create
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
        #DB컬럼에 저장될 이름의 전략을 설정(<- 이것은 java로 씌여져있는그대로 설정)
      use-new-id-generator-mappings: false  #JPA에서 기본적으로 제공하는 전략을 따라가지 않겠다
    show-sql: true  #테이블이 만들어지는 sql이 보여진다
    properties:
      hibernate.format_sql: true  #sql이 깔끔하게 보여진다
#cos:
#  key: cos1234
#  jackson:
#    serialization:
#      fail-on-empty-beans: false
```

## 데이터 베이스
create table board_table
```
create table User (
       id integer not null auto_increment,
        createTime datetime(6),
        loginId varchar(255) not null,
        name varchar(255) not null,
        password varchar(255) not null,
        phoneNumber varchar(255) not null,
        role varchar(255),
        username varchar(255) not null,
        primary key (id)
    )
    
    create table Reply (
       id integer not null auto_increment,
        content varchar(255) not null,
        createTime datetime(6),
        boardId integer,
        userId integer,
        primary key (id)
    )
    
    create table Board (
       id integer not null auto_increment,
        content longtext,
        createTime datetime(6),
        hit integer,
        title varchar(255) not null,
        userId integer,
        primary key (id)
    )
```
