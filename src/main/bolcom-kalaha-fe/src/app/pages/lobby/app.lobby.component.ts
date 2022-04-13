import {Component, OnInit} from '@angular/core';
import {GameController} from "../GameController";
import {Router} from "@angular/router";

@Component({
  selector: 'app-lobby',
  templateUrl: './app.lobby.component.html'
})
export class LobbyComponent implements OnInit{

  games:any;

  constructor(private gameController: GameController, private router: Router) {
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
