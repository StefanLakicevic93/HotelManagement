import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const baseUrl = "http://localhost:8080";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  signup(signupRequest: any): Observable<any> {
    return this.http.post(baseUrl + "/api/auth/signup", signupRequest);
  }

  login(loginRequest: any):Observable<any> {
    return this.http.post(baseUrl + "/api/auth/login", loginRequest);
  }
}
