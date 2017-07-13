// IOnNewBookArrivedListener.aidl
package com.hqf.demo.aidldemo.aidl;

// Declare any non-default types here with import statements
import com.hqf.demo.aidldemo.aidl.Book;

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book newBook);
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
