DROP SCHEMA IF EXISTS `icecreamapp`;
CREATE SCHEMA `icecreamapp` ;
USE icecreamapp;
# 회원 생성
  DROP TABLE IF EXISTS member;
CREATE TABLE `icecreamapp`.`member` (
  `id` INT NOT NULL AUTO_INCREMENT, # 회원번호
  `name` VARCHAR(10) NOT NULL, #이름
  `email` VARCHAR(20) NOT NULL UNIQUE, #이메일
  `password` VARCHAR(20) NOT NULL, #비밀번호
  `purchase_sum` INT NULL DEFAULT 0, #구매합
  `gender` INT NULL, #성별
  `age` INT NULL, #나이
  `notification_token` VARCHAR(200) NULL, #안드로이드 알림 토큰
  PRIMARY KEY (`id`));
  
 
  # 후기
  
  DROP TABLE IF EXISTS review;
  CREATE TABLE `icecreamapp`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT, # 후기번호
  `order_id` INT NOT NULL UNIQUE, # 주문번호
  `member_id` INT NOT NULL, # 회원번호
  `rate` FLOAT NULL, #별점	 
  `date` BIGINT NULL, #작성일자
  `content` VARCHAR(200) NULL, #내용
  PRIMARY KEY (`id`));
  
   # 주문
  
  DROP TABLE IF EXISTS `order`;
  CREATE TABLE `icecreamapp`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT, #주문번호
  `member_id` INT NOT NULL, #회원
  `date` BIGINT NULL, #주문일자
  `spoon` INT NULL DEFAULT 0, #스푼
  `dryice` INT NULL DEFAULT 0, #드라이아이스
  `isforhere` INT NULL DEFAULT 0, #매장/포장
  `discount_sum` INT ,# 할인 합계
  `result_sum` INT ,# 최종 합계
  `price_sum` INT ,# 합계
  PRIMARY KEY (`id`));
  
  
  # 주문상세
  
  DROP TABLE IF EXISTS order_detail;
  CREATE TABLE `icecreamapp`.`order_detail` (
  `id` INT NOT NULL AUTO_INCREMENT, #주문상세번호
  `order_id` INT NOT NULL, #주문번호
  `product_id` INT NOT NULL, #제품번호
  `quantity` INT NULL, #양
  PRIMARY KEY (`id`));
  
    #아이스크림
  DROP TABLE IF EXISTS icecreamapp;
  CREATE TABLE `icecreamapp`.`icecream` (
  `id` INT NOT NULL AUTO_INCREMENT, #제품번호
  `name` VARCHAR(45) NOT NULL, #이름
  `price` INT NOT NULL, #가격
  `isEvent` INT NULL DEFAULT 0, #할인율
  `type` VARCHAR(10) NULL, #유형
  `kcal` INT NULL DEFAULT 0, #칼로리
  `img` VARCHAR(100) NULL, #이미지경로
  `content` VARCHAR(200) NULL, #설명
  `count_male_one` INT DEFAULT 0, #10대주문횟수
  `count_female_one` INT DEFAULT 0, #10대주문횟수
  `count_male_two` INT DEFAULT 0, #20대주문횟수
  `count_female_two` INT DEFAULT 0, #20대주문횟수
  `count_male_three` INT DEFAULT 0, #30대주문횟수
  `count_female_three` INT DEFAULT 0, #30대주문횟수
  `count_male_fourover` INT DEFAULT 0, #40대이상주문횟수
  `count_female_fourover` INT DEFAULT 0, #40대이상주문횟수
  `count_total` INT DEFAULT 0, # 총 주문횟수
  PRIMARY KEY (`id`));

#알림
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `icecreamapp`.`notification`
(
    `id`        INT    NOT NULL AUTO_INCREMENT, #알림 번호
    `member_id` INT    NOT NULL,                #회원 번호
    `order_id` INT    NOT NULL,                 #주문 번호
    `date`      BIGINT NULL,                    #알림 시간
    `type` INT NOT NULL,  #주문 접수 완료, 준비 완료
    PRIMARY KEY (`id`)
);


  #############################
