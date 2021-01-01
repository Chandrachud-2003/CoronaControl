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


public class question2 extends Fragment {


    private View view;



    private RadioGroup optionGroup;

    private q2Listener listener;

    private static String text = "";
    private static boolean changed2 = false;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    public interface q2Listener{

        boolean onInputQ2Sent(question2 q2);

    }


    public question2() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_question2, container, false);
        // Inflate the layout for this fragment
        optionGroup = view.findViewById(R.id.optionGroup);
        text = "";
        changed2 = false;

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();

        String previous = mPreferences.getString(Constants.q2Pref, "");
        if (!previous.equals(""))
        {
            changed2 = true;
            if (previous.equals("Yes")||text.equals("ಹೌದು")) {


                changed2=true;


                //RadioButton tempButton = optionGroup.check();
                optionGroup.check(R.id.yesButton2);
                text = "Yes";



            }

            else if (previous.equals("No"))
            {
                text="No";
                changed2=true;
                optionGroup.check(R.id.noButton2);
            }
        }

        optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.d("test", "onCheckedChanged2: changed");
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
                if (!changed2)
                {
                    changed2 = true;
                }


            }
        });



        return view;
    }

    public String checkFinished()
    {
        if (changed2) {

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

        if (context instanceof q2Listener)
        {
            listener = (q2Listener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement q2 Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
