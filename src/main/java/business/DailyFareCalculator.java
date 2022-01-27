package business;

import interfaces.IDailyFareCalculator;
import lombok.Getter;
import lombok.Setter;
import pojos.*;
import configuration.Configuration;

import java.util.*;
/*
    DailyFareCalculator is simply to use the configuration facade to appropriately calculate the daily fare and daily cap.
    If in future this logic needs to change then the changes can stay isolated here.
 */
@Getter @Setter
public class DailyFareCalculator implements IDailyFareCalculator {
    public Configuration configuration;
    public DailyFareCalculator(Configuration configuration){
        this.configuration = configuration;
    }
    @Override
    public int calculateDailyFare(PeriodOfDay periodOfDay, Journey journey) {
        return configuration.getFare(periodOfDay, journey.getZone());
    }

    @Override
    public  int calculateDailyCap(Map<DayOfWeek, Set<Zone>> listOfZonesPerDay, DayOfWeek dayOfWeek, Journey journey){
        Objects.requireNonNull(listOfZonesPerDay.putIfAbsent(dayOfWeek, new HashSet<>())).add(journey.getZone());
        return configuration.getDailyCap(new ArrayList<>(listOfZonesPerDay.get(dayOfWeek)));
    }

    @Override
    public PeriodOfDay getPeriodOfDay(DayOfWeek dayOfWeek, Journey journey){
        return configuration.getPeriodOfDay(dayOfWeek.getDay(), journey.getLocalTime());
    }
}
