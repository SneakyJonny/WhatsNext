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

    private boolean showCompleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
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
                refreshList();
                return true;
            case R.id.wakaHelp:
                //showHelp();
                return true;
            case R.id.wakaToggleCompleted:
                showCompleted = !showCompleted;
                refreshList();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Called when the user taps the Send button */
    public void addWaka() {
        Intent intent = new Intent(this, EditWaka.class);
        int result = 0;
        intent.putExtra("mode", "ADD");
        startActivityForResult(intent, result);
    }

    public void refreshList() {
        mListView = (ListView) findViewById(R.id.waka_list_view);
        final ArrayList<Waka> wakaList = Waka.getWakas(showCompleted);
        WakaAdapter adapter = new WakaAdapter(this, wakaList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditWaka.class);
                Waka waka = (Waka) mListView.getItemAtPosition(position);
                intent.putExtra("waka", waka);
                intent.putExtra("mode", "DISPLAY");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshList();
    }
}
