import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin.service';

@Component({
  selector: 'app-update-room',
  standalone: false,
  templateUrl: './update-room.component.html',
  styleUrl: './update-room.component.scss'
})
export class UpdateRoomComponent {

  updateRoom!: FormGroup;
  id: number;

  constructor(private fb: FormBuilder,
              private router: Router,
              private msg: NzMessageService,
              private service: AdminService,
              private route:ActivatedRoute
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.updateRoom = this.fb.group({
      name: [null, [Validators.required]],
      type: [null, [Validators.required]],
      price: [null, [Validators.required]]
    })
    this.getRoomById();
  }

  submitForm() {
    this.service.updateRoom(this.id, this.updateRoom.value).subscribe(res => {
      this.msg.success("Room update successful", {nzDuration: 5000});
      this.router.navigateByUrl("/admin/dashboard");
    }, error => {
      this.msg.error(`${error.error}`, {nzDuration: 5000});
    })
  }

  getRoomById() {
    this.service.getRoomById(this.id).subscribe(res => {
      this.updateRoom.patchValue(res);
    }, error => {
      this.msg.error(`${error.error}`, {nzDuration: 5000})
    })
  }

}
