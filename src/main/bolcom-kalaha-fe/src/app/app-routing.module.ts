import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuard} from "./auth/AuthGuard";
import {AppComponent} from "./app.component";
import {LoginComponent} from "./pages/login/app.login.component";
import {SignupComponent} from "./pages/register/app.signup.component";
import {LobbyComponent} from "./pages/lobby/app.lobby.component";
import {GameComponent} from "./pages/game/app.game.component";

const routes: Routes = [
  {
    path: '', component: AppComponent, canActivate: [AuthGuard],
    children: [
      {path: 'lobby', component: LobbyComponent},
      {path: 'game/:id', component: GameComponent}
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: '**', redirectTo: 'lobby'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
