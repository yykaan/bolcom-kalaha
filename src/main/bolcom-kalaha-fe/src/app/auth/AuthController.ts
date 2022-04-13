import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {UrlService} from "../utility/url.service";
import {LoginModel, RegisterModel} from "../domain/user-model";

@Injectable()
export class AuthController {
  httpService: HttpClient;
  urlService: UrlService;

 public constructor(httpService: HttpClient, urlService: UrlService) {
    this.httpService = httpService;
    this.urlService = urlService;
  }

  public authenticateUser(loginRequest: LoginModel): Observable<any>  {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpService.post(this.urlService.getBackendUrl() + 'auth/login', JSON.stringify(loginRequest) , {headers, responseType: 'text'}).pipe(map(res => JSON.parse(res)));
  }

 public signup(registerModel: RegisterModel): Observable<any>  {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpService.post(this.urlService.getBackendUrl() + 'auth/register', JSON.stringify(registerModel) , {headers, responseType: 'text'}).pipe(map(res => res));
  }

}

