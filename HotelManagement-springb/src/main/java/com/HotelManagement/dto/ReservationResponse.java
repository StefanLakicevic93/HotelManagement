package com.HotelManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReservationResponse {

    private Integer totalPages;

    private Integer pageNumber;

    private List<ReservationDto> reservationDtoList;

}
