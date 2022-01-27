package interfaces;

import pojos.DailyWeeklyDetails;
import pojos.DayOfWeek;

import java.util.List;
import java.util.Map;

public interface IFareAdjuster {
    void applyDefaultFare(DailyWeeklyDetails dailyWeeklyDetails);
    void applyDailyCapOnDefaultFare(DailyWeeklyDetails dailyWeeklyDetails);
    void applyWeeklyCapBeforeDailyCapOnDefaultFare(DailyWeeklyDetails dailyWeeklyDetails);
}
