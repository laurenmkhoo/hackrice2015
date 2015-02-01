package com.pbj.teststat;

/**
 * Created by Hellemn on 1/31/2015.
 */
public class AllCategories {

    private static final String[] PROFANITY_TAGS = new String[] {"anal", "ass", "bastard", "beaner", "bitch", "boner",
            "camel toe", "chode", "clit", "cooch", "cock", "cum", "cunnilingus", "cunt", "damn", "dam", "dick", "douche",
            "fuck", "fag", "gay", "gringo", "homo", "nigger", "niggah", "penis", "poonani", "prick", "pussy", "queer",
            "rimjob", "shit", "sex", "slut", "skank", "spick", "tit", "twat", "wank", "whore"};

    private static Category[] categories = new Category[] {

        /*
         * PROFANITY DETECTION
         */
        new Category() {
            public int analyzeText(String text) {
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
            public int analyzeText(String text) {
                // WHATEVER NEEDS TO BE DONE
                return 0;
            }
        }
    };


    public static Category[] values() {
        return categories;
    }


    private AllCategories() {

    }
}
