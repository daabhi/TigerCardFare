package configuration;

import pojos.Day;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

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
        travelTimeConfigurations.build(Day.MON,"07:00-10:30");travelTimeConfigurations.build(Day.MON,"17:00-20:00");
        travelTimeConfigurations.build(Day.TUE,"07:00-10:30");travelTimeConfigurations.build(Day.TUE,"17:00-20:00");
        travelTimeConfigurations.build(Day.WED,"07:00-10:30");travelTimeConfigurations.build(Day.WED,"17:00-20:00");
        travelTimeConfigurations.build(Day.THU,"07:00-10:30");travelTimeConfigurations.build(Day.THU,"17:00-20:00");
        travelTimeConfigurations.build(Day.FRI,"07:00-10:30");travelTimeConfigurations.build(Day.FRI,"17:00-20:00");

        travelTimeConfigurations.build(Day.SAT,"09:00-11:00");travelTimeConfigurations.build(Day.SAT,"18:00-22:00");
        travelTimeConfigurations.build(Day.SUN,"09:00-11:00");travelTimeConfigurations.build(Day.SUN,"18:00-22:00");
        assertEquals(7,travelTimeConfigurations.getDayOfWeekTimeIntervalMap().size());
        assertEquals(LocalTime.of(07,00),travelTimeConfigurations.getDayOfWeekTimeIntervalMap().get(Day.MON).get(0).getStartTime());
        assertEquals(LocalTime.of(17,00),travelTimeConfigurations.getDayOfWeekTimeIntervalMap().get(Day.MON).get(1).getStartTime());



    }

}