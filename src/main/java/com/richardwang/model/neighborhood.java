package com.richardwang.model;

public enum neighborhood {
    BAYVIEW("Bayview"),
    BERNAL("Bernal Heights"),
    CASTRO("Castro/Upper Market"),
    CHINATOWN("Chinatown"),
    CROCKER("Crocker Amazon"),
    DIAMOND("Diamond Heights"),
    DOWNTOWN("Downtown/Civic Center"),
    EXCELSIOR("Excelsior"),
    FINANCIAL("Financial District"),
    GLEN("Glen Park"),
    GOLDEN("Golden Gate Park"),
    HAIGHT("Haight Ashbury"),
    INNERR("Inner Richmond"),
    INNERS("Inner Sunset"),
    LAKESHORE("Lakeshore"),
    MARINA("Marina"),
    MISSION("Mission"),
    NOB("Nob Hill"),
    NOE("Noe Valley"),
    NORTH("North Beach"),
    OCEAN("Ocean View"),
    OUTERM("Outer Mission"),
    OUTERR("Outer Richmond"),
    OUTERS("Outer Sunset"),
    PACIFIC("Pacific Heights"),
    PARKSIDE("Parkside"),
    POTRERO("Potrero Hill"),
    PRESIDIO("Presidio"),
    PRESIDIOH("Presidio Heights"),
    RUSSIAN("Russian Hill"),
    SEACLIFF("Seacliff"),
    SOUTH("South of Market"),
    TREASURE("Treasure Island/YBI"),
    TWIN("Twin Peaks"),
    VISITACION("Visitacion Valley"),
    WEST("West of Twin Peaks"),
    WESTERN("Western Addition");


    private String name;

    neighborhood(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
