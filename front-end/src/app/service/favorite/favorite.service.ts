import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class FavoriteService {

  private readonly Base_URL = "http://localhost:8080/favorite/fav/";

  constructor(private http:HttpClient) { }

  public addfav(jwttoken:String, favorite:any):Observable<any>{
    const httpOptions= {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: "Bearer " + jwttoken
      })
    }
    return this.http.post<any>(`http://localhost:8080/favorite/fav/`, favorite, httpOptions);
  }
  }

