import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthController} from "./AuthController";
import {RegisterModel} from "../domain/user-model";

@Injectable()
export class AuthenticationService {

    constructor(private http: HttpClient, private authService: AuthController) {
    }

    login(username: string, password: string ) {
        return this.authService.authenticateUser({username, password});
    }

    signup(userModel: RegisterModel){
        return this.authService.signup(userModel);
    }
}
