// IBookManager.aidl
package com.hqf.demo.aidldemo.aidl;

// Declare any non-default types here with import statements
import com.hqf.demo.aidldemo.aidl.Book;
import com.hqf.demo.aidldemo.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
