package com.pbj.teststat;

import android.app.Activity;
import android.content.res.Resources;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hellemn on 1/31/2015.
 */
public class AllCategories extends Activity {

    private static final String[] PROFANITY_TAGS = new String[] {"anal", "ass", "bastard", "beaner", "bitch", "boner", "fellatio",
            "camel toe", "chode", "clit", "cooch", "cock", "cum", "cunnilingus", "cunt", "damn", "dam", "dick", "douche",
            "fuck", "fag", "gay", "gringo", "homo", "nigger", "niggah", "penis", "poonani", "prick", "pussy", "queer",
            "rimjob", "shit", "sex", "slut", "skank", "spick", "tit", "twat", "wank", "whore"};


    private static final Category[] categories = new Category[] {

        /*
         * PROFANITY DETECTION
         */
        new Category() {
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
