package util;

import pojos.DayOfWeek;
import org.junit.jupiter.api.Test;
import pojos.TimeInterval;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
/*
    ● Peak hours timings
        ○ Monday - Friday
            ■ 07:00 - 10:30, 17:00 - 20:00
        ○ Saturday - Sunday
            ■ 09:00 - 11:00, 18:00 - 22:00
    ● Off-peak hours timings
        ○ All hours except the above peak hours
 */
class TravelTimeConfigurationsTest {

    @Test
    public void testTravelTimeConfigurations(){
        TravelTimeConfigurations travelTimeConfigurations = new TravelTimeConfigurations();
        travelTimeConfigurations.build(DayOfWeek.MON,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.MON,"17:00-20:00");
        travelTimeConfigurations.build(DayOfWeek.TUE,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.TUE,"17:00-20:00");
        travelTimeConfigurations.build(DayOfWeek.WED,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.WED,"17:00-20:00");
        travelTimeConfigurations.build(DayOfWeek.THU,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.THU,"17:00-20:00");
        travelTimeConfigurations.build(DayOfWeek.FRI,"07:00-10:30");travelTimeConfigurations.build(DayOfWeek.FRI,"17:00-20:00");

        travelTimeConfigurations.build(DayOfWeek.SAT,"09:00-11:00");travelTimeConfigurations.build(DayOfWeek.SAT,"18:00-22:00");
        travelTimeConfigurations.build(DayOfWeek.SUN,"09:00-11:00");travelTimeConfigurations.build(DayOfWeek.SUN,"18:00-22:00");
        assertEquals(7,travelTimeConfigurations.getDayOfWeekTimeIntervalMap().size());
        assertEquals(LocalTime.of(07,00),travelTimeConfigurations.getDayOfWeekTimeIntervalMap().get(DayOfWeek.MON).get(0).getStartTime());
        assertEquals(LocalTime.of(17,00),travelTimeConfigurations.getDayOfWeekTimeIntervalMap().get(DayOfWeek.MON).get(1).getStartTime());



    }

}