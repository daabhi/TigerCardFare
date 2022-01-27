package pojos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Locale;

/*
Day Time From Zone To Zone Calculated Fare Explanation
Monday 10:20 2 1 35 Peak hours Single fare
Monday 10:45 1 1 25 Off-peak single fare
Monday 16:15 1 1 25 Off-peak single fare
Monday 18:15 1 1 30 Peak hours Single fare
Monday 19:00 1 2 5  The Daily cap reached 120 for zone 1 - 2. Charged 5 instead of 35
 */
@Getter @Setter @EqualsAndHashCode
public class Journey {
    private DayOfWeek dayOfWeek;
    private LocalTime localTime;
    private Zone zone;

    public Journey(DayOfWeek dayOfWeek, LocalTime localTime, Zone zone){
        this.dayOfWeek = dayOfWeek;
        this.localTime = localTime;
        this.zone      = zone;
    }

}
