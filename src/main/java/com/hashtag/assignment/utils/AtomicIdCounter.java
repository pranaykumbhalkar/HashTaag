/**
 *
 */
package com.hashtag.assignment.utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created By Pranay on 8/27/2018
 */

public class AtomicIdCounter {

    public static synchronized Long getRandomUID() {
        AtomicLong counter = new AtomicLong(System.currentTimeMillis());
        Random r = new Random();
        return (counter.incrementAndGet() + r.nextInt());
    }

}
