package com.HotelManagement.service.admin.rooms;

import com.HotelManagement.dto.RoomDto;
import com.HotelManagement.dto.RoomsResponse;
import com.HotelManagement.entity.Room;
import com.HotelManagement.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {

    private final RoomRepository roomRepository;

    public boolean postRoom(RoomDto roomDto) {
        try {
            Room room = new Room();
            room.setName(roomDto.getName());
            room.setType(roomDto.getType());
            room.setPrice(roomDto.getPrice());
            room.setAvailable(true);

            roomRepository.save(room);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public RoomsResponse getAllRooms(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        RoomsResponse roomsResponse = new RoomsResponse();
        roomsResponse.setPageNumber(roomPage.getPageable().getPageNumber());
        roomsResponse.setTotalPages(roomPage.getTotalPages());
        roomsResponse.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));

        return roomsResponse;
    }

    public RoomDto getRoomById(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if(optionalRoom.isPresent()) {
            return optionalRoom.get().getRoomDto();
        } else {
            throw new EntityNotFoundException("Room not present");
        }
    }

    public boolean updateRoom(Long id, RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if(optionalRoom.isPresent()) {
            Room existingRoom = optionalRoom.get();
            existingRoom.setName(roomDto.getName());
            existingRoom.setType(roomDto.getType());
            existingRoom.setPrice(roomDto.getPrice());

            roomRepository.save(existingRoom);
            return true;
        }
        return false;
    }

    public void deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if(optionalRoom.isPresent()) {
            roomRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Room not found");
        }
    }

}
