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


public class question9 extends Fragment {


    private View view;



    private RadioGroup optionGroup;
    private CustomToggleButton hospitalButton;
    private CustomToggleButton airportButton;
    private CustomToggleButton publicButton;
    private CustomToggleButton shipButton;

    private LinearLayout occupationLayout1;
    private LinearLayout occupationLayout2;





    private q9Listener listener;

    private static String text = "";
    private static boolean changed9 = false;
    private static boolean airport=false;
    private static boolean publicServices=false;
    private static boolean ship=false;
    private static boolean hospital=false;

    private static String contact="";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;



    public interface q9Listener{

        boolean onInputQ9Sent(question9 q9);

    }


    public question9() {
        // Required empty public constructor


    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_question9, container, false);
        // Inflate the layout for this fragment

        optionGroup = (RadioGroup) view.findViewById(R.id.optionGroup);
        hospitalButton = view.findViewById(R.id.hospitalButton);
        airportButton = view.findViewById(R.id.airportButton);
        publicButton = view.findViewById(R.id.publicButton);
        shipButton = view.findViewById(R.id.shipButton);
        occupationLayout1 = view.findViewById(R.id.occupationLayout);
        occupationLayout2 = view.findViewById(R.id.occupationLayout2);

        occupationLayout1.setVisibility(View.GONE);
        occupationLayout2.setVisibility(View.GONE);


        ship=false;
        publicServices=false;
        airport=false;
        hospital=false;

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();





        text = "";
        changed9 = false;

        String previous = mPreferences.getString(Constants.q9Pref_1, "");
        if (!previous.equals(""))
        {
            changed9=true;
            if (previous.equals("Yes")) {

                changed9=true;

                //RadioButton tempButton = optionGroup.check();
                optionGroup.check(R.id.yesButton9);
                text = "Yes";

                occupationLayout2.setVisibility(View.VISIBLE);
                occupationLayout1.setVisibility(View.VISIBLE);

                String previous2 = mPreferences.getString(Constants.q9Pref_2, "");
                contact = previous2;
                if(previous2.contains("Hospital"))
                {
                    hospitalButton.setChecked(true);
                    hospital = true;
                }
                if(previous2.contains("Airport"))
                {
                    airportButton.setChecked(true);
                    airport = true;
                }
                if(previous2.contains("Ship / Port"))
                {
                    shipButton.setChecked(true);
                    ship=true;
                }
                if(previous2.contains("Public Services"))
                {
                    publicButton.setChecked(true);
                    publicServices=true;
                }



            }

            else if (previous.equals("No"))
            {
                changed9 = true;
                text="No";
                optionGroup.check(R.id.noButton9);
            }
        }



        optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.d("test", "onCheckedChanged9: changed");
                //Toast.makeText(getContext(), "Changed", Toast.LENGTH_SHORT).show();

                RadioButton radioButton = view.findViewById(i);
                text = (String) radioButton.getText();
                if(text.equals("ಹೌದು"))
                {
                    text = "Yes";
                }
                if(text.equals("ಇಲ್ಲ"))
                {
                    text = "No";
                }
                if (!changed9)
                {
                    changed9 = true;
                }
                if (text.equals("Yes"))
                {
                    occupationLayout1.setVisibility(View.VISIBLE);
                    occupationLayout2.setVisibility(View.VISIBLE);
                }
                else if (text.equals("No"))
                {
                    occupationLayout1.setVisibility(View.GONE);
                    occupationLayout2.setVisibility(View.GONE);
                }


            }
        });


        shipButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    ship=true;
                }
                else {
                    ship=false;
                }
            }
        });

        airportButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    airport=true;
                }
                else {
                    airport=false;
                }
            }
        });

        publicButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    publicServices=true;
                }
                else {
                    publicServices=false;
                }
            }
        });

        hospitalButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    hospital=true;
                }
                else {
                    hospital=false;
                }
            }
        });



        return view;
    }

    public String checkFinished()
    {
        if (changed9) {

            if (text.equals("Yes"))
            {
                if (ship||hospital||airport||publicServices)
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

    public String occupation()
    {
         contact="";
        if (publicServices)
        {
            contact+="Punlic services, ";

        }
        if (ship)
        {
            contact+="Ship / Port, ";
        }
        if (airport)
        {
            contact+="airport, ";
        }
        if (hospital)
        {
            contact+="hospital, ";
        }

        Log.d("test", "contact: "+contact);
        return contact;

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

        if (context instanceof q9Listener)
        {
            listener = (q9Listener) context;
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
