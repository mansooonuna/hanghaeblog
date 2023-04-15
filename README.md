# hanghaeblog 
> Spring Boot로 로그인 기능이 없는 나만의 항해 블로그 백엔드 서버 만들기

## 요구사항
1. 아래의 요구사항을 기반으로 Use Case 그려보기
2. 전체 게시글 목록 조회 API
  - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
  - 작성 날짜 기준 내림차순으로 정렬하기
3. 게시글 작성 API
  - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고
  - 저장된 게시글을 Client 로 반환하기
4. 선택한 게시글 조회 API 
  - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 
5. 선택한 게시글 수정 API
  - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
  - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
6. 선택한 게시글 삭제 API
  - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
  - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기

## 주의사항
1. Entity를 그대로 반환하지 말고, DTO에 담아서 반환해주세요.
2. JSON을 반환하는 API형태로 진행해주세요. (PostMan을 활용해 서버가 반환하는 결과값을 확인할 수 있습니다.)

## Use Case
![image](https://user-images.githubusercontent.com/97998858/232207991-565b6cf5-0a87-4186-a83b-46f0230ffc8e.png)

## API 명세서
![image](https://user-images.githubusercontent.com/97998858/232209293-27dd7f32-4398-4a4b-8fc9-a8b75e6ea07f.png)

## Why
1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
  -> @RequestBody를 사용했습니다. 클라이언트가 전송하는 JSON 형식의 HTTP Body 내용을 자바 객체로 변환해주기 때문입니다.
  
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
  -> param은 url 뒤에 붙는 파라미터의 값을 가져올 때 사용하고, Body를 직접 조회하지 않습니다. 
     원하는 조건의 데이터, 하나의 데이터를 받아올 때 적절합니다.
  -> query는 url 물음표 뒤에 나오며 변수를 담고 있습니다. key=value로 이루어져 조건을 줘서 원하는 결과를 얻을 수 있습니다. 
     검색, 필터링, sorting에 적절합니다.
  -> PathVariable은 /{id}처럼 구분자에 들어오는 값을 처리할 때 사용합니다. 실습에서는 id를 받아올 때 사용했습니다.
  -> body는 url에 보이지 않고, XML, JSON, Multi Form 등의 데이터를 가져옵니다.
     body에 담은 값을 변환하기 때문에, GET이 아닌 POST 방식에서만 사용이 가능합니다.
     (GET 방식은 Header에 값을 담아 보냅니다.)
3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
  -> controller/service/repository로 레이어 분리를 해주었습니다.
  -> 메소드를 적절하게 사용했습니다. (등록 : POST, 수정 : PUT, 삭제 : DELETE)
  -> 모든 URI에 api가 들어가기 때문에 Mapping을 추가로 해줄 수 있을 것 같습니다. 이 부분은 추가로 수정해 push하겠습니다.
  
4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
  -> 네. 분리해서 설계했습니다.
  
5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
  -> API 명세서를 보다 자세하게 작성할 수 있네요. Request에 Syntax만이 아니라 header, eliments도 들어갈 수 있고,
     Response에도 Syntax, Elements 도 들어갈 수 있고요. 보다 상세하게 작성하는 연습을 해야겠습니다!
     
