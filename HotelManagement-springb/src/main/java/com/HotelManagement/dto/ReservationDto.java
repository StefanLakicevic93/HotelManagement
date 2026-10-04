package com.HotelManagement.dto;

import com.HotelManagement.entity.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {

    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long price;

    private ReservationStatus reservationStatus;

    private Long roomId;

    private String roomName;

    private String roomType;

    private Long userId;

    private String username;

}
