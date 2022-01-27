package util;

import lombok.NoArgsConstructor;
import pojos.DayOfWeek;
import lombok.Getter;
import lombok.Setter;
import pojos.TimeInterval;

import java.time.LocalTime;
import java.util.*;

/*
    ● Peak hours timings
        ○ Monday - Friday
            ■ 07:00 - 10:30, 17:00 - 20:00
        ○ Saturday - Sunday
            ■ 09:00 - 11:00, 18:00 - 22:00
    ● Off-peak hours timings
        ○ All hours except the above peak hours
 */
@Getter @Setter @NoArgsConstructor
public class TravelTimeConfigurations {
    private final Map<DayOfWeek, ArrayList<TimeInterval>> dayOfWeekTimeIntervalMap = new HashMap<>();
    public void build(DayOfWeek dayOfWeek, String time){
        Objects.requireNonNull(dayOfWeek);
        Objects.requireNonNull(time);
        String[] timeString = time.split("-");
        TimeInterval timeInterval = new TimeInterval(LocalTime.parse(timeString[0]),LocalTime.parse(timeString[1]));
        ArrayList<TimeInterval> timeIntervalList = dayOfWeekTimeIntervalMap.get(dayOfWeek);
        if (timeIntervalList != null){
            dayOfWeekTimeIntervalMap.get(dayOfWeek).add(timeInterval);
        }else{
            ArrayList<TimeInterval> localTimeIntervalList = new ArrayList<>();
            localTimeIntervalList.add(timeInterval);
            dayOfWeekTimeIntervalMap.put(dayOfWeek,localTimeIntervalList);
        }
    }

    public boolean isPeakHours(DayOfWeek dayOfWeek, LocalTime time) {
        Objects.requireNonNull(dayOfWeek);
        Objects.requireNonNull(time);
        if (dayOfWeekTimeIntervalMap.containsKey(dayOfWeek)){
            return dayOfWeekTimeIntervalMap.get(dayOfWeek).stream().anyMatch(timeInterval -> timeInterval.contains(time));
        }
        return false;
    }
}
