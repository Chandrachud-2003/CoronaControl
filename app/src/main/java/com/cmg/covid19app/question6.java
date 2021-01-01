package com.cmg.covid19app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cmg.covid19app.R;

import java.util.Locale;


public class question6 extends Fragment {


    private View view;



    private RadioGroup optionGroup;
    private EditText hospitalName;
    private TextView detailText;
    private EditText detailInput;

    private q6Listener listener;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    private static String text = "";
    private  static boolean changed6 = false;
    private static boolean hospitalFull=false;
    private static boolean detailFull=false;
    private static String hospital;
    private static String detail;

    public interface q6Listener{

        boolean onInputQ6Sent(question6 q6);

    }


    public question6() {
        // Required empty public constructor


    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_question6, container, false);
        // Inflate the layout for this fragment
        optionGroup = view.findViewById(R.id.optionGroup);
        detailInput = view.findViewById(R.id.detailInput);
        detailText = view.findViewById(R.id.detailText);
        hospitalName = view.findViewById(R.id.hospitalName);

        text = "";
        changed6 = false;

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();

        detailInput.setVisibility(View.GONE);
        detailText.setVisibility(View.GONE);
        hospitalName.setVisibility(View.GONE);
        detailInput.setSaveEnabled(false);

        String previous = mPreferences.getString(Constants.q6Pref_1, "");
        if (!previous.equals(""))
        {
            changed6=true;
            if (previous.equals("Yes")) {

                //RadioButton tempButton = optionGroup.check();
                optionGroup.check(R.id.yesButton6);
                text = "Yes";
                changed6=true;

                hospitalName.setVisibility(View.VISIBLE);
                detailInput.setVisibility(View.VISIBLE);
                detailText.setVisibility(View.VISIBLE);

                String previous2 = mPreferences.getString(Constants.q6Pref_2, "");
                hospitalName.setText(previous2);
                hospitalFull = true;

                String previous3 = mPreferences.getString(Constants.q6Pref_3, "");
                detailInput.setText(previous3);
                detailFull=true;



            }

            else if (previous.equals("No"))
            {
                changed6=true;
                text="No";
                optionGroup.check(R.id.noButton6);
            }
        }

        optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.d("test", "onCheckedChanged6: changed");
                //Toast.makeText(getContext(), "Changed", Toast.LENGTH_SHORT).show();

                RadioButton radioButton = view.findViewById(i);

                text = radioButton.getText().toString();

                if(text.equals("ಹೌದು"))
                {
                    text = "Yes";
                }
                if(text.equals("ಇಲ್ಲ"))
                {
                    text = "No";
                }

                if (!changed6)
                {
                    changed6 = true;

                }
                if(text.equals("Yes"))
                {
                    detailInput.setVisibility(View.VISIBLE);
                    detailText.setVisibility(View.VISIBLE);
                    hospitalName.setVisibility(View.VISIBLE);
                }
                else if (text.equals("No")){
                    detailInput.setVisibility(View.GONE);
                    detailText.setVisibility(View.GONE);
                    hospitalName.setVisibility(View.GONE);

                }


            }
        });

        detailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                detail=detailInput.getText().toString();

                if (detail.length()>0)
                {
                    detailFull=true;

                }
                else {
                    detailFull=false;
                }

            }
        });

        hospitalName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                hospital= hospitalName.getText().toString();

                if (hospital.length()>0)
                {
                    hospitalFull=true;

                }
                else {
                    hospitalFull=false;
                }

            }
        });



        return view;
    }

    public String checkFinished()
    {
        if (changed6) {

            if (text.equals("Yes"))
            {
                if (hospitalFull && detailFull)
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

    public String nameOfHospital()
    {
        return hospital;
    }

    public String details()
    {
        return detail;
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

        if (context instanceof q6Listener)
        {
            listener = (q6Listener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement q6 Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
