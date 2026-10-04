import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin.service';

@Component({
  selector: 'app-post-room',
  standalone: false,
  templateUrl: './post-room.component.html',
  styleUrl: './post-room.component.scss'
})
export class PostRoomComponent {

  postRoom!: FormGroup;

  constructor(private fb: FormBuilder,
              private router: Router,
              private msg: NzMessageService,
              private service: AdminService
  ) {}

  ngOnInit() {
    this.postRoom = this.fb.group({
      name: [null, [Validators.required]],
      type: [null, [Validators.required]],
      price: [null, [Validators.required]]
    })
  }

  submitForm() {
    this.service.postRoom(this.postRoom.value).subscribe(res => {
      this.msg.success("Room posted successfully", {nzDuration: 5000});
      this.router.navigateByUrl("/admin/dashboard");
    }, error => {
      this.msg.error("Failed to post room", {nzDuration: 5000});
    })
  }

}
