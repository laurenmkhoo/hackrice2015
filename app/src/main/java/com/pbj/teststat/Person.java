package com.pbj.teststat;


import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hellemn on 1/31/2015.
 */

public class Person {
    public static final int SENT_TO_THEM = 0;
    public static final int RECEIVED_FROM_THEM = 1;

    private String name; // may be superfluous
    private ContactsContract.Contacts myContact;
    private Map<Category, Long> stats = new HashMap<Category, Long>();
    { // enum method from Java 8, I'll try and get the retro compiler working
        for (Category cat : AllCategories.values()) {
            stats.put(cat, new Long(0));
        }
    }

    // Counts of stuff
    private long[] countMessages = new long[] {0, 0};
    private long[] countWords = new long[] {0, 0};
    private long[] countChars = new long[] {0, 0};

    // Stats for Response Time
    private Long lastSentToThemTime = null;
    private boolean meSentLast = false;
    private List<Long> responseTimes = new ArrayList<Long>();


    public String getName() {
        return name;
    }

    /**
     * Updates all my parameters.
     * @param textMessage
     */
    public void update(SMSData textMessage) {
        assert (textMessage != null);
        boolean meSentToThem = textMessage.getFolderName() == SMSData.OUTBOX;

        // Response Time
        // If from me to them, then update my (not their) last sent time
        if (meSentToThem) {
            lastSentToThemTime = Long.parseLong(textMessage.getTime());
            meSentLast = true;
        }
        // Else if I've sent them something before, add the time to the list
        else if (lastSentToThemTime != null && meSentLast) {
            responseTimes.add(Long.parseLong(textMessage.getTime()) - lastSentToThemTime);
            meSentLast = false;
        }

        // Update each category
        for (Category cat : stats.keySet()) {
            stats.put(cat, stats.get(cat) + cat.analyzeText(textMessage.getBody()));
        }

        // Update all counts
        countMessagesAndWordsAndChars(textMessage.getBody(),
                meSentToThem? SENT_TO_THEM : RECEIVED_FROM_THEM);
    }


    /**
     * ONLY counts alphabetical characters into totalChars
     * @param text
    */
    public void countMessagesAndWordsAndChars(final String text, final int sentToThem) {
        assert (text != null);
        char c;

        // Clear starting whitespace
        int i = 0;
        while (!('a' <= (c = text.charAt(i++))
                && c <= 'z' || 'A' <= c && c <= 'Z'));

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
            else if (('a' <= c && c <= 'z')
                    || ('A' <= c && c <= 'Z')) {
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


}
