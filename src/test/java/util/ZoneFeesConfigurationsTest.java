package util;

import org.junit.jupiter.api.Test;
import pojos.PeriodOfDay;
import pojos.Zone;

import static org.junit.jupiter.api.Assertions.*;

/*

Zones            Peak hours Off-peak hours
1-1              30         25
1 - 2 or 2 - 1   35         30
2-2              25         20
 */
class ZoneFeesConfigurationsTest {

    @Test
    void testZoneFeesConfigurations() {
        ZoneFeesConfigurations zoneFeesConfigurations = new ZoneFeesConfigurations();
        zoneFeesConfigurations.build(new Zone(1,1), PeriodOfDay.PEAK_PERIOD, 30);
        zoneFeesConfigurations.build(new Zone(1,1), PeriodOfDay.OFF_PEAK_PERIOD, 25);

        zoneFeesConfigurations.build(new Zone(1,2), PeriodOfDay.PEAK_PERIOD, 35);
        zoneFeesConfigurations.build(new Zone(1,2), PeriodOfDay.OFF_PEAK_PERIOD, 30);

        zoneFeesConfigurations.build(new Zone(2,2), PeriodOfDay.PEAK_PERIOD, 25);
        zoneFeesConfigurations.build(new Zone(2,2), PeriodOfDay.OFF_PEAK_PERIOD, 20);

        assertEquals(30, zoneFeesConfigurations.getZoneListConfigurations().get(new Zone(1,1)).get(PeriodOfDay.PEAK_PERIOD));
        assertEquals(25, zoneFeesConfigurations.getZoneListConfigurations().get(new Zone(1,1)).get(PeriodOfDay.OFF_PEAK_PERIOD));
    }
}