import {UserModel} from "./user-model";

export interface KalahaGame{
  firstPlayer: UserModel;
  secondPlayer: UserModel;
  playerTurn: UserModel;
  id: string;
  gameState: string | '';
  kalahaBoard: KalahaBoard;
}

export interface KalahaBoard{
  id: string;
  pits: KalahaPit[];
}

export interface KalahaPit{
  id: string;
  pitType: string;
  position: string,
  stones: string;
}
