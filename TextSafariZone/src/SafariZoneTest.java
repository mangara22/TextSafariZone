import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SafariZoneTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        SafariZone.loadAllPokemon("pokemon");
        SafariZone.loadAllZones();
    }

    @Test
    @DisplayName("Load Test")
    void loadTest() {
        assertEquals(75, SafariZone.getAllPokemon().size());
        assertEquals("HO-OH", SafariZone.getAllPokemon().get(11).getName());
        assertEquals("REGISTEEL", SafariZone.getAllPokemon().get(54).getName());
        assertEquals("ROSELIA", SafariZone.getAllZones().get(1).getZonePokemon().get(2).getName()); //forest zone, 3rd pokemon
        assertEquals("GRENINJA", SafariZone.getAllZones().get(3).getZonePokemon().get(14).getName()); //tundra zone, 14th pokemon
    }
}