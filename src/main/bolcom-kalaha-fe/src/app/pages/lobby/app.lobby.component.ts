import {Component, OnInit} from '@angular/core';
import {GameController} from "../GameController";
import {Router} from "@angular/router";
import {interval, Subscription} from "rxjs";

@Component({
  selector: 'app-lobby',
  templateUrl: './app.lobby.component.html'
})
export class LobbyComponent implements OnInit{

  games:any;

  gameStateSubscription: Subscription;

  constructor(private gameController: GameController, private router: Router) {
    this.gameStateSubscription = new Subscription();
    this.gameStateSubscription = interval(2500).subscribe((x => {
      this.gameController.listAvailableGames().subscribe(result => {
        this.games = result;
      })
    }))
  }

  createNewGame() {
    this.gameController.createGame().subscribe(result => {
      this.router.navigateByUrl('/game/'+result.id);
    },error => {
      console.log(error)
    });
  }

  listAvailableGames(){
    this.gameController.listAvailableGames().subscribe(result => {
      this.games = result;
    });
  }

  joinGame(id:string) {
    this.gameController.joinGame(id).subscribe(result=>{
      this.router.navigateByUrl('/game/'+result.id);
    },error => {
      console.log(error)
    });
  }

  ngOnInit(): void {
    this.listAvailableGames();
  }
}
