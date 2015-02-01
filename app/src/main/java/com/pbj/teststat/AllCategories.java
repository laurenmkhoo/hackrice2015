package com.pbj.teststat;

/**
 * Created by Hellemn on 1/31/2015.
 */
public class AllCategories {

    private static final String[] PROFANITY_TAGS = new String[] {"anal", "ass", "bastard", "beaner", "bitch", "boner", "fellatio",
            "camel toe", "chode", "clit", "cooch", "cock", "cum", "cunnilingus", "cunt", "damn", "dam", "dick", "douche",
            "fuck", "fag", "gay", "gringo", "homo", "nigger", "niggah", "penis", "poonani", "prick", "pussy", "queer",
            "rimjob", "shit", "sex", "slut", "skank", "spick", "tit", "twat", "wank", "whore"};

    /*
     * !!---OVERRIDE TO_STRING---!!
     */
    private static final Category[] categories = new Category[] {

        /*
         * PROFANITY DETECTION
         */
        new Category() {
            public String toString() { return "Most Profane"; }

            public int analyzeText(String text) {
                text = text.toLowerCase();
                int count = 0;
                for (int i = 0; i < text.length(); i++) {
                    for (String profanity : PROFANITY_TAGS) {
                        if (text.startsWith(profanity, i)) {
                            count++;
                        }
                    }
                }
                return count;
            }
        },

        // SOMETHING ELSE
        new Category() {
            public String toString() { return "Whatever this category is"; }

            public int analyzeText(String text) {
                // WHATEVER NEEDS TO BE DONE
                return 0;
            }
        }
    };


    /**
     * Connects this class to the Person class and ensures that all Cateogries will always be used
     * @return
     */
    public static Category[] values() {
        return categories;
    }


    private AllCategories() {

    }
}
