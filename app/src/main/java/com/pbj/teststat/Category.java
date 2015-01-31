package com.pbj.teststat;

/**
 * Created by Hellemn on 1/31/2015.
 */
public enum Category {
    PROFANITY(),
    VANITY(),
    TEMPER(),
    HUNGER(),
    PARTYER();

    private String[] triggerWords;
    private Function<String, Int> function;


    private Category(String triggerWords) {
        this.triggerWords = triggerWords;
    }

    public int countOccurances(String text) {

    }
}