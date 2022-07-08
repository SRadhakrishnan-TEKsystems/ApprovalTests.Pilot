package org.legacybehavior;

import java.time.Instant;
import java.util.UUID;

public class Person {
    private final String name;
    private final String superHeroName;
    private final String universe;
    private final UUID id;
    private final Instant creationInstance;

    private Person(String name, String superHeroName, String universe, UUID id, Instant creationInstance) {
        this.name = name;
        this.superHeroName = superHeroName;
        this.universe = universe;
        this.id =id;
        this.creationInstance = creationInstance;
    }

    public static Person getInstance(String name, String superHeroName, String universe, UUID id, Instant creationInstance) {
        return new Person(name,superHeroName,universe,id,creationInstance);
    }
}
