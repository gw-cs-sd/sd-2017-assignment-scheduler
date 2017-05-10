package com.example.calendarquickstart;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

import com.google.api.services.calendar.*;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;


import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.Date;

import java.lang.Object;



import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends Activity
        implements EasyPermissions.PermissionCallbacks {
    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    private EditText et;
    private Button mCallApiButton;
    ProgressDialog mProgress;
    private int test = 0;
    private com.google.api.services.calendar.Calendar mService = null;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String BUTTON_TEXT1 = "View my current calendar";
    private static final String BUTTON_TEXT2 = "Create an Additional Event";
    private static final String BUTTON_TEXT3 = "Begin Assignment";
    private static final String BUTTON_TEXT4 = "Settings/Preferences";

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR};


    public static Event newEvent = null;
    public static String assignment;
    public static String timeOfDay;
    public static boolean cEvent;
    public static long timeBlock;
    public static long reading;
    public static long math;
    public static long essay;
    public static int rMod;
    public static int mMod;
    public static int eMod;
    public static long modifier;
    public static Event currentEvent;
    public static long timeDay;
    public static long prez = 0;

    public static long comp_reading = 400;
    public static long comp_essay = 0;
    public static long comp_math = 0;

    public static long task = 50;
    public static long it = 0;


    Button btnAdd,btnGetAll;
    TextView student_Id;





