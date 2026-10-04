package com.HotelManagement.entity;

import com.HotelManagement.dto.RoomDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private Long price;

    private boolean available;

    public RoomDto getRoomDto() {
        RoomDto dto = new RoomDto();
        dto.setId(id);
        dto.setType(type);
        dto.setName(name);
        dto.setPrice(price);
        dto.setAvailable(available);

        return dto;
    }

}
