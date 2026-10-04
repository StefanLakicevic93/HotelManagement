package com.HotelManagement.service.customer.booking;

import com.HotelManagement.dto.ReservationDto;
import com.HotelManagement.dto.ReservationResponse;
import com.HotelManagement.entity.Reservation;
import com.HotelManagement.entity.ReservationStatus;
import com.HotelManagement.entity.Room;
import com.HotelManagement.entity.User;
import com.HotelManagement.repository.ReservationRepository;
import com.HotelManagement.repository.RoomRepository;
import com.HotelManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final ReservationRepository reservationRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public boolean postReservation(ReservationDto reservationDto) {
        Optional<User> optionalUser = userRepository.findById(reservationDto.getUserId());
        Optional<Room> optionalRoom = roomRepository.findById(reservationDto.getRoomId());

        if(optionalRoom.isPresent() && optionalUser.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setRoom(optionalRoom.get());
            reservation.setUser(optionalUser.get());
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            reservation.setCheckOutDate(reservationDto.getCheckOutDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);

            Long days = ChronoUnit.DAYS.between(reservationDto.getCheckInDate(), reservationDto.getCheckOutDate());
            reservation.setPrice(optionalRoom.get().getPrice() * days);

            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public ReservationResponse getAllReservationByUserId(Long userId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(pageable, userId);

        ReservationResponse reservationResponse = new ReservationResponse();

        reservationResponse.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto)
                .collect(Collectors.toList()));

        reservationResponse.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponse.setTotalPages(reservationPage.getTotalPages());

        return reservationResponse;
    }

}
