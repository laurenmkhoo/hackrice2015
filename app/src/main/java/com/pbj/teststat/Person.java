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

    private String name; // may be superfluous
    private ContactsContract.Contacts myContact;
    private Map<Category, Long> stats = new HashMap<>();

    // Other statistics we need
    private long totalMessages;
    private long totalWords;
    private long totalChars;
    private long messagesToThem;
    private long lastSentToThemTime;
    private List<Long> responseTimes = new ArrayList<>();


    public String getName() {
        return name;
    }


    public void update(SMSData textMessage) {
        assert (textMessage != null);

        // Update each category
        for (Category cat : stats.keySet()) {
            stats.put(cat,
                    stats.getOrDefault(cat, 0)
                            + cat.countOccurances(textMessage.getBody()));
        }
    }


    public double getStat(Category category) {
        return stats.get(category);
    }


}
