import {Component, OnDestroy, OnInit} from '@angular/core';
import {GameController} from "../GameController";
import {ActivatedRoute} from "@angular/router";
import {KalahaGame} from "../../domain/game-model";
import {interval, Subscription} from "rxjs";

@Component({
  selector: 'app-game',
  templateUrl: './app.game.component.html'
})
export class GameComponent implements OnInit, OnDestroy{
  id: number | undefined;
  game!: KalahaGame;

  gameStateSubscription: Subscription;

  constructor(private gameController: GameController, private route: ActivatedRoute) {
    this.gameStateSubscription = new Subscription();
    this.gameStateSubscription = interval(500).subscribe((x => {
      this.gameController.getGameById(String(this.id)).subscribe(result => {
        this.game = result;
      })
    }))
  }

  ngOnDestroy(): void {
    this.gameStateSubscription = new Subscription();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.gameController.joinGame(String(this.id)).subscribe(result => {
        this.game = result;
      })
    })
  }

  getPitByIndex(index: number){
    return this.game?.kalahaBoard?.pits?.find(x => x.position == String(index))?.stones;
  }

  move(number: number) {
    this.gameController.move(this.game.id, number).subscribe(result => {
      this.game = result;
    })
  }
}
