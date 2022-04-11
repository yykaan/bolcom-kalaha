import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {LoginComponent} from "./pages/login/app.login.component";
import {AuthenticationService} from "./auth/AuthService";
import {AuthGuard} from "./auth/AuthGuard";
import {UrlService} from "./utility/url.service";
import {SignupComponent} from "./pages/register/app.signup.component";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptor} from "./auth/AuthInterceptor";
import {CommonModule, HashLocationStrategy, LocationStrategy} from "@angular/common";
import {AuthController} from "./auth/AuthController";
import {LobbyComponent} from "./pages/lobby/app.lobby.component";
import {GameController} from "./pages/GameController";
import {GameComponent} from "./pages/game/app.game.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    LobbyComponent,
    GameComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule
  ],
  providers: [
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    AppComponent,
    AuthController,
    GameController,
    AuthenticationService,
    AuthGuard,
    UrlService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
