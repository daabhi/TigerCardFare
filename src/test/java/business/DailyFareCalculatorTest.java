package business;

import configuration.Configuration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.collections.Sets;
import pojos.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DailyFareCalculatorTest {
    @Mock Configuration mockConfiguration = Mockito.mock(Configuration.class);
    DailyFareCalculator dailyFareCalculator = new DailyFareCalculator(mockConfiguration);
    @Test
    void calculateDailyFare() {
        when(mockConfiguration.getFare(PeriodOfDay.PEAK_PERIOD,new Zone(1,1))).thenReturn(35);
        Journey journey = new Journey(1, Day.MON, LocalTime.of(9,00),new Zone(1,1));
        Assert.assertEquals(35,dailyFareCalculator.calculateDailyFare(PeriodOfDay.PEAK_PERIOD,journey));
    }

    @Test
    void calculateDailyCap() {
        Map<pojos.DayOfWeek, Set<Zone>> listOfZonesPerDay= new HashMap<>();
        DayOfWeek dayOfWeek = new DayOfWeek(Day.MON,1);
        listOfZonesPerDay.put(dayOfWeek, Sets.newHashSet(new Zone(1,1)));
        Journey journey = new Journey(1, Day.MON, LocalTime.of(9,00),new Zone(1,1));

        when(mockConfiguration.getDailyCap(new ArrayList<>(listOfZonesPerDay.get(dayOfWeek)))).thenReturn(100);
        Assert.assertEquals(100,dailyFareCalculator.calculateDailyCap(listOfZonesPerDay,dayOfWeek,journey));

    }

    @Test
    void getPeriodOfDay() {
        DayOfWeek dayOfWeek = new DayOfWeek(Day.MON,1);
        Journey journey = new Journey(1, Day.MON, LocalTime.of(9,00),new Zone(1,1));
        when(mockConfiguration.getPeriodOfDay(dayOfWeek.getDay(),LocalTime.of(9,00))).thenReturn(PeriodOfDay.PEAK_PERIOD);

        Assert.assertEquals(PeriodOfDay.PEAK_PERIOD,dailyFareCalculator.getPeriodOfDay(dayOfWeek,journey));

    }
}