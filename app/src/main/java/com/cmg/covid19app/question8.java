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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmg.covid19app.R;

import java.util.Locale;


public class question8 extends Fragment {


    private View view;



    private RadioGroup optionGroup;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    private q8Listener listener;

    private static String text = "";
    private static boolean changed8 = false;

    public interface q8Listener{

        boolean onInputQ8Sent(question8 q8);

    }


    public question8() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_question8, container, false);
        // Inflate the layout for this fragment
        optionGroup = view.findViewById(R.id.optionGroup8);
        text = "";
        changed8 = false;

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();

        String previous = mPreferences.getString(Constants.q8Pref, "");
        if (!previous.equals(""))
        {
            changed8=true;
            if (previous.equals("Home")) {

                changed8=true;

                //RadioButton tempButton = optionGroup.check();
                optionGroup.check(R.id.insideButton);
                text = "Home";



            }

            else if (previous.equals("Outside"))
            {
                changed8 = true;
                text="Outside";
                optionGroup.check(R.id.outsideButton);
            }
        }

        optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.d("test", "onCheckedChanged8: changed");
                //Toast.makeText(getContext(), "Changed", Toast.LENGTH_SHORT).show();

                RadioButton radioButton = view.findViewById(i);
                text = (String) radioButton.getText();
                if(text.equals("ಮನೆ"))
                {
                    text = "Home";
                }
                if(text.equals("ಹೊರಗೆ"))
                {
                    text = "Outside";
                }
                if (!changed8)
                {
                    changed8 = true;
                }


            }
        });


        return view;
    }

    public String checkFinished()
    {
        if (changed8) {

            return text;
        }
        else {
            return "";
        }
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

        if (context instanceof q8Listener)
        {
            listener = (q8Listener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement q88 Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
