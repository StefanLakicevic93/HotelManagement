import { Component } from '@angular/core';
import { UserStorageService } from './auth/user-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.scss'
})
export class AppComponent {
  
isAdminLoggedIn = UserStorageService.isAdminLoggedIn();
isCustomerLoggedIn = UserStorageService.isCustomerLoggedIn();

constructor(private router: Router) {}

ngOnInit() {
  this.router.events.subscribe(event => {
    if(event.constructor.name === "NavigationEnd") {
      this.isAdminLoggedIn = UserStorageService.isAdminLoggedIn();
      this.isCustomerLoggedIn = UserStorageService.isCustomerLoggedIn();
  }
  })
}

logout() {
  UserStorageService.signout();
  this.router.navigateByUrl("/");
}

}
