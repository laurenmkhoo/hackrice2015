package com.pbj.teststat;

/**
 * Created by Hellemn on 1/31/2015.
 */

public interface Category {

    /**
     *
     * @param text MUST BE IN LOWER CASE
     * @return
     */
    public abstract int analyzeText(String text);
}
