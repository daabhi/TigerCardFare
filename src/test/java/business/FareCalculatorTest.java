package business;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojos.*;
import configuration.Configuration;
import configuration.FareCappingConfigurations;
import configuration.TravelTimeConfigurations;
import configuration.ZoneFeesConfigurations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pojos.Day.*;

/*
Day Time From Zone To Zone Calculated Fare Explanation
Monday 10:20 2 1 35 Peak hours Single fare
Monday 10:45 1 1 25 Off-peak single fare
Monday 16:15 1 1 25 Off-peak single fare
Monday 18:15 1 1 30 Peak hours Single fare
Monday 19:00 1 2 5  The Daily cap reached 120 for zone 1 - 2. Charged 5 instead of 35

 */
class FareCalculatorTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final TravelTimeConfigurations travelTimeConfigurations = new TravelTimeConfigurations();
    private final ZoneFeesConfigurations zoneFeesConfigurations = new ZoneFeesConfigurations();
    private final FareCappingConfigurations fareCappingConfigurations = new FareCappingConfigurations();
    private Configuration configuration;
    private FareCalculator fareCalculator;
    private List<Journey> journeyList = new ArrayList<>();
    private List<Integer> expectedFares = new ArrayList<>();
    private final String filePath = "/Users/user/IdeaProjects/TigerCard/src/test/java/";

    @BeforeEach
    public void setup(){
        loadTravelTimeConfig("TravelTimeConfig");
        loadZoneFeesConfig();
        loadFareCappingConfig();
        configuration = new Configuration(travelTimeConfigurations,zoneFeesConfigurations,fareCappingConfigurations);
        fareCalculator = new FareCalculator(new DailyWeeklyFareCalculator(new DailyFareCalculator(configuration),new WeeklyFareCalculator(configuration)), new FareAdjuster());
        journeyList.clear();
        expectedFares.clear();
    }
    @Test
    void computeFareFromProblemStatementPDF() {
        load("Journeys");
        IntStream.range(0, expectedFares.size()).forEach(i -> assertEquals(expectedFares.get(i), fareCalculator.computeFare(journeyList).get(i)));
    }
    @Test
    void computeFareReachingLimitInSameZone() {
        load("JourneysReachingLimitSameZone");
        IntStream.range(0, expectedFares.size()).forEach(i -> assertEquals(expectedFares.get(i), fareCalculator.computeFare(journeyList).get(i)));
    }
    @Test
    void computeFareMultipleDays() {
        load("JourneysMultipleDays");
        int bound = expectedFares.size();
        List<Integer> calculatedFares = fareCalculator.computeFare(journeyList);
        for(int i =0; i< bound ; i++){
            //logger.info("Index Test="+i);
            assertEquals(expectedFares.get(i), calculatedFares.get(i));
        }
    }

    private void loadFareCappingConfig() {
        fareCappingConfigurations.build(new Zone(1,1), Cap.DAILY,100);
        fareCappingConfigurations.build(new Zone(1,1), Cap.WEEKLY,500);
        fareCappingConfigurations.build(new Zone(1,2), Cap.DAILY,120);
        fareCappingConfigurations.build(new Zone(1,2), Cap.WEEKLY,600);
        fareCappingConfigurations.build(new Zone(2,2), Cap.DAILY,80);
        fareCappingConfigurations.build(new Zone(2,2), Cap.WEEKLY,400);
    }
    public void loadTravelTimeConfig(String fileName){
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(Paths.get(filePath+ fileName + ".txt"));
        } catch (IOException e) {
        }
        if (allLines != null) {
            for (String line : allLines) {
                if (!line.isEmpty() && !line.isBlank() && !line.contains("#")) {
                    String[] content = line.split(" ");
                    Day dayOfWeek = getDayOfWeek(content[0]);
                    String[] time = content[1].split("\\|");
                    String morningTime = time[0];
                    String eveningTime = time[1];
                    travelTimeConfigurations.build(dayOfWeek, morningTime);
                    travelTimeConfigurations.build(dayOfWeek, eveningTime);
                }
            }
        }
    }
    public void loadZoneFeesConfig(){
        zoneFeesConfigurations.build(new Zone(1,1), PeriodOfDay.PEAK_PERIOD, 30);
        zoneFeesConfigurations.build(new Zone(1,1), PeriodOfDay.OFF_PEAK_PERIOD, 25);
        zoneFeesConfigurations.build(new Zone(1,2), PeriodOfDay.PEAK_PERIOD, 35);
        zoneFeesConfigurations.build(new Zone(1,2), PeriodOfDay.OFF_PEAK_PERIOD, 30);
        zoneFeesConfigurations.build(new Zone(2,2), PeriodOfDay.PEAK_PERIOD, 25);
        zoneFeesConfigurations.build(new Zone(2,2), PeriodOfDay.OFF_PEAK_PERIOD, 20);
    }
    public void addJourney(int weekOfYear, Day dow, String time, String zones, int expectedFare, String comments) {
        String[] timeString = time.split(":");
        String[] zoneString = zones.split("-");
        int hour = Integer.parseInt(timeString[0]); int min = Integer.parseInt(timeString[1]);
        int startZone = Integer.parseInt(zoneString[0]); int endZone = Integer.parseInt(zoneString[1]);
        journeyList.add(new Journey(weekOfYear,dow, LocalTime.of(hour,min),new Zone(startZone,endZone)));
        expectedFares.add(expectedFare);
    }
    private void load(String fileName)  {
        journeyList.clear();
        try {
            List<String> allLines = Files.readAllLines(Paths.get(filePath+ fileName + ".txt"));
            for(String line : allLines){
                if (!line.isBlank() && !line.isEmpty() && !line.contains("#")) {
                    String[] contents = line.split(" ");
                    Integer weekOfYear = Integer.parseInt(contents[0]);
                    Day day = getDayOfWeek(contents[1]);
                    String time = contents[2];
                    String zone = contents[3] + "-" + contents[4];
                    int expectedFare = Integer.parseInt(contents[5]);
                    String comments = "";
                    if (contents.length == 6)
                        comments = contents[6];
                    addJourney(weekOfYear, day, time, zone, expectedFare, comments);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private Day getDayOfWeek(String dayOfWeek) {
        switch (dayOfWeek){
            case "Monday" : return MON;
            case "Tuesday": return TUE;
            case "Wednesday" : return WED;
            case "Thursday" : return THU;
            case "Friday" : return FRI;
            case "Saturday" : return SAT;
            case "Sunday" : return SUN;
        }
        return MON;
    }

}