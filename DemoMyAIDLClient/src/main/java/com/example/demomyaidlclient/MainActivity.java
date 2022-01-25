package com.example.demomyaidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.demomyaidl.Book;
import com.example.demomyaidl.BookManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MyAidl";
    private BookManager bookManager;
    private boolean connected;
    private List<Book> bookList;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            bookManager = BookManager.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            connected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGetBookList = findViewById(R.id.get_booklist);
        btnGetBookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    try {
                        bookList = bookManager.getBookList();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    for (Book book : bookList) {
                        Log.d(TAG, "get book list: " + book.toString());
                    }
                }
            }
        });

        Button btnAddBookInOut = findViewById(R.id.add_book_in_out);
        btnAddBookInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    Book book = new Book("沙丘InOut");
                    try {
                        Log.d(TAG, "add a new book " + book.getName());
                        bookManager.addBookInOut(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Button btnAddBookIn = findViewById(R.id.add_book_in);
        btnAddBookIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    Book book = new Book("沙丘In");
                    try {
                        Log.d(TAG, "add a new book " + book.getName());
                        bookManager.addBookIn(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Button btnAddBookOut = findViewById(R.id.add_book_out);
        btnAddBookOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    Book book = new Book("沙丘Out");
                    try {
                        Log.d(TAG, "add a new book " + book.getName());
                        bookManager.addBookOut(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
//        intent.setPackage("com.example.demomyaidl");
//        intent.setAction("com.example.demomyaidl.action");
        intent.setClassName("com.example.demomyaidl", "com.example.demomyaidl.AIDLService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}