# Kalaha Game for bol.com case study 

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=yykaan_bolcom-kalaha&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=yykaan_bolcom-kalaha)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=yykaan_bolcom-kalaha&metric=bugs)](https://sonarcloud.io/summary/new_code?id=yykaan_bolcom-kalaha)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=yykaan_bolcom-kalaha&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=yykaan_bolcom-kalaha)

Kalah (Kalaha or Mancala) Board Game. For more information visit https://en.wikipedia.org

![How to play mancala](https://www.thesprucecrafts.com/thmb/YFWtK83D2opopm846zMVs3kFbPE=/700x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/SPR_409424-how-to-play-mancala-a8d885a87c904a2babb946896f1870bb.gif)

(reference https://www.thesprucecrafts.com/how-to-play-mancala-409424 )

# Information

- :zap: Spring boot is used for fast development since it takes care of everything like automating magical bean carrier code fairies
- :hourglass_flowing_sand: Hibernate and H2 works like charm and they are the key of fast development since this is a case study, time is money!
- :moneybag: Lombok saved me a lot of time for not letting me type boilerplate code at compile time, again time is money!
- :briefcase: Everything needs to work together! Junit keeps them organized.
- :memo: Documenting your code with Javadoc is important but how about Rest api documentation ? OpenAPI is the go!


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
  - H2 console is accessible at http://localhost:8080/h2-console. (no password, schh :hushed:)
  - REST Documentation will be accessible here http://localhost:8080/swagger-ui/index.html
- To run frontend you must access your terminal and go to /src/main/bolcom-kalaha-fe directory
  - run `npm-install`
  - Wait for 8 hours, then come back..
  - run `ng serve`
  - Ta da! frontend will be accessible at http://localhost:4200

# Screen Shots (Not Sorry for top level UI/UX :eyes:)

## Login

![Screen Shot 2022-04-14 at 12 53 47](https://user-images.githubusercontent.com/16269104/163362224-cca385a2-e5ee-4f2a-98bc-326ac3e7c111.png)

## Register

![Screen Shot 2022-04-14 at 12 54 44](https://user-images.githubusercontent.com/16269104/163362264-6838172a-fc00-47a8-ba3e-8bebad01e41c.png)

## Lobby

![Screen Shot 2022-04-14 at 12 55 47](https://user-images.githubusercontent.com/16269104/163362324-56f17093-d12b-4575-83b2-cecc8d836832.png)

## Lobby (List of available games)

![Screen Shot 2022-04-14 at 12 56 25](https://user-images.githubusercontent.com/16269104/163362333-70a884c8-755c-4656-8d68-e83c41443fea.png)

## Game

![Screen Shot 2022-04-14 at 12 57 22](https://user-images.githubusercontent.com/16269104/163362372-93678cd2-cbf6-4823-acfe-621e1ec59c36.png)

# Possible Improvements

- Game state is not refreshed on frontend so I could have used websockets to refresh the game state.
- NoSql could be a better choice since it does not restrict data model and easy to change, can be scaled vertically compared to RDBMS.
  This allows for a more flexible architecture in case of user number increases dramatically.
- Improve tests to cover all possible scenarios.
- Improve UI/UX