//    public void onClick(View view) {
//        if (view== findViewById(R.id.btnAdd)){
//
//            Intent intent = new Intent(this,StudentDetail.class);
//            intent.putExtra("student_Id",0);
//            startActivity(intent);
//
//        }else {
//
//            StudentRepo repo = new StudentRepo(this);
//
//            ArrayList<HashMap<String, String>> studentList =  repo.getStudentList();
//            if(studentList.size()!=0) {
//                ListView lv = getListView();
//                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//                        student_Id = (TextView) view.findViewById(R.id.student_Id);
//                        String studentId = student_Id.getText().toString();
//                        Intent objIndent = new Intent(getApplicationContext(),StudentDetail.class);
//                        objIndent.putExtra("student_Id", Integer.parseInt( studentId));
//                        startActivity(objIndent);
//                    }
//                });
//                ListAdapter adapter = new SimpleAdapter( MainActivity.this,studentList, R.layout.view_student_entry, new String[] { "id","name"}, new int[] {R.id.student_Id, R.id.student_name});
//                setListAdapter(adapter);
//            }else{
//                Toast.makeText(this,"No student!",Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }

    /**
     * Create the main activity.
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout activityLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        activityLayout.setLayoutParams(lp);
        activityLayout.setOrientation(LinearLayout.VERTICAL);
        activityLayout.setPadding(16, 16, 16, 18);


        ViewGroup.LayoutParams tlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);




        mCallApiButton = new Button(this);
        mCallApiButton.setText(BUTTON_TEXT1);
        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallApiButton.setEnabled(false);
                mOutputText.setText("");

                getResultsFromApi();

                mCallApiButton.setEnabled(true);
            }
        });
        activityLayout.addView(mCallApiButton);


        mOutputText = new TextView(this);
        mOutputText.setLayoutParams(tlp);
        mOutputText.setPadding(16, 16, 16, 16);
        mOutputText.setTextSize(20);
        mOutputText.setVerticalScrollBarEnabled(true);
        mOutputText.setMovementMethod(new ScrollingMovementMethod());
        mOutputText.setText(
                "Please click the \'" + BUTTON_TEXT1 + "\' button to view your Calendar");
        activityLayout.addView(mOutputText);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Connection to your Google Calendar...");


        setContentView(activityLayout);

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());


        //send to create event activity
        mCallApiButton = new Button(this);
        mCallApiButton.setText(BUTTON_TEXT2);
        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallApiButton.setEnabled(false);


                startActivity(new Intent(MainActivity.this, eventCreate.class));


                mCallApiButton.setEnabled(true);
            }
        });
        activityLayout.addView(mCallApiButton);


        //Timer
        mCallApiButton = new Button(this);
        mCallApiButton.setText(BUTTON_TEXT3);
        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallApiButton.setEnabled(false);
                startActivity(new Intent(MainActivity.this, assignTracker.class));

                mCallApiButton.setEnabled(true);
            }
        });
        activityLayout.addView(mCallApiButton);




        mCallApiButton = new Button(this);
        mCallApiButton.setText(BUTTON_TEXT4);
        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallApiButton.setEnabled(false);
                mOutputText.setText("");

                startActivity(new Intent(MainActivity.this, SettingsActivity.class));

                mCallApiButton.setEnabled(true);
            }
        });
        activityLayout.addView(mCallApiButton);



        btnAdd = (Button) findViewById(R.id.btnAdd);
//        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
//        btnGetAll.setOnClickListener(this);




        // Sets up and implements live notifications for when its time for an scheduled assignment
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification")
                        .setContentText("Scheduled assignment");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, assignTracker.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(this);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
    }


        public void myNotif(){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("My notification")
                            .setContentText("Scheduled assignment");
            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, assignTracker.class);

            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(this);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
            // mId allows you to update the notification later on.



    }



    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
            mOutputText.setText("No network connection available.");
        } else {
            new MakeRequestTask(mCredential).execute();
        }
    }



    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /*
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mOutputText.setText(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }



    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                MainActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }



    /**
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {

            try {
                if(cEvent){
                    eventAlg();
                    createEvent(mCredential);
                    cEvent = false;
                }



                DateTime now = new DateTime(System.currentTimeMillis());
                List<String> eventStrings = new ArrayList<String>();
                Events events = mService.events().list("primary")
                        .setMaxResults(5)
                        .setTimeMin(now)
                        .setOrderBy("startTime")
                        .setSingleEvents(true)
                        .execute();
                List<Event> items = events.getItems();

                Event nEvent = items.get(0);

                long eventTime = nEvent.getStart().getDateTime().getValue();



                if(eventTime - System.currentTimeMillis() < 10000000){
                    Log.d("tag", String.valueOf(currentEvent));
                    Log.d("tag", String.valueOf(System.currentTimeMillis()));
                    myNotif();

                }

                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.


            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            List<String> testStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            currentEvent = items.get(0);
            currentEvent.setDescription(" ");

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    start = event.getStart().getDate();
                }
                eventStrings.add(
                        String.format("%s (%s)", event.getSummary(), start));
            }

            return eventStrings;
        }

        public void createEvent(GoogleAccountCredential mCredential) throws IOException {


            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            Calendar service = new Calendar.Builder(
                    transport, jsonFactory, mCredential)
                    .setApplicationName("R_D_Location Callendar")
                    .build();

            reading = 7200000;
            math = 5000000;
            essay = 10000000;


            if(assignment.equals("reading")){

                timeBlock = timeBlock + rMod;
            }
            else if(assignment.equals("math")){

                timeBlock = timeBlock + mMod;
            }
            else if(assignment.equals("essay")){

                timeBlock = timeBlock + eMod;
            }

            long currentTime = System.currentTimeMillis();
            long delta = currentTime % 24 * 60 * 60 * 1000;
            long midnight = currentTime - delta;

            DateTime now = new DateTime(System.currentTimeMillis() + 156400000 + it);
            //it = it + 75000000;

//            if(timeOfDay.equals("morning")){
//
//                DateTime r = new DateTime((long)(midnight + 25200000));
//                now = r;
//            }
//            else if(timeOfDay.equals("evening")){
//
//                DateTime m = new DateTime((long) (midnight + 43200000));
//                now = m;
//            }
//            else if(timeOfDay.equals("night")){
//
//                DateTime e = new DateTime((long) (midnight + 61200000));
//                now = e;
//            }


            //find next available free time

            Events events = mService.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            DateTime start = null;
            DateTime temp = null;
            DateTime end = now;
            if(!items.isEmpty()) {
                for (Event event : items) {
                    start = event.getStart().getDateTime();
                    if (start.getValue() - end.getValue() >= timeBlock) {
                        temp = start;
                        start = end;
                        end = start;
                        break;
                    }
                    end = event.getEnd().getDateTime();
                }
            }else{
                start = now;
            }


            newEvent.setLocation("GWU");

            EventDateTime eventStart = new EventDateTime()
                    .setDateTime(start)
                    .setTimeZone("EST");
            newEvent.setStart(eventStart);

            DateTime endValue = new DateTime(start.getValue() + timeBlock);
            EventDateTime eventEnd = new EventDateTime()
                    .setDateTime(endValue)
                    .setTimeZone("EST");
            newEvent.setEnd(eventEnd);

            String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=1"};
            newEvent.setRecurrence(Arrays.asList(recurrence));




            String calendarId = "primary";
            try {
                newEvent = service.events().insert(calendarId, newEvent).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        public void eventAlg (){
            long avgRead = 315;
            long avgMath = 260;
            long avgWrite = 375;

            long output =0;

            if(assignment.equals("reading")){

                //avgRead = 303;
                output = ((task * comp_reading)*avgRead);

            }
            else if(assignment.equals("math")){

                //avgMath = 500;
                output = ((task * comp_reading)*avgMath);

            }
            else if(assignment.equals("essay")){

                //avgWrite = 700;
                output = ((task * comp_reading)*avgWrite);

            }


            timeBlock = output;

        }
//


        @Override
        protected void onPreExecute() {
            mOutputText.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.hide();
            if (output == null || output.size() == 0) {
                mOutputText.setText("No results returned.");
            } else {
                output.add(0, "Your upcoming events:");
                mOutputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            MainActivity.REQUEST_AUTHORIZATION);
                } else {
                    mOutputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());
                }
            } else {
                mOutputText.setText("Request cancelled.");
            }
        }
    }
}

