package com.example.jonny.whatsnext;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

enum Mode {
    UPDATE, ADD, DISPLAY
}

public class EditWaka extends AppCompatActivity {

    private static final String TAG = "EditWaka";
    EditText title;
    EditText description;
    EditText value;
    EditText cost;
    EditText time;
    EditText completedOn;

    private Waka waka;

    private Mode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_waka);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        value = (EditText) findViewById(R.id.value);
        cost = (EditText) findViewById(R.id.cost);
        time = (EditText) findViewById(R.id.time);
        completedOn = (EditText) findViewById(R.id.completed_on);

        Bundle b = getIntent().getExtras();
        String modeStr = b.getString("mode");
        waka = b.getParcelable("waka");

        if (modeStr.equals("ADD")) {
            setMode(Mode.ADD);
        } else if (modeStr.equals("UPDATE")) {
            setMode(Mode.UPDATE);
        } else if (modeStr.equals("DISPLAY")) {
            setMode(Mode.DISPLAY);
        }

        if (waka != null) {
            title.setText(waka.title);
            description.setText(waka.description);
            value.setText(Double.toString(waka.value));
            cost.setText(Double.toString(waka.cost));
            time.setText(Double.toString(waka.time));
            completedOn.setText(waka.completedOn);
        } else {
            waka = new Waka();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_edit_waka, menu);

        if (mode == Mode.ADD || mode == Mode.UPDATE) {
            menu.findItem(R.id.wakaSave).setVisible(true);
            menu.findItem(R.id.wakaEdit).setVisible(false);
            menu.findItem(R.id.wakaComplete).setVisible(false);
            menu.findItem(R.id.wakaUncomplete).setVisible(false);
        } else {
            menu.findItem(R.id.wakaSave).setVisible(false);
            menu.findItem(R.id.wakaEdit).setVisible(true);
            if (waka.completedOn == null) {
                menu.findItem(R.id.wakaComplete).setVisible(true);
                menu.findItem(R.id.wakaUncomplete).setVisible(false);
            } else {
                menu.findItem(R.id.wakaComplete).setVisible(false);
                menu.findItem(R.id.wakaUncomplete).setVisible(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.wakaSave:
                saveWaka();
                return true;
            case R.id.wakaEdit:
                setMode(Mode.UPDATE);
                return true;
            case R.id.wakaCancel:
                if (mode == Mode.ADD) {
                    finish();
                } else if (mode == Mode.UPDATE) {
                    setMode(Mode.DISPLAY);
                } else if (mode == Mode.DISPLAY) {
                    finish();
                }
                return true;
            case R.id.wakaComplete:
                waka.complete();
                finish();
                return true;
            case R.id.wakaUncomplete:
                waka.uncomplete();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveWaka() {
        // Do something in response to button
        waka.title = title.getText().toString();
        waka.description = description.getText().toString();
        waka.value = Double.parseDouble(value.getText().toString());
        waka.cost = Double.parseDouble(cost.getText().toString());
        waka.time = Double.parseDouble(time.getText().toString());

        if (mode == Mode.DISPLAY) {
            setMode(Mode.UPDATE);
        } else {
            if (mode == Mode.ADD) {
                waka.write();
            } else if (mode == Mode.UPDATE) {
                waka.update();
            }
            setResult(RESULT_OK);
            finish();
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;

        //Lock fields on display
        if (mode == Mode.DISPLAY ) {
            disableEditText(title);
            disableEditText(description);
            disableEditText(value);
            disableEditText(cost);
            disableEditText(time);
            disableEditText(completedOn);

        // Otherwise unlock fields
        } else {
            enableEditText(title);
            enableEditText(description);
            enableEditText(value);
            enableEditText(cost);
            enableEditText(time);
        }

        if (waka == null || waka.completedOn == null) {
            completedOn.setVisibility(View.GONE);
        } else {
            completedOn.setVisibility(View.VISIBLE);
        }

        invalidateOptionsMenu();
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
    }

    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setFocusableInTouchMode(true);
    }
}