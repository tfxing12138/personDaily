package com.tfxing.rpgGame.entity.enums;

public enum StatsEnums {
    FIRE("fire","火"),
    PHYSICS("physics", "物理"),
    ICE("ice","冰"),
    LIGHT("light","光"),
    DARK("dark","暗"),
    THUNDER("thunder", "雷"),
    WIND("wind","冲击");

    StatsEnums(String statsName, String desc) {
        this.statsName = statsName;
        this.desc = desc;
    }

    private String statsName;

    private String desc;

}
