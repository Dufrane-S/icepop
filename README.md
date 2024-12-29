# IcePop
## 개요
IcePop은 아이스크림 소비자용 스마트 스토어 앱으로 구매, 메뉴 확인 등의 기능을 제공합니다.

## 사용 기술 스택
### SpringBoot, MySQL, MyBatis

## 서버 실행 방법

1. 최상위 폴더의 icecreamapp.jar과 icepop.sql을 다운 받습니다.
2. MySQL에 계정명 ssafy, 암호 ssafy 계정을 만들고 icepop.sql을 실행합니다.
3. java -jar를 통해 실행합니다. (자바 LTS 21 버전을 사용하였습니다.)
4. http://localhost:8080/swagger-ui/index.html 에서 작동을 확인합니다.

## ERD

![ERD IMG](https://github.com/user-attachments/assets/b9074bb6-f5d5-44a1-a7ae-3dbbe616642d)

https://www.erdcloud.com/d/srt39q4vzhfyGSaCN

## 주요 기능
1. [주문 및 주문 내역 조회, 주문 완료시 FCM PUSH를 통한 알림 기능](https://github.com/Dufrane-S/icepop/blob/master/server/icecreamapp/src/main/java/com/ssafy/icecreamapp/service/OrderServiceImpl.java)

2. [예외처리를 통해 에러메시지를 적절히 처리](https://github.com/Dufrane-S/icepop/blob/master/server/icecreamapp/src/main/java/com/ssafy/icecreamapp/handler/GlobalExceptionHandler.java)

3. [상품 조회 기능, 전체 주문 내역을 이용한 연령별, 성별별 판매순 조회기능](https://github.com/Dufrane-S/icepop/blob/master/server/icecreamapp/src/main/resources/mappers/icecreammapper.xml)

4. [회원 가입, 로그인](https://github.com/Dufrane-S/icepop/blob/master/server/icecreamapp/src/main/java/com/ssafy/icecreamapp/service/MemberServiceImpl.java)

5. [주문 1건당 1개의 리뷰 작성 및 수정 기능 ](https://github.com/Dufrane-S/icepop/blob/master/server/icecreamapp/src/main/java/com/ssafy/icecreamapp/service/ReviewServiceImpl.java)

6. [사용자의 주문 내역과 ChatGPT Api를 이용한 사용자별 메뉴 AI 추천 기능 ](https://github.com/Dufrane-S/icepop/blob/master/server/icecreamapp/src/main/java/com/ssafy/icecreamapp/service/AiService.java)(api 키 소진으로 작동 X)




## 시연 영상
https://www.youtube.com/watch?v=as0I6TCWNug