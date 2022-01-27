package business;

import interfaces.IDailyFareCalculator;
import interfaces.IWeeklyFareCalculator;
import interfaces.IDailyWeeklyFareCalculator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pojos.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
/*
    This class encapsulates the daily and weekly calculations and returns a DailyWeeklyDetails back to the calculator
    In case we want to add some more calculations here as well as return more details back to calculator it can easily be added here
 */
@Getter @Setter @EqualsAndHashCode @ToString
public class DailyWeeklyFareCalculator implements IDailyWeeklyFareCalculator {
    private final IDailyFareCalculator dailyFareCalculator;
    private final IWeeklyFareCalculator weeklyFareCalculator;

    public DailyWeeklyFareCalculator(IDailyFareCalculator dailyFareCalculator, IWeeklyFareCalculator weeklyFareCalculator) {
        this.dailyFareCalculator = dailyFareCalculator;
        this.weeklyFareCalculator = weeklyFareCalculator;
    }

    public DailyWeeklyDetails calculate(List<Integer> journeyFares,Map<DayOfWeek, Integer> dayFares, Map<DayOfWeek, Set<Zone>> listOfZonesPerDay, Journey journey) {
        DayOfWeek dayOfWeek      = new DayOfWeek(journey.getDay(),journey.getWeekOfYear());
        PeriodOfDay periodOfDay  = dailyFareCalculator.getPeriodOfDay(dayOfWeek, journey);
        int dailyFareSoFar       = dailyFareCalculator.calculateDailyFare(periodOfDay, journey);
        int dailyCap             = dailyFareCalculator.calculateDailyCap(listOfZonesPerDay, dayOfWeek, journey);
        dayFares.merge(dayOfWeek, dailyFareSoFar, Integer::sum);
        int weeklyFareSoFar      = weeklyFareCalculator.calculateWeeklyFare(dayFares, dayOfWeek);
        int weeklyCap            = weeklyFareCalculator.calculateWeeklyCap(listOfZonesPerDay, dayOfWeek);
        return constructDailyWeeklyDetails(journeyFares, dayFares, dayOfWeek, periodOfDay, dailyFareSoFar, dailyCap, weeklyFareSoFar, weeklyCap);
    }

    private DailyWeeklyDetails constructDailyWeeklyDetails(List<Integer> journeyFares, Map<DayOfWeek, Integer> dayFares,DayOfWeek dayOfWeek, PeriodOfDay periodOfDay, int dailyFareSoFar, int dailyCap, int weeklyFareSoFar, int weeklyCap) {
        DailyWeeklyDetails dailyWeeklyDetails = new DailyWeeklyDetails();
        dailyWeeklyDetails.setJourneyFares(journeyFares);
        dailyWeeklyDetails.setDayFares(dayFares);
        dailyWeeklyDetails.setDailyCap(dailyCap);
        dailyWeeklyDetails.setDailyFareSoFar(dailyFareSoFar);
        dailyWeeklyDetails.setWeeklyCap(weeklyCap);
        dailyWeeklyDetails.setWeeklyFareSoFar(weeklyFareSoFar);
        dailyWeeklyDetails.setDayOfWeek(dayOfWeek);
        dailyWeeklyDetails.setPeriodOfDay(periodOfDay);
        return dailyWeeklyDetails;
    }
}