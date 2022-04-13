import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {UrlService} from "../utility/url.service";

@Injectable()
export class GameController {
  httpService: HttpClient;
  urlService: UrlService;

 public constructor(httpService: HttpClient, urlService: UrlService) {
    this.httpService = httpService;
    this.urlService = urlService;
  }

  public createGame(): Observable<any>  {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpService.post(this.urlService.getBackendUrl() + 'game/create', null, {headers, responseType: 'text'}).pipe(map(res => JSON.parse(res)));
  }

 public listAvailableGames(): Observable<any>  {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpService.get(this.urlService.getBackendUrl() + 'game/list', {headers, responseType: 'text'}).pipe(map(res => JSON.parse(res)));
  }

  public joinGame(id: string) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpService.post(this.urlService.getBackendUrl() + 'game/join/'+id, null,{headers, responseType: 'text'}).pipe(map(res => JSON.parse(res)));
  }

  public move(gameId: string, number: number) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpService.post(this.urlService.getBackendUrl() + 'game/'+gameId+'/move/'+number, null,{headers, responseType: 'text'}).pipe(map(res => JSON.parse(res)));
  }

  public getGameById(gameId: string){
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpService.get(this.urlService.getBackendUrl() + 'game/'+gameId, {headers, responseType: 'text'}).pipe(map(res => JSON.parse(res)));
  }
}

