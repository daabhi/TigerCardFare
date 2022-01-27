package pojos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter @Setter @EqualsAndHashCode @ToString
public class DailyWeeklyDetails {
    private List<Integer> journeyFares;
    private Map<DayOfWeek, Integer> dayFares;
    private Map<DayOfWeek, Set<Zone>> listOfZonesPerDay;
    private Journey journey;
    private DayOfWeek dayOfWeek;
    private PeriodOfDay periodOfDay;
    private int dailyFareSoFar;
    private int dailyCap;
    private int weeklyFareSoFar;
    private int weeklyCap;
}