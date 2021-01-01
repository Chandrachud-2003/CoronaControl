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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmg.covid19app.R;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;


public class question3 extends Fragment {


    private View view;



    private RadioGroup optionGroup;
    private TextView placeText;
    private Spinner placeSpinner;
    private CustomToggleButton indiaButton;
    private CustomToggleButton internationalButton;
    private LinearLayout placeLayout1;

    private String[] countryList;
    private String[] stateList;

    private q3Listener listener;

    private static String text = "";
    private static String text2="";
    private static boolean changed3 = false;
    private static String placeSelected ="";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;


    public interface q3Listener{

        boolean onInputQ3Sent(question3 q3);

    }


    public question3() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_question3, container, false);
        // Inflate the layout for this fragment
        optionGroup = view.findViewById(R.id.optionGroup);
        placeSpinner = view.findViewById(R.id.placeSpinner);
        placeText = view.findViewById(R.id.placeText);
        indiaButton = view.findViewById(R.id.indiaButton);
        internationalButton = view.findViewById(R.id.internationalButton);
        placeLayout1 = view.findViewById(R.id.placeLayout1);

        countryList =  Constants.countries;
        stateList = Constants.states;

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();


        placeText.setVisibility(View.GONE);
        placeSpinner.setVisibility(View.GONE);
        indiaButton.setVisibility(View.GONE);
        internationalButton.setVisibility(View.GONE);
        placeLayout1.setVisibility(View.GONE);



        text = "";
        text2="";
        changed3 = false;

        String previous = mPreferences.getString(Constants.q3Pref_1, "");
        if (!previous.equals(""))
        {
            changed3=true;

            if (previous.equals("Yes")) {

                changed3=true;

                indiaButton.setVisibility(View.VISIBLE);
                internationalButton.setVisibility(View.VISIBLE);
                placeSpinner.setVisibility(View.VISIBLE);
                placeText.setVisibility(View.VISIBLE);

                //RadioButton tempButton = optionGroup.check();
                optionGroup.check(R.id.yesButton3);
                text = "Yes";
                String previous2 = mPreferences.getString(Constants.q3Pref_type, "");
                Log.d("test", "onCreateView3: "+previous2);

                text2 = previous2;

                Log.d("test", "onCreateView3: "+text2);
                String previous3 = mPreferences.getString(Constants.q3Pref_2, "");




                if (previous2.equals("india"))
                {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Arrays.asList(stateList));
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    placeSpinner.setAdapter(dataAdapter);
                    List<String> tempState = Arrays.asList(stateList);

                    placeSpinner.setSelection(tempState.indexOf(previous3));

                }
                else if (previous2.equals("international"))
                {
                    List<String> tempCountry = Arrays.asList(countryList);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Arrays.asList(countryList));
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    placeSpinner.setAdapter(dataAdapter);

                    placeSpinner.setSelection(tempCountry.indexOf(previous3));

                }






                placeSelected = previous3;



            }

            else if (previous.equals("No"))
            {
                changed3=true;
                text="No";
                optionGroup.check(R.id.noButton3);
            }
        }

        optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.d("test", "onCheckedChanged3: changed");
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
                if (!changed3)
                {
                    changed3 = true;
                }

                if (text.equals("Yes"))
                {
                    internationalButton.setVisibility(View.VISIBLE);
                    indiaButton.setVisibility(View.VISIBLE);
                    placeText.setVisibility(View.GONE);
                    placeSpinner.setVisibility(View.GONE);
                    placeLayout1.setVisibility(View.VISIBLE);
                }
                else {
                    placeText.setVisibility(View.GONE);
                    placeSpinner.setVisibility(View.GONE);
                    indiaButton.setVisibility(View.GONE);
                    internationalButton.setVisibility(View.GONE);
                    placeLayout1.setVisibility(View.GONE);
                }


            }
        });

        internationalButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    text2="international";
                    indiaButton.setChecked(false);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Arrays.asList(countryList));
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    placeSpinner.setAdapter(dataAdapter);

                    placeSpinner.setVisibility(View.VISIBLE);
                    placeText.setVisibility(View.VISIBLE);

                    placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            placeSelected = countryList[i];

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                else {
                    text2="india";

                    internationalButton.setChecked(false);
                    indiaButton.setChecked(true);
                }
            }
        });

        indiaButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    text2="india";
                    internationalButton.setChecked(false);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Arrays.asList(stateList));
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    placeSpinner.setAdapter(dataAdapter);

                    placeSpinner.setVisibility(View.VISIBLE);
                    placeText.setVisibility(View.VISIBLE);

                    placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            placeSelected = stateList[i];

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                else {
                    text2="international";
                    indiaButton.setChecked(false);
                    internationalButton.setChecked(true);
                }
            }
        });


        return view;
    }

    public String checkFinished()
    {
        if (changed3) {

            if (text.equals("Yes"))
            {
                if (!(placeSelected.equals("")))
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

    public String typeChosen()
    {
        return text2;
    }

    public String placeChosen()
    {
        return placeSelected;
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

        if (context instanceof q3Listener)
        {
            listener = (q3Listener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement q3 Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
