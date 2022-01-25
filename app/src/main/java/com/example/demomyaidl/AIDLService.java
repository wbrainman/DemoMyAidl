package com.example.demomyaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {

//    private final String TAG = AIDLService.class.getSimpleName();
    private final String TAG = "MyAidl";
    private List<Book> bookList;

    public AIDLService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList = new ArrayList<>();

        Book book1 = new Book("活着");
        Book book2 = new Book("追风筝的人");
        Book book3 = new Book("百年孤独");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        Log.d(TAG, "server onCreate: ");
    }

    private final BookManager.Stub stub = new BookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            if (book != null) {
                Log.d(TAG, "addBookInOut: " + book.toString());
                book.setName("addBookInOut");
                bookList.add(book);
            } else {
                Log.e(TAG, "addBookInOut: book is null");
            }
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            if (book != null) {
                Log.d(TAG, "addBookIn: " + book.toString());
                book.setName("addBookIn");
                bookList.add(book);
            } else {
                Log.e(TAG, "addBookIn: book is null");
            }

        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            if (book != null) {
                Log.d(TAG, "addBookOut: " + book.toString());
                book.setName("addBookOut");
                bookList.add(book);
            } else {
                Log.e(TAG, "addBookOut: book is null");
            }

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "server onBind: ");
        return stub;
    }
}
