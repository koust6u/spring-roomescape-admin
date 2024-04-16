package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    private final List<ReservationDto> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> reservations() {
        reservations.add(new ReservationDto(0L, "fram", LocalDate.now(), LocalTime.now()));
        return ResponseEntity.ok(reservations);
    }
}
