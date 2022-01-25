// BookManager.aidl
package com.example.demomyaidl;
import com.example.demomyaidl.Book;

// Declare any non-default types here with import statements

interface BookManager {
    List<Book> getBookList();
    void addBookInOut(inout Book book);

    void addBookIn(in Book book);

    void addBookOut(out Book book);
}