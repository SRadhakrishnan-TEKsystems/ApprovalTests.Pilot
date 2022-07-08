package org.legacybehavior;

public class Person {
    private final String name;
    private final String superHeroName;
    private final String universe;

    private Person(String name, String superHeroName, String universe) {
        this.name = name;
        this.superHeroName = superHeroName;
        this.universe = universe;
    }

    public static Person getInstance(String name, String superHeroName, String universe) {
        return new Person(name, superHeroName, universe);
    }
}
