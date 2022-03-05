import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient) { }

  Id:String="";

  loginUser(user:any): Observable<any> {
    return this.http.post('http://localhost:8080/user/userservice/api/v1/login/user', user, {
      headers: new HttpHeaders({'Content-Type': 'application/json'}),
      observe: 'response'
    });
  }

  setId(id:String){
    this.Id =id;
  }

  getId():String{
    return this.Id
  }
}
