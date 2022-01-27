package pojos;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Getter @Setter
public class TimeInterval {
    private LocalTime startTime;
    private LocalTime endTime;
    public TimeInterval(LocalTime startTime, LocalTime endTime){
        this.startTime = startTime;
        this.endTime   = endTime;
    }

    public boolean contains(LocalTime localTime){
        return localTime.isAfter(startTime) && localTime.isBefore(endTime);
    }
}
