package com.pbj.teststat;


import android.provider.ContactsContract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hellemn on 1/31/2015.
 */
public class Person {

    private String name; // may be superfluous
    private ContactsContract.Contacts myContact;
    private Map<Category, Double> stats = new HashMap<>();


    public String getName() {
        return name;
    }


    public update(String text) {
        assert (text != null);

        for (Category cat : stats.keySet()) {
            stats.put(cat,
                    stats.getOrDefault(cat, 0) + cat.countOccurances(text));
        }
    }


    public double getStat(Category category) {
        return stats.get(category);
    }


}
