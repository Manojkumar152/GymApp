package app.com.gymapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import app.com.gymapp.Adapter.WorkoutAdapter;
import app.com.gymapp.Fragments.FragmentEditProfile;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
    private ImageView img_user;
    private ImageButton btnBack, btnMenu, tMsg, tWorkout;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private RecyclerView workoutList;
    private SwipeRefreshLayout refreshLayout;
    private TextView textTitle, header_text;
    private TextView tv_name, tv_addr,currentDate;
    private Toolbar toolbar;
    private ArrayList<String> workDays;
    private DrawerLayout drawer;
    private String Title = "Dashboard";
    private String TAG = "HomeActivity";
    FragmentManager fragmentManager;
    SessionManager session;
    RelativeLayout container;
    //SharedPreferences mPrefs;
    //SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView item_notice, item_class_schedule,  item_workouts, item_viewProfile, item_feePayment, item_dashboard, item_event, item_coupons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkConnection();

        addData();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        textTitle = findViewById(R.id.toolbar_text);
        textTitle.setText(Title);
        session = new SessionManager(getApplicationContext());
       /* mPrefs = getSharedPreferences("Session", Context.MODE_PRIVATE);
        pref = getSharedPreferences("Session", Context.MODE_PRIVATE);
        editor = pref.edit();*/

        setView();
        setViewContainer();
        fragmentManager = getSupportFragmentManager();
        img_user = findViewById(R.id.img_user);
        tv_name = findViewById(R.id.user_name);
        tv_addr = findViewById(R.id.user_addr);
        drawer = findViewById(R.id.drawer_layout);
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setupFragmentManager();
        setProfie();
        /*settingRequest();
        InfoRequest();
        mRequest();*/
    }

    private void addData() {
        workDays= new ArrayList<>();
        workDays.add("Sat");
        workDays.add("Fri");
        workDays.add("Thu");
        workDays.add("Wed");
        workDays.add("Tue");
        workDays.add("Mon");
        workDays.add("Sun");
    }

   /* public void setVisibleMenu(int i) {
        if (accessrightItems.get(i).getMenu().equals(item_class_schedule.getTag())) {
            item_class_schedule.setVisibility(View.VISIBLE);
        } else if (accessrightItems.get(i).getMenu().equals(item_workouts.getTag())) {
            item_workouts.setVisibility(View.VISIBLE);
        }else if (accessrightItems.get(i).getMenu().equals(item_viewProfile.getTag())) {
            item_viewProfile.setVisibility(View.VISIBLE);
        }else if (accessrightItems.get(i).getMenu().equals(item_feePayment.getTag())) {
            item_feePayment.setVisibility(View.VISIBLE);
        } else if (accessrightItems.get(i).getMenu().equals(item_notice.getTag())) {
            item_notice.setVisibility(View.VISIBLE);
        } else if (accessrightItems.get(i).getMenu().equals(item_event.getTag())) {
            item_event.setVisibility(View.VISIBLE);
        } else if (accessrightItems.get(i).getMenu().equals(item_coupons.getTag())) {
            item_coupons.setVisibility(View.VISIBLE);
        }
    }*/


    /*public void setVisibleMenu1() {
        item_class_schedule.setVisibility(View.VISIBLE);
        item_workouts.setVisibility(View.VISIBLE);
        item_viewProfile.setVisibility(View.VISIBLE);
        item_feePayment.setVisibility(View.VISIBLE);
        item_notice.setVisibility(View.VISIBLE);
        item_event.setVisibility(View.VISIBLE);
        item_coupons.setVisibility(View.VISIBLE);
    }*/

    //item_staffmember

    private void checkConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        alertMassege(activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void alertMassege(boolean isConnected) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog;
        if (!isConnected) {

            alertDialogBuilder.setMessage("No Internet Connection Available." +
                    "Do you want to try again?");
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HomeActivity.super.recreate();
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    HomeActivity.this.finish();
                }
            });
            alertDialogBuilder.setCancelable(false);
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void setView() {
        LinearLayout navigation_profile = findViewById(R.id.navigation_profile);
        navigation_profile.setOnClickListener(this);
        tMsg = findViewById(R.id.toolbar_msg);
        tMsg.setOnClickListener(this);
        header_text = findViewById(R.id.header_text);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        btnMenu = findViewById(R.id.btn_menu);

        /******Set Current Date on DashBoard*****/
        currentDate= findViewById(R.id.current_date);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        currentDate.setText(dateFormat.format(date));

        btnMenu.setOnClickListener(this);
        item_feePayment = findViewById(R.id.item_feePayment);
        item_feePayment.setOnClickListener(this);
        item_dashboard = findViewById(R.id.item_dashboard);
        item_dashboard.setOnClickListener(this);
        item_workouts = findViewById(R.id.item_workouts);
        item_workouts.setOnClickListener(this);
        item_viewProfile = findViewById(R.id.item_viewProfile);
        item_viewProfile.setOnClickListener(this);
        item_class_schedule = findViewById(R.id.item_class_schedule);
        item_class_schedule.setOnClickListener(this);
        item_notice = findViewById(R.id.item_notice);
        item_notice.setOnClickListener(this);
        item_event = findViewById(R.id.item_event);
        item_event.setOnClickListener(this);
        item_coupons = findViewById(R.id.item_coupon);
        item_coupons.setOnClickListener(this);

        /****DashBoard Screen*****/
        workoutList=findViewById(R.id.workout_list);
        workoutList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        WorkoutAdapter adapter= new WorkoutAdapter(this,workDays);
        workoutList.setAdapter(adapter);
    }

    private void setViewContainer() {
        container = findViewById(R.id.content_home);
        TextView tv = findViewById(R.id.lbMember);
        tv.setSelected(true);
        TextView nut = findViewById(R.id.lbl3);
        nut.setSelected(true);
        RelativeLayout divClass = findViewById(R.id.divClass);
        RelativeLayout divNutrition = findViewById(R.id.divNutrition);
        RelativeLayout divWorkout = findViewById(R.id.divWorkout);
        divNutrition.setOnClickListener(this);
        divWorkout.setOnClickListener(this);
        divClass.setOnClickListener(this);
       /* cv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                checkDate();
            }
        });*/
        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //InfoRequest();
                //mRequest();
            }
        });
    }

    public void setProfie() {
        if (session.isLoggedIn()) {
            HashMap<String, String> user_profile = session.getUserDetails();
            tv_name.setText(user_profile.get(SessionManager.KEY_NAME));
            tv_addr.setText(user_profile.get(SessionManager.KEY_ADDR));
            Picasso.with(getApplicationContext())
                    .load(user_profile.get(SessionManager.KEY_IMAGE))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    //.placeholder(getResources().getDrawable(R.drawable.ic_placeholder))
                    .fit()
                    .centerCrop()
                    .into(img_user);
        }
    }

    public void setFragment(Fragment fragment) {
        checkConnection();
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_home, fragment)
                    .addToBackStack(null).commit();
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.e("HomeActivity", "Error in creating fragment");
        }
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }

    public void closeBar(View view) {
        drawer.closeDrawers();
    }

    private void dashboard() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation_profile:
                setFragment(new FragmentEditProfile());
                break;
            case R.id.btn_back:
                super.onBackPressed();
                break;
            case R.id.btn_menu:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.item_dashboard:
                dashboard();
                break;
           /*case R.id.item_feePayment:
                setFragment(new FeePaymentFragment());
                break;
            case R.id.item_workouts:
                setFragment(new WorkoutFragment());
                break;
            case R.id.item_viewProfile:
                setFragment(new ViewMemberFragment());
                break;
            case R.id.item_class_schedule:
                setFragment(new ClassScheduleScreen());
                break;

            case R.id.item_notice:
                setFragment(new Fragment_NoticeList());
                break;

            case R.id.divNutrition:
                setFragment(new NutritionScheduleFragment());
                break;

            case R.id.divWorkout:
                setFragment(new WorkoutFragment());
                break;

            case R.id.divClass:
                setFragment(new ClassScheduleScreen());
                break;*/
        }
    }

    private void setupFragmentManager() {
   //     fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.w("Count", fragmentManager.getBackStackEntryCount() + "");
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    if (btnBack.getVisibility() == View.VISIBLE)
                        btnBack.setVisibility(View.INVISIBLE);
                    textTitle.setText(Title);
                } else {
                    if (btnBack.getVisibility() == View.INVISIBLE)
                        btnBack.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onExpansionUpdate(float expansionFraction) {

    }

   /* private void InfoRequest() {
        refreshLayout.setRefreshing(true);
        final StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.DASHBOARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                refreshLayout.setRefreshing(false);
                try {
                    //cv.removeDecorators();
                    DashboardInfo info = new JsonParser(getBaseContext()).getDashboardInfo(response);
                    noticeItems = info.getNotice();
                    resItems = info.getReservation();
                    BDItems = info.getBirthdate();

                    ArrayList<CalendarDay> notice = new ArrayList<>();
                    if (!(noticeItems == null)) {
                        for (int i = 0; i < noticeItems.size(); i++) {
                            Date date = sdf.parse(noticeItems.get(i).getDate());
                            CalendarDay dat = new CalendarDay(date);
                            notice.add(dat);
                        }
                    }
                    //cv.addDecorator(new PresentDecorator(notice, getBaseContext(), 4));
                    ArrayList<CalendarDay> dob = new ArrayList<>();
                    if (BDItems != null) {
                        for (int i = 0; i < BDItems.size(); i++) {
                            Date date = sdf.parse(BDItems.get(i).getBirthDate());
                            CalendarDay dat = new CalendarDay(date);
                            dob.add(dat);
                        }
                    }
                    //cv.addDecorator(new PresentDecorator(dob, getBaseContext(), PresentDecorator.BLUE));
                    ArrayList<CalendarDay> reservation = new ArrayList<>();
                    if (resItems != null) {
                        for (int i = 0; i < resItems.size(); i++) {
                            Date date = sdf.parse(resItems.get(i).getEventDate());
                            CalendarDay dat = new CalendarDay(date);
                            reservation.add(dat);
                        }
                    }
                    //cv.addDecorator(new PresentDecorator(reservation, getBaseContext(), PresentDecorator.GREEN));
                    intersect();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(TAG, "Error: " + error.getMessage());
                refreshLayout.setRefreshing(false);
                Toasty.warning(getBaseContext(),
                        "Error: " + "Connection fail!",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", session.getUserDetails().get(SessionManager.KEY_ID));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void checkDate() {
        boolean Flag = false;
        String detail = "";
        int cnt = 0;
        ArrayList<Events> arr = new ArrayList<>();
        String mDate = sdf.format(cv.getSelectedDate().getDate());
        if (!(noticeItems == null)) {
            for (int i = 0; i < noticeItems.size(); i++) {
                if (noticeItems.get(i).getDate().equals(mDate)) {
                    if (!Flag) {
                        detail = noticeItems.get(i).getTitle();
                        Flag = true;
                    } else
                        detail = "(" + (++cnt) + ") " + detail + "\n\n(" + (++cnt) + ") " + noticeItems.get(i).getTitle();
                }

            }
        }
        if (!detail.equals("")) {
            Events e = new Events();
            e.title = "Notice";
            e.detail = detail;
            arr.add(e);
        }
        cnt = 0;
        Flag = false;
        detail = "";
        for (int i = 0; i < BDItems.size(); i++) {
            if (BDItems.get(i).getBirthDate().equals(mDate)) {
                if (!Flag) {
                    detail = BDItems.get(i).getName();
                    Flag = true;
                } else
                    detail = "(" + (++cnt) + ") " + detail + "\n(" + (++cnt) + ") " + BDItems.get(i).getName();
            }
        }
        if (!detail.equals("")) {
            Events e = new Events();
            e.title = "Birthday";
            e.detail = detail;
            arr.add(e);
        }
        cnt = 0;
        Flag = false;
        detail = "";
        if (resItems != null) {
            for (int i = 0; i < resItems.size(); i++) {
                if (resItems.get(i).getEventDate().equals(mDate)) {
                    if (!Flag) {
                        detail = "Event Name : " + resItems.get(i).getEventName() + "\nStart Time : " +
                                resItems.get(i).getStartTime() + "\nEnd Time : " +
                                resItems.get(i).getEndTime() + "\nPlace : " +
                                resItems.get(i).getPlace();
                        Flag = true;
                    } else
                        detail = "(" + (++cnt) + ") " + detail + "\n\n(" + (++cnt) + ") " + "Event Name : " + resItems.get(i).getEventName() + "\nStart Time : " +
                                resItems.get(i).getStartTime() + "\nEnd Time : " +
                                resItems.get(i).getEndTime() + "\nPlace : " +
                                resItems.get(i).getPlace();

                }
            }
        }
        if (!detail.equals("")) {
            Events e = new Events();
            e.title = "Reservation";
            e.detail = detail;
            arr.add(e);
        }
        if (arr.isEmpty())
            return;
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dashbord_event, null);
        mPopupWindow = new PopupWindow(customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, false);
        RelativeLayout close = customView.findViewById(R.id.popup_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        ListView listView = customView.findViewById(R.id.dash_list);
        EventsAdapter adapter = new EventsAdapter(arr);
        listView.setAdapter(adapter);
        mPopupWindow.showAtLocation(container, Gravity.CENTER, 0, 0);
    }

    private void mRequest() {
        final StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.MEASUREMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mes = new JsonParser(getBaseContext()).getMes(response);
                /*chartData(mes.getWeight(), weightView);
                chartData(mes.getHeight(), heightView);
                chartData(mes.getArms(), armsView);
                chartData(mes.getChest(), chestView);
                chartData(mes.getFat(), fatView);
                chartData(mes.getThigh(), thighView);
                chartData(mes.getWaist(), waistView);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(TAG, "Error: " + error.getMessage());
                refreshLayout.setRefreshing(false);
                Toasty.warning(getBaseContext(),
                        "Error: " + "Connection fail!",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", session.getUserDetails().get(SessionManager.KEY_ID));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

   /* private void chartData(List<ChestItem> list, LineChart mChart) {
        mChart.clear();
        if (list.size() > 0) {
            ArrayList<String> bottom = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                bottom.add(list.get(i).getResultDate());
            }
            ArrayList<Map.Entry> values = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                float f = Float.parseFloat(list.get(i).getResult());
                values.add(new Entry(i, f));
            }
            new setChart(mChart, HomeActivity.this, bottom, values);
        }
    }*/

    /*private void intersect() throws ParseException {
        ArrayList<CalendarDay> arrayList = new ArrayList<>();
        if (BDItems != null) {
            for (int i = 0; i < BDItems.size(); i++) {
                for (int l = i + 1; l < BDItems.size(); l++) {
                    if (BDItems.get(i).getBirthDate().equals(BDItems.get(l).getBirthDate())) {
                        Date date = sdf.parse(BDItems.get(l).getBirthDate());
                        CalendarDay dat = new CalendarDay(date);
                        arrayList.add(dat);
                        break;
                    }
                }
                if (!(resItems == null)) {
                    for (int j = 0; j < resItems.size(); j++) {
                        if (BDItems.get(i).getBirthDate().equals(resItems.get(j).getEventDate())) {
                            Date date = sdf.parse(resItems.get(j).getEventDate());
                            CalendarDay dat = new CalendarDay(date);
                            arrayList.add(dat);
                            BDItems.get(i).getBirthDate();
                            break;
                        }
                    }
                }
                if (!(noticeItems == null)) {
                    for (int k = 0; k < noticeItems.size(); k++) {
                        if (BDItems.get(i).getBirthDate().equals(noticeItems.get(k).getDate())) {
                            Date date = sdf.parse(noticeItems.get(k).getDate());
                            CalendarDay dat = new CalendarDay(date);
                            arrayList.add(dat);
                            BDItems.get(i).getBirthDate();
                            break;
                        }
                    }
                }
            }
        }
        //cv.addDecorator(new PresentDecorator(arrayList, getBaseContext(), PresentDecorator.PURPLE));
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow != null) {
            if (mPopupWindow.isShowing())
                mPopupWindow.dismiss();
            else
                super.onBackPressed();
        } else
            super.onBackPressed();
    }

    private void settingRequest() {

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Const.URL_SETTING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    String status = jobj.getString("status");
                    String error = jobj.getString("error");
                    Log.w("Status", status);
                    if (status.equals("1")) {
                        JsonParser parser = new JsonParser(HomeActivity.this);
                        String object = jobj.getString("result");
                        GeneralSetting info = parser.getSettingInfo(object);
                        header_text.setText(info.getLeftHeader());
                        Currency currency = Currency.getInstance(info.getCurrency());
                        AppController.CURRENCY = currency.getSymbol().charAt(currency.getSymbol().length() - 1);
                    } else
                        Log.w("Setting Request", error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(TAG, "Error: " + error.getMessage());

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(500, 1000, Bitmap.Config.ARGB_8888);
        return bitmap;
    }*/
}
