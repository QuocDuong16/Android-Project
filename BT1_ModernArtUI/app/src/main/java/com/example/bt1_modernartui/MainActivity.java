package com.example.bt1_modernartui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static private final String URL = "https://hoctructuyen.sgu.edu.vn/";
    SeekBar sb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        event();
    }

    private void event() {
        sb = (SeekBar) findViewById(R.id.seekBar);
        sb.setMax(100);

        final TextView purpleView = (TextView) findViewById(R.id.purpleView);
        final TextView pinkView = (TextView) findViewById(R.id.pinkView);
        final TextView redView = (TextView) findViewById(R.id.redView);
        final TextView whiteView = (TextView) findViewById(R.id.whiteView);
        final TextView blueView = (TextView) findViewById(R.id.blueView);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int change = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                change = i;
                int purple[] = {238,130,238};
                int pink[] = {255,192,203};
                int red[] = {255,0,0};
                int white[] = {255,255,255};
                int blue[] = {0,191,255};

                purple[0] -= (17/100)*change;
                purple[1] += (125/100)*change;
                purple[2] -= (17/100)*change;
                pink[0] -= (255/100)*change;
                pink[1] += (63/100)*change;
                pink[2] -= (52/100)*change;
                red[0] -= (255/100)*change;
                red[1] += (255/100)*change;
                red[2] += (255/100)*change;
                white[0] -= (255/100)*change;
                white[1] -= (255/100)*change;
                white[2] -= (255/100)*change;
                blue[0] += (255/100)*change;
                blue[1] += (64/100)*change;
                blue[2] -= (255/100)*change;

                purpleView.setBackgroundColor(Color.rgb(purple[0], purple[1], purple[2]));
                pinkView.setBackgroundColor(Color.rgb(pink[0], pink[1], pink[2]));
                redView.setBackgroundColor(Color.rgb(red[0], red[1], red[2]));
                whiteView.setBackgroundColor(Color.rgb(white[0], white[1], white[2]));
                blueView.setBackgroundColor(Color.rgb(blue[0], blue[1], blue[2]));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_more_information) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //Initialize and format dialog
            //
            TextView dialog_title = new TextView(this);
            dialog_title.setText("Nhóm 7 - Android");
            dialog_title.setGravity(Gravity.CENTER_HORIZONTAL);
            dialog_title.setPadding(100,30,100,30);
            dialog_title.setTextSize(20);
            builder.setCustomTitle(dialog_title);

            builder.setMessage("Nguyễn Võ Quốc Dương - 3120410104\n" +
                    "Nguyễn Thanh Duy - 3120410095\n" +
                    "Đặng Duy Thành Công - 3120410072\n" +
                    "Mai Văn Dương - 3120410102\n");

            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            builder.setNegativeButton("Moodle", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                    startActivity(intent);
                }
            });

            builder.show();
        }
        if (id == R.id.action_linear_layout) {
            setContentView(R.layout.activity_main);
            event();
        }
        if (id == R.id.action_relative_layout) {
            setContentView(R.layout.activity_main_relative);
            event();
        }
        if (id == R.id.action_table_layout) {
            setContentView(R.layout.activity_main_table);
            event();
        }
        return super.onOptionsItemSelected(item);
    }
}