package business;

import pojos.DayOfWeek;
import pojos.Journey;
import pojos.PeriodOfDay;
import pojos.Zone;
import util.FareCappingConfigurations;
import util.TravelTimeConfigurations;
import util.ZoneFeesConfigurations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pojos.PeriodOfDay.OFF_PEAK_PERIOD;
import static pojos.PeriodOfDay.PEAK_PERIOD;

/*
Given a list of journeys, the program should return the fare applicable.
Input Format: List of journeys for a single commuter, fields include date-time, from-zone, to-zone
Output: Computed Fare for the input journeys data

Day Time From Zone To Zone Calculated Fare Explanation
Monday 10:20 2 1 35 Peak hours Single fare
Monday 10:45 1 1 25 Off-peak single fare
Monday 16:15 1 1 25 Off-peak single fare
Monday 18:15 1 1 30 Peak hours Single fare
Monday 19:00 1 2 5  The Daily cap reached 120 for zone 1 - 2. Charged 5 instead of 35
 */
public class FareCalculator {
    private final TravelTimeConfigurations travelTimeConfigurations ;
    private final ZoneFeesConfigurations zoneFeesConfigurations ;
    private final FareCappingConfigurations fareCappingConfigurations;

    public FareCalculator(TravelTimeConfigurations travelTimeConfigurations, ZoneFeesConfigurations zoneFeesConfigurations, FareCappingConfigurations fareCappingConfigurations){
        this.travelTimeConfigurations = travelTimeConfigurations;
        this.zoneFeesConfigurations = zoneFeesConfigurations;
        this.fareCappingConfigurations = fareCappingConfigurations;
    }
    public List<Integer> computeFare(List<Journey> journeyList){
        List<Integer> journeyFares = new ArrayList<>(journeyList.size());
        Map<Zone,Integer> zoneCumulativeFare = new HashMap<>();
        for(Journey journey : journeyList) {
            DayOfWeek dayOfWeek     = journey.getDayOfWeek();
            LocalTime localTime     = journey.getLocalTime();
            PeriodOfDay periodOfDay = travelTimeConfigurations.isPeakHours(dayOfWeek, localTime) ? PEAK_PERIOD : OFF_PEAK_PERIOD;
            Zone zone               = journey.getZone();
            int fare                = zoneFeesConfigurations.calculate(periodOfDay, zone);
            calculateZoneCumulativeFare(zoneCumulativeFare, zone, fare);

            if (zone.getZoneStartId() == zone.getZoneEndId()){
                if (zoneCumulativeFare.get(zone) < fareCappingConfigurations.getDailyCap(zone)){
                    journeyFares.add(fare);
                }else{
                    int dailyCap = fareCappingConfigurations.getDailyCap(zone);
                    int newFare = dailyCap - (zoneCumulativeFare.get(zone) - fare);
                    journeyFares.add(newFare);
                }
            } else {
                int totalFare = 0;
                if (zoneCumulativeFare.containsKey(zone)){
                    int currentZoneFare = zoneCumulativeFare.get(zone);
                    Zone oppositeZone = new Zone(zone.getZoneStartId(),zone.getZoneStartId());
                    if (zoneCumulativeFare.containsKey(oppositeZone)){
                        int oppositeZoneFare = zoneCumulativeFare.get(oppositeZone);
                        totalFare = currentZoneFare + oppositeZoneFare + fare;
                    }else{
                        totalFare = currentZoneFare;
                    }
                }
                if (totalFare < fareCappingConfigurations.getDailyCap(zone)){
                    journeyFares.add(fare);
                }else {
                    int dailyCap = fareCappingConfigurations.getDailyCap(zone);
                    int newFare = dailyCap - (totalFare - fare);
                    journeyFares.add(newFare);
                }
            }
        }
        return journeyFares;
    }

    private void calculateZoneCumulativeFare(Map<Zone, Integer> zoneCumulativeFare, Zone zone, int fare) {
        if (zoneCumulativeFare.containsKey(zone)){
            Integer currentZoneFare = zoneCumulativeFare.get(zone);
            currentZoneFare += fare;
            zoneCumulativeFare.put(zone,currentZoneFare);
        }else{
            zoneCumulativeFare.put(zone, fare);
        }
    }
}
