package com.team5.cbl;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CreatorFilter {

    /**
     * Filter comics by creator name (case-insensitive substring)
     * and by age range (minAge ≤ age ≤ maxAge).
     *
     * @param comics list of Comic objects
     * @param name   optional name substring (null or empty = ignore)
     * @param minAge optional minimum age (null = no lower bound)
     * @param maxAge optional maximum age (null = no upper bound)
     * @return filtered list of comics
     */
    public static List<Comic> filterByCreator(List<Comic> comics, String name, Integer minAge, Integer maxAge) {
        String search = (name == null ? "" : name.toLowerCase(Locale.ROOT));
        int min = (minAge == null) ? Integer.MIN_VALUE : minAge;
        int max = (maxAge == null) ? Integer.MAX_VALUE : maxAge;

        return comics.stream()
            .filter(c -> c.getCreator() != null)
            .filter(c -> search.isEmpty() || c.getCreator().getName().toLowerCase(Locale.ROOT).contains(search))
            .filter(c -> {
                int age = c.getCreator().getAge();
                return age >= min && age <= max;
            })
            .collect(Collectors.toList());
    }
}
