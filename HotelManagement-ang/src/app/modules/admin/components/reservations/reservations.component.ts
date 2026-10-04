import { Component } from '@angular/core';
import { AdminService } from '../../admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';


@Component({
  selector: 'app-reservations',
  standalone: false,
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.scss'
})
export class ReservationsComponent {

  currentPage: any = 1;
  total: any;
  reservations: any;

  constructor(private service: AdminService,
              private msg: NzMessageService
  ) {
    this.getReservations();
  }

  getReservations() {
    this.service.getReservations(this.currentPage - 1).subscribe(res => {
      console.log(res);
      this.reservations = res.reservationDtoList;
      this.total = res.totalPages * 5;
    })
  }

  pageIndexChange(value: any) {
    this.currentPage = value;
    this.getReservations();
  }

  changeReservationStatus(bookingId: number, status: string) {
    console.log("bookingId:", bookingId, "status:", status);
    this.service.changeReservationStatus(bookingId, status).subscribe(res => {
      this.msg.success("Updated reservation status successfully", {nzDuration: 5000});
      this.getReservations();
    }, error => {
      this.msg.error(`${error.error}`, {nzDuration: 5000})
    })
  }

}

