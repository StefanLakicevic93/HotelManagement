package com.HotelManagement.service.customer.room;

import com.HotelManagement.dto.RoomsResponse;

public interface RoomService {

    RoomsResponse getAvailableRooms(int pageNumber);

}
