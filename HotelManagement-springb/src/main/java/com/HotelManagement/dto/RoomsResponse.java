package com.HotelManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomsResponse {

    private List<RoomDto> roomDtoList;

    private Integer totalPages;

    private Integer pageNumber;

}
