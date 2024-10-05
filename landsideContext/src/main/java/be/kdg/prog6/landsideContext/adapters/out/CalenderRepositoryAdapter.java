package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.domain.Calendar;
import be.kdg.prog6.landsideContext.domain.Slot;
import be.kdg.prog6.landsideContext.ports.out.CalendarRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CalenderRepositoryAdapter implements CalendarRepositoryPort {
    @Override
    public Calendar getCalendar() {

        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)));
        slots.add(new Slot(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3)));

        return new Calendar(slots);
    }
}
