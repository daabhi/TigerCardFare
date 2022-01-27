package business;

import interfaces.IFareCalculator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pojos.DailyWeeklyDetails;
import pojos.DayOfWeek;
import pojos.Journey;
import pojos.Zone;

import java.util.*;

/*
    FareCalculator is composed of dailyWeeklyCalculator to do the calculations and FareAdjuster to do the capping
 */
public class FareCalculator implements IFareCalculator {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final DailyWeeklyFareCalculator dailyWeeklyFareCalculator ;
    private final FareAdjuster fareAdjuster;
    public FareCalculator(DailyWeeklyFareCalculator dailyWeeklyFareCalculator, FareAdjuster fareAdjuster){
        this.dailyWeeklyFareCalculator    = dailyWeeklyFareCalculator;
        this.fareAdjuster                 = fareAdjuster;
    }
    @Override
    public List<Integer> computeFare(List<Journey> journeyList){
        List<Integer> journeyFares                 = new ArrayList<>(journeyList.size());
        Map<DayOfWeek,Integer> currentDayFares     = new LinkedHashMap<>();
        Map<DayOfWeek,Set<Zone>> listOfZonesPerDay = new HashMap<>();
        for(Journey journey : journeyList) {
            DailyWeeklyDetails dailyWeeklyDetails = dailyWeeklyFareCalculator.calculate(journeyFares,currentDayFares, listOfZonesPerDay, journey);
            if (currentDayFares.get(dailyWeeklyDetails.getDayOfWeek()) < dailyWeeklyDetails.getDailyCap()){
                if(dailyWeeklyDetails.getWeeklyFareSoFar() < dailyWeeklyDetails.getWeeklyCap()) {
                    fareAdjuster.applyDefaultFare(dailyWeeklyDetails);
                }else{
                    fareAdjuster.applyWeeklyCapBeforeDailyCapOnDefaultFare(dailyWeeklyDetails);
                }
            }else {
                fareAdjuster.applyDailyCapOnDefaultFare(dailyWeeklyDetails);
            }
        }
        return journeyFares;
    }
}
