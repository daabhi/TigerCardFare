package configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pojos.Cap;
import pojos.Zone;

import java.util.*;
import java.util.stream.Collectors;

/*
Zones          Daily Cap Weekly Cap (Monday - Sunday)
1-1            100       500
1 - 2 or 2 - 1 120       600
2-2            80        400
 */
@Getter @Setter @NoArgsConstructor
public class FareCappingConfigurations {
    private Map<Zone, Map<Cap,Integer>> fareCappingConfig = new HashMap<>();

    public void build(Zone zone, Cap cap, int capLimit){
        Map<Cap,Integer> zoneCapConfig = fareCappingConfig.get(zone);
        if (zoneCapConfig == null){
            Map<Cap,Integer> localZoneCapConfig = new HashMap<>();
            localZoneCapConfig.put(cap, capLimit);
            fareCappingConfig.put(zone,localZoneCapConfig);
            populateOtherSide(zone, localZoneCapConfig);
        }else {
            zoneCapConfig.put(cap,capLimit);
            populateOtherSide(zone, zoneCapConfig);
        }
    }

    private void populateOtherSide(Zone zone, Map<Cap, Integer> localZoneCapConfig) {
        if (zone.getZoneStartId() != zone.getZoneEndId()) {
            Zone otherWayZone = new Zone(zone.getZoneEndId(), zone.getZoneStartId());
            fareCappingConfig.put(otherWayZone, localZoneCapConfig);
        }
    }

    public int getDailyCap(Zone zone){
        if (fareCappingConfig.containsKey(zone)){
            return fareCappingConfig.get(zone).get(Cap.DAILY);
        }
        throw new IllegalArgumentException("Zone="+zone + " is not configured to have a daily cap");
    }

    public int getWeeklyCap(Zone zone){
        if (fareCappingConfig.containsKey(zone)){
            return fareCappingConfig.get(zone).get(Cap.WEEKLY);
        }
        throw new IllegalArgumentException("Zone="+zone + " is not configured to have a weekly cap");
    }

    public Integer getDailyCap(List<Zone> zones) {
        return zones.stream().map(this::getDailyCap)
                .collect(Collectors.toList()).stream()
                .max(Comparator.naturalOrder()).get();
    }

    public Integer getWeeklyCap(List<Zone> zones) {
        return zones.stream().map(this::getWeeklyCap)
                .collect(Collectors.toList()).stream()
                .max(Comparator.naturalOrder()).get();
    }
}
