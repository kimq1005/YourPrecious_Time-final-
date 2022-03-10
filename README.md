# Your_PreciousTime(경기교통정보서비스 - MVVM 도입중)

학교에 등교를 하기 위해서는 버스->지하철→셔틀버스로 환승을 해야 합니다. 등교할 때마다 버스 시간을 검색하고 지하철 시간표를 찾아봐야 하는 게 불편해서 모든 것을 한눈에 볼 수 있는 서비스를 개발하면 어떨까 하는 생각으로 개발을 시작했습니다. 버스정류소와 지하철역을 검색해 버스,지하철의 정보를 알 수 있으며  원하는 정류장,역에 대한 즐겨찾기 기능으로 버스, 지하철의 도착정보를 한눈에 볼 수 있도록 하였습니다. 

프로젝트는 총 2차로 구성했습니다. 

1차. 초기 기획안으로 프로젝트 완성 (2021.10 ~ 2022.02 End)  https://github.com/kimq1005/Your_PreciousTime (End).  

2차. 클린 코딩 및 코드 리펙토링을 통한 성능향상 (2022.02 ~ Ing).



# ⭐️주요 기능
* 도착정보가 알고싶은 버스정류장, 지하철역의 이름을 검색가능
* 해당 정류장(역)에 대한 버스(지하철) 정보들을 확인가능
* 원하는 정류장(역)을 즐겨찾기에 추가할 수 있으며, 한눈에 확인 가능  

  
    
    
# 🛠Tech Stack
* Kotlin
* MVC , MVVM
* JetPack(LiveData, ViewModel, DataBinding, Room)
* Retrofit2, OKHttp, Gson 
* Coroutine
* GoogleMap


# 🧑🏻‍💻역할
* 개인프로젝트(기획, 디자인 , 개발)

# 📖배운점(1차)
* Android의 lifecycle 개념에 대해서 알게되었음.
* 현재 사용하고 있는 아키텍쳐가(MVC) 무엇인지 이해할 수 있었음.
* 디버깅을 위한 로그를 적극적으로 활용하면서 데이터확인을 보다 편리하게 할 수 있었음.
* Xml데이터를 파싱하는 법을 알게 되었음.
* Retrofit2로 가져온 공공데이터들(XML , Json)을 원하는 뷰에 처리할 수 있음.



# 📖배운점(2차)
* MVVM패턴을 도입함으로써 이에 대한 이점과 개념을 알 수 있었음.
* JetPack(DataBinding, LiveData, ViewModel, Room)의 사용법과 이점을 알게 되었음.
* 싱글톤 패턴을 적극 사용함으로써 이에 대한 이점을 알 수 있었고 반복코드를 줄일 수 있었음.
* 주석 작성의 중요성을 깨닫게 되었음. 



# 🧐개선사항 및 깨달은 점

* **UI/UX**

  프로젝트를 진행 하면서 나름 고민해서 만든 UI/UX를 구동시키니까 불편하고 부족한 부분이 많았습니다. 덕분에 어떻게 해야 서비스 이용자들이 더욱 편리하게 서비스를 사용할 수 있을것인지, 어떤 디자인이 보기 편할지에 대한 UI/UX또한 중요하게 고려할점 중 하나라는 것을 깨닫게 되었습니다.
  
* **클린코딩의 중요성**
 
  프로젝트를 진행 하면서 변수이름과 엑티비티의 이름도 헷갈림은 물론 네트워크통신때도 사용했던 URL도 뒤죽박죽이 되어서 몇번이고 다시 확인하는 상황이 자주 일어났습니다.  이 상태로 협업을 하게되면 팀원들에게 민폐를 끼칠 수 있다는 생각에 코드를 남들이 알아보기 쉽고 간결하게 만드는 것이 중요하다는 것을 깨달았고 설명이 필요한 곳에는 주석을 작성의 중요성을 알게 되었습니다.
  
  
 



# 📷스크린샷
* 메인 화면  

  <img src = "https://user-images.githubusercontent.com/68366753/154833313-2a0e1467-56d8-444a-8d4e-65f875c1fce5.png" width="25%" height="25%">   
    
      
      
  
  
* 정류장(역) 검색

  <img src = "https://user-images.githubusercontent.com/68366753/154833313-2a0e1467-56d8-444a-8d4e-65f875c1fce5.png" width="20%" height="20%">  <img src = "https://user-images.githubusercontent.com/68366753/154833623-b78084e8-0876-481e-81ee-37095d575020.png" width="20%" height="20%">  <img src = "https://user-images.githubusercontent.com/68366753/154833637-3c48804e-a15b-4749-aed2-5c204424a4e3.png" width="20%" height="20%">
  <img src = "https://user-images.githubusercontent.com/68366753/154833703-77f69573-f9a3-4a9e-be57-328d5554961b.png" width="20%" height="20%">
  <img src = "https://user-images.githubusercontent.com/68366753/156972490-35405ebe-15b3-4945-828b-03424f39ebf0.png" width="20%" height="20%">
 

 

* 즐겨찾기  
  
  <img src = "https://user-images.githubusercontent.com/68366753/154833778-2555b0fb-5ad0-4151-9f2a-69a81e93b436.png" width="20%" height="20%">  <img src = "https://user-images.githubusercontent.com/68366753/154833794-e0d1a9a0-d352-428f-b41f-8a97f8fed582.png" width="20%" height="20%">  <img src = "https://user-images.githubusercontent.com/68366753/154833814-9eeed1a5-78d2-4b4a-976a-b6c8145bc3ca.png" width="20%" height="20%">  
  
  


  





  

  


  