#
#icecream 넣기
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('내가아인슈페너', '3500', '5', '아이스크림', '250', 'imain', '진한 커피 아이스크림, 우유맛 아이스크림에 프레첼 볼과 초콜릿이 어우러진 맛');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('너는참달고나', '3500', '10', '아이스크림', '257', 'ursweet', '달콤한 카라멜 아이스크림에 바삭한 달고나가 쏘옥~');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('뉴욕치즈케익', '3500', '5', '아이스크림', '275', 'nycheese', '부드럽게 즐기는 뉴욕식 정통 치즈케이크 아이스크림');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('마법사의비밀레시피', '3500', '0', '아이스크림', '292', 'secrecipe', '쿨~한 민트 맛과 진한 초콜릿을 담은 달콤한 비밀 레시피');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('블루베리파나코타', '3500', '0', '아이스크림', '226', 'blueberrypana', '이탈리안 디저트 파나코타와 상큼한 블루베리의 부드러운 만남');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('슈팅스타', '3500', '0', '아이스크림', '260', 'shootingstar', '블루베리 & 바닐라향에 입안에서 톡톡 터지는 캔디와 신나는 축제');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('아몬드봉봉', '3500', '0', '아이스크림', '312', 'amdbong', '입안 가득 즐거운 초콜릿, 아몬드로 더욱 달콤하게!');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('엄마는외계인', '3500', '0', '아이스크림', '296', 'mom', '밀크 초콜릿, 다크 초콜릿, 화이트 무스 세 가지 아이스크림에 달콤 바삭한 초코볼이 더해진 아이스크림');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('쫀떡궁함', '3500', '0', '아이스크림', '282', 'jjon', '고소한 흑임자, 인절미 아이스크림에 쫄깃한 떡리본과 바삭한 프랄린 피칸이 쏙쏙');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('치토스밀크쉐이크', '3500', '0', '아이스크림', '248', 'cheetos', '달콤한 밀크쉐이크 아이스크림과 치즈 아이스크림에 초콜릿과 치토스 볼이 가득!');

#케이크 넣기

INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('1979쿠앤크떠먹는큐브', '25000', '5', '케이크', '152', '1979cube', '시그니처 플레이버 탄생년도로 즐기는 바닐라 속 진한 달콤한 블랙 쿠키 가득~ 떠먹어야 더욱 맛있는 1979 쿠앤크 떠먹는 큐브!');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('2007 뉴치케 떠먹는 큐브', '25000', '10', '케이크', '183', '2007cube', '시그니처 플레이버 탄생년도로 즐기는 치즈 풍미 가득~ 고소한 그레이엄 쿠키 가득~ 떠먹어야 더욱 맛있는 2007 뉴치케 떠먹는 큐브!');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('골라먹는스노우볼', '28000', '5', '케이크', '130', 'picksnowball', '동글동글 스노우 볼을 가득! BR의 아이스크림을 한입에 쏙~ 넣어 즐길 수 있는 스노우 볼 케이크');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('나눠먹는큐브와츄원', '33000', '0', '케이크', '126', 'divide', '다양한 종류의 데코와 아이스크림을 트레이로 편리하게 나눠먹고, 보관까지 쉬운 케이크');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('더 베스트 No.6', '25000', '0', '케이크', '121', 'thebest', '6가지의 베스트 플레이버를 부담 없이 맛 볼 수 있는 제품');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('골라먹는 27 큐브', '29000', '0', '케이크', '164', 'pickcube', '9가지 맛 아이스크림 바이트를 초콜릿 판 위에 쌓아올려 전체 다 취식 가능한 실속있는 케이크');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('우주에서 온 엄마는 외계인', '28000', '0', '케이크', '130', 'momcake', 'BR을 대표하는 플레이버! 부동의 NO.1 플레이버 ´엄마는 외계인´을 컨셉으로 한 ONLY BR 케이크');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('한입 가득 티라미수', '26000', '0', '케이크', '121', 'tiramisu', '케이크 상단에 초코&치즈 바이트가 가득 올라간 달콤쌉싸름한 매력의 티라미수 케이크');

