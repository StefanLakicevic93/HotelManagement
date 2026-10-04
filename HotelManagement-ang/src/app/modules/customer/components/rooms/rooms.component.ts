import { Component } from '@angular/core';
import { CustomerService } from '../../customer.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { UserStorageService } from '../../../../auth/user-storage.service';
import dayjs, { Dayjs } from 'dayjs';

@Component({
  selector: 'app-rooms',
  standalone: false,
  templateUrl: './rooms.component.html',
  styleUrl: './rooms.component.scss'
})
export class RoomsComponent {
  currentPage = 1;
  rooms = [];
  total: any;
  loading = false;

  isVisibleMiddle = false;
  date: Date[] = [];
  checkInDate: string = '';
  checkOutDate: string = '';
  id: number;

  constructor(
    private service: CustomerService,
    private msg: NzMessageService,
    private modalService: NzModalService
  ) {
    this.getRooms();
  }

  getRooms() {
    this.service.getRooms(this.currentPage - 1).subscribe(
      res => {
        this.rooms = res.roomDtoList;
        this.total = res.totalPages * 1;
      },
      error => {
        console.error(error);
      }
    );
  }

  pageIndexChange(value: any) {
    this.currentPage = value;
    this.getRooms();
  }

  onChange(result: Date[]) {
  if (result.length === 2) {
    this.checkInDate = result[0].toISOString().split('T')[0];
    this.checkOutDate = result[1].toISOString().split('T')[0];
  }
}

  handleCancelMiddle() {
    this.isVisibleMiddle = false;
  }

  handleOkMiddle(): void {
    const obj = {
      userId: UserStorageService.getUserId(),
      roomId: this.id,
      checkInDate: this.checkInDate,
      checkOutDate: this.checkOutDate
    };

    this.service.bookRoom(obj).subscribe(
      res => {
        this.msg.success('Request submitted for approval', { nzDuration: 5000 });
        this.isVisibleMiddle = false;
      },
      error => {
        this.msg.error(`${error.error}`, { nzDuration: 5000 });
      }
    );
  }

  showModalMiddle(id: number) {
    this.id = id;
    this.isVisibleMiddle = true;
  }

  disablePastDates = (current: Date): boolean => {
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  return current < today;
};

}

