import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserStorageService } from '../../auth/user-storage.service';
import { Observable } from 'rxjs';

const baseUrl = "http://localhost:8080";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  createAuthorizationHeader() {
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization',
      'Bearer ' + UserStorageService.getToken()
    )
  }

  getRooms(pageNumber: any): Observable<any> {
      return this.http.get(baseUrl + `/api/customer/rooms/${pageNumber}`, {
        headers: this.createAuthorizationHeader(),
      })
    }

  bookRoom(bookingDto: any): Observable<any> {
    return this.http.post(baseUrl + `/api/customer/book`, bookingDto, {
      headers: this.createAuthorizationHeader(),
    })
  }

  getMyBookings(pageNumber: any): Observable<any> {
    const userId = UserStorageService.getUserId();
      return this.http.get(baseUrl + `/api/customer/bookings/${userId}/${pageNumber}`, {
        headers: this.createAuthorizationHeader(),
      })
    }
}
