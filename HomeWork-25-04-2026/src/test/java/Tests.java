import org.example.Main;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void objectToMap_shouldConvertSimpleObjectToMap() throws Exception {
        Main.Address address = new Main.Address("Москва", 15);
        Main.Letter letter = new Main.Letter("Привет", 1, address);

        Map<String, Object> map = Main.objectToMap(letter);

        assertEquals("Привет", map.get("title"));
        assertEquals(1, map.get("priority"));

        assertTrue(map.get("address") instanceof Map);

        Map<String, Object> addressMap = (Map<String, Object>) map.get("address");
        assertEquals("Москва", addressMap.get("city"));
        assertEquals(15, addressMap.get("houseNumber"));
    }

    @Test
    void mapToObject_shouldRestoreObjectFromMap() throws Exception {
        Map<String, Object> addressMap = Map.of(
                "city", "Москва",
                "houseNumber", 15
        );

        Map<String, Object> letterMap = Map.of(
                "title", "Привет",
                "priority", 1,
                "address", addressMap
        );

        Main.Letter letter = Main.mapToObject(letterMap, Main.Letter.class);

        assertNotNull(letter);
        assertEquals("Привет", letter.getTitle());
        assertEquals(1, letter.getPriority());

        assertNotNull(letter.getAddress());
        assertEquals("Москва", letter.getAddress().getCity());
        assertEquals(15, letter.getAddress().getHouseNumber());
    }

    @Test
    void objectToMap_shouldReturnNullIfObjectIsNull() throws Exception {
        Map<String, Object> map = Main.objectToMap(null);

        assertNull(map);
    }

    @Test
    void mapToObject_shouldReturnNullIfMapIsNull() throws Exception {
        Main.Letter letter = Main.mapToObject(null, Main.Letter.class);

        assertNull(letter);
    }

    @Test
    void objectToMapAndMapToObject_shouldWorkTogether() throws Exception {
        Main.Letter original = new Main.Letter(
                "Важное письмо",
                5,
                new Main.Address("Казань", 10)
        );

        Map<String, Object> map = Main.objectToMap(original);
        Main.Letter restored = Main.mapToObject(map, Main.Letter.class);

        assertEquals(original.getTitle(), restored.getTitle());
        assertEquals(original.getPriority(), restored.getPriority());
        assertEquals(original.getAddress().getCity(), restored.getAddress().getCity());
        assertEquals(original.getAddress().getHouseNumber(), restored.getAddress().getHouseNumber());
    }
}
