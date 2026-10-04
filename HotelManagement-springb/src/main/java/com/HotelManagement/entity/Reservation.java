package com.HotelManagement.entity;

import com.HotelManagement.dto.ReservationDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long price;

    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public ReservationDto getReservationDto() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(id);
        reservationDto.setPrice(price);
        reservationDto.setCheckInDate(checkInDate);
        reservationDto.setCheckOutDate(checkOutDate);
        reservationDto.setReservationStatus(reservationStatus);

        reservationDto.setUserId(user.getId());
        reservationDto.setUsername(user.getUsername());

        reservationDto.setRoomId(room.getId());
        reservationDto.setRoomName(room.getName());
        reservationDto.setRoomType(room.getType());

        return reservationDto;


    }

}
