package id.ac.poliban.mi.nia.sqlitetesting.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import id.ac.poliban.mi.nia.sqlitetesting.dao.FriendDao;
import id.ac.poliban.mi.nia.sqlitetesting.domain.Friend;

public class FriendDaoImplSQLite extends SQLiteOpenHelper implements FriendDao {
    private static final String DB_NAME = "dts.db";
    private static final int DB_VERSION = 2;

    public FriendDaoImplSQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table friend(" +
                "id integer not null primary key autoincrement," +
                "name text not null," +
                "address text," +
                "phone text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            db.execSQL("drop table if exists friend");
            onCreate(db);
        }
    }

    @Override
    public void insert(Friend f) {
        String sql = "insert into friend values(?,?,?,?)";
        getWritableDatabase().execSQL(sql, new Object[]{
                null,
                f.getName(),
                f.getAddress(),
                f.getPhone()
        });
    }

    @Override
    public void update(Friend f) {
        String sql = "update friend set name=?, address=?, phone=? where id=?";
        getWritableDatabase().execSQL(sql, new Object[]{
                f.getName(),
                f.getAddress(),
                f.getPhone(),
                f.getId()
        });
    }

    @Override
    public void delete(int id) {
        String sql = "delete from friend where id=?";
        getWritableDatabase().execSQL(sql, new Object[]{id});
    }

    @Override
    public Friend getAFriendById(int id) {
        Friend f = null;
        String sql = "select * from friend where id=?";
        Cursor c = getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(id)});
        if (c.moveToFirst())
            f = new Friend(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3)
            );

        c.close();
        return f;
    }

    @Override
    public List<Friend> getAllFriends() {
        List<Friend> fs = new ArrayList<>();
        String sql = "select * from friend";
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        while (c.moveToNext())
            fs.add(new Friend(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3)
            ));

        c.close();
        return fs;
    }
}
