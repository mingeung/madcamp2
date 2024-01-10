# madcamp_week2

## 조원 소개

손다윤: 로그인 구현, 탭1 및 탭3 구현
정민승: 탭2(커뮤니티) 구현 및 전체 디자인

## 앱 소개

MadCo는 몰입캠프 참여자들이 자유롭게 활동할 수 있는 커뮤니티 앱입니다. 
잡담을 나누고, 함께 밥을 먹을 사람을 구하거나, 개발 파트너를 찾아보세요!

## 개발 환경

- Frontend: Android Studio(Kotlin)
- Backend: Django
- Database: MySQL
- Server: kcloudvpn.kaist.ac.kr을 활용한 ssh 접속

## 초기 ERD

![Untitled-4](https://github.com/mingeung/madcamp2/assets/119600579/f5791d7a-60e9-4f88-80fb-c3e026c392df)


## 기능 소개
<img width="471" alt="Untitled" src="https://github.com/mingeung/madcamp2/assets/119600579/089801e5-37df-423e-a95d-aae9bdc90a00">


왼쪽의 사진은 초기 화면입니다. 자체 로그인과 카카오 로그인을 지원합니다. 비즈 앱 신청이 불가능한 관계로 카카오에서 이메일 정보를 받아오지 못해, 부득이하게 닉네임을 기반으로 로그인을 구성하였습니다. 

회원가입 화면에서는 이메일, 비밀번호, 이름, 닉네임 텍스트를 입력받고 분반을 선택합니다. 카카오 로그인을 누른 경우, 해당하는 닉네임이 이름으로 등록되어 있으면(보통 카톡 닉네임은 사람 이름인 것을 고려) 회원가입 창에 바로 이름이 나오도록 구현하였습니다. 그렇지 않으면 일반적인 로그인을 진행합니다. 

회원가입이나 로그인이 완료되면 메인 화면으로 이동합니다.

<img width="156" alt="Untitled-2" src="https://github.com/mingeung/madcamp2/assets/119600579/b740bd55-c1da-485f-960b-152e2f014351">


첫 번째 화면입니다. 첫 주차의 경험을 바탕으로 CalendarView와 간단한 TextView를 활용해 다음 발표까지 남은 날을 출력하도록 만들었습니다. 오늘의 경우, 두 번째 발표일인 1월 10일이므로 ‘Today is 2nd participation.\n good luck!’이 출력되는 것을 볼 수 있습니다. 



<img width="314" alt="Untitled-5" src="https://github.com/mingeung/madcamp2/assets/119600579/e1125d0c-0e6c-4afb-82b9-1b2420695208">

두 번째 화면입니다. ‘에브리타임’과 유사하게 익명으로 글과 댓글을 달 수 있습니다. title, content, created_at으로 구성되어 있는 Post django 모델을 만들었습니다.  Retrofit을 사용하여 Django 서버에서 데이터를 가져오고 저장할 수 있습니다.

![Untitled-3](https://github.com/mingeung/madcamp2/assets/119600579/c1cb0de1-5d90-4f5a-991b-2e825920cd76)

세 번째 화면입니다. 현재 접속한 본인의 profile을 보여줍니다. 위에서부터 차례대로 프로필 사진, 닉네임, 분반과 이메일 정보를 보여줍니다.
