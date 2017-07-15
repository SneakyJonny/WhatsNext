package com.example.jonny.whatsnext;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Jonny on 7/8/2017.
 */

public class Waka implements Comparable<Waka>, Parcelable {

    public String title;
    public String description;
    public double value;
    public double time;
    public double cost;

    public final double timeValue = 5;

    //parcel part
    public Waka() {
    }

    public Waka(Parcel in){
        String[] data= new String[1];

        this.title = in.readString();
        this.description = in.readString();
        this.value = in.readDouble();
        this.time = in.readDouble();
        this.cost = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.title);
        out.writeString(this.description);
        out.writeDouble(this.value);
        out.writeDouble(this.time);
        out.writeDouble(this.cost);
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

    public static ArrayList<Waka> getWakasFromFile(String filename, Context context) {
        final ArrayList<Waka> wakaList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("wakas.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray wakas = json.getJSONArray("wakas");

            // Get the wakas
            for(int i = 0; i < wakas.length(); i++) {
                Waka waka = new Waka();

                waka.title = wakas.getJSONObject(i).getString("title");
                waka.description = wakas.getJSONObject(i).getString("description");
                waka.value = wakas.getJSONObject(i).getDouble("value");
                waka.time = wakas.getJSONObject(i).getDouble("time");
                waka.cost = wakas.getJSONObject(i).getDouble("cost");

                wakaList.add(waka);
            }
            Collections.sort(wakaList);
            Collections.reverse(wakaList);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return wakaList;
    }

    public double getPriority() {
        return value / (time * timeValue + cost);
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
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
}