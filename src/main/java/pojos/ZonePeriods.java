package pojos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ZonePeriods {
    private final Zone zone;
    private final PeriodOfDay periodOfDay;
    public ZonePeriods(Zone zone, PeriodOfDay periodOfDay){
        this.zone = zone;
        this.periodOfDay = periodOfDay;
    }
}
