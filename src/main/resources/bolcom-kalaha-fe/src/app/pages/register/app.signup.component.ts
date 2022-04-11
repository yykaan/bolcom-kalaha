import {Component, ElementRef, ViewChild} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../auth/AuthService";
import {TokenStorageService} from "../../auth/TokenStorageService";
import {RegisterModel} from "../../domain/user-model";

@Component({
    selector: 'app-signup',
    templateUrl: './app.signup.component.html'
})
export class SignupComponent {
    @ViewChild('email') email: ElementRef | undefined;
    @ViewChild('username') username: ElementRef | undefined;
    @ViewChild('password') password: ElementRef | undefined;

    constructor(private authService: AuthenticationService, private tokenStorage: TokenStorageService
        , private router: Router) {
    }

    signup() {
      if (this.email != undefined && this.username != undefined && this.password != undefined){
        const emailText = this.email.nativeElement.value;
        const usernameText = this.username.nativeElement.value;
        const passwordText = this.password.nativeElement.value;

        if (usernameText && passwordText && emailText) {
          const user : RegisterModel = {
            email: emailText,
            username:usernameText,
            password:passwordText
          };
          this.authService.signup(user).subscribe(result => {
            this.login();
          },error => {
            console.log(error)
          });
          this.tokenStorage.signOut();
        }
      }
    }

    login(): void {
        this.router.navigateByUrl('/login');
    }
}
