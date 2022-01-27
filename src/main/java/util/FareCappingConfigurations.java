package util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pojos.Cap;
import pojos.Zone;

import java.util.HashMap;
import java.util.Map;

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
            if (zone.getZoneStartId() != zone.getZoneEndId()){
                //Need some more intelligent logic here to say if given 1-2 then insert 2-1 and vice versa
                fareCappingConfig.put(new Zone(zone.getZoneEndId(),zone.getZoneStartId()),localZoneCapConfig);
            }
        }else {
            zoneCapConfig.put(cap,capLimit);
            if (zone.getZoneStartId() != zone.getZoneEndId()){
                //Need some more intelligent logic here to say if given 1-2 then insert 2-1 and vice versa
                fareCappingConfig.put(new Zone(zone.getZoneEndId(),zone.getZoneStartId()),zoneCapConfig);
            }

        }
    }

    public int getDailyCap(Zone zone){
        if (fareCappingConfig.containsKey(zone)){
            return fareCappingConfig.get(zone).get(Cap.DAILY);
        }else {
            return 0;
        }
    }

    public int getWeeklyCap(Zone zone){
        if (fareCappingConfig.containsKey(zone)){
            return fareCappingConfig.get(zone).get(Cap.WEEKLY);
        }else {
            return 0;
        }
    }
}
