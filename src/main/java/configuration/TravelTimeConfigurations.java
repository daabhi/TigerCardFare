package configuration;

import lombok.NoArgsConstructor;
import pojos.Day;
import lombok.Getter;
import lombok.Setter;
import pojos.PeriodOfDay;
import pojos.TimeInterval;

import java.time.LocalTime;
import java.util.*;

import static pojos.PeriodOfDay.OFF_PEAK_PERIOD;
import static pojos.PeriodOfDay.PEAK_PERIOD;

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
    private final Map<Day, ArrayList<TimeInterval>> dayOfWeekTimeIntervalMap = new HashMap<>();
    public void build(Day day, String time){
        Objects.requireNonNull(day);
        Objects.requireNonNull(time);
        String[] timeString = time.split("-");
        TimeInterval timeInterval = new TimeInterval(LocalTime.parse(timeString[0]),LocalTime.parse(timeString[1]));
        ArrayList<TimeInterval> timeIntervalList = dayOfWeekTimeIntervalMap.get(day);
        if (timeIntervalList != null){
            dayOfWeekTimeIntervalMap.get(day).add(timeInterval);
        }else{
            ArrayList<TimeInterval> localTimeIntervalList = new ArrayList<>();
            localTimeIntervalList.add(timeInterval);
            dayOfWeekTimeIntervalMap.put(day,localTimeIntervalList);
        }
    }

    public boolean isPeakHours(Day day, LocalTime time) {
        Objects.requireNonNull(day);
        Objects.requireNonNull(time);
        if (dayOfWeekTimeIntervalMap.containsKey(day)){
            return dayOfWeekTimeIntervalMap.get(day).stream().anyMatch(timeInterval -> timeInterval.contains(time));
        }
        return false;
    }

    public PeriodOfDay getPeriodOfDay(Day day, LocalTime localTime) {
        return isPeakHours(day, localTime) ? PEAK_PERIOD : OFF_PEAK_PERIOD;
    }
}
