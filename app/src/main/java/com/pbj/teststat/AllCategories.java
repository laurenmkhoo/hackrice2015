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

    private static final String[] VANITY_TAGS = new String[] {"i", "me", "my", "moi"};
    private static final String[] PARTY_TAGS = new String[] {};


    /*
     * GIVE EVERY CATEGORY A UNIQUE ID SO THAT PERSON CLASS CAN IDENTIFY THEM
     */
    public static final int PROFANITY = 0;
    public static final int VANITY = 1;
    public static final int PARTY = 2;

    private static final Category[] categories = new Category[] {

            /*
             * PROFANITY DETECTION
             */
            new Category() {
                public int analyzeText(String text) {
                    return countTagInstances(text, PROFANITY_TAGS);
                }
                public int getUniqueID() { return PROFANITY; }
            },

            /*
             * VANITY DETECTION
             */
            new Category() {
                public int analyzeText(String text) {
                    return countTagInstances(text, VANITY_TAGS);
                }
                public int getUniqueID() { return VANITY; }
            },

            /*
             * PARTY DETECTION
             */
            new Category() {
                public int analyzeText(String text) {
                    return countTagInstances(text, PARTY_TAGS);
                }
                public int getUniqueID() { return PARTY; }
            }



    };

    /**
     * TEXT BETTER BE IN LOWER CASE BY NOW!
     * @param text
     * @param tags
     * @return
     */
    private static final int countTagInstances(String text, final String[] tags) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            for (String tag : tags) {
                if (text.startsWith(tag, i)) {
                    count++;
                }
            }
        }
        return count;
    }


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
