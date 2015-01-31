package com.pbj.teststat;


import android.provider.ContactsContract;

/**
 * Created by Hellemn on 1/31/2015.
 */
public class Person {

    private String name; // may be superfluous
    private ContactsContract.Contacts myContact;
    private Map<Category, Double> stats;


    public String getName() {
        return name;
    }


    public double getStat(Category category) {
        return stats.get(category);
    }


}
