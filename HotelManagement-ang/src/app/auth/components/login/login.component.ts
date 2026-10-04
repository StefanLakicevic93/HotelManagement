import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';
import { UserStorageService } from '../../user-storage.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm!: FormGroup;

  constructor(private service: AuthService,
              private msg: NzMessageService,
              private router: Router,
              private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]]
    })
  }

  submitForm() {
    this.service.login(this.loginForm.value).subscribe(res => {
      console.log(res);
      if(res.userId != null) {
        const user = {
          id: res.userId,
          role: res.userRole
        }
        UserStorageService.saveUser(user);
        UserStorageService.saveToken(res.jwt);

        if(UserStorageService.isAdminLoggedIn()) {
          this.router.navigateByUrl('/admin/dashboard');
        } else if(UserStorageService.isCustomerLoggedIn()) {
          this.router.navigateByUrl('/customer/rooms');
        }
      }
    }, error => {
      this.msg.error("Bad credentials", {nzDuration: 5000});
    })
  }

}
