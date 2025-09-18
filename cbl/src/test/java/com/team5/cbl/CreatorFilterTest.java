package com.team5.cbl;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CreatorFilterTest {

    @Test
    public void testFilterByNameAndAge() {
        List<Comic> comics = List.of(
            new Comic("Neon Sentinel", new Creator("R. Mendez", 35)),
            new Comic("Moonblade Chronicles", new Creator("D. Calder", 41)),
            new Comic("Garden of Titans", new Creator("S. Okoye", 52))
        );

        // Filter creators with name containing "men" and age between 30–40
        List<Comic> result = CreatorFilter.filterByCreator(comics, "men", 30, 40);

        assertEquals(1, result.size());
        assertEquals("Neon Sentinel", result.get(0).getTitle());
    }

    @Test
    public void testFilterWithNoNameOnlyAgeRange() {
        List<Comic> comics = List.of(
            new Comic("Alpha", new Creator("Alice", 20)),
            new Comic("Beta", new Creator("Bob", 50))
        );

        // Age between 30–60 should return "Beta"
        List<Comic> result = CreatorFilter.filterByCreator(comics, null, 30, 60);

        assertEquals(1, result.size());
        assertEquals("Beta", result.get(0).getTitle());
    }

    @Test
    public void testFilterWithNoBoundsReturnsAll() {
        List<Comic> comics = List.of(
            new Comic("X", new Creator("Xavier", 25)),
            new Comic("Y", new Creator("Yara", 40))
        );

        // No name, no age restrictions → all comics
        List<Comic> result = CreatorFilter.filterByCreator(comics, null, null, null);

        assertEquals(2, result.size());
    }
}
