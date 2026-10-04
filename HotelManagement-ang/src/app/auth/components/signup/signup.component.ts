import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: false,
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {

  signupForm!: FormGroup;

  constructor(private fb: FormBuilder,
              private service: AuthService,
              private msg: NzMessageService,
              private router: Router
  ) {}

  ngOnInit() {
    this.signupForm = this.fb.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]]
    })
  }

  signup() {
    this.service.signup(this.signupForm.value).subscribe(res => {
      
      if(res.id != null) {
        this.msg.success("Signup successful", {nzDuration: 5000});
        this.router.navigateByUrl("/");
      } else {
        this.msg.error("Signup failed", {nzDuration: 5000});
      }
    })
      
    
  }

}
