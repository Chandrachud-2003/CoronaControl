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
import android.widget.TextView;

import com.cmg.covid19app.R;

import java.util.Locale;


public class nameFragment extends Fragment {


    private View view;

    private static TextView nameText;
    private static TextView lastNameText;
    private static EditText nameInput;
    private static EditText lastNameInput;

    private static String firstName="";
    private static String lastName="";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    private Locale locale = null;




    private nameFragmentListener listener;

    private static String text = "";
    private static boolean changedName = false;

    public interface nameFragmentListener{

        boolean onInputNameSent(nameFragment nameFragment);

    }


    public nameFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_name, container, false);
        // Inflate the layout for this fragment
        firstName="";
        lastName="";


        changedName = false;

        nameText = view.findViewById(R.id.nameText);
        lastNameText = view.findViewById(R.id.lastNameText);
        nameInput = view.findViewById(R.id.nameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);

        lastNameInput.setSaveEnabled(false);
        nameInput.setSaveEnabled(false);



        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();

        String previousFirst = mPreferences.getString(Constants.firstN_pref, "");
        String lastFirst = mPreferences.getString(Constants.lasntN_pref, "");

        Log.d("test", "firstName: "+previousFirst);
        Log.d("test", "onCreateView: "+lastFirst);


        if (!previousFirst.equals("") && !lastFirst.equals(""))
        {
            Log.d("test", "onCreateView: set auto called");
            nameInput.setText(previousFirst);
            firstName = previousFirst;
            lastNameInput.setText(lastFirst);
            lastName = lastFirst;
        }


        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                firstName =  nameInput.getText().toString();

            }
        });

        lastNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                lastName =  lastNameInput.getText().toString();

            }
        });





        return view;
    }

    public String getFirstName()
    {


        if (!firstName.equals("") && !lastName.equals(""))
        {
            return firstName;
        }
        else {
            return "";
        }

    }

    public String getLastName()
    {


        if (!lastName.equals("") && !firstName.equals("") )
        {
            return lastName;
        }
        else {
            return "";
        }

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        SharedPreferences pref = getActivity().getSharedPreferences(Constants.languageId, Context.MODE_PRIVATE);
        String lang = pref.getString(Constants.language_Pref, "");

        Log.d("test", "onAttach: language:"+lang);

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());

        if (context instanceof nameFragmentListener)
        {
            listener = (nameFragmentListener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement nameFragment Listener");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (locale != null)
        {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getActivity().getResources().updateConfiguration(newConfig, getActivity().getResources().getDisplayMetrics());
        }
    }






}
