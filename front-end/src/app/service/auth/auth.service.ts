import { Injectable } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { Subscription } from 'rxjs';

import { LocalStorageService } from 'src/app/service/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  subscription:Subscription

  constructor(
      private router:Router,
      private lss: LocalStorageService
  ) {
    this.login.subscribe((value)=>{
      this.loggedIn=value;
    })
    this.subscription = router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        console.log("page refreshed", this.lss.get('jwttoken'))
        if (this.lss.get('jwttoken') != null) {
          console.log('jwttoken exists!')
          this.loggedIn = true
        }
      }
    })
  }

  loggedIn:boolean = false;
  login:Subject<boolean> = new Subject<boolean>();

  logIn(){
    this.loggedIn=true;
    this.login.next(true);
  }

  logOut(){
    this.login.next(false);
//     this.router.navigate(['/login']);
    this.lss.remove('jwttoken')

    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigate([this.router.url])
    });
  }
}
