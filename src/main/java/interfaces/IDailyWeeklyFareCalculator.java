package interfaces;

import pojos.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDailyWeeklyFareCalculator {
     DailyWeeklyDetails calculate(List<Integer> journeyFares,
                                  Map<DayOfWeek, Integer> dayFares,
                                  Map<DayOfWeek, Set<Zone>> listOfZonesPerDay,
                                  Journey journey);
}
