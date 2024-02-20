![header](https://capsule-render.vercel.app/api?type=rect&height=300%&width=150%&color=fefae0&text=다%20각%20&section=header&reversal=false&textBg=false&fontAlign=50&desc=스터디%20랜덤%20매칭&fontColor=dda15e&rotate=-1&descAlignY=71&fontSize=100&animation=twinkling)

## 목차  
[1. 서비스 소개](#1-서비스-소개)  
[2. 개발환경](#2-개발환경)  
[3. 시스템 아키텍처](#3-시스템-아키텍처)  
[4. ERD](#4-erd)  
[5. 주요 기능](#5-주요-기능)  
[6. 기술적 차별점](#6-기술적-차별점)  
[7. 서비스 화면](#7-서비스-화면)  
[8. 맡은 역할](#8-맡은-역할) 


![](https://capsule-render.vercel.app/api?type=transparent&height=50&color=gradient&text=1.%20서비스%20소개&fontAlign=14.4&fontColor=bc6c25&fontSize=40&fontAlignY=63)
---
<b>기획 배경</b>

- 혼자 공부하는데 의지가 꺾이는 사람들을 위해서, 같은 목표를 가진 사람들을 소개하고 함께 체계적인 공부환경을 제공할 수 없을까?

<b>목표</b>

- 같은 과목을 공부하는 사람들을 신속하게 매칭시켜줄 수 있다
- 공부 스케쥴의 시스템적인 관리를 통해 공부 능률을 올려줄 수 있다

<br>

![](https://capsule-render.vercel.app/api?style=flat-square&type=transparent&height=50&color=gradient&text=2.%20개발환경&fontAlign=12.5&fontColor=bc6c25&fontSize=40&fontAlignY=63)
---
<b>기술스택</b>

- Front-End: ![](https://img.shields.io/badge/Vue.js-35495E?style=flat-square&logo=vuedotjs&logoColor=white) ![](https://img.shields.io/badge/SCSS-CC6699?style=flat-square&logo=Sass&logoColor=white)
- Back-End: ![](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white) ![](https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white)
- Database: ![](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white) ![](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white)
<br>

<b>프로젝트 기간, 인원</b>

- 2024.01.02 ~ 2024.02.16 (7주)
- 백엔드 4명, 프론트엔드 2명


<br>

![](https://capsule-render.vercel.app/api?style=flat-square&type=transparent&height=50&color=gradient&text=3.%20시스템%20아키텍처&fontAlign=20.5&fontColor=bc6c25&fontSize=40&fontAlignY=63)
---
<br>
<p align="center">
    <img src="./assets/architecture.png" width="80%" height="80%" />
</p>
<br>

![](https://capsule-render.vercel.app/api?type=transparent&height=50&color=gradient&text=4.%20ERD&fontAlign=7&fontColor=bc6c25&fontSize=40&fontAlignY=63)
---

![다각_ERD](./assets/다각_ERD.png)
<br>

![](https://capsule-render.vercel.app/api?style=flat-square&type=transparent&height=50&color=gradient&text=5.%20주요%20기능&fontAlign=13&fontColor=bc6c25&fontSize=40&fontAlignY=63)
---
<p align="center">  
    <img src="./assets/dagak.png" width="10%" height="10%" />
    <img src="./assets/study.png" width="10%" height="10%"/>
    <img src="./assets/wing.png" width="10%" height="10%" />
    <img src="./assets/mokkoji.png" width="10%" height="10%" />
    <img src="./assets/avatar.png" width="10%" height="10%" />
</p>  


- **체계적인 공부환경** : 오늘의 공부 스케쥴(이하 다각)에 따라서 자동으로 공부시간 관리/스터디룸 관리를 해줍니다.  

- **맞춤형 스터디룸** : 나와 같은 목표를 가진 사람들을 매칭해줍니다.  

- **신속한 공부환경** : 시작과 동시에 랜덤 매칭으로 즉시 스터디룸에 입장시켜줍니다.  

- **함께하는 모꼬지** : 모임에 가입하고 함께 공부할 수 있습니다.  

- **맞춤형 아바타** : 공부시간에 따른 포인트 지급으로 자신의 아바타를 꾸밀 수 있습니다.  
<br>

 ![](https://capsule-render.vercel.app/api?style=flat-square&type=transparent&height=50&color=gradient&text=6.%20기술적%20차별점&fontAlign=17.5&fontColor=bc6c25&fontSize=40&fontAlignY=63)

## 1. Recapcha

구글API를 이용한 브루트포스 / 매크로 방지
<p align="center">  
    <img src="./assets/recapcha.png" width="60%" height="50%" />
</p>



## 2. 다각 생성 로직

공부 과목 갯수와 일정에 따른 다각형 모양
<p align="center">  
    <img src="./assets/circle.png" width="10%" height="10%" />
    <img src="./assets/triangle.png" width="10%" height="10%"/>
    <img src="./assets/square.png" width="10%" height="10%" />
    <img src="./assets/dagak.png" width="10%" height="10%" />
</p>

## 3. 실시간 상호작용

사용자와 서버와의 웹소켓 통신과,구독/발행을 통해 실시간 상호작용 시스템
<p align="center">  
    <img src="./assets/websocket.png" width="50%" height="50%" />
</p>

![](https://capsule-render.vercel.app/api?style=flat-square&type=transparent&height=50&color=gradient&text=7.%20서비스%20화면&fontAlign=15&fontColor=bc6c25&fontSize=40&fontAlignY=63)
---

### 1. 메인
![main](./assets/main.png)

### 2. 유저/모꼬지 랭킹

![ranking_1](./assets/ranking_1.png) |![ranking_2](./assets/ranking_2.png)
--- | --- |  

### 3. 스터디룸
![studyroom](./assets/studyroom.png)
![studyroom_qna](./assets/studyroom_qna.png)
![studyroom_next](./assets/studyroom_next.png)

### 4. 친구/모꼬지 신청, 모꼬지 페이지
![apply](./assets/apply.png)
![mokkoji_page](./assets/mokkoji_page.png)

### 5. 마이페이지
- 스케줄 관리
![mypage_schedule](./assets/mypage_schedule.png)
![mypage_mydagak_list](./assets/mypage_mydagak_list.png)
![mypage_mydagak_detail](./assets/mypage_mydagak_detail.png)
![mypage_addschedule](./assets/mypage_addschedule.png)

- 친구, 질문, 알림 목록 
<p align="center">  
    <img src="./assets/mypage_friend.png" width="32%">
    <img src="./assets/mypage_qna.png" width="32%">
    <img src="./assets/mypage_alarm.png" width="32%">
</p>

- 보관함
![mypage_inven](./assets/mypage_inven.png)

- 정보
![mypage_info](./assets/mypage_info.png)

### 6. 상점
![mypage_info](./assets/store.png)

<br>

![](https://capsule-render.vercel.app/api?style=flat-square&type=transparent&height=50&color=gradient&text=8.%20맡은역할&fontAlign=12&fontColor=bc6c25&fontSize=40&fontAlignY=63)
---

### 최영진
- **프론트엔드 담당**
    - Vue3, Pinia(상태 관리 라이브러리), scss, eslint, prettier

- **담당 기능**
    - vue 디렉토리 구조 구축
    
    - 회원가입, 로그인
    - 마이페이지
        - 스케줄 관리
        - 친구 목록
        - 질문 목록
        - 알림 관리
        - 아이템 보관함
        - 내 정보 수정
    - 친구/모꼬지 신청, 수락, 거절
    - 모꼬지 상세페이지
    - 상점
