package pojos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Zone {
    private int zoneStartId;
    private int zoneEndId;
    public Zone(final int zoneStartId, final int zoneEndId){
        this.zoneStartId = zoneStartId;
        this.zoneEndId   = zoneEndId;
    }
}
