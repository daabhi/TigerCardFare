package business;

import interfaces.IFareAdjuster;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pojos.DailyWeeklyDetails;
/*
    This adjuster adds the capping logic for daily/weekly scenarios.
    In future if we want to add more logic for monthly/roundtrip/yearly cases it can be added here easily
 */
public class FareAdjuster implements IFareAdjuster {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void applyDefaultFare(DailyWeeklyDetails dailyWeeklyDetails) {
        int dailyFareSoFar = dailyWeeklyDetails.getDailyFareSoFar();
        dailyWeeklyDetails.getJourneyFares().add(dailyFareSoFar);
    }

    @Override
    public void applyDailyCapOnDefaultFare(DailyWeeklyDetails dailyWeeklyDetails) {
        int fare = dailyWeeklyDetails.getDailyFareSoFar();
        int dailyCap = dailyWeeklyDetails.getDailyCap();
        int prevFare = dailyWeeklyDetails.getDayFares().get(dailyWeeklyDetails.getDayOfWeek());
        int newFare = prevFare != dailyCap ? Math.max(0, dailyCap - (dailyWeeklyDetails.getDayFares().get(dailyWeeklyDetails.getDayOfWeek()) - fare)) : 0;
        dailyWeeklyDetails.getJourneyFares().add(newFare);
        dailyWeeklyDetails.getDayFares().put(dailyWeeklyDetails.getDayOfWeek(), dailyCap);
    }

    @Override
    public void applyWeeklyCapBeforeDailyCapOnDefaultFare(DailyWeeklyDetails dailyWeeklyDetails) {
        int weeklyFareSoFar = dailyWeeklyDetails.getWeeklyFareSoFar();
        int weeklyCap = dailyWeeklyDetails.getWeeklyCap();
        int fare = dailyWeeklyDetails.getDailyFareSoFar();
        int dailyCap = dailyWeeklyDetails.getDailyCap();
        int finalFare = (weeklyFareSoFar != weeklyCap) ? Math.max(0, weeklyCap - (weeklyFareSoFar - fare)) : 0;
        dailyWeeklyDetails.getJourneyFares().add(finalFare);
        dailyWeeklyDetails.getDayFares().put(dailyWeeklyDetails.getDayOfWeek(), dailyCap);
    }
}
