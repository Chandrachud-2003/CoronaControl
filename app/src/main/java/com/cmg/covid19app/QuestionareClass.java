package com.cmg.covid19app;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.cmg.covid19app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import am.appwise.components.ni.NoInternetDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import mehdi.sakout.fancybuttons.FancyButton;

public class QuestionareClass extends AppCompatActivity implements question1.q1Listener, question2.q2Listener, question3.q3Listener, question4.q4Listener, question5.q5Listener, question6.q6Listener ,question7.q7Listener,question8.q8Listener, question9.q9Listener, nameFragment.nameFragmentListener, phoneFragment.phoneFragmentListener, idFragment.idFragmentListener, familyInfoFragment.familyInfoFragmentListener, additionalDetails.additionalDetailsListener{

    private FancyButton nextButton;

    private FrameLayout mFrameLayout;

    private static final String TAG = "test";

    private question1 q1;
    private question2 q2;
    private question3 q3;
    private question4 q4;
    private question5 q5;
    private question6 q6;
    private question7 q7;
    private question8 q8;
    private question9 q9;
    private additionalDetails mAdditionalDetails;

    private static int gone = View.GONE;
    private static int visible = View.VISIBLE;
    private static int invisible = View.INVISIBLE;
    private  static int newFamilyNum=0;

    private static boolean errorFound = false;


    private static boolean done1;
    private static boolean done2;
    private static boolean done3;
    private static boolean done4;

    private ArrayList<String> wardsList=new ArrayList<>();
    SpinnerDialog spinnerDialog;

    private Button restartButton;

    private nameFragment mNameFragment;
    private phoneFragment mPhoneFragment;
    private idFragment mIdFragment;
    private familyInfoFragment mFamilyInfoFragment;

    private TextView successText;
    private LottieAnimationView successAnimation;


    private SharedPreferences sh;

    String[] wards;

    private static int questionNumber=1;


    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    private ProgressBar mProgressBar;

    private TextView welcomeText2;
    private TextView welcomeText3;
    private TextView welcomeText;
    private TextView security1;
    private TextView security2;
    private TextView survey1;
    private TextView survey2;
    private TextView basic1;
    private TextView basic2;

    private ImageButton backButton;


    private ScrollView descriptionScroll;

    private LottieAnimationView securityAnimation;

