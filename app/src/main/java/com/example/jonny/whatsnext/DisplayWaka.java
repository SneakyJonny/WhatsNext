package com.example.jonny.whatsnext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayWaka extends AppCompatActivity {

    //private Waka waka;

    //DisplayWaka(Waka wakaIn) {
    //    waka = wakaIn;
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_waka);

        Bundle b = getIntent().getExtras();
        Waka waka = b.getParcelable("waka");

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(waka.title);

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(waka.description);

        TextView value = (TextView) findViewById(R.id.value);
        value.setText("Value: " + waka.printValue());

        TextView cost = (TextView) findViewById(R.id.cost);
        cost.setText("Cost: " + waka.printCost());

        TextView time = (TextView) findViewById(R.id.time);
        time.setText("Time (hours) : " + waka.printTime());

        TextView priority = (TextView) findViewById(R.id.priority);
        priority.setText("Priority: " + waka.printPriority());
    }
}