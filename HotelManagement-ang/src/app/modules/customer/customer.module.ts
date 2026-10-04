import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerComponent } from './customer.component';
import { RoomsComponent } from './components/rooms/rooms.component';
import { NgZorroAntdModule } from '../../NgZorroAntdModule';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ViewBookingsComponent } from './components/view-bookings/view-bookings.component';


@NgModule({
  declarations: [
    CustomerComponent,
    RoomsComponent,
    ViewBookingsComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    NgZorroAntdModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ]
})
export class CustomerModule { }
