package com.Som3a.movicasa.UI.BookMarks;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.Som3a.movicasa.Adapters.ListCursorAdapter;
import com.Som3a.movicasa.Data.DBHelper;
import com.Som3a.movicasa.R;

import java.util.List;

public class BookMarks extends AppCompatActivity {
    ListView bookmarkList ;
    ListCursorAdapter mListAdapter;
    DBHelper dbHelper;
    Cursor cursor;
    ImageView imgClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_marks);
        bookmarkList = findViewById(R.id.listBookmark);
        imgClose = findViewById(R.id.img_close);
        dbHelper = new DBHelper(this);
        cursor = dbHelper.getallBookmarks();
        mListAdapter = new ListCursorAdapter(BookMarks.this,cursor);
        bookmarkList.setAdapter(mListAdapter);
        if(cursor.getCount() == 0){
            Toast.makeText(BookMarks.this,"NO BOOKMARKS" , Toast.LENGTH_SHORT).show();
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
