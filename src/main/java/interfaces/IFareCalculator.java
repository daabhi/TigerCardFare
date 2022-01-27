package interfaces;

import pojos.Journey;
import java.util.List;

public interface IFareCalculator {
    List<Integer> computeFare(List<Journey> journeyList);
}
