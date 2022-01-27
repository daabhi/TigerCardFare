package util;

import org.junit.jupiter.api.Test;
import pojos.Cap;
import pojos.Zone;

import static org.junit.jupiter.api.Assertions.*;
/*
Zones          Daily Cap Weekly Cap (Monday - Sunday)
1-1            100       500
1 - 2 or 2 - 1 120       600
2-2            80        400
 */
class FareCappingConfigurationsTest {
    private FareCappingConfigurations fareCappingConfigurations = new FareCappingConfigurations();
    @Test
    void build() {
        fareCappingConfigurations.build(new Zone(1,1), Cap.DAILY,100);
        fareCappingConfigurations.build(new Zone(1,1), Cap.WEEKLY,500);

        fareCappingConfigurations.build(new Zone(1,2), Cap.DAILY,120);
        fareCappingConfigurations.build(new Zone(1,2), Cap.WEEKLY,600);

        fareCappingConfigurations.build(new Zone(2,2), Cap.DAILY,80);
        fareCappingConfigurations.build(new Zone(2,2), Cap.WEEKLY,400);

        assertEquals(100, fareCappingConfigurations.getFareCappingConfig().get(new Zone(1,1)).get(Cap.DAILY));
    }
}