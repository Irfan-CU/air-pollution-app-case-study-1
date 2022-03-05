import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { JWTTokenService } from './jwttoken.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizeGuardService implements CanActivate {

  constructor(private js: JWTTokenService) { }

  canActivate() {
    if (this.js.isTokenExpired()) {
      return false;
    } else {
      return true;
    }
  }
}
