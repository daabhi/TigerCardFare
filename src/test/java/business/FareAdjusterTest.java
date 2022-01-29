package business;

import org.junit.jupiter.api.Test;
import org.testng.Assert;
import pojos.DailyWeeklyDetails;
import pojos.Day;
import pojos.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FareAdjusterTest {
    FareAdjuster fareAdjuster = new FareAdjuster();
    @Test
    void applyDefaultFare() {
        DailyWeeklyDetails dailyWeeklyDetails = new DailyWeeklyDetails();
        dailyWeeklyDetails.setDailyFareSoFar(100);
        dailyWeeklyDetails.setJourneyFares(new ArrayList<>());
        fareAdjuster.applyDefaultFare(dailyWeeklyDetails);
        Assert.assertEquals(100,dailyWeeklyDetails.getJourneyFares().get(0));
    }

    @Test
    void applyDailyCapOnDefaultFare() {
        DailyWeeklyDetails dailyWeeklyDetails = new DailyWeeklyDetails();
        dailyWeeklyDetails.setDailyFareSoFar(130);
        dailyWeeklyDetails.setDailyCap(120);
        dailyWeeklyDetails.setDayOfWeek(new DayOfWeek(Day.MON,1));
        Map<DayOfWeek,Integer> dayFares = new HashMap<>();
        dayFares.put(new DayOfWeek(Day.MON,1),130);
        dailyWeeklyDetails.setDayFares(dayFares);
        dailyWeeklyDetails.setJourneyFares(new ArrayList<>());
        fareAdjuster.applyDailyCapOnDefaultFare(dailyWeeklyDetails);
        Assert.assertEquals(120,dailyWeeklyDetails.getJourneyFares().get(0));
    }

    @Test
    void applyWeeklyCapBeforeDailyCapOnDefaultFare() {
        DailyWeeklyDetails dailyWeeklyDetails = new DailyWeeklyDetails();
        dailyWeeklyDetails.setDailyFareSoFar(130);
        dailyWeeklyDetails.setDailyCap(120);
        dailyWeeklyDetails.setDayOfWeek(new DayOfWeek(Day.MON,1));
        Map<DayOfWeek,Integer> dayFares = new HashMap<>();
        dayFares.put(new DayOfWeek(Day.MON,1),130);
        dailyWeeklyDetails.setDayFares(dayFares);
        dailyWeeklyDetails.setJourneyFares(new ArrayList<>());
        fareAdjuster.applyWeeklyCapBeforeDailyCapOnDefaultFare(dailyWeeklyDetails);
        Assert.assertEquals(0,dailyWeeklyDetails.getJourneyFares().get(0));

    }
}