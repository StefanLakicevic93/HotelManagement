package com.HotelManagement.service.admin.reservation;

import com.HotelManagement.dto.ReservationResponse;

public interface ReservationService {

    ReservationResponse getAllReservations(int pageNumber);

    boolean changeReservationStatus(Long id, String status);

}
