package business;

import interfaces.IWeeklyFareCalculator;
import pojos.DayOfWeek;
import pojos.Zone;
import configuration.Configuration;

import java.util.*;
import java.util.stream.Collectors;
/*
    WeeklyFareCalculator is simply to use the configuration facade to appropriately calculate the weekly fare and weekly cap.
    If in future this logic needs to change then the changes can stay isolated here.
    Also if weekly logic needs to change, it can be isolated here
 */
public class WeeklyFareCalculator implements IWeeklyFareCalculator {
    private final Configuration configuration;
    public WeeklyFareCalculator(Configuration configuration){
        this.configuration = configuration;
    }
    @Override
    public int calculateWeeklyCap(Map<DayOfWeek,Set<Zone>> zonesPerDayOfWeek, DayOfWeek dayOfWeek){
        Set<DayOfWeek> set = zonesPerDayOfWeek.keySet().stream().filter(day -> Objects.equals(day.getWeek(), dayOfWeek.getWeek())).collect(Collectors.toSet());
        List<Zone> weeklyZones = set.stream().flatMap(dow -> zonesPerDayOfWeek.get(dow).stream()).distinct().collect(Collectors.toList());
        return configuration.getWeeklyCap(weeklyZones);
    }
    @Override
    public int calculateWeeklyFare(Map<DayOfWeek, Integer> dayFares, DayOfWeek dayOfWeek) {
        return dayFares.entrySet().stream()
                .filter(e -> Objects.equals(e.getKey().getWeek(), dayOfWeek.getWeek()))
                .mapToInt(Map.Entry::getValue).sum();
    }


}
