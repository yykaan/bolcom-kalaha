import {Component, ElementRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {TokenStorageService} from "../../auth/TokenStorageService";
import {AuthenticationService} from "../../auth/AuthService";

@Component({
  selector: 'app-login',
  templateUrl: './app.login.component.html'
})
export class LoginComponent {
  @ViewChild('username') username: ElementRef | undefined;
  @ViewChild('password') password: ElementRef | undefined;

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';

  constructor(private authService: AuthenticationService, private tokenStorage: TokenStorageService,private router: Router) {
  }

  login() {
    if (this.username != undefined && this.password != undefined){
      const username = this.username.nativeElement.value;
      const password = this.password.nativeElement.value;
      if (username  && password){
        this.authService.login(username, password).subscribe(
          data => {
            this.tokenStorage.saveToken(data.token);

            this.isLoginFailed = false;
            this.isLoggedIn = true;
            this.home();
          },
          err => {
            this.errorMessage = err.error;
            this.isLoginFailed = true;
          }
        );
      }
    }
  }

    home(): void {
        this.router.navigateByUrl('/lobby');
    }
}
