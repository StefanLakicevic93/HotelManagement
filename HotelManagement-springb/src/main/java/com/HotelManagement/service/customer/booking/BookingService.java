package com.HotelManagement.service.customer.booking;

import com.HotelManagement.dto.ReservationDto;
import com.HotelManagement.dto.ReservationResponse;

public interface BookingService {

    boolean postReservation(ReservationDto reservationDto);

    ReservationResponse getAllReservationByUserId(Long userId, int pageNumber);

}
