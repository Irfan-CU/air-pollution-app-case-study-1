import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JWTTokenService {

  jwtToken?: string;
  decodedToken?: { exp: number, userId: string };

  constructor() { }

  setToken(token: string) {
    if (token) this.jwtToken = token;
  }

  getToken(){
    return this.jwtToken;
  }

  decodeToken() {
    if (this.jwtToken) this.decodedToken = jwt_decode(this.jwtToken);
  }

  getUserId() {
    this.decodeToken();
    return this.decodedToken ? this.decodedToken.userId : null as any;
  }

  getExpiryTime() {
    this.decodeToken();
    return this.decodedToken ? this.decodedToken.exp : null as any;
  }

  isTokenExpired(): boolean {
    const expiryTime: number = this.getExpiryTime();

    if (expiryTime) return (expiryTime - (new Date()).getTime() < 5000);
    else return false;
  }
}
