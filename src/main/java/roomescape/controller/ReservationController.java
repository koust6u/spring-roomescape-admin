package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.data.dto.response.ReservationResponse;
import roomescape.data.vo.Reservation;
import roomescape.data.dto.request.ReservationRequest;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        try{
            return ResponseEntity.ok(reservationService.getAllReservationQuery());
        } catch (final IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody final ReservationRequest reservationRequest) {
        try{
            final var savedId = reservationService.createReservationCommand(reservationRequest);

            final var response = reservationService.getReservationQuery(savedId);
            return ResponseEntity.created(URI.create("/reservations/" + savedId))
                    .body(response);
        } catch (final IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final long id) {
        reservationService.deleteReservationCommand(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
