package interfaces;

import pojos.*;

import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

public interface IDailyFareCalculator {
     int calculateDailyFare(PeriodOfDay periodOfDay, Journey journey) ;
     int calculateDailyCap(Map<DayOfWeek, Set<Zone>> listOfZonesPerDay, DayOfWeek dayOfWeek, Journey journey);
     PeriodOfDay getPeriodOfDay(DayOfWeek dayOfWeek, Journey journey);
}
