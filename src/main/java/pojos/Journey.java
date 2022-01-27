package pojos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

/*
Day Time From Zone To Zone Calculated Fare Explanation
Monday 10:20 2 1 35 Peak hours Single fare
Monday 10:45 1 1 25 Off-peak single fare
Monday 16:15 1 1 25 Off-peak single fare
Monday 18:15 1 1 30 Peak hours Single fare
Monday 19:00 1 2 5  The Daily cap reached 120 for zone 1 - 2. Charged 5 instead of 35
 */
@Getter @Setter @EqualsAndHashCode @ToString
public class Journey {
    private int weekOfYear;
    private Day day;
    private LocalTime localTime;
    private Zone zone;

    public Journey(int weekOfYear, Day day, LocalTime localTime, Zone zone){
        this.weekOfYear = weekOfYear;
        this.day       = day;
        this.localTime = localTime;
        this.zone      = zone;
    }

}
