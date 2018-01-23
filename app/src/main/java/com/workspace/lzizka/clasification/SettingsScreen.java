package com.workspace.lzizka.clasification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.util.List;

public class SettingsScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        public static Preference number_one;
        public static Preference number_two;
        public static Preference number_three;
        public static Preference number_four;
        public static Preference round_up;


        SharedPreferences settings;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

            addPreferencesFromResource(R.xml.settings_screen);

            number_one = (Preference) findPreference("number_one");
            number_two = (Preference) findPreference("number_two");
            number_three = (Preference) findPreference("number_three");
            number_four = (Preference) findPreference("number_four");
            round_up = (Preference) findPreference("round_up");

            number_one.setSummary(settings.getString("number_one", "89"));

            number_two.setSummary(settings.getString("number_two", "74"));
            number_three.setSummary(settings.getString("number_three", "59"));
            number_four.setSummary(settings.getString("number_four", "39"));


            number_one.setDefaultValue(settings.getString("number_one", "89"));
            number_two.setDefaultValue(settings.getString("number_two", "74"));
            number_three.setDefaultValue(settings.getString("number_three", "59"));
            number_four.setDefaultValue(settings.getString("number_four", "39"));
            round_up.setDefaultValue(settings.getBoolean("round_up", true));


            number_one.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    number_one.setSummary(newValue.toString());
                    return true;
                }
            });

            number_two.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    number_two.setSummary(newValue.toString());
                    return true;
                }
            });

            number_three.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    number_three.setSummary(newValue.toString());
                    return true;
                }
            });

            number_four.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    number_four.setSummary(newValue.toString());
                    return true;
                }
            });



        }

    }




}





