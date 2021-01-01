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

import com.cmg.covid19app.R;

import java.util.Locale;


public class additionalDetails extends Fragment {


    private View view;



    private EditText inputText;

    private additionalDetailsListener listener;

    private static String text = "";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    public interface additionalDetailsListener{

        boolean onInputAdditionalSent(additionalDetails additionalDetails);

    }


    public additionalDetails() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_additional_details, container, false);
        // Inflate the layout for this fragment

        inputText = view.findViewById(R.id.additonalInput);
        inputText.setSaveEnabled(false);



        text = "";

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                text = inputText.getText().toString();

            }
        });






        return view;
    }

    public String getText()
    {
        return text;
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

        if (context instanceof additionalDetailsListener)
        {
            listener = (additionalDetailsListener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement additionalDetails Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }




}
