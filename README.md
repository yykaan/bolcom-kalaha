# Kalaha Game for bol.com case study

Kalah (Kalaha or Mancala) Board Game. For more information visit https://en.wikipedia.org

![How to play mancala](https://www.thesprucecrafts.com/thmb/YFWtK83D2opopm846zMVs3kFbPE=/700x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/SPR_409424-how-to-play-mancala-a8d885a87c904a2babb946896f1870bb.gif)

(reference https://www.thesprucecrafts.com/how-to-play-mancala-409424 )

# Information

- :fairy: Spring boot is used for fast development since it takes care of everything like automating magical bean carrier code fairies
- :hourglass_flowing_sand:Hibernate and H2 works like charm and they are the key of fast development since this is a case study, time is money!
- :moneybag:Lombok saved me a lot of time for not letting me type boilerplate code at compile time, again time is money!
- :gear:Everything needs to work! Junit put them in line.
- :bookmark_tabs:Documenting your code with Javadoc is important but how about Rest api documentation ? OpenAPI is the go!


# Tech Stack

Backend;

* Spring Boot
* Hibernate
* H2 DB
* Lombok
* Spring Security
* Junit5/Mockito
* OpenApiv3
* Maven

![How to play mancala](https://www.infotechacademy.com.tr/content/blog/frontback2.png)

Frontend

* Angular
* Bootstrap

# How to run ?

- Spring boot will take care of database creation but do not forget since H2 is used, everything will be deleted each application cycle!
  - Backend will be running at http://localhost:8080
  - H2 console is accessible at http://localhost:8080/h2-console. (no password, schh:shushing_face:)
  - REST Documentation is accessible at http://localhost:8080/swagger-ui/index.html
- To run frontend you must access your terminal and go to /src/main/bolcom-kalaha-fe directory
  - run `npm-install`
  - Wait for 8 hours, then come back..
  - run `ng serve`
  - Ta da! frontend will be accessible at http://localhost:4200
 
# Screen Shots (Not Sorry for top level UI/UX:facepalm:)

Login
![Screen Shot 2022-04-13 at 22 12 18](https://user-images.githubusercontent.com/16269104/163253176-2433da74-f76b-452b-bb0c-de4201d43d42.png)
Register
![Screen Shot 2022-04-13 at 22 13 30](https://user-images.githubusercontent.com/16269104/163253347-a209049d-d511-465b-9ed6-c16df2da7aaa.png)
Lobby
![Screen Shot 2022-04-13 at 22 14 23](https://user-images.githubusercontent.com/16269104/163253464-1634c35a-e4c8-4731-a1a7-d97781569779.png)
Lobby (List of available games)
![Screen Shot 2022-04-13 at 22 15 07](https://user-images.githubusercontent.com/16269104/163253575-725fc0f6-9152-4975-ae09-ad7242c66e57.png)
Game
![Screen Shot 2022-04-13 at 22 15 42](https://user-images.githubusercontent.com/16269104/163253621-c8fa8686-77b3-4159-8a8a-e9d2575aa3b9.png)


