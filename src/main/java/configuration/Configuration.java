package configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pojos.Day;
import pojos.PeriodOfDay;
import pojos.Zone;

import java.time.LocalTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class Configuration {
    private TravelTimeConfigurations travelTimeConfigurations;
    private ZoneFeesConfigurations zoneFeesConfigurations;
    private FareCappingConfigurations fareCappingConfigurations;
    public Configuration(TravelTimeConfigurations travelTimeConfigurations,
                         ZoneFeesConfigurations zoneFeesConfigurations,
                         FareCappingConfigurations fareCappingConfigurations){
        this.travelTimeConfigurations = travelTimeConfigurations;
        this.zoneFeesConfigurations   = zoneFeesConfigurations;
        this.fareCappingConfigurations= fareCappingConfigurations;
    }

    public PeriodOfDay getPeriodOfDay(Day day, LocalTime localTime) {
        return getTravelTimeConfigurations().getPeriodOfDay(day,localTime);
    }

    public int getFare(PeriodOfDay periodOfDay, Zone zone) {
        return getZoneFeesConfigurations().getFare(periodOfDay, zone);
    }

    public Integer getDailyCap(List<Zone> listOfZones) {
        return getFareCappingConfigurations().getDailyCap(listOfZones);
    }
    public Integer getWeeklyCap(List<Zone> listOfZones) {
        return getFareCappingConfigurations().getWeeklyCap(listOfZones);
    }
}
