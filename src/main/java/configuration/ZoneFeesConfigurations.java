package configuration;

import lombok.NoArgsConstructor;
import pojos.PeriodOfDay;
import lombok.Getter;
import lombok.Setter;
import pojos.Zone;

import java.util.*;

/*

Zones            Peak hours Off-peak hours
1-1              30         25
1 - 2 or 2 - 1   35         30
2-2              25         20
 */
@Getter @Setter @NoArgsConstructor
public class ZoneFeesConfigurations {
    private final Map<Zone, Map<PeriodOfDay,Integer>> zoneListConfigurations = new HashMap<>();
    public void  build(Zone zone, PeriodOfDay periodOfDay, int fare){
        Objects.requireNonNull(zone);
        Objects.requireNonNull(periodOfDay);
        if (fare < 0) {
            throw new IllegalArgumentException("Fare cannot be negative "+ fare);
        }
        Map<PeriodOfDay,Integer> periodOfDays = zoneListConfigurations.get(zone);
        if (periodOfDays == null){
            Map<PeriodOfDay,Integer> localPeriodOfDays = new HashMap<>();
            localPeriodOfDays.put(periodOfDay,fare);
            zoneListConfigurations.put(zone,localPeriodOfDays);
            if (zone.getZoneStartId() != zone.getZoneEndId()) {
                zoneListConfigurations.put(new Zone(zone.getZoneEndId(), zone.getZoneStartId()), localPeriodOfDays);
            }
        } else {
            periodOfDays.put(periodOfDay,fare);
            zoneListConfigurations.put(zone,periodOfDays);
            if (zone.getZoneStartId() != zone.getZoneEndId()) {
                zoneListConfigurations.put(new Zone(zone.getZoneEndId(), zone.getZoneStartId()), periodOfDays);
            }
        }
    }

    public int getFare(PeriodOfDay periodOfDay, Zone zone) {
        Map<PeriodOfDay, Integer> periodOfDayIntegerMap = zoneListConfigurations.get(zone);
        if (!periodOfDayIntegerMap.isEmpty()) {
            return periodOfDayIntegerMap.get(periodOfDay);
        }
        return 0;
    }
}
