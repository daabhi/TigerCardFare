package pojos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class Zone {
    private int zoneStartId;
    private int zoneEndId;
    public Zone(final int zoneStartId, final int zoneEndId){
        this.zoneStartId = zoneStartId;
        this.zoneEndId   = zoneEndId;
    }
}
