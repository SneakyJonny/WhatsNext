package com.example.jonny.whatsnext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.waka_list_view);    // 1
        final ArrayList<Waka> wakaList = Waka.getWakasFromFile("wakas.json", this);
        WakaAdapter adapter = new WakaAdapter(this, wakaList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, DisplayWaka.class);
                Waka tempWaka = (Waka) mListView.getItemAtPosition(position);
                System.out.println(tempWaka.title);
                myIntent.putExtra("waka", tempWaka);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.wakaAdd:
                addWaka();
                return true;
            case R.id.wakaRefresh:
                //showHelp();
                return true;
            case R.id.wakaHelp:
                //showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Called when the user taps the Send button */
    public void addWaka() {
        Intent intent = new Intent(this, DisplayWaka.class);
        Waka tempWaka = new Waka();
        intent.putExtra("waka", tempWaka);
        startActivity(intent);
    }
}
