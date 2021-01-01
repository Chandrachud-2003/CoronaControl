package com.cmg.covid19app;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.cmg.covid19app.R;
import com.franmontiel.localechanger.LocaleChanger;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Locale;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import mehdi.sakout.fancybuttons.FancyButton;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "test";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ExpandingList expandingList;
    //private LottieAnimationView worldAnimation;

    private FancyButton surveyButton;

    private FancyButton langButton;

    private ImageButton infoButton;

    private TextView appText;

    private ExpandingList expandingList2;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;


    private TextView numOfWorldCases;
    private TextView numOfIndiaCases;



    private static String numWorld="";
    private static String numIndia="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        langButton = findViewById(R.id.langaugeButton);
        langButton.setText(getResources().getString(R.string.languageButton_text));

        loadLocale();

        String[] wards = getResources().getStringArray(R.array.Wards);

        appText = findViewById(R.id.appName);
        appText.setText(getResources().getString(R.string.app_name));


        Log.d(TAG, "onCreate: " + wards.length);

        expandingList =  findViewById(R.id.expanding_list_main);
        expandingList2 = findViewById(R.id.expanding_list_main2);

        /*worldAnimation = findViewById(R.id.worldAnimation);

        numOfWorldCases = findViewById(R.id.worldDisplay);
        numOfIndiaCases = findViewById(R.id.confirmedDisplay);

        layout1 = findViewById(R.id.linearLayoutWorld);
        layout2 = findViewById(R.id.linearLayoutIndia);
        layout3 = findViewById(R.id.infoLayout);*/

        infoButton = findViewById(R.id.infoButton);





        surveyButton = findViewById(R.id.surveyButton);
        surveyButton.setText(getResources().getString(R.string.startSurvey));



        /*numOfIndiaCases.setText("0");

        numOfWorldCases.setText("0");
*/




       /* worldAnimation.setProgress(0);
        worldAnimation.setSpeed(0.3f);
        worldAnimation.pauseAnimation();
        worldAnimation.playAnimation();
*/



        ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
        item.setIndicatorColorRes(R.color.white);


        /*ExpandingItem extends from View, so you can call
        findViewById to get any View inside the layout*/
        TextView itemText =  item.findViewById(R.id.title);
        itemText.setTextColor(Color.WHITE);
        itemText.setText(getResources().getString(R.string.fqi_helpLine));
        item.setIndicatorIconRes(R.drawable.contact);

        item.createSubItems(3);

        View subItemZero = item.getSubItemView(0);
        TextView sub1 =  subItemZero.findViewById(R.id.sub_title);
        sub1.setText(getResources().getString(R.string.phone)+": +91 9986837807");
        sub1.setTextColor(Color.WHITE);

        sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Phone - FQI", "+91 9986837807");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Phone Number copied", Toast.LENGTH_SHORT).show();

            }
        });

        View subItemOne = item.getSubItemView(1);
        TextView sub2 =  subItemOne.findViewById(R.id.sub_title);
        sub2.setText(getResources().getString(R.string.email)+": venkatesh.thuppil@gmail.com");
        sub2.setTextColor(Color.WHITE);

        sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Email - FQI", "venkatesh.thuppil@gmail.com");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Email Address copied", Toast.LENGTH_SHORT).show();

            }
        });




        View subItemTwo = item.getSubItemView(2);
        TextView sub3 =  subItemTwo.findViewById(R.id.sub_title);
        sub3.setText(getResources().getString(R.string.website)+": foundationforqualityindia.co.in");
        sub3.setTextColor(Color.WHITE);

        sub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://foundationforqualityindia.co.in"));
                startActivity(browserIntent);



            }
        });

        ExpandingItem item2 = expandingList2.createNewItem(R.layout.expanding_layout);
        item2.setIndicatorColorRes(R.color.limeGreen);

        TextView itemText2 =  item2.findViewById(R.id.title);
        itemText2.setTextColor(Color.BLACK);
        itemText2.setText(getResources().getString(R.string.MOH_text));
        item2.setIndicatorIconRes(R.drawable.india_map);

        item2.createSubItems(3);

        View subItemZero2 = item2.getSubItemView(0);
        TextView sub1_2 =  subItemZero2.findViewById(R.id.sub_title);
        sub1_2.setText(getResources().getString(R.string.phone)+": +91-11-23978046");
        sub1_2.setTextColor(Color.BLACK);

        subItemZero2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Phone - India helpline", "+911123978046");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Phone Number copied", Toast.LENGTH_SHORT).show();

            }
        });

        View subItemOne2 = item2.getSubItemView(1);
        TextView sub2_2 =  subItemOne2.findViewById(R.id.sub_title);
        sub2_2.setText(getResources().getString(R.string.email)+": ncov2019@gmail.com");
        sub2_2.setTextColor(Color.BLACK);


        subItemOne2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Email - India helpline", "ncov2019@gmail.com");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Email Address copied", Toast.LENGTH_SHORT).show();

            }
        });

        View subItemTwo2 = item2.getSubItemView(2);
        TextView sub3_2 =  subItemTwo2.findViewById(R.id.sub_title);
        sub3_2.setText(getResources().getString(R.string.website)+": www.mohfw.gov.in");
        sub3_2.setTextColor(Color.BLACK);


        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);

            }
        });



        subItemTwo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mohfw.gov.in"));
                startActivity(browserIntent);

            }
        });

        //getStats();

        surveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearInfo();

                Intent intent = new Intent(MainActivity.this, QuestionareClass.class);
                startActivity(intent);

            }
        });



        /*layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStats();
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStats();
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStats();
            }
        });*/



        boolean firstRun = getSharedPreferences(Constants.firstTimePref, MODE_PRIVATE)
                .getBoolean("First Run", true);
        if (firstRun)
        {
            new SimpleTooltip.Builder(this)
                    .anchorView(surveyButton)
                    .text("TAKE OUR SURVEY !")
                    .gravity(Gravity.TOP)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();

            getSharedPreferences(Constants.firstTimePref, MODE_PRIVATE).edit()
                    .putBoolean("First Run", false).commit();

        }

        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLangMessage();
            }
        });










        /*for (int i = 0; i < wards.length; i++) {

            Map<String, Object> note = new HashMap<>();
            note.put("Ward Number", (i + 1));
            note.put("Number of Families", 0);
            note.put(Constants.numOfCritical, 0);
            db.collection("Karnataka").document("Bengaluru").collection(wards[i]).document("Ward Info").set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onCreate: Collection added");
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onCreate: Collection failed");
                            Log.d(TAG, "onCreate: " + e.toString());
                        }
                    });


        }*/
    }

    /*private void getStats()
    {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://coronavirus-monitor.p.rapidapi.com/coronavirus/latest_stat_by_country.php?country=India")
                .get()
                .addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "d8759c9696mshbe288522e69c857p152fa9jsn0438d231cdb4")
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);

                } else {

                    String body = response.body().string();

                    int index1 = body.indexOf("total_cases");
                    int index2 = body.indexOf(":", index1);
                    int index3 = index2+2;
                    numIndia = body.substring(index3, body.indexOf("\"", index3));
                    Log.d(TAG, "onResponse: "+numIndia);

                    String display="";
                    numIndia=numIndia.replaceAll(",","");


                    if (numIndia.length()>3)
                    {
                        if (numIndia.length()==4) {
                            display = Character.toString(numIndia.charAt(0)) + "." + Character.toString(numIndia.charAt(1)) + " K";
                        }
                        else if (numIndia.length()==5)
                        {
                            display = Character.toString(numIndia.charAt(0))  + Character.toString(numIndia.charAt(1)) +"."+Character.toString(numIndia.charAt(2))+ " K";
                        }
                        else if (numIndia.length()==6)
                        {


                            display = Character.toString(numIndia.charAt(0))  + Character.toString(numIndia.charAt(1)) +Character.toString(numIndia.charAt(2))+ " K";

                        }
                        else if (numIndia.length()==7)
                        {
                            display = Character.toString(numIndia.charAt(0)) + "." + Character.toString(numIndia.charAt(1)) + " M";
                        }
                        else if (numIndia.length()==8)
                        {
                            display = Character.toString(numIndia.charAt(0))  + Character.toString(numIndia.charAt(1)) +"."+Character.toString(numIndia.charAt(2))+ " M";
                        }
                        else if (numIndia.length()==9)
                        {
                            display = Character.toString(numIndia.charAt(0))  + Character.toString(numIndia.charAt(1)) +Character.toString(numIndia.charAt(2))+ " K";
                        }
                        else if (numIndia.length()==10)
                        {
                            display = Character.toString(numIndia.charAt(0)) + "." + Character.toString(numIndia.charAt(1)) + " B";
                        }
                        Log.d(TAG, "onResponse: "+display);

                        numIndia=display;
                    }


                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            setIndiaText(numIndia);

                        }
                    });


                    display="";











                }

                }
            });






        OkHttpClient client2 = new OkHttpClient();

        Request request2 = new Request.Builder()
                .url("https://coronavirus-monitor.p.rapidapi.com/coronavirus/worldstat.php")
                .get()
                .addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "d8759c9696mshbe288522e69c857p152fa9jsn0438d231cdb4")
                .build();

        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String body = response.body().string();


                    int index1 = body.indexOf("total_cases");
                    int index2 = body.indexOf(":", index1);
                    int index3 = index2+2;
                    numWorld = body.substring(index3, body.indexOf("\"", index3));


                    String display="";

                    numWorld=numWorld.replaceAll(",","");
                    Log.d(TAG, "onResponse: world total"+numWorld);
                    Log.d(TAG, "onResponse: world total"+numWorld.length());

                    if (numWorld.length()>3)
                    {
                        if (numWorld.length()==4) {
                            display = Character.toString(numWorld.charAt(0)) + "." + Character.toString(numWorld.charAt(1)) + " K";
                        }
                        else if (numWorld.length()==5)
                        {
                            display = Character.toString(numWorld.charAt(0))  + Character.toString(numWorld.charAt(1)) +"."+Character.toString(numWorld.charAt(2))+ " K";
                        }
                        else if (numWorld.length()==6)
                        {


                             display = Character.toString(numWorld.charAt(0))  + Character.toString(numWorld.charAt(1)) +Character.toString(numWorld.charAt(2))+ " K";

                        }
                        else if (numWorld.length()==7)
                        {
                            display = Character.toString(numWorld.charAt(0)) + "." + Character.toString(numWorld.charAt(1)) + " M";
                        }
                        else if (numWorld.length()==8)
                        {
                            display = Character.toString(numWorld.charAt(0))  + Character.toString(numWorld.charAt(1)) +"."+Character.toString(numWorld.charAt(2))+ " M";
                        }
                        else if (numWorld.length()==9)
                        {
                            display = Character.toString(numWorld.charAt(0))  + Character.toString(numWorld.charAt(1)) +Character.toString(numWorld.charAt(2))+ " K";
                        }
                        else if (numWorld.length()==10)
                        {
                            display = Character.toString(numWorld.charAt(0)) + "." + Character.toString(numWorld.charAt(1)) + " B";
                        }
                        Log.d(TAG, "onResponse: "+display);

                        numWorld=display;
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            setWorldText(numWorld);

                        }
                    });


                    display="";



                }
            }




        });





    }*/

    private void clearInfo()
    {
        Log.d(TAG, "clearInfo: Entered");
        //editor.clear().commit();
        SharedPreferences settings = this.getSharedPreferences(Constants.sharedId,MODE_PRIVATE);
        settings.edit().clear().commit();
        Log.d(TAG, "clearInfo: "+settings.getString(Constants.firstN_pref,""));
        //editor.remove(Constants.q1Pref).commit();
        /*editor.putString(Constants.q1Pref, "").commit();
        editor.remove(Constants.q1Pref_2).commit();
        editor.remove(Constants.q2Pref).commit();
        editor.remove(Constants.q3Pref_1).commit();
        editor.remove(Constants.q3Pref_2).commit();
        editor.remove(Constants.q4Pref_1).commit();
        editor.remove(Constants.q4Pref_2).commit();
        editor.remove(Constants.q5Pref).commit();
        editor.remove(Constants.q6Pref_1).commit();
        editor.remove(Constants.q6Pref_2).commit();
        editor.remove(Constants.q6Pref_3).commit();
        editor.remove(Constants.q7Pref).commit();
        editor.remove(Constants.q8Pref).commit();
        editor.remove(Constants.q9Pref_1).commit();
        editor.remove(Constants.q9Pref_2).commit();
        editor.remove(Constants.q10Pref).commit();

        editor.remove(Constants.firstN_pref).commit();
        editor.remove(Constants.lasntN_pref).commit();
        editor.remove(Constants.addressPref).commit();
        editor.remove(Constants.phonePref).commit();
        editor.remove(Constants.voterId).commit();
        editor.remove(Constants.driverLicense).commit();
        editor.remove(Constants.teenagerNumber).commit();
        editor.remove(Constants.childrenNumber).commit();
        editor.remove(Constants.adultNumber).commit();
        editor.remove(Constants.seniorNumber).commit();
*/





    }

    private void displayLangMessage()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);

        mBuilder.setTitle("Language");
        mBuilder.setSingleChoiceItems(Constants.languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(i==0)
                {
                    setLocale("en");
                    recreate();
                }
                else if (i==1)
                {
                    setLocale("kn");
                    recreate();
                }

                dialogInterface.dismiss();

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();



    }

   /* private void setWorldText(String s)
    {
        numOfWorldCases.setText(s);
    }
    private void setIndiaText(String s)
    {
        numOfIndiaCases.setText(s);
    }
*/


   private void setLocale(String lang)
   {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences preferences = getSharedPreferences(Constants.languageId, MODE_PRIVATE);

       SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.language_Pref, lang);
        editor.commit();
       SharedPreferences pref = getSharedPreferences(Constants.languageId, MODE_PRIVATE);
       String lang2 = pref.getString(Constants.language_Pref, "");
       Log.d(TAG, "setLocale: saved:"+lang2);


   }

   private void loadLocale()
   {
       SharedPreferences pref = getSharedPreferences(Constants.languageId, MODE_PRIVATE);
       String lang = pref.getString(Constants.language_Pref, "");
       if (lang.equals(""))
       {
           setLocale("en");
           langButton.setText(getResources().getString(R.string.languageButton_text));
       }
       else {
           setLocale(lang);
           if (lang.equals("en"))
           {
               langButton.setText(getResources().getString(R.string.languageButton_text));

           }
           else if (lang.equals("kn"))
           {
               langButton.setText(getResources().getString(R.string.languageButton_text));

           }
       }
   }


}





