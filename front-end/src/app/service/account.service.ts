import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http:HttpClient) { }

  upload(file: File, jwttoken: String): Observable<any> {
    console.log("uploading pic now: ", file)
    const formData = new FormData();
    formData.append("file", file, file.name);

    console.log("ok gonna send the pic now!!")
    return this.http.post('http://localhost:8080/user/userservice/api/v1/profilePic/add', formData, {
      headers: new HttpHeaders({
        Authorization: "Bearer " + jwttoken
      }),
      responseType: 'blob'
    })
  }

  getProfilPic(jwttoken: String): Observable<any> {
    return this.http.get('http://localhost:8080/user/userservice/api/v1/profilePic/get', {
      headers: new HttpHeaders({
        'Content-Type': 'image/jpg',
        Authorization: "Bearer " + jwttoken
      }),
      responseType: 'blob'
   })
  }
}
