package com.cmg.covid19app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmg.covid19app.R;
import com.marcinmoskala.arcseekbar.ArcSeekBar;
import com.marcinmoskala.arcseekbar.ProgressListener;

import java.util.Locale;


public class familyInfoFragment extends Fragment {


    private View view;


    private static ArcSeekBar childrenBar;
    private static ArcSeekBar adultBar;
    private static ArcSeekBar teenagerBar;
    private static ArcSeekBar seniorBar;

    private static TextView childrenView;
    private static TextView adultView;
    private static TextView teenagerView;
    private static TextView seniorView;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;



    private static int numOfChildren=0;
    private static int numOfAdults=0;
    private static int numOfTeenagers=0;
    private static int numOfSeniors=0;




    private familyInfoFragmentListener listener;

    private static String text = "";
    private static boolean changedName = false;

    public interface familyInfoFragmentListener{

        boolean onInputFamilySent(familyInfoFragment familyInfoFragment);

    }


    public familyInfoFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_family_info, container, false);
        // Inflate the layout for this fragment
        numOfAdults=0;
        numOfChildren=0;
        numOfSeniors=0;
        numOfTeenagers=0;
        changedName = false;

        childrenBar = view.findViewById(R.id.childrenSeekBar);
        childrenView = view.findViewById(R.id.childrenNumber);
        childrenBar.setProgressBackgroundColor(R.color.indigo);
        childrenBar.setProgressColor(R.color.indigo);

        adultView = view.findViewById(R.id.adultNumber);
        adultBar = view.findViewById(R.id.adultSeekBar);
        adultBar.setProgressBackgroundColor(R.color.indigo);
        adultBar.setProgressColor(R.color.indigo);

        teenagerBar = view.findViewById(R.id.teenagerSeekBar);
        teenagerView = view.findViewById(R.id.teenagerNumber);
        teenagerBar.setProgressBackgroundColor(R.color.indigo);
        teenagerBar.setProgressColor(R.color.indigo);

        seniorBar = view.findViewById(R.id.seniorSeekBar);
        seniorView = view.findViewById(R.id.seniorNumber);
        teenagerBar.setProgressBackgroundColor(R.color.indigo);
        teenagerBar.setProgressColor(R.color.indigo);

        childrenBar.setProgress(0);
        childrenBar.setMaxProgress(10);
        childrenView.setText("");


        adultBar.setProgress(0);
        adultBar.setMaxProgress(10);
        adultView.setText("");

        teenagerBar.setProgress(0);
        teenagerBar.setMaxProgress(10);
        teenagerView.setText("");

        seniorBar.setProgress(0);
        seniorBar.setMaxProgress(10);
        seniorView.setText("");

        mPreferences = getActivity().getSharedPreferences(Constants.sharedId, Context.MODE_PRIVATE);
        editor = mPreferences.edit();
        int previousChildren=0;
        int previousTeenager=0;
        int previousAdult=0;
        int previousSenior=0;

        if (!(mPreferences.getString(Constants.childrenNumber, "").equals(""))) {

             previousChildren= Integer.parseInt(mPreferences.getString(Constants.childrenNumber, ""));
        }

        if (!(mPreferences.getString(Constants.adultNumber, "").equals(""))) {

            previousAdult= Integer.parseInt(mPreferences.getString(Constants.adultNumber, ""));
        }

        if ((!mPreferences.getString(Constants.teenagerNumber, "").equals(""))) {

            previousTeenager= Integer.parseInt(mPreferences.getString(Constants.teenagerNumber, ""));
        }

        if ((!mPreferences.getString(Constants.seniorNumber, "").equals(""))) {

            previousSenior= Integer.parseInt(mPreferences.getString(Constants.seniorNumber, ""));
        }
        if (previousChildren>0)
        {
            childrenBar.setProgress(previousChildren);
            childrenView.setText(Integer.toString(previousChildren));
            numOfChildren = previousChildren;
        }

        if (previousAdult>0)
        {
            adultBar.setProgress(previousAdult);
            adultView.setText(Integer.toString(previousAdult));
            numOfAdults = previousAdult;
        }
        if (previousSenior>0)
        {
            seniorBar.setProgress(previousSenior);
            seniorView.setText(Integer.toString(previousSenior));
            numOfSeniors = previousSenior;
        }
        if (previousTeenager>0)
        {
            teenagerBar.setProgress(previousTeenager);
            teenagerView.setText(Integer.toString(previousTeenager));
            numOfTeenagers = previousTeenager;
        }

        adultBar.setOnProgressChangedListener(new ProgressListener() {
            @Override
            public void invoke(int progress) {
                if (progress==0)
                {
                    adultView.setText("");

                }
                else {
                    adultView.setText(Integer.toString(progress));
                }
                numOfAdults = progress;
            }
        });

        childrenBar.setOnProgressChangedListener(new ProgressListener() {
            @Override
            public void invoke(int progress) {
                if (progress==0)
                {
                    childrenView.setText("");
                }
                else {
                    childrenView.setText(Integer.toString(progress));
                }
                numOfChildren = progress;
            }
        });

        seniorBar.setOnProgressChangedListener(new ProgressListener() {
            @Override
            public void invoke(int progress) {
                if (progress==0)
                {
                    seniorView.setText("");
                }
                else {
                    seniorView.setText(Integer.toString(progress));
                }
                numOfSeniors = progress;
            }
        });

        teenagerBar.setOnProgressChangedListener(new ProgressListener() {
            @Override
            public void invoke(int progress) {
                if (progress==0)
                {
                    teenagerView.setText("");
                }
                else {
                    teenagerView.setText(Integer.toString(progress));
                }
                numOfTeenagers = progress;
            }
        });


        return view;
    }

    public int getChildren()
    {
        return numOfChildren;

    }

    public int getTeenagers()
    {
        return numOfTeenagers;

    }

    public int getAdults()
    {
        return numOfAdults;

    }

    public int getSeniors()
    {
        return numOfSeniors;

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

        if (context instanceof familyInfoFragmentListener)
        {
            listener = (familyInfoFragmentListener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "must implement familyInfoFragmentListener Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
