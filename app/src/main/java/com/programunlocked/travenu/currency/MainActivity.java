package com.programunlocked.travenu.currency;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.programunlocked.travenu.R;
import com.programunlocked.travenu.currency.rest.Data;
import com.programunlocked.travenu.currency.utils.Globals;
import com.programunlocked.travenu.currency.utils.Prefs;
import com.programunlocked.travenu.currency.utils.RelativeLayoutTouchListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Fragment {

    public static TextView date, currencyTitle, countryTo, countryFrom;
    public static int flag;
    public static EditText currency_from;
    public static TextView currency_to;
    Typeface tfRegular, tfThin;
    public static ImageView arrowDown, arrowUp;
    public static BottomSheetBehavior behavior;
    RecyclerView recyclerView;
    CurrencyAdapter mAdapter;
    ArrayList items = new ArrayList();
    LinearLayout linearLayoutOne, linearLayoutTwo, mainLayout;
    public static RelativeLayout relativeOne, relativeTwo;
    Calendar calendar;
    int screenHeight = 0;
    public static EditText search;
    CardView interchange;
    public static Context ctx;
    public static View line;
    View view;

    public static final String TAG = "MainActivity";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.currency_activity_main, container, false);


        //Hiding the status bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initialize();

        ctx = getActivity().getApplicationContext();

        try {
            if(!(Prefs.getPrefs("country_from",getActivity()).equals("notfound"))){
                countryFrom.setText(Prefs.getPrefs("country_from",getActivity()));
            }else {
                countryFrom.setText("USD");
            }

            if(!(Prefs.getPrefs("country_to",getActivity()).equals("notfound"))){
                countryTo.setText(Prefs.getPrefs("country_to",getActivity()));
            }else {
                countryTo.setText("INR");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        screenHeight = getScreenHeight();

        currencyApi(getActivity());

        Globals.getCountryCode(getActivity());

        //inflating bottom sheet
        final LinearLayout bottomSheet = (LinearLayout) view.findViewById(R.id.bottom_sheet);
        bottomSheet.getLayoutParams().height = getScreenHeight() - 100;
        behavior = BottomSheetBehavior.from(bottomSheet);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //TODO fill the list
        mAdapter = new CurrencyAdapter(getActivity(), Globals.NEWcountryCode, Globals.NEWcountriesCurrencies);
        recyclerView.setAdapter(mAdapter);

        behavior.setPeekHeight(0);



        //Setting click listeners on arrows
        linearLayoutOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayoutOne.getWindowToken(), 0);
//                behavior.setPeekHeight(screenHeight/2);
            }
        });
        linearLayoutTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayoutTwo.getWindowToken(), 0);
            }
        });

        relativeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                behavior.setPeekHeight(0);
            }
        });
        relativeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                behavior.setPeekHeight(0);
            }
        });

        interchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempFrom = String.valueOf(countryFrom.getText());
                countryFrom.setText(countryTo.getText());
                countryTo.setText(tempFrom);

                tempFrom = String.valueOf(currency_from.getText());
                currency_from.setText(currency_to.getText());
                currency_to.setText(tempFrom);

            }
        });

        relativeOne.setOnTouchListener(new RelativeLayoutTouchListener(this));

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    mAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        currency_from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    String value = Globals.convertCurrency(countryFrom.getText().toString(), countryTo.getText().toString(), s.toString());
                    currency_to.setText(value.toString());
                }else {
                    currency_to.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        relativeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(relativeOne.getWindowToken(), 0);
            }
        });

        return view;
    }


    private int getScreenHeight() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d(TAG, "getScreenHeight: " + height);
        return height;
    }


    private void initialize() {

        date = (TextView) view.findViewById(R.id.date);
        currencyTitle = (TextView) view.findViewById(R.id.currency_title);
        arrowDown = (ImageView) view.findViewById(R.id.down_arrow);
        arrowUp = (ImageView) view.findViewById(R.id.up_arrow);
        countryTo = (TextView) view.findViewById(R.id.country_to);
        countryFrom = (TextView) view.findViewById(R.id.country_from);

        interchange = (CardView) view.findViewById(R.id.interchange);

        currency_from = (EditText) view.findViewById(R.id.currency_from_et);
        currency_to = (TextView) view.findViewById(R.id.currency_to_et);

        line = view.findViewById(R.id.txt_line);

        search = (EditText) view.findViewById(R.id.search);

        date.setText(getCurrentMonth().substring(0, 3) + " " + getCurrentDate() + "," + getCurrentYear());

        relativeOne = (RelativeLayout) view.findViewById(R.id.relative_1);
        relativeTwo = (RelativeLayout) view.findViewById(R.id.relative_2);
        linearLayoutOne = (LinearLayout) view.findViewById(R.id.linearll_1);
        linearLayoutTwo = (LinearLayout) view.findViewById(R.id.linearll_2);
        mainLayout = (LinearLayout) view.findViewById(R.id.maine_linear_layout);

        tfRegular = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/OpenSans-Regular.ttf");
        tfRegular = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/Raleway-ExtraLight.ttf");
        date.setTypeface(tfRegular);
        currencyTitle.setTypeface(tfRegular);
        countryTo.setTypeface(tfThin);
        countryFrom.setTypeface(tfThin);

        hideKeyboard(view.findViewById(R.id.relative_1));
        hideKeyboard(view.findViewById(R.id.relative_2));
        hideKeyboard(view.findViewById(R.id.interchange));

        ColorStateList csl = AppCompatResources.getColorStateList(getActivity(), R.color.blue);
        Drawable drawableone = getResources().getDrawable(R.drawable.ic_keyboard_arrow_down);
        DrawableCompat.setTintList(drawableone, csl);
        arrowDown.setImageDrawable(drawableone);
    }


    public void currencyApi(Activity activity) {
        Data.getCurrency(activity, new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Log.d("tagg", "success api");
            }

            @Override
            public void onFailure() {
                Log.d("tagg", "fail api");
            }
        });
    }

    private String getCurrentDate() {
        calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = mdformat.format(calendar.getTime());
        String date = strDate.substring(0, 2);
        return date;
    }

    private String getCurrentMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        String month = (dateFormat.format(date));
        String currMonth = null;
        if (month.equals("01")) {
            currMonth = "January";
        } else if (month.equals("02")) {
            currMonth = "February";
        } else if (month.equals("03")) {
            currMonth = "March";
        } else if (month.equals("04")) {
            currMonth = "April";
        } else if (month.equals("05")) {
            currMonth = "May";
        } else if (month.equals("06")) {
            currMonth = "June";
        } else if (month.equals("07")) {
            currMonth = "July";
        } else if (month.equals("08")) {
            currMonth = "August";
        } else if (month.equals("09")) {
            currMonth = "September";
        } else if (month.equals("10")) {
            currMonth = "October";
        } else if (month.equals("11")) {
            currMonth = "November";
        } else if (month.equals("12")) {
            currMonth = "December";
        }
        return currMonth;
    }

    private String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = mdformat.format(calendar.getTime());
        String year = strDate.substring(0, 4);
        return year;
    }

    private void hideKeyboard(View root) {
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    View view = v.getRootView().findFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return false;
                }
                return false;
            }
        });
    }

}
