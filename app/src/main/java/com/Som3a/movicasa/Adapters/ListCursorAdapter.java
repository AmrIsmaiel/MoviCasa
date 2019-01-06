package com.Som3a.movicasa.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Som3a.movicasa.Data.DBHelper;
import com.Som3a.movicasa.R;
import com.Som3a.movicasa.UI.BookMarks.BookMarks;

public class ListCursorAdapter extends CursorAdapter {

    private final BookMarks bookMarks;
    private Cursor mCursor;
    private DBHelper dbHelper;
    public ListCursorAdapter(BookMarks context, Cursor c) {
        super(context, c, 0);
        bookMarks = context;
        mCursor = c;
        dbHelper = new DBHelper(bookMarks);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_bookmark_card, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = view.findViewById(R.id.tv_movieTitleBookmarked);
        ImageView ivBookmarkSign = view.findViewById(R.id.img_bookmark);
        String title = cursor.getString(cursor.getColumnIndex(DBHelper.BOOKMARKS_COLUMN_STATE));
        tvTitle.setText(title);
        final int id = cursor.getInt(cursor.getColumnIndex(DBHelper.BOOKMARKS_COLUMN_MOVIE_ID));

        ivBookmarkSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteBookmark(id);
                mCursor.close();
                mCursor = dbHelper.getallBookmarks();
                swapCursor(mCursor);
            }
        });
    }
}