#레디팩 넣기

INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('레디팩 (아몬드 봉봉)', '10800', '5', '레디팩', '183', 'amdbongong_rdp', '입안 가득 즐거운 초콜릿, 아몬드로 더욱 달콤하게!');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('레디팩 (엄마는 외계인)', '10800', '10', '레디팩', '190', 'mom_rdp', '밀크 초콜릿, 다크 초콜릿, 화이트 무스 세 가지 아이스크림에 달콤 바삭한 초코볼이 더해진 아이스크림');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('레디팩 (슈팅스타)', '10800', '50', '레디팩', '172', 'shootingstar_rdp', '블루베리 & 바닐라향에 입안에서 톡톡 터지는 캔디와 신나는 축제');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('레디팩 (초코나무 숲)', '10800', '0', '레디팩', '180', 'chocowood_rdp', '그린티와 초콜렛이 만나 초코볼이 열리는 초코나무가 되었어요');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('레디팩 (봉쥬르, 마카롱)', '10800', '0', '레디팩', '178', 'france_rdp', '부드러운 마스카포네 아이스크림과 마카롱, 초콜릿의 달콤한 만남!');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('레디팩 (베리베리 스트로베리)', '10800', '0', '레디팩', '182', 'strawberry_rdp', '새콤상큼 딸기 과육이 듬뿍!');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('레디팩 (31 요거트)', '10800', '0', '레디팩', '188', 'yogurt_rdp', '유산균이 살아 있는 오리지널 요거트 아이스크림');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('레디팩 (레인보우 샤베트)', '10800', '0', '레디팩', '185', 'rainbow_rdp', '상큼한 라즈베리, 파인애플, 오렌지 샤베트');
INSERT INTO `icecreamapp`.`icecream` (`name`, `price`, `isEvent`, `type`, `kcal`, `img`, `content`) VALUES ('레디팩 (체리쥬빌레)', '10800', '0', '레디팩', '178', 'cherry_rdp', '더욱 진해진 체리 쥬빌레에 탱글탱글 체리가 가득');


###################################
#유저 넣기 test, test, test 10대 남성
INSERT INTO `icecreamapp`.`member` (`name`, `email`, `password`, `gender`, `age`) VALUES ('test1', 'test1', 'test1', '1', '10');
# test2 test2 test2 10대 여성
INSERT INTO `icecreamapp`.`member` (`name`, `email`, `password`, `gender`, `age`) VALUES ('test2', 'test2', 'test2', '2', '10');
INSERT INTO `icecreamapp`.`member` (`name`, `email`, `password`, `gender`, `age`) VALUES ('test3', 'test3', 'test3', '1', '20');
INSERT INTO `icecreamapp`.`member` (`name`, `email`, `password`, `gender`, `age`) VALUES ('test4', 'test4', 'test4', '2', '20');
INSERT INTO `icecreamapp`.`member` (`name`, `email`, `password`, `gender`, `age`) VALUES ('test5', 'test5', 'test5', '1', '30');
INSERT INTO `icecreamapp`.`member` (`name`, `email`, `password`, `gender`, `age`) VALUES ('test6', 'test6', 'test6', '2', '30');
INSERT INTO `icecreamapp`.`member` (`name`, `email`, `password`, `gender`, `age`) VALUES ('test7', 'test7', 'test7', '1', '40');
INSERT INTO `icecreamapp`.`member` (`name`, `email`, `password`, `gender`, `age`) VALUES ('test8', 'test8', 'test8', '2', '40');
