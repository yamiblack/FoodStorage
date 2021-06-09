# FoodStorage
- 강의명 : 2021학년도 1학기 모바일 앱 프로그래밍
- 팀명 : Three Amigos
  - [이주형](https://github.com/yamiblack)
  - [정보균](https://github.com/jeongbogyun)
</br>

## 서비스 소개
- 해당 애플리케이션은 ‘유통기한’, ‘쇼핑’, 그리고 ‘식품 관리 리스트’에 초점을 맞춰 서비스를 제공한다. 
- ‘품명 검색’과 ‘유통기한 검색’과 같이 세분화된 검색 기능을 제공한다. 
- ‘요리할 때 듣는 음악’도 제공하여 식품 관리하는데 오락성을 추가했다.
- 식품이 소진되면 이에 대해 평가하여 재구매 의사를 물어 ‘재구매 리스트’를 관리한다. 
</br>

## 시연 영상
[![Video Label](https://user-images.githubusercontent.com/50551349/121334700-db61fd00-c954-11eb-901a-de56df8f4b41.png)](https://youtu.be/Wra2Aca1Rb4)

</br> 
       
## 상세 기능 소개
### 1. Firebase 활용 회원가입 및 로그인

<p align="center"> 
 <img src="https://user-images.githubusercontent.com/50551349/121333861-229bbe00-c954-11eb-8adc-85d0f785871a.jpg" width="300"/>
 <img src="https://user-images.githubusercontent.com/50551349/121333870-24658180-c954-11eb-9a9f-3c2080b542e5.jpg" width="300"/> 
</p>

- Firebase 활용 회원가입 및 로그인 화면은 위 그림과 같다.

<p align="center"> 
 <img src="https://user-images.githubusercontent.com/50551349/121333938-347d6100-c954-11eb-8b2b-a6324c2d7b15.jpg" width="300"/>
 <img src="https://user-images.githubusercontent.com/50551349/121334220-70182b00-c954-11eb-808a-c40a286fd110.jpg" width="300"/> 
</p>
 
- 좌측은 회원가입 오류 화면을, 우측은 회원가입 성공 화면을 나타낸다. 

<p align="center"> 
 <img src="https://user-images.githubusercontent.com/50551349/121333951-36dfbb00-c954-11eb-8123-01789bc0453e.jpg" width="300"/>
 <img src="https://user-images.githubusercontent.com/50551349/121333954-37785180-c954-11eb-9f8c-c0ea91efd1b1.jpg" width="300"/> 
</p>

- 좌측은 로그인 오류 화면을, 우측은 로그인 성공 화면을 나타낸다. 
</br>

### 2. 식품 리스트 및 식품 추가

<p align="center"> 
 <img src="https://user-images.githubusercontent.com/50551349/121329852-8623ec80-c950-11eb-8c60-798e52ec5af5.jpg" width="300"/>
 <img src="https://user-images.githubusercontent.com/50551349/121329845-83c19280-c950-11eb-8f9e-da3b50cf0044.jpg" width="300"/> 
</p>

- 위 그림과 같이 식품 리스트의 기능을 제공하며 이를 식품과 알림에 대하여 관리할 수 있다. 
- 우측 상단의 + 버튼을 통해서 식품을 추가할 수 있다. 
</br>

<p align="center"> 
  <img src="https://user-images.githubusercontent.com/50551349/121330233-c8e5c480-c950-11eb-822d-b4095f33cee2.jpg" width="300"/> 
  <img src="https://user-images.githubusercontent.com/50551349/121330223-c6836a80-c950-11eb-848d-abf6eef4f7aa.jpg" width="300"/>
</p>

- 위 그림과 같은 화면을 통해서 리스트에 식품을 추가할 수 있다. 
- 사용자가 입력해야 될 정보는 ‘식품명’, ‘식품 개수’, 그리고’ 유통기한’이다. 
- 모든 입력이 마치고 우측상단의 ‘추가’ 버튼을 터치하면 리스트에 사용자의 식품이 추가된다.
</br>

### 3. 기한 및 알림
<p align="center"> 
  <img src="https://user-images.githubusercontent.com/50551349/121330720-3c87d180-c951-11eb-9668-520e088345cf.jpg" width="300"/> 
  <img src="https://user-images.githubusercontent.com/50551349/121330743-40b3ef00-c951-11eb-8a70-636914a281cd.jpg" width="300"/>
</p>

- 식품 리스트에서는 유통기한을 확인할 수 있다.
- 유통기한을 확인하고 이에 대한 알림도 별도로 설정할 수 있다. 
- 좌측의 그림과 같이 ‘n일 전’으로 알림 주기를 설정할 수 있고, 아예 알림을 꺼버릴 수도 있다. 
- 해당 알림을 설정하고 해당 날짜 혹은 유통기한이 가까워지면 우측의 그림과 같이 알림이 상단바에서의 푸시 알림을 확인할 수 있다. 
- 푸시 알림에는 해당 품목의 유통기한이 얼마나 남았는지 포함한다. 
- 사용자가 알림을 설정할 경우 따로 Activity를 생성하지 않고 우측 하단의 Floating Action Button을 이용해서 간편하게 조작할 수 있도록 했다.
</br>

### 4. 재구매 평가 및 리스트

<p align="center"> 
  <img src="https://user-images.githubusercontent.com/50551349/121331293-b15b0b80-c951-11eb-9bc7-e72efb9c839e.jpg" width="300"/> 
  <img src="https://user-images.githubusercontent.com/50551349/121331296-b28c3880-c951-11eb-84f2-72f25324ee98.jpg" width="300"/>
</p>

- 식품의 개수가 소진되어 ‘사용완료’ 버튼을 터치할 경우 좌측의 그림과 같이 재구매 의사를 평가할 수 있는 화면이 나타난다. 
- 해당 화면에서 좌측의 웃는 버튼을 터치할 경우 해당 식품은 재구매 리스트로 이동하고, 우측의 버튼을 터치할 경우 해당 식품은 재구매 식품 리스트로 이동하지 않는다. 
- 우측의 그림은 좌측의 그림에서 웃는 버튼을 클릭하였을 때 식품 리스트에 식품이 추가된 것을 나타낸다. 
</br>

### 5. 검색 기능

<p align="center"> 
  <img src="https://user-images.githubusercontent.com/50551349/121331969-58d83e00-c952-11eb-9d4e-8156c5f2c54c.jpg" width="300"/> 
  <img src="https://user-images.githubusercontent.com/50551349/121331975-5aa20180-c952-11eb-8ba0-f2c9c65d04c9.jpg" width="300"/>
</p>

- ‘식량창고’는 ‘품목 검색’과 ‘유통기한 검색’과 같이 2가지의 검색 서비스를 제공한다. 
- 원하는 검색 기능에서 우측의 그림에서 ‘검색 하기’ 버튼을 터치하면 좌측의 그림과 같이 리스트로 검색 결과를 제공한다.
</br>

### 5. 요리 음악 기능

<p align="center"> 
  <img src="https://user-images.githubusercontent.com/50551349/121332925-46123900-c953-11eb-935b-24f9f38d0b9e.jpg" width="300"/> 
  <img src="https://user-images.githubusercontent.com/50551349/121332935-49a5c000-c953-11eb-9889-c1ba39b990e4.jpg" width="300"/>
</p>

- ‘식량창고’는 요리할 때 듣는 음악 서비스도 제공한다. 
- 좌측 그림과 같이 순서대로 음악을 재생할 수도 있고, 우측 그림과 같이 랜덤으로 음악을 재생할 수도 있다. 
</br>

## 추후 보완 내용(예정)
- 새로운 오류 및 버그 수정
- 공용 냉장고 추가
- OCR 기능 추가
- 냉장고 추가
</br> 


## 사용 기술 스택
- Android(Java)
- Firebase
