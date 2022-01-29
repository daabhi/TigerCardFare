package business;

import configuration.Configuration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.collections.Sets;
import pojos.Day;
import pojos.DayOfWeek;
import pojos.Journey;
import pojos.Zone;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class WeeklyFareCalculatorTest {
    @Mock
    Configuration mockConfiguration = Mockito.mock(Configuration.class);
    WeeklyFareCalculator weeklyFareCalculator = new WeeklyFareCalculator(mockConfiguration);

    @Test
    void calculateWeeklyCap() {
        Map<DayOfWeek, Set<Zone>> listOfZonesPerDay= new HashMap<>();
        DayOfWeek dayOfWeek = new DayOfWeek(Day.MON,1);
        listOfZonesPerDay.put(dayOfWeek, Sets.newHashSet(new Zone(1,1)));
        when(mockConfiguration.getWeeklyCap(new ArrayList<>(listOfZonesPerDay.get(dayOfWeek)))).thenReturn(500);
        Assert.assertEquals(500,weeklyFareCalculator.calculateWeeklyCap(listOfZonesPerDay,dayOfWeek));
    }

    @Test
    void calculateWeeklyFare() {
        Map<DayOfWeek, Integer> dayFares= new HashMap<>();
        DayOfWeek dayOfWeek = new DayOfWeek(Day.MON,1);
        dayFares.put(dayOfWeek, 35);
        Assert.assertEquals(35,weeklyFareCalculator.calculateWeeklyFare(dayFares,dayOfWeek));
    }
}