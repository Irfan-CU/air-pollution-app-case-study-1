import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AirQualityModel } from '../component/model/main/airQuality/airQuality-model';

import { JWTTokenService } from './jwttoken.service';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {

  // private readonly ROOT_URL = 'http://localhost:8081/airQualityInfo/api/v1/supportedCountries';
  private readonly Base_URL_V1 = "http://localhost:8080/airquality/airQualityInfo/api/v1";
  private readonly Base_URL_V2 = "http://localhost:8080/airquality/airQualityInfo/api/v2";

  constructor(private http:HttpClient, private js: JWTTokenService) { }

  public getCountriesList():Observable<any>{
    this.http.get<any>(`${this.Base_URL_V1}`)
    return this.http.get<any>(`${this.Base_URL_V1}/supportedCountries`)
  }

  public getStatesList(country:String):Observable<any>{
    this.http.get<any>(`${this.Base_URL_V1}`)
    return this.http.get<any>(`${this.Base_URL_V1}/supportedStates?country=`+country)
  }

  public getCitiesList(country:String,state:String):Observable<any>{
    this.http.get<any>(`${this.Base_URL_V1}`)
    console.log(`${this.Base_URL_V1}/supportedCities?country=`+country+`&state=`+state);
    return this.http.get<any>(`${this.Base_URL_V1}/supportedCities?country=`+country+`&state=`+state);
  }

  public getFavorites(jwttoken: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: "Bearer " + jwttoken
      })
    }
    return this.http.get<any>(`${this.Base_URL_V1}/favorites`, httpOptions);
  }
  public removeFavoriteItem(jwttoken:any, favorite:any):Observable<any>
  {
      const httpOptions= {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: "Bearer " + jwttoken
      }),
      body:favorite
    }
    
    return this.http.request<any>("delete", `http://localhost:8080/favorite/fav/`, httpOptions );
    }
  public addFavoriteItem(jwttoken:any, favorite:any):Observable<any>
  {
    const httpOptions= {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: "Bearer " + jwttoken
      }),
      body:favorite
    }
    return this.http.post<any>(`http://localhost:8080/favorite/fav/`, httpOptions);
  }
  public getPollutionData(country:String,state:String,city:String):Observable<any>{
    if (country==="USA"){
    // this.http.get<any>(`${this.Base_URL_V2}`)
    return this.http.get<any>(`${this.Base_URL_V2}/city?country=`+country+`&city=`+city);
    }
   else{
    this.http.get<any>(`${this.Base_URL_V1}`)
    return this.http.get<any>(`${this.Base_URL_V1}/city?country=`+country+`&state=`+state+`&city=`+city);
   }
    
  }
  public userExists(email: String):Observable<Boolean>
  {
    return this.http.get<Boolean>('http://localhost:8080/user/userservice/api/v1/userExists?email='+email);
  }
}
