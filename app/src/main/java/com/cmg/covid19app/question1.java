package com.cmg.covid19app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmg.covid19app.R;

import java.util.Locale;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;


public class question1 extends Fragment {


    private View view;



    private RadioGroup optionGroup;
    private CustomToggleButton coldButton;
    private CustomToggleButton feverButton;
    private CustomToggleButton diarrheaButton;
    private CustomToggleButton breathingButton;
    private CustomToggleButton soreButton;

    private LinearLayout symptomLayout1;
    private LinearLayout symptomLayout2;


    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;





    private q1Listener listener;

    private static String text = "";
    private static boolean changed1;
    private static boolean changed2;
    private static boolean cold;
    private static boolean fever;
    private static boolean diarrhea;
    private static boolean breathing;
    private static boolean sore;
    private static String symptoms;


    public interface q1Listener{

        boolean onInputQ1Sent(question1 q1);

    }


    public question1() {
        // Required empty public constructor


    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_question1, container, false);
        // Inflate the layout for this fragment

        optionGroup = (RadioGroup) view.findViewById(R.id.optionGroup1);
        coldButton = view.findViewById(R.id.coldButton);
        feverButton = view.findViewById(R.id.feverButton);
        diarrheaButton = view.findViewById(R.id.diarrheaButton);
        breathingButton = view.findViewById(R.id.breathingButton);
        soreButton = view.findViewById(R.id.soreButton);
        symptomLayout1 = view.findViewById(R.id.symptomLayout1);
        symptomLayout2 = view.findViewById(R.id.symptomLayout2);

        symptomLayout2.setVisibility(View.GONE);
        symptomLayout1.setVisibility(View.GONE);

        changed1 = false;
        cold=false;
        fever=false;
        diarrhea=false;
         breathing=false;
        sore=false;

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();



        text = "";
        changed1 = false;

        String previous = mPreferences.getString(Constants.q1Pref, "");
        if (!previous.equals(""))
        {
            changed1=true;
            if (previous.equals("Yes")) {

                changed1=true;


                //RadioButton tempButton = optionGroup.check();
                optionGroup.check(R.id.yesButton1);
                text = "Yes";
                symptomLayout1.setVisibility(View.VISIBLE);
                symptomLayout2.setVisibility(View.VISIBLE);

                String previous2=mPreferences.getString(Constants.q1Pref_2, "");
                symptoms = previous2;

                if (previous2.contains("Sore Throat"))
                {
                    coldButton.setChecked(true);
                    cold=true;
                }
                if (previous2.contains("Fever"))
                {
                    feverButton.setChecked(true);
                    fever = true;
                }
                if (previous2.contains("Diarrhea"))
                {
                    diarrheaButton.setChecked(true);
                    diarrhea = true;
                }
                if (previous2.contains("Breathing Difficulty"))
                {
                    breathingButton.setChecked(true);
                    breathing = true;
                }
                if (previous2.contains("Sore Throat"))
                {
                    soreButton.setChecked(true);
                    sore = true;
                }


            }

            else if (previous.equals("No"))
            {
                changed1=true;
                text="No";
                optionGroup.check(R.id.noButton1);
            }
        }


        Log.d("test", "onCreateView: Sore-"+sore);
        Log.d("test", "onCreateView: breathinng-"+breathing);
        Log.d("test", "onCreateView: diarrhea-"+diarrhea);
        Log.d("test", "onCreateView: cold-"+cold);
        Log.d("test", "onCreateView: fever-"+fever);




        optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.d("test", "onCheckedChanged1: changed");
                //Toast.makeText(getContext(), "Changed", Toast.LENGTH_SHORT).show();
                Log.d("test", "onCheckedChanged1: "+i);

                RadioButton radioButton = view.findViewById(i);



                text = (String) radioButton.getText();

                changed1 = true;

                if (text.equals("Yes")||text.equals("ಹೌದು"))
                {
                    text = "Yes";
                    symptomLayout1.setVisibility(View.VISIBLE);
                    symptomLayout2.setVisibility(View.VISIBLE);
                }
                else if (text.equals("No")||text.equals("ಇಲ್ಲ"))
                {
                    text = "No";
                    symptomLayout1.setVisibility(View.GONE);
                    symptomLayout2.setVisibility(View.GONE);
                }


            }
        });


        coldButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    cold=true;
                }
                else {
                    cold=false;
                }
            }
        });

        feverButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    fever=true;
                }
                else {
                    fever=false;
                }
            }
        });

        breathingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    breathing=true;
                }
                else {
                    breathing=false;
                }
            }
        });

        diarrheaButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    diarrhea=true;
                }
                else {
                    diarrhea=false;
                }
            }
        });

        soreButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    sore=true;
                }
                else {
                    sore=false;
                }
            }
        });


        return view;
    }

    public String checkFinished()
    {
        if (changed1) {

            if (text.equals("Yes"))
            {
                if (cold||fever||breathing||diarrhea||sore)
                {
                    return text;
                }
                else {
                    return "";
                }
            }

            return text;
        }
        else {
            return "";
        }
    }

    public String symptoms()
    {
         symptoms="";
        if (cold)
        {
            symptoms+="Cold, ";

        }
        if (fever)
        {
            symptoms+="Fever, ";
        }
        if (breathing)
        {
            symptoms+="Breathing Difficulty, ";
        }
        if (diarrhea)
        {
            symptoms+="Diarrhea, ";
        }
        if (sore)
        {
            symptoms+="Sore Throat, ";
        }
        Log.d("Test", "symptoms: "+symptoms);
        return symptoms;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        SharedPreferences pref = this.getActivity().getSharedPreferences(Constants.languageId, Context.MODE_PRIVATE);
        String lang = pref.getString(Constants.language_Pref, "");

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());

        if (context instanceof q1Listener)
        {
            listener = (q1Listener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement q1 Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
