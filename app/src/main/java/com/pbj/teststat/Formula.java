package com.pbj.teststat;

/**
 * Created by Hellemn on 1/31/2015.
 */

public interface Formula {

    /**
     *
     * @param text MUST BE IN LOWER CASE
     * @return
     */
    public abstract double calculateFor(Person p);
}
