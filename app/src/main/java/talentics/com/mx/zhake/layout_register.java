package talentics.com.mx.zhake;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;



public class layout_register extends AppCompatActivity {
    EditText mFNameView, mLNameView, mSexView, mAgeView, mEmailView, mPasswordView;
    EditText mStreetView, mNumberView, mColView, mCityView, mStateView, mCPView;
    private View mProgressView;
    private View mRegisterFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_register);

        mRegisterFormView = findViewById(R.id.register_formView) ;
        mProgressView = findViewById(R.id.register_progressbar);

        mFNameView = (EditText) findViewById(R.id.fname_text);
        mLNameView = (EditText) findViewById(R.id.lname_text);
        mSexView = (EditText) findViewById(R.id.sex_text);
        mAgeView = (EditText) findViewById(R.id.age_text);
        mEmailView = (EditText) findViewById(R.id.email_text);
        mPasswordView = (EditText) findViewById(R.id.pass_text);
        mStreetView = (EditText) findViewById(R.id.street_text);
        mNumberView = (EditText) findViewById(R.id.num_text);
        mColView = (EditText) findViewById(R.id.col_text);
        mCityView = (EditText) findViewById(R.id.city_text);
        mStateView = (EditText) findViewById(R.id.state_text);
        mCPView = (EditText) findViewById(R.id.cp_text);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Intent.EXTRA_TEXT);
        mEmailView.setText(message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.register_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        attemptRegister();
        return true;
    }

     /* Database information */


    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRICES = "prices";

    // Common column names
    private static final String KEY_ID = "id";

    // users Table - column names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_SEX = "sex";
    private static final String KEY_AGE = "age";
    private static final String KEY_STREET = "street";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_COL = "col";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_CP = "cp";

    // prices Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";

    UserRegisterTask mRegisterTask = null;

    private void callRegister(String mess){
        Intent intent = new Intent(this, layout_register.class);
        intent.putExtra(Intent.EXTRA_TEXT, mess);
        startActivity(intent);
    }

    private void attemptRegister(){

        if (mRegisterTask != null) {
            return;
        }

        /*Capture data */
        EditText[] Fields = new EditText[12];
        String[] Values = new String[12];

        Fields[0] = mFNameView;
        Fields[1] = mLNameView;
        Fields[2] = mSexView;
        Fields[3] = mAgeView;
        Fields[4] = mEmailView;
        Fields[5] = mPasswordView;
        Fields[6] = mStreetView;
        Fields[7] = mNumberView;
        Fields[8] = mColView;
        Fields[9] = mCityView;
        Fields[10] = mStateView;
        Fields[11] = mCPView;

        /*Reset errors*/
        for(EditText i:Fields){
            i.setError(null);
        }

        View focusView = null;

        int aux= 0;
        for (EditText i:Fields) {
            if(isEmpty(i)){
                i.setError(getString(R.string.error_field_required));
                focusView= i;
                focusView.requestFocus();
                return;
            }
            else{
                Values[aux] = i.getText().toString();
            }
            aux++;
        }
        if(!isValidEmail(Fields[4])){
            focusView = Fields[4];
            Fields[4].setError(getString(R.string.error_invalid_email));
            focusView.requestFocus();
            return;
        }
        if(!isPasswordValid(Fields[5])){
            focusView= Fields[5];
            Fields[5].setError(getString(R.string.error_invalid_password));
            focusView.requestFocus();
            return;
        }
        if(!isValidAge(Fields[3])){
            focusView=Fields[3];
            Fields[3].setError(getString(R.string.errro_invalid_age));
            focusView.requestFocus();
            return;
        }
        if(!isValidSex(Fields[2])) {
            focusView = Fields[2];
            Fields[2].setError(getString(R.string.error_invalid_sex));
            focusView.requestFocus();
            return;
        }

        showProgress(true);

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, Values[0]);
        values.put(KEY_LNAME, Values[1]);
        values.put(KEY_SEX, Values[2]);
        values.put(KEY_AGE, Values[3]);
        values.put(KEY_EMAIL, Values[4]);
        values.put(KEY_PASS, Values[5]);
        values.put(KEY_STREET, Values[6]);
        values.put(KEY_NUMBER, Values[7]);
        values.put(KEY_COL, Values[8]);
        values.put(KEY_CITY, Values[9]);
        values.put(KEY_STATE, Values[10]);
        values.put(KEY_CP, Values[11]);

        mRegisterTask = new UserRegisterTask(values);
        mRegisterTask.execute((Void) null);




    }

    private void callInit(){
        Intent intent = new Intent(this, layout_login.class);
        startActivity(intent);
        finish();
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final ContentValues data;
        UserRegisterTask(ContentValues content) {
            data= content;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return false;
            }
            FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            long newRowId = 0;
            newRowId = db.insert(TABLE_USERS, null, data);
            if(newRowId != 0){
                return true;
            }else return false;


        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRegisterTask = null;
            showProgress(false);
            if (success) {
                callInit();
            }
        }

        @Override
        protected void onCancelled() {
            mRegisterTask = null;
            showProgress(false);
        }
    }





    /* Functions to validate fields */
    private boolean isEmpty(EditText field){
        return TextUtils.isEmpty(field.getText().toString());
    }

    private boolean isValidEmail(EditText Email){
        String SEmail = Email.getText().toString();
        return SEmail.contains("@");
    }

    private boolean isPasswordValid(EditText password) {
        String SPassword = password.getText().toString();
        return SPassword.length() > 8;
    }
    private  boolean isValidAge(EditText Age){
        String SAge = Age.getText().toString();
        return SAge.length() < 3;
    }
    private boolean isValidSex(EditText Sex){
        String SSex = Sex.getText().toString();
        return SSex.equals("M")||SSex.equals("F");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



}

