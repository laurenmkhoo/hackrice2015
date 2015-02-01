package com.pbj.teststat;


import android.provider.ContactsContract;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hellemn on 1/31/2015.
 */

public class Person {
    public static final int SENT_TO_THEM = 0;
    public static final int RECEIVED_FROM_THEM = 1;

    private String name; // may be superfluous
    private String myContact;
    private Map<Category, Long> stats = new HashMap<Category, Long>();
    {
        for (Category cat : AllCategories.values()) {
            stats.put(cat, new Long(0));
        }
    }

    // Counts of stuff
    private long[] countMessages = new long[] {0, 0};
    private long[] countWords = new long[] {0, 0};
    private long[] countChars = new long[] {0, 0};
    private long[] countEmoji = new long[] {0, 0};

    // Stats for Response Time
    private boolean meSentLast = false;
    private Long lastSentToThemTime = null;
    private BigInteger totalResponseTime = BigInteger.ZERO;
    private int numResponseInstances = 0;

    //Rankings
    private int profRank;
    private int narcRank;
    private int sesqRank;
    private int krunkRank;
    private int loveRank;
    private int stalkRank;
    private int laughRank;
    private int knowRank;
    private int basicRank;
    private int triggerRank;
    private int novelRank;

     public Person(String number, String inputName){
        myContact = number;
        name = inputName;
    }

    public String getName() {
        return name;
    }
    public String getNumber(){ return myContact;}
    /**
     * Updates all my parameters.
     * @param textMessage
     */
    public void update(SMSData textMessage) {
        assert (textMessage != null);
        boolean meSentToThem = textMessage.getFolderName() == SMSData.OUTBOX;
        String lowerCaseText = textMessage.getBody().toLowerCase();

        // Response Time
        // If from me to them, then update my (not their) last sent time
        if (meSentToThem) {
            lastSentToThemTime = Long.parseLong(textMessage.getTime());
            meSentLast = true;
        }
        // Else if I've sent them something before, add the time to the list
        else if (meSentLast) {
            totalResponseTime.add(BigInteger.valueOf(
                    (Long.parseLong(textMessage.getTime()) - lastSentToThemTime) / 1000
            ));
            meSentLast = false;
            numResponseInstances++;
        }

        // Update each category
        for (Category cat : stats.keySet()) {
            stats.put(cat, stats.get(cat) + cat.analyzeText(lowerCaseText));
        }

        // Update all counts
        countMessagesAndWordsAndChars(lowerCaseText, meSentToThem? SENT_TO_THEM : RECEIVED_FROM_THEM);
    }


    /**
     * ONLY counts alphabetical characters into totalChars
     * TEXT MUST BE IN LOWER CASE!!
     * @param text
    */
    public void countMessagesAndWordsAndChars(final String text, final int sentToThem) {
        assert (text != null);
        char c;

        // Clear starting whitespace
        int i = 0;
        while (i < text.length() && ('a' > (c = text.charAt(i++)) || c > 'z'));

        // Loop through the rest of the text
        boolean wasChar = false;
        for (; i < text.length(); i++) {
            c = text.charAt(i);
            assert (c != '\t');

            // Whitespace. Maybe a word!
            if (c == ' ' || c == '\n') {
                if (wasChar) {
                    countWords[sentToThem]++;
                }
                wasChar = false;
            }

            // Alphabetical Character
            else if ('a' <= c && c <= 'z') {
                countChars[sentToThem]++;
                wasChar = true;
            }

            // Numerical, Punctuation, or Something Much Worse
            else {
                wasChar = false;
            }
        }

        // The final word did not have a space after it
        if (wasChar) {
            countWords[sentToThem]++;
        }

        // And this has only been one message
        countMessages[sentToThem]++;
    }


    public double getCountOf(Category category) {
        return stats.get(category);
    }

    /**
     *
     * @param sentToThem either Person.SENT_TO_THEM or Person.RECEIVED_FROM_THEM
     * @return
     */
    public long getTotalMessages(int sentToThem) {
        return countMessages[sentToThem];
    }

    /**
     *
     * @param sentToThem either Person.SENT_TO_THEM or Person.RECEIVED_FROM_THEM
     * @return
     */
    public long getTotalWords(int sentToThem) {
        return countWords[sentToThem];
    }

    /**
     *
     * @param sentToThem either Person.SENT_TO_THEM or Person.RECEIVED_FROM_THEM
     * @return
     */
    public long getTotalChars(int sentToThem) {
        return countChars[sentToThem];
    }


    public double getAverageResponseTime() {
        return totalResponseTime.divide(BigInteger.valueOf(numResponseInstances)).longValue();
    }


}