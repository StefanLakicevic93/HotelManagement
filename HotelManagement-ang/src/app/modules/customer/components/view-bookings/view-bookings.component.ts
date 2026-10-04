import { Component } from '@angular/core';
import { CustomerService } from '../../customer.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-view-bookings',
  standalone: false,
  templateUrl: './view-bookings.component.html',
  styleUrl: './view-bookings.component.scss'
})
export class ViewBookingsComponent {

  currentPage: any = 1;
  total: any;
  bookings: any;

  constructor(private service: CustomerService,
              private msg: NzMessageService
  ) {
    this.getBookings();
  }

  getBookings() {
    this.service.getMyBookings(this.currentPage - 1).subscribe(res => {
      console.log(res);
      this.bookings = res.reservationDtoList;
      this.total = res.totalPages * 5;
    }, error => {
      this.msg.error(`${error.error}`, {nzDuration: 5000});
    })
  }

  pageIndexChange(value: any) {
    this.currentPage = value;
    this.getBookings();
  }

}
