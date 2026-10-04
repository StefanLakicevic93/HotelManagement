package com.HotelManagement.service.customer.room;


import com.HotelManagement.dto.RoomsResponse;
import com.HotelManagement.entity.Room;
import com.HotelManagement.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomsResponse getAvailableRooms(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = roomRepository.findByAvailable(true, pageable);

        RoomsResponse roomsResponse = new RoomsResponse();
        roomsResponse.setPageNumber(roomPage.getPageable().getPageNumber());
        roomsResponse.setTotalPages(roomPage.getTotalPages());
        roomsResponse.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));

        return roomsResponse;
    }

}
