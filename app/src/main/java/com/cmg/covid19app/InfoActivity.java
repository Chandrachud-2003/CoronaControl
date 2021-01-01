package com.cmg.covid19app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.cmg.covid19app.R;

import java.util.Locale;

public class InfoActivity extends AppCompatActivity {

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        backButton = findViewById(R.id.backButton2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        loadLocale();


    }

    public void loadLocale()
    {
        SharedPreferences pref = getSharedPreferences(Constants.languageId, MODE_PRIVATE);
        String lang = pref.getString(Constants.language_Pref, "");
        Log.d("test", "loadLocale: lang"+pref.getString(Constants.language_Pref, ""));
        if (lang.equals(""))
        {
            setLocale("en");
        }
        else {
            setLocale(lang);
        }
    }

    private void setLocale(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences pref = getSharedPreferences(Constants.languageId, MODE_PRIVATE);
        String lang2 = pref.getString(Constants.language_Pref, "");
        Log.d("test", "setLocale: language:"+lang2);


    }
}
