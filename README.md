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
* 모드버스 시뮬레이터 메모리 READ 해서 WEB에 실시간으로 보여주기
1. spring batch 사용하는 베치 모듈을 통해 주기적으로 REDIS 에 값 넣기 
2. axios 비동기 처리로 REDIS 값 읽어서 화면에 보여주기

테그 목록 관리
* 페이징 구현
* WEB에 WRITE 해서 시뮬레이터에 값넣기 및 테그 삭제
* 테그와 메모리영역 검증기능

장비 시뮬레이터 활용
* 모드버스 오픈소스 라이브러리 활용해서 read & write
* 추후 다른 종류의 장비 추가를 염두한 interface 활용


## 이미지 및 부연설명

![redis 저장](https://user-images.githubusercontent.com/104551163/182080477-7604b7cc-8d71-4831-aa33-faed3a260eb2.PNG)

* 베치 서버를 통해서 시뮬레이터의 값을 읽어서 redis로 저장 ( 현재 1분주기 ) , 두번째 폴링때 첫번째 폴링때 가지고 있는 값을 REDIS에 저장

![simulator read 화면 창](https://user-images.githubusercontent.com/104551163/182082325-618165db-b5ef-4cda-86dd-60d1bc1472ce.PNG)

* REDIS 에서 10초 단위로 web화면으로 값 가져오기 

<img src="https://user-images.githubusercontent.com/104551163/182088544-01ec3f6a-9e39-410f-a811-3b58c5bbc45e.PNG"   float:left; width: 33% />
<img src="https://user-images.githubusercontent.com/104551163/182088557-894f3daf-dd55-4646-9423-2a56edd1cc92.PNG"   float:left; width: 33% />  
<img src="https://user-images.githubusercontent.com/104551163/182088566-4ac5a2b1-a660-420f-944f-e1c3ec271123.PNG"   float:left; width: 33% />                                                                                                            

* 테그 이름 중복 및 테그 메모리와 데이터 타입  검증 

## DB구조 

![tagWeb (2)](https://user-images.githubusercontent.com/104551163/179693985-8bb8a501-652e-4f0c-b5c8-353072f140a2.png)


