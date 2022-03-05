import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef} from '@angular/material/dialog';
import { HttpResponse } from '@angular/common/http';
import { Output, EventEmitter } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

import { LoginService } from 'src/app/service/login.service';
import { LocalStorageService } from 'src/app/service/local-storage.service';
import { JWTTokenService } from 'src/app/service/jwttoken.service';

import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth/auth.service';
import { AccountService } from 'src/app/service/account.service';
import { CommonService } from 'src/app/service/common.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Output() image:EventEmitter<any> = new EventEmitter<any>();

  loginForm:FormGroup;

  id:FormControl;
  password:FormControl;

  isFormValid=false;

  isInDialog=false;

  successfulLoggedIn:Boolean=true;

  constructor(
    public loginDialogRef: MatDialogRef<LoginComponent>,
    private ls: LoginService,
    private lss: LocalStorageService,
    private js: JWTTokenService,
    private router: Router,
    private authService:AuthService,
    private accountService: AccountService,
    private sanitizer: DomSanitizer,
    private commonService:CommonService)
  {
    this.id = new FormControl('', [Validators.required, Validators.email]);
    this.password = new FormControl('', Validators.required);

    this.loginForm = new FormGroup({
      id:this.id,
      password:this.password
    });
  }

  onCloseLoginDialog(): void {
    this.loginDialogRef.close();
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      console.log("invalid form");
    } else {
      console.log(this.authService.loggedIn);
      this.ls.setId(this.id.value);
      this.ls.loginUser(this.loginForm.value).subscribe(
        (data:HttpResponse<any>) => {
          this.successfulLoggedIn=true;
          console.log("successful user login: ", data.headers.get('token'));
          this.authService.logIn();
          if (data != null) {
            this.lss.set('jwttoken', data.headers.get('token') || '');
            this.js.setToken(data.headers.get('token') || '');
          }
          // getting profile pic
          console.log('about to get pic')
          this.accountService.getProfilPic(this.lss.get('jwttoken')!).subscribe(
            data => {
              let objectUrl = URL.createObjectURL(data);
              console.log('url that is being pushed! ', objectUrl, this.sanitizer.bypassSecurityTrustUrl(objectUrl))
              this.commonService.updateImage(this.sanitizer.bypassSecurityTrustUrl(objectUrl))
              console.log('pic is here: ', data);
            },
            error => {
              console.log("no image set")
            }
          )
          //this.router.navigate('favorites');
          this.loginForm.reset();
          this.loginDialogRef.close();
        },
        error => {
          this.successfulLoggedIn=false;
          console.log("unsuccessful user login: ", error)
        }
      )
    }
  }
  getFormValidation(): boolean{
    return this.isFormValid;
  }
  getIsInDialog():boolean {
    return this.isInDialog
  }

  ngOnInit(): void {

  }
  get formEmail()
  {
    return this.loginForm.get('id');
  }
  get formPassword()
  {
    return this.loginForm.get('password')
  }
  getSuccessfulLoggedIn():Boolean{
    return this.successfulLoggedIn;
  }
}
