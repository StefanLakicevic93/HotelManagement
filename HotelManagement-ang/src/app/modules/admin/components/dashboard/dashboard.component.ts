import { Component } from '@angular/core';
import { AdminService } from '../../admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  currentPage = 1;
  rooms = [];
  total: any;
  loading = false;

  constructor(private service:AdminService,
              private msg: NzMessageService,
              private modalService: NzModalService
  ) {
    this.getRooms();
  }

  getRooms() {
    this.service.getRooms(this.currentPage - 1).subscribe(res => {
      console.log(res);
      this.rooms = res.roomDtoList;
      this.total = res.totalPages * 1;
    }, error => {

    })
  }

  pageIndexChange(value: any) {
    this.currentPage = value;
    this.getRooms();
  }

  showConfirm(roomId: any) {
    this.modalService.confirm({
      nzTitle: 'Confirm',
      nzContent: 'Do you want to delete this room?',
      nzOkText: 'Delete',
      nzCancelText: 'Cancel',
      nzOnOk: () => this.deleteRoom(roomId)
    })
  }

  deleteRoom(roomId: number) {
    this.service.deleteRoom(roomId).subscribe(res => {
      this.msg.success("Room deleted successfully", {nzDuration: 5000});
      this.getRooms();
    }, error => {
      this.msg.error(`${error.error}`, {nzDuration: 5000});
    })
  }

}
