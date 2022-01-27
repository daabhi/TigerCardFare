package pojos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @EqualsAndHashCode @ToString
public class DayOfWeek {
    private Day day;
    private Integer week;
    public DayOfWeek(Day day,Integer week){
        this.day = day;
        this.week = week;
    }
}
