# DEVICE TAG WEB
개인 프로젝트 포트폴리오 - 장비의 메모리값 실시간으로 보여주는 웹사이트 

## 필요 세팅
- H2 DB구성 및 구동
- REDIS 서버 구동
- 모드버스 시뮬레이터 구동
- module-pump >  FRONTEND  에서 react module 다운로드를 위해서 npm install 필요


## 모듈 설명
- module-pump :  기본 웹 , react와 packaging을 위해서 jar 파일로 실행 필요
- module-middle :  batch 서버용 ,  장비의 테그 및 시뮬레이터의 테그정보를 REDIS 로 주기적으로 READ
- module-core :   멀티 모듈을 위한 공통 모듈 (실행 X)



## 사용 기술
- JAVA11 
- SPRING BOOT
- REACT
- GRADLE
- H2 DB
- JPA
- SPRING SECURITY
- SPRING BATCH
- REDIS

## 구현 내용
인증 및 보안
* 회원가입
* JWT

데이터 연계
* 모드버스 시뮬레이터 메모리 READ
* 베치서버 모듈을 통해 주기적으로 REDIS 에 값 넣기

테그 목록 관리
* 페이징 구현
* 시뮬레이터에 WRITE 및 테그 삭제
* 테그와 메모리영역 검증 


## 이미지



## DB구조 

![tagWeb (2)](https://user-images.githubusercontent.com/104551163/179693985-8bb8a501-652e-4f0c-b5c8-353072f140a2.png)


