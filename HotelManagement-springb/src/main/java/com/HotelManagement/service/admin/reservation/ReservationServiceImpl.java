package com.HotelManagement.service.admin.reservation;

import com.HotelManagement.dto.ReservationResponse;
import com.HotelManagement.entity.Reservation;
import com.HotelManagement.entity.ReservationStatus;
import com.HotelManagement.entity.Room;
import com.HotelManagement.repository.ReservationRepository;
import com.HotelManagement.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public ReservationResponse getAllReservations(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        ReservationResponse reservationResponse = new ReservationResponse();

        reservationResponse.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto)
                .collect(Collectors.toList()));

        reservationResponse.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponse.setTotalPages(reservationPage.getTotalPages());

        return reservationResponse;
    }

    public boolean changeReservationStatus(Long id, String status) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(optionalReservation.isPresent()) {
            Reservation existingReservation = optionalReservation.get();
            if(Objects.equals(status, "APPROVED")) {  // koristi velike slova da bude konzistentno sa frontendom
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);
            } else if (Objects.equals(status, "REJECTED")) {
                existingReservation.setReservationStatus(ReservationStatus.REJECTED);
            } else {
                // opcionalno, možeš vratiti false ako status nije validan
                return false;
            }
            reservationRepository.save(existingReservation);
            Room existingRoom = existingReservation.getRoom();
            existingRoom.setAvailable(false);

            roomRepository.save(existingRoom);
            return true;  // OVDE VRATI TRUE
        }
        return false;
    }



}
