import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserStorageService } from '../../auth/user-storage.service';
import { Observable } from 'rxjs';

const baseUrl = "http://localhost:8080";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  createAuthorizationHeader() {
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization',
      'Bearer ' + UserStorageService.getToken()
    )
  }

  postRoom(RoomDto: any): Observable<any> {
    return this.http.post(baseUrl + "/api/admin/room", RoomDto, {
      headers: this.createAuthorizationHeader(),
    })
  }

  getRooms(pageNumber: number): Observable<any> {
    return this.http.get(baseUrl + `/api/admin/rooms/${pageNumber}`, {
      headers: this.createAuthorizationHeader(),
    })
  }

  getRoomById(id: number): Observable<any> {
    return this.http.get(baseUrl + `/api/admin/room/${id}`, {
      headers: this.createAuthorizationHeader(),
    }); 
  }

  updateRoom(id: number, RoomDto: any): Observable<any> {
    return this.http.put(baseUrl + `/api/admin/room/${id}`, RoomDto, {
      headers: this.createAuthorizationHeader(),
    })
  }

  deleteRoom(roomId: number): Observable<any> {
    return this.http.delete(baseUrl + `/api/admin/room/${roomId}`, {
      headers: this.createAuthorizationHeader(),
    }); 
  }

  getReservations(pageNumber: number): Observable<any> {
    return this.http.get(baseUrl + `/api/admin/reservations/${pageNumber}`, {
      headers: this.createAuthorizationHeader(),
    })
  }

  changeReservationStatus(reservationId: number, status: string): Observable<any> {
  return this.http.put(baseUrl + `/api/admin/reservation/${reservationId}/${status}`, null, {
    headers: this.createAuthorizationHeader(),
  });
}


}
