package com.example.jonny.whatsnext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.jonny.whatsnext.db.WakaContract;
import com.example.jonny.whatsnext.db.WakaDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class Waka implements Comparable<Waka>, Parcelable {

    private static final String TAG = "Waka";

    public long id;
    public String title;
    public String description;
    public double value;
    public double time;
    public double cost;
    public String completedOn;

    public final double timeValue = 5;

    //parcel part
    public Waka() {
    }

    public Waka(Parcel in){
        String[] data= new String[1];
        this.id = in.readLong();
        this.title = in.readString();
        this.description = in.readString();
        this.value = in.readDouble();
        this.time = in.readDouble();
        this.cost = in.readDouble();
        this.completedOn = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.id);
        out.writeString(this.title);
        out.writeString(this.description);
        out.writeDouble(this.value);
        out.writeDouble(this.time);
        out.writeDouble(this.cost);
        out.writeString(this.completedOn);
    }

    public static final Parcelable.Creator<Waka> CREATOR
            = new Parcelable.Creator<Waka>() {
        public Waka createFromParcel(Parcel in) {
            return new Waka(in);
        }

        public Waka[] newArray(int size) {
            return new Waka[size];
        }
    };

    public static ArrayList<Waka> getWakas(boolean includeCompleted) {
        final ArrayList<Waka> wakaList = new ArrayList<>();

        SQLiteDatabase db = WakaDbHelper.getInstance().getReadableDatabase();

        Cursor cursor = db.query(WakaContract.WakaEntry.TABLE,
                new String[]{WakaContract.WakaEntry._ID,
                             WakaContract.WakaEntry.COL_WAKA_TITLE,
                             WakaContract.WakaEntry.COL_WAKA_DESCRIPTION,
                             WakaContract.WakaEntry.COL_WAKA_VALUE,
                             WakaContract.WakaEntry.COL_WAKA_COST,
                             WakaContract.WakaEntry.COL_WAKA_TIME,
                             WakaContract.WakaEntry.COL_WAKA_COMPLETED_ON},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Waka waka = new Waka();
            waka.id = cursor.getLong(cursor.getColumnIndexOrThrow(WakaContract.WakaEntry._ID));
            waka.title = cursor.getString(cursor.getColumnIndexOrThrow(WakaContract.WakaEntry.COL_WAKA_TITLE));
            waka.description = cursor.getString(cursor.getColumnIndexOrThrow(WakaContract.WakaEntry.COL_WAKA_DESCRIPTION));
            waka.value = cursor.getDouble(cursor.getColumnIndexOrThrow(WakaContract.WakaEntry.COL_WAKA_VALUE));
            waka.cost = cursor.getDouble(cursor.getColumnIndexOrThrow(WakaContract.WakaEntry.COL_WAKA_COST));
            waka.time = cursor.getDouble(cursor.getColumnIndexOrThrow(WakaContract.WakaEntry.COL_WAKA_TIME));
            waka.completedOn = cursor.getString(cursor.getColumnIndexOrThrow(WakaContract.WakaEntry.COL_WAKA_COMPLETED_ON));
            if (!includeCompleted && waka.completedOn != null || includeCompleted && waka.completedOn == null) {
                continue;
            }
            wakaList.add(waka);
        }

        cursor.close();
        Collections.sort(wakaList);
        Collections.reverse(wakaList);
        return wakaList;
    }

    public void write() {
        SQLiteDatabase db = WakaDbHelper.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WakaContract.WakaEntry.COL_WAKA_TITLE, this.title);
        values.put(WakaContract.WakaEntry.COL_WAKA_DESCRIPTION, this.description);
        values.put(WakaContract.WakaEntry.COL_WAKA_VALUE, this.value);
        values.put(WakaContract.WakaEntry.COL_WAKA_COST, this.cost);
        values.put(WakaContract.WakaEntry.COL_WAKA_TIME, this.time);
        this.id = db.insert(WakaContract.WakaEntry.TABLE, null, values);
    }

    public void update() {
        SQLiteDatabase db = WakaDbHelper.getInstance().getWritableDatabase();
        String strFilter = "_id = ?";
        String[] selectionArgs = {Double.toString(id)};
        ContentValues values = new ContentValues();
        values.put(WakaContract.WakaEntry.COL_WAKA_TITLE, this.title);
        values.put(WakaContract.WakaEntry.COL_WAKA_DESCRIPTION, this.description);
        values.put(WakaContract.WakaEntry.COL_WAKA_VALUE, this.value);
        values.put(WakaContract.WakaEntry.COL_WAKA_COST, this.cost);
        values.put(WakaContract.WakaEntry.COL_WAKA_TIME, this.time);
        values.put(WakaContract.WakaEntry.COL_WAKA_COMPLETED_ON, this.completedOn);
        db.update("wakas", values, strFilter, selectionArgs);
    }

    public void delete() {
        SQLiteDatabase db = WakaDbHelper.getInstance().getWritableDatabase();
        String strFilter = "_id = ?";
        String[] selectionArgs = {Double.toString(id)};
        db.delete("wakas", strFilter, selectionArgs);
    }

    public double getPriority() {
        return value / (time * timeValue + cost);
    }

    @Override
    public int compareTo(Waka o) {
        if (this.getPriority() > o.getPriority()) {
            return 1;
        } else if (this.getPriority() == o.getPriority()) {
            return 0;
        } else {
            return -1;
        }
    }

    public String printValue() {
        return String.format("%.2f",this.value);
    }

    public String printTime() {
        return String.format("%.2f",this.time);
    }

    public String printCost() {
        return String.format("%.2f",this.cost);
    }

    public String printPriority() {
        return String.format("%.2f",this.getPriority());
    }

    public void complete() {
        completedOn = getDateTime();
        update();
    }

    public void uncomplete() {
        completedOn = null;
        update();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}