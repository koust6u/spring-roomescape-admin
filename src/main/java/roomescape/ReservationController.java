package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong reservationIndex = new AtomicLong(0);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> reserve(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = new Reservation.Builder().id(reservationIndex.incrementAndGet())
                .name(reservationDto.getName())
                .date(reservationDto.getDate())
                .time(reservationDto.getTime())
                .build();

        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.isSameId(id))
                .findAny()
                .orElseThrow();

        reservations.remove(reservation);
        return ResponseEntity.ok().build();
    }
}
