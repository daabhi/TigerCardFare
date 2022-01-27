package interfaces;

import pojos.DayOfWeek;
import pojos.Zone;

import java.util.Map;
import java.util.Set;

public interface IWeeklyFareCalculator {
    int calculateWeeklyCap(Map<DayOfWeek, Set<Zone>> zonesPerDayOfWeek, DayOfWeek dayOfWeek) ;
    int calculateWeeklyFare(Map<DayOfWeek, Integer> dayFares, DayOfWeek dayOfWeek);
}