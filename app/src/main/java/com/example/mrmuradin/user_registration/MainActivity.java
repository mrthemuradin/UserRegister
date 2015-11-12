package com.example.mrmuradin.user_registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] countries;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private View mSignUp;
    private View mShowUsers;
    private Spinner spinner;
    private RadioGroup rgSex;
    private String country;
    private String sex;
    private DBHelper dbHelper;
    private ImageView ivCrossUsername;
    private ImageView ivCrossPassword1;
    private ImageView ivCrossPassword2;
    private String userName;
    private String password;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);

        ivCrossUsername = (ImageView) findViewById(R.id.iv_redCross_username);
        ivCrossUsername.setImageResource(R.drawable.redcross);

        ivCrossPassword1 = (ImageView) findViewById(R.id.iv_redCross_password1);
        ivCrossPassword1.setImageResource(R.drawable.redcross);

        ivCrossPassword2 = (ImageView) findViewById(R.id.iv_redCross_password2);
        ivCrossPassword2.setImageResource(R.drawable.redcross);

        spinner = (Spinner) findViewById(R.id.spinner_Country);

        rgSex = (RadioGroup) findViewById(R.id.rg_Sex);

        mSignUp = findViewById(R.id.view_sign_up);
        mSignUp.setOnClickListener(this);

        mShowUsers = findViewById(R.id.view_show_users);
        mShowUsers.setOnClickListener(this);

        countries = getResources().getStringArray(R.array.countries);

        dbHelper = new DBHelper(this);

        etUserName.addTextChangedListener(new InputFieldChecker(etUserName, ivCrossUsername));
        etPassword.addTextChangedListener(new InputFieldChecker(etPassword, ivCrossPassword1));
        etConfirmPassword.addTextChangedListener(new InputFieldChecker(etConfirmPassword, ivCrossPassword2, etPassword));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                country = countries[position].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public void onClick(View v) {
         userName = etUserName.getText().toString();
         password = etPassword.getText().toString();
         confirmPassword = etConfirmPassword.getText().toString();
        switch (rgSex.getCheckedRadioButtonId()) {
            case R.id.rb_Male:
                sex = "Male";
                break;
            case R.id.rb_Female:
                sex = "Female";
                break;
        }

        switch (v.getId()) {
            case R.id.view_sign_up:
                if (!userName.isEmpty()) {
                    if (password.equals(confirmPassword) && (!password.isEmpty())) {
                        dbHelper.saveUserInfo(userName, password, sex, country);
                    } else {
                        etPassword.requestFocus();
                        Toast.makeText(getBaseContext(), "Password mismatch or password field is empty", Toast.LENGTH_SHORT).show();
                        break;
                    }
                } else {
                    etUserName.requestFocus();
                    Toast.makeText(getBaseContext(), "Input username", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case R.id.view_show_users:
                dbHelper.printUserInfo();
                break;
        }
    }

    class InputFieldChecker implements TextWatcher {

        private EditText inputText;
        private EditText shouldEqualsWithText;
        private ImageView errorImage;

        public InputFieldChecker(EditText inputText, ImageView errorImage) {
            this(inputText, errorImage, null);
        }

        public InputFieldChecker(EditText inputText, ImageView errorImage, EditText shouldEqualsWithText){
            this.inputText = inputText;
            this.errorImage = errorImage;
            this.shouldEqualsWithText = shouldEqualsWithText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String field = inputText.getText().toString();

            if (shouldEqualsWithText != null) {
                if (!shouldEqualsWithText.getText().toString().equals(field)) {
                    errorImage.setVisibility(View.VISIBLE);
                    return;
                }
            }
            if (!field.isEmpty()) {
                errorImage.setVisibility(View.INVISIBLE);
            }
            else {
                errorImage.setVisibility(View.VISIBLE);
            }

        }
    }
}
