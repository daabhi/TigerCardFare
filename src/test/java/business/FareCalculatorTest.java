package business;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.*;
import util.FareCappingConfigurations;
import util.TravelTimeConfigurations;
import util.ZoneFeesConfigurations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
Day Time From Zone To Zone Calculated Fare Explanation
Monday 10:20 2 1 35 Peak hours Single fare
Monday 10:45 1 1 25 Off-peak single fare
Monday 16:15 1 1 25 Off-peak single fare
Monday 18:15 1 1 30 Peak hours Single fare
Monday 19:00 1 2 5  The Daily cap reached 120 for zone 1 - 2. Charged 5 instead of 35

 */
class FareCalculatorTest {
    private final TravelTimeConfigurations travelTimeConfigurations = new TravelTimeConfigurations();
    private final ZoneFeesConfigurations zoneFeesConfigurations = new ZoneFeesConfigurations();
    private final FareCappingConfigurations fareCappingConfigurations = new FareCappingConfigurations();
    private FareCalculator fareCalculator;

    @Test
    void computeFareFromProblemStatementPDF() {
        loadTravelTimeConfig();
        loadZoneFeesConfig();
        loadFareCappingConfig();
        fareCalculator = new FareCalculator(travelTimeConfigurations,zoneFeesConfigurations,fareCappingConfigurations);
        List<Journey> journeyList = new ArrayList<>();
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(10,20),new Zone(2,1)));
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(10,45),new Zone(1,1)));
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(16,15),new Zone(1,1)));
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(18,15),new Zone(1,1)));
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(19,00),new Zone(1,2)));
        assertEquals(35,fareCalculator.computeFare(journeyList).get(0));
        assertEquals(25,fareCalculator.computeFare(journeyList).get(1));
        assertEquals(25,fareCalculator.computeFare(journeyList).get(2));
        assertEquals(30,fareCalculator.computeFare(journeyList).get(3));
        assertEquals(5,fareCalculator.computeFare(journeyList).get(4));

    }
    @Test
    void computeFareReachingLimitInSameZone() {
        loadTravelTimeConfig();
        loadZoneFeesConfig();
        loadFareCappingConfig();
        fareCalculator = new FareCalculator(travelTimeConfigurations,zoneFeesConfigurations,fareCappingConfigurations);
        List<Journey> journeyList = new ArrayList<>();
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(10,20),new Zone(2,1)));
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(10,45),new Zone(1,1)));
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(16,15),new Zone(1,1)));
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(18,15),new Zone(1,1)));
        journeyList.add(new Journey(DayOfWeek.MON, LocalTime.of(19,00),new Zone(1,1)));
        assertEquals(35,fareCalculator.computeFare(journeyList).get(0));
        assertEquals(25,fareCalculator.computeFare(journeyList).get(1));
        assertEquals(25,fareCalculator.computeFare(journeyList).get(2));
        assertEquals(30,fareCalculator.computeFare(journeyList).get(3));
        assertEquals(20,fareCalculator.computeFare(journeyList).get(4));
    }

    private void loadFareCappingConfig() {
        fareCappingConfigurations.build(new Zone(1,1), Cap.DAILY,100);
        fareCappingConfigurations.build(new Zone(1,1), Cap.WEEKLY,500);
        fareCappingConfigurations.build(new Zone(1,2), Cap.DAILY,120);
        fareCappingConfigurations.build(new Zone(1,2), Cap.WEEKLY,600);
        fareCappingConfigurations.build(new Zone(2,2), Cap.DAILY,80);
        fareCappingConfigurations.build(new Zone(2,2), Cap.WEEKLY,400);
    }

    public void loadTravelTimeConfig(){
        travelTimeConfigurations.build(DayOfWeek.MON,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.MON,"17:00-20:00");
        travelTimeConfigurations.build(DayOfWeek.TUE,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.TUE,"17:00-20:00");
        travelTimeConfigurations.build(DayOfWeek.WED,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.WED,"17:00-20:00");
        travelTimeConfigurations.build(DayOfWeek.THU,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.THU,"17:00-20:00");
        travelTimeConfigurations.build(DayOfWeek.FRI,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.FRI,"17:00-20:00");
        travelTimeConfigurations.build(DayOfWeek.SAT,"09:00-11:00");travelTimeConfigurations.build(DayOfWeek.SAT,"18:00-22:00");
        travelTimeConfigurations.build(DayOfWeek.SUN,"09:00-11:00");travelTimeConfigurations.build(DayOfWeek.SUN,"18:00-22:00");
    }

    public void loadZoneFeesConfig(){
        zoneFeesConfigurations.build(new Zone(1,1), PeriodOfDay.PEAK_PERIOD, 30);
        zoneFeesConfigurations.build(new Zone(1,1), PeriodOfDay.OFF_PEAK_PERIOD, 25);
        zoneFeesConfigurations.build(new Zone(1,2), PeriodOfDay.PEAK_PERIOD, 35);
        zoneFeesConfigurations.build(new Zone(1,2), PeriodOfDay.OFF_PEAK_PERIOD, 30);
        zoneFeesConfigurations.build(new Zone(2,2), PeriodOfDay.PEAK_PERIOD, 25);
        zoneFeesConfigurations.build(new Zone(2,2), PeriodOfDay.OFF_PEAK_PERIOD, 20);
    }
}