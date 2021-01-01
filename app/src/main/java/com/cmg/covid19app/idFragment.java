package com.cmg.covid19app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cmg.covid19app.R;

import java.util.Locale;


public class idFragment extends Fragment {


    private View view;

    private static TextView aadharText;
    private static TextView voterText;
    private static TextView driverText;

    private static EditText aadharInput;
    private static EditText voterInput;
    private static EditText driverInput;

    private static String aadharNum="";
    private static String voterId="";
    private static String driverLicense="";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;




    private idFragmentListener listener;

    private static String text = "";
    private static boolean changedName = false;

    public interface idFragmentListener{

        boolean onInputIDSent(idFragment idFragment);

    }


    public idFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_id, container, false);
        // Inflate the layout for this fragment
        aadharNum="";
        voterId="";
        driverLicense="";
        changedName = false;

        aadharText = view.findViewById(R.id.aadharText);
        voterText = view.findViewById(R.id.voterText);
        driverText = view.findViewById(R.id.driverText);

        aadharInput = view.findViewById(R.id.aadharInput);
        voterInput = view.findViewById(R.id.voterInput);
        driverInput = view.findViewById(R.id.driverInput);

        aadharInput.setSaveEnabled(false);
        driverInput.setSaveEnabled(false);
        voterInput.setSaveEnabled(false);

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();

        String previousAadhar = mPreferences.getString(Constants.aadharNumber, "");
        String previousVoter = mPreferences.getString(Constants.voterId, "");
        String previousDriver = mPreferences.getString(Constants.driverLicense, "");

        if (!previousAadhar.equals("") )
        {
            aadharNum = previousAadhar;
            aadharInput.setText(previousAadhar);

            if (!previousVoter.equals(""))
            {
                voterId=previousVoter;
                voterInput.setText(previousVoter);
            }
            if (!previousDriver.equals(""))
            {
                driverLicense=previousDriver;
                driverInput.setText(previousDriver);
            }
        }

        aadharInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                aadharNum = aadharInput.getText().toString();

            }
        });

        voterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                voterId = voterInput.getText().toString();

            }
        });

        driverInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                driverLicense = driverInput.getText().toString();

            }
        });






        return view;
    }

    public String getAadharNum()
    {

        if (!(aadharNum.equals("")) && aadharNum.length()==12)
        {

            return aadharNum;
        }
        else {
            return "";
        }



    }

    public String getVoterId()
    {



        if (!(voterId.equals("")) && voterId.length()==10)
        {
            for (int i=0;i<voterId.length();i++)
            {
                char ch = voterId.charAt(i);
                if (i<3)
                {
                    if (Character.isLetter(ch))
                    {
                        continue;
                    }
                    else {
                        return "";
                    }
                }

                else
                {
                    if (Character.isDigit(ch))
                    {
                        continue;
                    }
                    else {
                        return "";
                    }
                }
            }
            return voterId;
        }
        else {
            return "";
        }



    }

    public String getDriverLicense()
    {


        if (!(driverLicense.equals("")))
        {
            return driverLicense;
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

        if (context instanceof idFragmentListener)
        {
            listener = (idFragmentListener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement idFragment Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
