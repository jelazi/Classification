package com.workspace.lzizka.clasification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mistakes;
    ListView points;
    ListView maxValues;
    TextView sign;

    TextView numMistakes;
    TextView numPoints;
    TextView numSign;
    TextView maxView;




    int valuePoints;
    int valueMistakes;
    int max;

    ArrayList<String> mistakesList = new ArrayList<>();
    ArrayList<String> pointsList = new ArrayList<>();
    ArrayList<String> maxList = new ArrayList<>();

    ArrayAdapter<String> mistakeAdapter;
    ArrayAdapter<String> pointsAdapter;
    ArrayAdapter<String> maxAdapter;
    SharedPreferences settings;


    int maxOne = 89;
    int maxTwo = 74;
    int maxThree = 59;
    int maxFour = 39;

    boolean roundUp = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settings = PreferenceManager.getDefaultSharedPreferences(this);



        valueMistakes = 3;

        max = 60;
        valuePoints = max - valueMistakes;


        mistakes = (ListView) findViewById(R.id.mistakes);
        points = (ListView) findViewById(R.id.points);
        sign = (TextView) findViewById(R.id.sign);
        numMistakes = (TextView) findViewById(R.id.num_mistakes);
        numPoints = (TextView) findViewById(R.id.num_points);
        maxValues = (ListView) findViewById(R.id.max_list);
        numSign = (TextView) findViewById(R.id.sign_number);
        maxView = (TextView) findViewById(R.id.max_value);



        mistakeAdapter = new ArrayAdapter <String>
                (MainActivity.this, R.layout.my_text_view,
                        mistakesList);


        pointsAdapter = new ArrayAdapter <String>
                (MainActivity.this, R.layout.my_text_view2,
                        pointsList);

        maxAdapter = new ArrayAdapter <String>
                (MainActivity.this, R.layout.my_text_view3,
                        maxList);

     //   settings.edit().putString("number_one", "89").apply();
     //   settings.edit().putString("number_two", "74").apply();
     //   settings.edit().putString("number_three", "59").apply();
     //   settings.edit().putString("number_four", "39").apply();
     //   settings.edit().putString("round_up", "true").apply();

        count();





        mistakes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String entry= mistakeAdapter.getItem(position);
                numMistakes.setText("Počet chyb: " + entry);
                valueMistakes = Integer.parseInt(entry);
                valuePoints = getPoints(max, valueMistakes);
                numPoints.setText("Počet bodů: " + String.valueOf(valuePoints));
                points.setSelection(valuePoints);
                numSign.setText(String.valueOf(getSign(max, valuePoints)));


            }
        });

        points.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String entry= pointsAdapter.getItem(position);
                numPoints.setText("Počet bodů: " + entry);
                valuePoints = Integer.parseInt(entry);
                valueMistakes = getMistakes(max, valuePoints);
                numMistakes.setText("Počet chyb: " + String.valueOf(valueMistakes));
                mistakes.setSelection(valueMistakes);
                numSign.setText(String.valueOf(getSign(max, valuePoints)));

            }
        });


        maxValues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String entry= maxAdapter.getItem(position);

                max = Integer.parseInt(entry);



                valueMistakes = getMistakes(max, valuePoints);
                valuePoints = getPoints(max, valueMistakes);
                numMistakes.setText("Počet chyb: " + String.valueOf(valueMistakes));
                mistakes.setSelection(valueMistakes);
                numPoints.setText("Počet bodů: " + String.valueOf(valuePoints));
                points.setSelection(valuePoints);
                try {
                    numSign.setText(getSign(max, valuePoints));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                maxValues.setSelection(max);
                maxView.setText("Maximální počet bodů: " + String.valueOf(max));
                count();



            }
        });

        maxView.setText("Maximální počet bodů: " + String.valueOf(max));

            numSign.setText(String.valueOf(getSign(max, valuePoints)));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent i = new Intent(this, SettingsScreen.class);
            startActivity(i);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected int getSign(int max, int points){

        double maximum = (double) max;
        double pointsdouble = (double) points;
        double percent;

        if (roundUp){
            percent = Math.ceil(pointsdouble / (maximum / 100));

        }else {
            percent = Math.round(pointsdouble / (maximum / 100));

        }


        if (percent >= maxOne){
            return 1;
        }

        if (percent >= maxTwo){
            return 2;
        }
        if (percent >= maxThree){
            return 3;
        }

        if (percent >= maxFour){
            return 4;
        }

            return 5;


    }

    @Override
    public void onResume(){ //aktualizace nastaveni hodnot ze SettingsScreen
        super.onResume();

        settings = PreferenceManager
                .getDefaultSharedPreferences(this);


        maxOne = Integer.parseInt(settings.getString("number_one", "89"));
        maxTwo = Integer.parseInt(settings.getString("number_two", "74"));
        maxThree = Integer.parseInt(settings.getString("number_three", "59"));
        maxFour = Integer.parseInt(settings.getString("number_four", "39"));

        roundUp = (settings.getBoolean("round_up", true));




    }

    protected int getPoints(int max, int mistakes){
        return max - mistakes;
    }

    protected int getMistakes (int max, int points){

        return max - points;
    }



    protected void count (){

        mistakesList.clear();
        pointsList.clear();
        maxList.clear();
        valuePoints = max;
        valueMistakes = 0;
        numMistakes.setText("Počet chyb: " + String.valueOf(valueMistakes));
        numPoints.setText("Počet bodů: " + String.valueOf(valuePoints));




        for (int i = 0; i <= max; i++){
            mistakesList.add(String.valueOf(i));
        }


        mistakes.setAdapter(mistakeAdapter);

        for (int i = 0; i <= max; i++){
            pointsList.add(String.valueOf(i));
        }


        points.setAdapter(pointsAdapter);


        for (int i = 0; i <= 100; i++){
            maxList.add(String.valueOf(i));
        }


        maxValues.setAdapter(maxAdapter);

        mistakes.setSelection(valueMistakes);
        points.setSelection(valuePoints);
        maxValues.setSelection(max);




    }


}
