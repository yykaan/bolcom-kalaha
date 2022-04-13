import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';

import {Observable, throwError} from 'rxjs';
import {TokenStorageService} from './TokenStorageService';
import {catchError} from 'rxjs/operators';
import {AuthenticationService} from './AuthService';
import {Router} from '@angular/router';

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private tokenStorageService: TokenStorageService, private authService: AuthenticationService,
                private router: Router) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let authReq = req;
        const token = this.tokenStorageService.getToken();
        if (token != null && (!authReq.url.includes('login') || !authReq.url.includes("register"))) {
            authReq = this.addTokenHeader(req, token);
        }
        return next.handle(authReq).pipe(catchError(error => {
            if (error instanceof HttpErrorResponse && !authReq.url.includes('auth/login') && error.status === 401) {
              this.router.navigateByUrl('/login');
            }else if (error.status === 503){
              this.router.navigateByUrl('/login');
            }else if(error.status === 500){
                if (JSON.parse(error.error).error.code === 401)
                    this.router.navigateByUrl('/login');
            }

            return throwError(error);
        }))as Observable<HttpEvent<any>>;
    }

    private addTokenHeader(request: HttpRequest<any>, token: string) {
        return request.clone({ headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
    }
}
