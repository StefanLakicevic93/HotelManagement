package com.HotelManagement.service.admin.rooms;

import com.HotelManagement.dto.RoomDto;
import com.HotelManagement.dto.RoomsResponse;

public interface RoomsService {

    boolean postRoom(RoomDto roomDto);

    public RoomsResponse getAllRooms(int pageNumber);

    public RoomDto getRoomById(Long id);

    boolean updateRoom(Long id, RoomDto roomDto);

    void deleteRoom(Long id);

}
