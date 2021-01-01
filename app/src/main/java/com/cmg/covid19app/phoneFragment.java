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


public class phoneFragment extends Fragment {


    private View view;

    private static TextView phoneText;
    private static TextView addressText;
    private static EditText phoneInput;
    private static EditText addressInput;

    private static String phoneNumber="";
    private static String address="";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;




    private phoneFragmentListener listener;

    private static String text = "";
    private static boolean changedName = false;

    public interface phoneFragmentListener{

        boolean onInputPhoneSent(phoneFragment phoneFragment);

    }


    public phoneFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_phone, container, false);
        // Inflate the layout for this fragment
        address="";
        phoneNumber="";
        changedName = false;

        phoneInput = view.findViewById(R.id.phoneInput);
        phoneText = view.findViewById(R.id.phoneText);
        addressInput = view.findViewById(R.id.addressInput);
        addressText = view.findViewById(R.id.addressText);

        phoneText.setSaveEnabled(false);
        addressInput.setSaveEnabled(false);

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();

        String previousPhone = mPreferences.getString(Constants.phonePref, "");
        String previousAddress = mPreferences.getString(Constants.addressPref, "");

        if (!previousPhone.equals("") && !previousAddress.equals(""))
        {
            phoneInput.setText(previousPhone);
            phoneNumber = previousPhone;
            addressInput.setText(previousAddress);
            address = previousAddress;
        }


        phoneInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                phoneNumber = phoneInput.getText().toString();

            }
        });

        addressInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                address = addressInput.getText().toString();

            }
        });





        return view;
    }

    public String getPhoneNumber()
    {

        if (!(phoneNumber.equals("")) )
        {
            if (phoneNumber.startsWith("+91") && phoneNumber.length()==14) {
                Log.d("test", "getPhoneNumber: " + phoneNumber.length());
                return phoneNumber;
            }
            else if (!(phoneNumber.startsWith("+91") && phoneNumber.length()==10))
            {
                Log.d("test", "getPhoneNumber: " + phoneNumber.length());
                return phoneNumber;
            }
            else
            {
                return "";
            }
        }
        else {
            return  "";
        }



    }

    public String getAddress()
    {
        if (!(address.equals("")))
        {
            return address;
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

        if (context instanceof phoneFragmentListener)
        {
            listener = (phoneFragmentListener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement phoneFragment Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }




}