    private NoInternetDialog mNoInternetDialog;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionarre);

        nextButton = findViewById(R.id.nextButton);

        nextButton.setText(getResources().getString(R.string.go_text));


        welcomeText = findViewById(R.id.welcomeText);
        welcomeText2 = findViewById(R.id.welcome2);
        welcomeText3 = findViewById(R.id.welcome3);
        security1 = findViewById(R.id.securityText1);
        security2 = findViewById(R.id.securityText2);
        survey1 = findViewById(R.id.surveyInfo1);
        survey2 = findViewById(R.id.surveyInfo2);
        basic1 = findViewById(R.id.basicInfo1);
        basic2 = findViewById(R.id.basicInfo2);
        restartButton = findViewById(R.id.restartButton);

        securityAnimation = findViewById(R.id.securityAnimation);

        backButton = findViewById(R.id.backButton);
        backButton.setVisibility(invisible);

        successText = findViewById(R.id.successText);
        successAnimation = findViewById(R.id.successAnimation);

        mFrameLayout = findViewById(R.id.fragmentContainer);

        successText.setVisibility(View.INVISIBLE);
        successAnimation.setVisibility(View.INVISIBLE);

        done1=false;
        done2=false;
        done3=false;
        done4=false;

        clearInfo();

        loadLocale();





        descriptionScroll = findViewById(R.id.descriptionScrollView);

        newFamilyNum=0;



        mNoInternetDialog  = new NoInternetDialog.Builder(QuestionareClass.this).build();



        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction t = manager.beginTransaction();

        wards = getResources().getStringArray(R.array.Wards);
        Collections.addAll(wardsList, wards);

        mProgressBar = findViewById(R.id.questionBar);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);

        Drawable progressDrawable = mProgressBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        mProgressBar.setProgressDrawable(progressDrawable);

        mPreferences = getSharedPreferences(Constants.sharedId, MODE_PRIVATE);
        editor = mPreferences.edit();

        //clearInfo(false);

        setBasicVisibility(invisible);
        setInfoVisibility(gone);
        setSurveyVisibility(invisible);
        setSecurityVisibility(invisible);
        setWelcomeVisibility(visible);
        mProgressBar.setVisibility(invisible);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backFragment();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionareClass.this);

                builder.setMessage(getResources().getString(R.string.exit_text_2))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes_2), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();


                                //clearInfo(true);
                                //finish();
                                Intent intent = new Intent(QuestionareClass.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no_text), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });

                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Verify Exit");
                alert.show();

            }
        });
        questionNumber=1;



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (questionNumber)
                {

                    case 1:
                    {
                        spinnerDialog=new SpinnerDialog(QuestionareClass.this,wardsList,"Select or Search Ward",R.style.DialogAnimations_SmileWindow,"Cancel");// With 	Animation
                        spinnerDialog.setCancellable(true); // for cancellable
                        spinnerDialog.setShowKeyboard(false);
                        showWardSpinner();

                        spinnerDialog.showSpinerDialog();


                        break;
                    }



                    case 3:
                    {
                        setButtonText(getResources().getString(R.string.next_text));
                        setWelcomeVisibility(gone);
                        setInfoVisibility(invisible);
                        setBasicVisibility(invisible);
                        setSecurityVisibility(visible);
                        setSurveyVisibility(invisible);
                        setButtonText(getResources().getString(R.string.got_it));


                        questionNumber=4;

                        securityAnimation.setProgress(0);
                        securityAnimation.pauseAnimation();
                        securityAnimation.playAnimation();
                        securityAnimation.setRepeatCount(7);

                        securityAnimation.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                double prog = Double.parseDouble(valueAnimator.getAnimatedValue().toString());
                                if (prog>0.97 && prog<=1)
                                {
                                    securityAnimation.setProgress(0.20f);
                                }

                            }
                        });


                        break;
                    }

                    case 4:
                    {
                        setSecurityVisibility(invisible);
                        setWelcomeVisibility(invisible);
                        setInfoVisibility(invisible);
                        setBasicVisibility(visible);
                        mProgressBar.setVisibility(visible);
                        basic1.setVisibility(visible);
                        basic2.setVisibility(visible);
                        setButtonText(getResources().getString(R.string.go_text));

                        setSurveyVisibility(invisible);
                        questionNumber=5;
                        break;
                    }

                    case 5:
                    {
                        setSecurityVisibility(invisible);
                        setWelcomeVisibility(gone);
                        setInfoVisibility(gone);
                        setBasicVisibility(invisible);
                        basic1.setVisibility(invisible);
                        basic2.setVisibility(invisible);

                        setSurveyVisibility(invisible);
                        setButtonText(getResources().getString(R.string.next_text));
                        clearInfo();


                        changeBasicFragment();
                        mProgressBar.setVisibility(visible);
                        questionNumber=6;
                        break;
                    }

                    case 6:
                    {
                        mNameFragment = new nameFragment();
                        boolean cont = onInputNameSent(mNameFragment);
                        if (cont)
                        {
                            cont = false;
                            backButton.setVisibility(visible);
                            changeBasicFragment();
                            questionNumber=7;

                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Blank fields / Invalid format found", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    }

                    case 7:
                    {
                        mPhoneFragment = new phoneFragment();
                        boolean cont = onInputPhoneSent(mPhoneFragment);
                        if (cont)
                        {
                            cont = false;
                            changeBasicFragment();
                            questionNumber=8;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Blank fields / Invalid format found", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    }

                    case 8:
                    {
                        mIdFragment = new idFragment();
                        boolean cont = onInputIDSent(mIdFragment);
                        if (cont)
                        {
                            cont = false;
                            changeBasicFragment();
                            questionNumber=9;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Blank fields / Invalid format found", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    }

                    case 9:
                    {
                        mFamilyInfoFragment = new familyInfoFragment();
                        final boolean cont = onInputFamilySent(mFamilyInfoFragment);
                        if (cont)
                        {
                            Context context;
                            AlertDialog.Builder builder=new AlertDialog.Builder(QuestionareClass.this);
                            builder.setMessage(getResources().getString(R.string.verify_basic))
                                    .setCancelable(false)
                                    .setPositiveButton(getResources().getString(R.string.yes_text), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            removeFragments();
                                            setSurveyVisibility(visible);
                                            survey1.setVisibility(visible);
                                            survey2.setVisibility(visible);
                                            setSecurityVisibility(invisible);
                                            setBasicVisibility(invisible);
                                            setInfoVisibility(gone);
                                            backButton.setVisibility(View.INVISIBLE);
                                            updateProgressBar(0);
                                            setWelcomeVisibility(gone);
                                            setButtonText(getResources().getString(R.string.go_text));
                                            questionNumber=10;

                                        }
                                    })
                                    .setNegativeButton(getResources().getString(R.string.no_2), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();

                                        }
                                    });
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Verify Basic Info");
                            alert.show();


                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "No input identified", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    }

                    case 10:
                    {
                        setWelcomeVisibility(gone);
                        setInfoVisibility(gone);
                        setSecurityVisibility(gone);
                        setSurveyVisibility(gone);
                        survey1.setVisibility(invisible);
                        survey2.setVisibility(invisible);
                        setBasicVisibility(gone);
                        backButton.setVisibility(invisible);
                        setButtonText(getResources().getString(R.string.next_text));

                        changeQuestionFragment();
                        mProgressBar.setVisibility(visible);
                        questionNumber=11;
                    }




                    case 11:
                    {

                        q1 = new question1();
                        boolean cont = onInputQ1Sent(q1);
                        if (cont)
                        {
                            cont = false;
                            backButton.setVisibility(visible);
                            changeQuestionFragment();
                            questionNumber=12;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please select all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case 12:
                    {
                        q2 = new question2();
                        boolean cont = onInputQ2Sent(q2);
                        if (cont)
                        {
                            cont = false;
                            changeQuestionFragment();
                            questionNumber=13;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please select all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case 13:
                    {
                        q3 = new question3();
                        boolean cont = onInputQ3Sent(q3);
                        if (cont)
                        {
                            cont = false;
                            changeQuestionFragment();
                            questionNumber=14;
                            break;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please select all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case 14:
                    {
                        q4 = new question4();
                        boolean cont = onInputQ4Sent(q4);
                        if (cont)
                        {
                            cont = false;
                            changeQuestionFragment();
                            questionNumber=15;
                            break;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please select all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case 15:
                    {
                        q5 = new question5();
                        boolean cont = onInputQ5Sent(q5);
                        if (cont)
                        {
                            cont = false;
                            changeQuestionFragment();
                            questionNumber=16;
                            break;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please select all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case 16:
                    {
                        q6 = new question6();
                        boolean cont = onInputQ6Sent(q6);
                        if (cont)
                        {
                            cont = false;
                            changeQuestionFragment();
                            questionNumber = 17;


                            break;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please select all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }

                    case 17:
                    {
                        q7 = new question7();
                        boolean cont = onInputQ7Sent(q7);
                        if (cont)
                        {
                            cont = false;
                            changeQuestionFragment();
                            questionNumber = 18;
                            break;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please select all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }

                    case 18:
                    {
                        q8 = new question8();
                        boolean cont = onInputQ8Sent(q8);
                        if (cont)
                        {
                            cont = false;
                            changeQuestionFragment();
                            questionNumber = 19;
                            break;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please select all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }

                    case 19:
                    {
                        q9 = new question9();
                        boolean cont = onInputQ9Sent(q9);
                        if (cont)
                        {
                            cont = false;
                            updateProgressBar(100);

                            questionNumber=20;
                            changeQuestionFragment();
                            setButtonText(getResources().getString(R.string.submit));

                            break;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please select all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }

                    case 20:
                    {
                        mAdditionalDetails = new additionalDetails();
                        boolean cont = onInputAdditionalSent(mAdditionalDetails);
                        if (cont)
                        {
                            errorFound=false;
                            cont = false;
                            updateProgressBar(100);

                            mFrameLayout.setVisibility(View.INVISIBLE);

                            successAnimation.setVisibility(View.VISIBLE);
                            successText.setVisibility(View.VISIBLE);




                            successAnimation.setProgress(0);
                            successAnimation.pauseAnimation();
                            successAnimation.setSpeed(0.7f);
                            successAnimation.playAnimation();
                            successAnimation.setRepeatCount(20);
                            questionNumber = 21;

                            storeData();
                            break;
                        }
                        else {
                            Toast.makeText(QuestionareClass.this,  "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }

                    case 22:
                    {
                        Intent intent = new Intent(QuestionareClass.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    }



                }

            }
        });
    }

    private void backFragment()
    {
        nextButton.setText("Next");
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment instanceof phoneFragment)
        {
            fragment = new nameFragment();
            backButton.setVisibility(View.INVISIBLE);
            questionNumber=6;
        }
        else if (fragment instanceof idFragment)
        {
            fragment = new phoneFragment();
            questionNumber=7;
        }
        else if (fragment instanceof familyInfoFragment)
        {
            fragment = new idFragment();
            questionNumber=8;
        }

        else if (fragment instanceof question2)
        {
            fragment = new question1();
            questionNumber=11;
        }
        else if (fragment instanceof question3)
        {
            fragment = new question2();
            questionNumber=12;
        }
        else if (fragment instanceof question4)
        {
            fragment = new question3();
            questionNumber=13;
        }
        else if (fragment instanceof question5)
        {
            fragment = new question4();
            questionNumber=14;
        }
        else if (fragment instanceof question6)
        {
            fragment = new question5();
            questionNumber=15;
        }
        else if (fragment instanceof question7)
        {
            fragment = new question6();
            questionNumber=16;
        }
        else if (fragment instanceof question8)
        {
            fragment = new question7();
            questionNumber=17;
        }
        else if (fragment instanceof question9)
        {
            fragment = new question8();
            questionNumber=18;
        }
        else if (fragment instanceof additionalDetails)
        {
            fragment = new question9();
            questionNumber=19;
        }



        fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

    }



    private void changeBasicFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);



        if (fragment instanceof nameFragment)
        {
            fragment = new phoneFragment();
            updateProgressBar(50);
        }
        else if (fragment instanceof phoneFragment)
        {
            fragment = new idFragment();
            updateProgressBar(75);
        }
        else if (fragment instanceof idFragment)
        {
            fragment=new familyInfoFragment();
            updateProgressBar(100);
        }
        else
        {
            fragment = new nameFragment();
            updateProgressBar(25);
        }





        fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,
                R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();


    }

    private void changeQuestionFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);


        if (fragment instanceof question1)
        {
            fragment = new question2();
            updateProgressBar(20);
        }
        else if (fragment instanceof question2)
        {
            fragment = new question3();
            updateProgressBar(30);
        }
        else if (fragment instanceof question3)
        {
            fragment = new question4();
            updateProgressBar(40);
        }
        else if (fragment instanceof question4)
        {
            fragment = new question5();
            updateProgressBar(50);
        }
        else if (fragment instanceof question5)
        {
            fragment = new question6();
            updateProgressBar(60);
        }
        else if (fragment instanceof question6)
        {
            fragment = new question7();
            updateProgressBar(70);

        }
        else if (fragment instanceof question7)
        {
            fragment = new question8();
            updateProgressBar(80);
        }
        else if (fragment instanceof question8)
        {
            fragment = new question9();
            updateProgressBar(90);
        }
        else if (fragment instanceof  question9)
        {
            fragment = new additionalDetails();
            updateProgressBar(100);


        }
        else {
            fragment = new question1();
            updateProgressBar(10);
        }





        fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,
                R.anim.exit_to_left);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();


    }

    @Override
    public boolean onInputFamilySent(familyInfoFragment familyInfoFragment) {

        int numChildren = familyInfoFragment.getChildren();
        int numAdults = familyInfoFragment.getAdults();
        int numTeenagers = familyInfoFragment.getTeenagers();
        int numSeniors = familyInfoFragment.getSeniors();

        int total = numChildren+numAdults+numTeenagers+numSeniors;
        if (total>0)
        {
            Log.d(TAG, "onInputFamilySent: "+numAdults);
            Log.d(TAG, "onInputFamilySent: "+numChildren);
            Log.d(TAG, "onInputFamilySent: "+numSeniors);
            Log.d(TAG, "onInputFamilySent: "+numTeenagers);
            editor.putString(Constants.childrenNumber, Integer.toString(numChildren));
            editor.putString(Constants.adultNumber, Integer.toString(numAdults));
            editor.putString(Constants.teenagerNumber, Integer.toString(numTeenagers));
            editor.putString(Constants.seniorNumber, Integer.toString(numSeniors));
            return true;
        }



        return false;
    }

    @Override
    public boolean onInputIDSent(idFragment idFragment) {

        String aadhar = idFragment.getAadharNum();
        String voterID = idFragment.getVoterId();
        String driverLicense = idFragment.getDriverLicense();

        if (!(aadhar.equals("")))
        {
            Log.d(TAG, "onInputIDSent: "+aadhar);
            Log.d(TAG, "onInputIDSent: "+voterID);
            Log.d(TAG, "onInputIDSent: "+driverLicense);
            editor.putString(Constants.aadharNumber, aadhar).commit();
            editor.putString(Constants.voterId, voterID).commit();
            editor.putString(Constants.driverLicense, driverLicense).commit();
            return true;

        }

        return false;
    }

    @Override
    public boolean onInputNameSent(nameFragment nameFragment) {

        String firstName = nameFragment.getFirstName();
        String lastName = nameFragment.getLastName();

        if (!(firstName.equals("")) && !(lastName.equals("")))
        {
            Log.d(TAG, "onInputNameSent: "+firstName);
            Log.d(TAG, "onInputNameSent: "+lastName);
            editor.putString(Constants.firstN_pref, firstName).commit();
            editor.putString(Constants.lasntN_pref, lastName).commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onInputPhoneSent(phoneFragment phoneFragment) {

        String phoneNum = phoneFragment.getPhoneNumber();
        String address = phoneFragment.getAddress();

        if (!(phoneNum.equals("")) && !(address.equals("")))
        {
            Log.d(TAG, "onInputPhoneSent: "+phoneNum);
            Log.d(TAG, "onInputPhoneSent: "+address);
            editor.putString(Constants.addressPref, address).commit();
            if (phoneNum.startsWith("+91"))
            {
                editor.putString(Constants.phonePref, phoneNum).commit();
            }
            else {
                editor.putString(Constants.phonePref, "+91"+phoneNum).commit();
            }
            return true;
        }

        return false;
    }




    public boolean onInputQ1Sent(question1 q1) {

        String result = q1.checkFinished();
        if (!result.equals(""))
        {
            Log.d(TAG, "onInputQ1Sent: "+result);
            if (result.equals("Yes"))
            {
                String symptoms = q1.symptoms();
                Log.d(TAG, "onInputQ1Sent: "+symptoms);
                editor.putString(Constants.q1Pref_2, symptoms).commit();
            }
            editor.putString(Constants.q1Pref, result).commit();

            return true;
        }
        else return false;

    }


    public boolean onInputQ2Sent(question2 q2) {

        String result = q2.checkFinished();

        if (!result.equals(""))
        {
            Log.d(TAG, "onInputQ2Sent: "+result);
            editor.putString(Constants.q2Pref, result).commit();
            return true;
        }
        else return false;

    }


    public boolean onInputQ3Sent(question3 q3) {
        String result = q3.checkFinished();
        if (!result.equals(""))
        {
            Log.d(TAG, "onInputQ3Sent: "+result);
            if (result.equals("Yes"))
            {
                String result2 = q3.typeChosen();
                Log.d(TAG, "onInputQ3Sent: "+result2);
                editor.putString(Constants.q3Pref_type, result2).commit();

                String placeSelect = q3.placeChosen();
                Log.d(TAG, "onInputQ3Sent: "+placeSelect);
                editor.putString(Constants.q3Pref_2, placeSelect).commit();
            }

            editor.putString(Constants.q3Pref_1, result).commit();
            return true;
        }
        else return false;
    }


    public boolean onInputQ4Sent(question4 q4) {
        String result = q4.checkFinished();
        if (!result.equals(""))
        {
            Log.d(TAG, "onInputQ4Sent: "+result);

            if (result.equals("Yes"))
            {
                String result2 = q3.typeChosen();
                editor.putString(Constants.q4Pref_type, result2).commit();
                Log.d(TAG, "onInputQ3Sent: "+result2);
                String placeSelect = q4.placeChosen();
                Log.d(TAG, "onInputQ4Sent: "+placeSelect);
                editor.putString(Constants.q4Pref_2, placeSelect).commit();
            }
            editor.putString(Constants.q4Pref_1, result).commit();
            return true;
        }
        else return false;
    }

    public boolean onInputQ5Sent( question5 q5) {
        String result = q5.checkFinished();

        if (!result.equals(""))
        {
            Log.d(TAG, "onInputQ5Sent: "+result);
            editor.putString(Constants.q5Pref, result).commit();
            return true;
        }
        else return false;
    }


    public boolean onInputQ6Sent(question6 q6) {
        String result = q6.checkFinished();
        if (!result.equals(""))
        {
            Log.d(TAG, "onInputQ6Sent: "+result);
            if (result.equals("Yes"))
            {
                String nameHospital = q6.nameOfHospital();
                String detailsOfVisit = q6.details();
                Log.d(TAG, "onInputQ6Sent: "+nameHospital);
                Log.d(TAG, "onInputQ6Sent: "+detailsOfVisit);
                editor.putString(Constants.q6Pref_2, nameHospital);
                editor.putString(Constants.q6Pref_3, detailsOfVisit);
            }
            editor.putString(Constants.q6Pref_1, result).commit();
            return true;
        }
        else return false;
    }

    @Override
    public boolean onInputQ7Sent(question7 q7) {

        String result = q7.checkFinished();
        if (!result.equals(""))
        {
            Log.d(TAG, "onInputQ7Sent: "+result);
            editor.putString(Constants.q7Pref, result).commit();
            return true;
        }
        else return false;

    }

    @Override
    public boolean onInputQ8Sent(question8 q8) {

        String result = q8.checkFinished();
        if (!result.equals(""))
        {

            editor.putString(Constants.q8Pref, result).commit();

            return true;
        }
        else return false;
    }

    @Override
    public boolean onInputQ9Sent(question9 q9) {

        String result = q9.checkFinished();
        if (!result.equals(""))
        {
            Log.d(TAG, "onInputQ9Sent: "+result);
            Log.d("Test", "onInputQ1Sent: "+result);
            if (result.equals("Yes"))
            {
                String occupation = q9.occupation();
                Log.d(TAG, "onInputQ9Sent: "+occupation);
                editor.putString(Constants.q9Pref_2, occupation).commit();
            }
            editor.putString(Constants.q9Pref_1, result).commit();

            return true;
        }
        else return false;
    }

    @Override
    public boolean onInputAdditionalSent(additionalDetails additionalDetails) {

        String result = additionalDetails.getText();

        if (result!=null) {
            editor.putString(Constants.q10Pref, result);
            return true;
        }
        else {
            return false;
        }


    }

    private void storeData()
    {

        final String wardName = mPreferences.getString(Constants.wardPref, "");






        final DocumentReference documentReference = db.collection(Constants.karnataka).document(Constants.bangalore).collection(wardName).document(Constants.wardInfo);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful())
                {

                    String q1_1 = mPreferences.getString(Constants.q1Pref, "");
                    String q1_2 = mPreferences.getString(Constants.q1Pref_2, "");


                    String q2 = mPreferences.getString(Constants.q2Pref, "");

                    String q3 = mPreferences.getString(Constants.q3Pref_1, "");
                    String q3_2 = mPreferences.getString(Constants.q3Pref_2, "");

                    String q4 = mPreferences.getString(Constants.q4Pref_1, "");
                    String q4_2 = mPreferences.getString(Constants.q4Pref_2, "");

                    String q5 = mPreferences.getString(Constants.q5Pref, "");

                    String q6 = mPreferences.getString(Constants.q6Pref_1, "");
                    String q6_2 = mPreferences.getString(Constants.q6Pref_2, "");
                    String q6_3 = mPreferences.getString(Constants.q6Pref_3, "");

                    String q7 = mPreferences.getString(Constants.q7Pref, "");

                    String q8 = mPreferences.getString(Constants.q8Pref, "");

                    String q9_1 = mPreferences.getString(Constants.q9Pref_1, "");
                    String q9_2 = mPreferences.getString(Constants.q9Pref_2, "");

                    String q10 = mPreferences.getString(Constants.q10Pref, "");




                    String numAdults = mPreferences.getString(Constants.adultNumber, "");
                    String numChildren = mPreferences.getString(Constants.childrenNumber, "");
                    String numSeniors = mPreferences.getString(Constants.seniorNumber, "");
                    String numTeenagers = mPreferences.getString(Constants.teenagerNumber, "");
                    String firstName = mPreferences.getString(Constants.firstN_pref, "");
                    String lastName = mPreferences.getString(Constants.lasntN_pref, "");
                    String aadharNum = mPreferences.getString(Constants.aadharNumber, "");
                    String voterId = mPreferences.getString(Constants.voterId, "");
                    String driverLicense = mPreferences.getString(Constants.driverLicense, "");
                    String phoneNum = mPreferences.getString(Constants.phonePref, "");
                    String address = mPreferences.getString(Constants.addressPref, "");

                    double criticalPercentage=0.0d;

                    double totalCritical=11.0d;
                    double current=0.0d;

                    if (q1_1.equals("Yes"))
                    {
                        Log.d(TAG, "onComplete: q entered");
                        current=1.5;

                        String[] symptoms = q1_2.split(",");
                        Log.d(TAG, "onComplete: symptoms num : "+(symptoms.length-1));
                        current+=0.25*(symptoms.length-1);
                        if (current>2)
                        {
                            current=2;
                        }

                        Log.d(TAG, "onComplete: "+current);

                    }

                    if (q9_1.equals("Yes"))
                    {
                        current+=1.5;
                        String[] occupations = q9_2.split(",");
                        Log.d(TAG, "onComplete: occupations num : "+(occupations.length-1));

                        current+=(0.25*(occupations.length-1));
                        if (current>2)
                        {
                            current=4;
                        }
                        Log.d(TAG, "onComplete: q entered");
                        Log.d(TAG, "onComplete: "+current);

                    }

                    if (q2.equals("Yes"))
                    {


                        current+=1;
                        Log.d(TAG, "onComplete: q entered");
                        Log.d(TAG, "onComplete: "+current);
                    }

                    if (q3.equals("Yes"))
                    {
                        current+=1;
                        Log.d(TAG, "onComplete: q entered");
                        Log.d(TAG, "onComplete: "+current);
                    }
                    if (q4.equals("Yes"))
                    {
                        current+=1;
                        Log.d(TAG, "onComplete: q entered");
                        Log.d(TAG, "onComplete: "+current);
                    }
                    if (q5.equals("Yes"))
                    {
                        current+=0.75;
                        Log.d(TAG, "onComplete: q entered");
                        Log.d(TAG, "onComplete: "+current);
                    }
                    if (q6.equals("Yes"))
                    {
                        current+=0.75;
                        Log.d(TAG, "onComplete: q entered");
                        Log.d(TAG, "onComplete: "+current);
                    }
                    if (q7.equals("Yes"))
                    {
                        current+=0.5;
                        Log.d(TAG, "onComplete: q entered");
                        Log.d(TAG, "onComplete: "+current);
                    }
                    if (q8.equals("Outside"))
                    {
                        current+=0.5;
                        Log.d(TAG, "onComplete: q entered");
                        Log.d(TAG, "onComplete: "+current);
                    }


                    criticalPercentage = (int) ((current/totalCritical)*100);


                    Map<String, Object> notePersonal = new HashMap<>();
                    Map<String, Object> noteQuestionarre = new HashMap<>();
                    noteQuestionarre.put(Constants.q1Data, q1_1);
                    if(q1_1.equals("Yes")) {
                        noteQuestionarre.put(Constants.q1Data_2, q1_2);
                    }
                    else {
                        noteQuestionarre.put(Constants.q1Data_2, "Not applicable");
                    }

                    noteQuestionarre.put(Constants.q2Data, q2);

                    noteQuestionarre.put(Constants.q3Data_1, q3);
                    if(q3.equals("Yes")) {
                        noteQuestionarre.put(Constants.q3Data_2, q3_2);
                    }
                    else {
                        noteQuestionarre.put(Constants.q3Data_2, "Not applicable");
                    }

                    noteQuestionarre.put(Constants.q4Data_1, q4);
                    if(q4.equals("Yes")) {
                        noteQuestionarre.put(Constants.q4Data_2, q4_2);
                    }
                    else {
                        noteQuestionarre.put(Constants.q4Data_2, "Not applicable");
                    }

                    noteQuestionarre.put(Constants.q5Data, q5);

                    noteQuestionarre.put(Constants.q6Data_1, q6);
                    if(q6.equals("Yes")) {
                        noteQuestionarre.put(Constants.q6Data_2, q6_2);
                        noteQuestionarre.put(Constants.q6Data_3, q6_3);
                    }
                    else {
                        noteQuestionarre.put(Constants.q6Data_2, "Not applicable");
                        noteQuestionarre.put(Constants.q6Data_3, "Not applicable");
                    }


                    noteQuestionarre.put(Constants.q7Data, q7);

                    noteQuestionarre.put(Constants.q8Data, q8);

                    noteQuestionarre.put(Constants.q9Data_1, q9_1);
                    if(q9_1.equals("Yes")) {
                        noteQuestionarre.put(Constants.q9Data_2, q9_2);
                    }
                    else {
                        noteQuestionarre.put(Constants.q9Data_2, "Not applicable");
                    }


                    if (!q10.equals("")) {

                        noteQuestionarre.put(Constants.q10Data, q10);
                    }
                    else {
                        noteQuestionarre.put(Constants.q10Data, "Not entered");
                    }



                    notePersonal.put(Constants.f_aadharNum, aadharNum);
                    notePersonal.put(Constants.f_lastName, lastName);
                    notePersonal.put(Constants.f_firstName, firstName);

                    if (voterId != "")
                    {
                        notePersonal.put(Constants.f_voterid, voterId);
                    }
                    else {
                        notePersonal.put(Constants.f_voterid, "Not entered");
                    }

                    if (driverLicense != "")
                    {
                        notePersonal.put(Constants.f_driverLicense, driverLicense);

                    }
                    else {
                        notePersonal.put(Constants.f_driverLicense, "Not entered");
                    }

                    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    notePersonal.put(Constants.f_date, currentDate);


                    notePersonal.put(Constants.f_teenagerNum, numTeenagers);
                    notePersonal.put(Constants.f_adultNum, numAdults);
                    notePersonal.put(Constants.f_seniorNum, numSeniors);
                    notePersonal.put(Constants.f_childrenNum, numChildren);
                    notePersonal.put(Constants.f_phone, phoneNum);
                    notePersonal.put(Constants.f_address, address);

                    String familyType ="";
                    String familyNum="";

                    if (criticalPercentage>=57)
                    {
                        noteQuestionarre.put(Constants.isCrtical, "Yes");
                        familyType = Constants.nameCriticals;
                        familyNum = Constants.numOfCritical;
                    }
                    else {
                        noteQuestionarre.put(Constants.isCrtical, "No");
                        familyType = Constants.familyDocument;
                        familyNum = Constants.familyNum;
                    }

                    Log.d(TAG, "storeData: "+criticalPercentage);

                    noteQuestionarre.put(Constants.riskFactor, criticalPercentage);

                    //final String typeFam = familyType;
                    final String numFam = familyNum;

                    String num="";
                    DocumentSnapshot doc = task.getResult();
                    num = doc.get(numFam).toString();


                    Log.d("test", "onComplete: " + num);

                    final String tempType = familyType;
                    final String wardTemp = wardName;
                    final String riskTemp = Double.toString(criticalPercentage);






                    newFamilyNum=Integer.parseInt(num)+1;
                    Log.d(TAG, "onComplete: "+newFamilyNum);

                    db.collection("Karnataka").document("Bengaluru").collection(wardName).document(familyType).collection("Family "+newFamilyNum).document("Personal Information").set(notePersonal)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    //Toast.makeText(getBaseContext(), "Personal Info added", Toast.LENGTH_SHORT).show();
                                    if(!errorFound) {
                                        Log.d("test", "onSuccess: Personal");
                                        done1 = true;
                                        checkFinished(tempType, wardTemp, riskTemp);
                                    }


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.d("test", "onFailure: "+e.toString());
                                    done1=false;
                                    errorFound=true;
                                    displayError();

                                }
                            });



                    db.collection("Karnataka").document("Bengaluru").collection(wardName).document(familyType).collection("Family "+newFamilyNum).document("Questionnaire Information").set(noteQuestionarre)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if(!errorFound) {

                                    done2=true;

                                    checkFinished(tempType, wardTemp, riskTemp);


                                        //Toast.makeText(getBaseContext(), "Questionarre info added", Toast.LENGTH_SHORT).show();
                                        Log.d("test", "onSuccess: questions");
                                        documentReference.update(numFam, newFamilyNum)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        //Toast.makeText(QuestionareClass.this, "All info saved", Toast.LENGTH_SHORT).show();
                                                        if (!errorFound) {
                                                            done3 = true;
                                                            checkFinished(tempType, wardTemp, riskTemp);
                                                        }

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Log.d(TAG, "onFailure: " + e.toString());
                                                        done3 = false;
                                                        errorFound=true;
                                                        displayError();


                                                    }
                                                });
                                    }







                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.d("test", "onFailure: "+e.toString());
                                    done2 = false;
                                    errorFound=true;
                                    displayError();

                                }
                            });

                            updateTotalNum(familyType, wardName, riskTemp);

                    }

            }
        });



    }


    private void updateProgressBar(int value)
    {
        mProgressBar.setProgress(value);
    }

    private void setWelcomeVisibility(int v)
    {
        welcomeText.setVisibility(v);
        welcomeText2.setVisibility(v);
        welcomeText3.setVisibility(v);
    }

    private void setSecurityVisibility(int v)
    {
        securityAnimation.setVisibility(v);
        security2.setVisibility(v);
        security1.setVisibility(v);

    }

    private void setSurveyVisibility(int v)
    {
        survey1.setVisibility(v);
        survey2.setVisibility(v);
    }

    private void setInfoVisibility(int v){
        descriptionScroll.setVisibility(v);
    }

    private void setBasicVisibility(int v)
    {
        basic2.setVisibility(v);
        basic2.setVisibility(v);
    }

    private void removeFragments()
    {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    private void setButtonText (String text)
    {
        nextButton.setText(text);
    }

    private void clearInfo()
    {
        Log.d(TAG, "clearInfo: Entered");
        //editor.clear().commit();
        SharedPreferences settings = this.getSharedPreferences(Constants.sharedId,MODE_PRIVATE);
        settings.edit().clear().commit();
        Log.d(TAG, "clearInfo: "+settings.getString(Constants.firstN_pref,""));
        //editor.remove(Constants.q1Pref).commit();
        /*editor.putString(Constants.q1Pref, "").commit();
        editor.remove(Constants.q1Pref_2).commit();
        editor.remove(Constants.q2Pref).commit();
        editor.remove(Constants.q3Pref_1).commit();
        editor.remove(Constants.q3Pref_2).commit();
        editor.remove(Constants.q4Pref_1).commit();
        editor.remove(Constants.q4Pref_2).commit();
        editor.remove(Constants.q5Pref).commit();
        editor.remove(Constants.q6Pref_1).commit();
        editor.remove(Constants.q6Pref_2).commit();
        editor.remove(Constants.q6Pref_3).commit();
        editor.remove(Constants.q7Pref).commit();
        editor.remove(Constants.q8Pref).commit();
        editor.remove(Constants.q9Pref_1).commit();
        editor.remove(Constants.q9Pref_2).commit();
        editor.remove(Constants.q10Pref).commit();

        editor.remove(Constants.firstN_pref).commit();
        editor.remove(Constants.lasntN_pref).commit();
        editor.remove(Constants.addressPref).commit();
        editor.remove(Constants.phonePref).commit();
        editor.remove(Constants.voterId).commit();
        editor.remove(Constants.driverLicense).commit();
        editor.remove(Constants.teenagerNumber).commit();
        editor.remove(Constants.childrenNumber).commit();
        editor.remove(Constants.adultNumber).commit();
        editor.remove(Constants.seniorNumber).commit();
*/






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNoInternetDialog.onDestroy();
    }

    private void showWardSpinner()
    {


        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {




                String ward = wardsList.get(position);
                editor.putString(Constants.wardPref, ward);
                if (questionNumber==1) {
                    setWelcomeVisibility(gone);
                    setInfoVisibility(visible);

                    setBasicVisibility(invisible);
                    setSecurityVisibility(invisible);
                    setSurveyVisibility(invisible);

                    setButtonText(getResources().getString(R.string.next_text));
                    questionNumber = 3;
                }

            }
        });


    }

    private void updateTotalNum(String type, String ward, final String risk)
    {
        final String t;
        String tempT="";
        if (type.equals(Constants.nameCriticals))
        {
            tempT = Constants.totalCrticalFamilyStats;
        }
        else if (type.equals(Constants.familyDocument))
        {
            tempT = Constants.totalFamilyStats;
        }
        t = tempT;

        final String typeTemp=type;

        final String wardT = ward;
        final String riskT = risk;

        final DocumentReference documentReference = db.collection(Constants.f_Stats).document(Constants.f_familyStats);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful())
                {
                    DocumentSnapshot doc = task.getResult();
                    String oldNum = doc.get(t).toString();
                    int newnum = Integer.parseInt(oldNum) +1;
                    documentReference.update(t, newnum)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getBaseContext(), "Total updated", Toast.LENGTH_SHORT).show();
                                    done4 = true;
                                    checkFinished(t, wardT, risk);
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.d(TAG, "onFailure: "+e.getMessage());
                                    done4=false;

                                }
                            });



                }

            }
        });
    }

    private void checkFinished(String familyType, String ward, String risk)
    {
        if (done1 && done2 && done3 && done4)
        {
            successAnimation.cancelAnimation();
            successAnimation.setAnimation("success.json");
            successAnimation.setProgress(0);
            successAnimation.setRepeatCount(0);
            successAnimation.playAnimation();
            Log.d(TAG, "checkFinished: "+"Finished");

            successText.setText(getResources().getString(R.string.upload_success));

            nextButton.setText(getResources().getString(R.string.return_home));
            questionNumber = 22;

            if (familyType.equals(Constants.familyDocument)) {

                sendMail("New Family Surveyed", Constants.karnataka, Constants.bangalore, ward, "");
            }
            else if (familyType.equals(Constants.nameCriticals))
            {
                sendMail("Alert! New Critical Family surveyed", Constants.karnataka, Constants.bangalore, ward, risk);
            }





        }


    }

    private void displayError()
    {
        errorFound=true;
        questionNumber=20;
        successAnimation.cancelAnimation();
        successAnimation.setAnimation("error.json");
        successAnimation.setProgress(0);
        successAnimation.setRepeatCount(0);
        successAnimation.playAnimation();
        nextButton.setText("Try Again");
        mFrameLayout.setVisibility(View.VISIBLE);
        successText.setVisibility(View.INVISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionareClass.this);

        builder.setMessage("An error occured, please check your internet connection or try again later")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                       dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Error Dialog");
        alert.show();




    }

    private void sendMail(String subject, String state, String district,String ward, String riskFactor) {

        String mail = "fqi.corona.taskforce@gmail.com";
        String message="";

        String subjectSend = subject;
        if (riskFactor!="")
        {
             message = "ID: "+newFamilyNum+","+ "State: "+state+","+ "District: "+district+","+"Ward Name: "+ward+","+"Risk percentage: " +riskFactor;
        }
        else {
             message = "ID: "+newFamilyNum+","+ "State: "+state+","+ "District: "+district+","+"Ward Name: "+ward;
        }

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(QuestionareClass.this,mail,subjectSend,message);

        javaMailAPI.execute();

        Log.d(TAG, "sendMail: Email sent");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void loadLocale()
    {
        SharedPreferences pref = getSharedPreferences(Constants.languageId, MODE_PRIVATE);
        String lang = pref.getString(Constants.language_Pref, "");
        Log.d(TAG, "loadLocale: lang"+pref.getString(Constants.language_Pref, ""));
        if (lang.equals(""))
        {
            setLocale("en");
        }
        else {
            setLocale(lang);
        }
    }

    private void setLocale(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences pref = getSharedPreferences(Constants.languageId, MODE_PRIVATE);
        String lang2 = pref.getString(Constants.language_Pref, "");
        Log.d(TAG, "setLocale: language:"+lang2);


    }







}
