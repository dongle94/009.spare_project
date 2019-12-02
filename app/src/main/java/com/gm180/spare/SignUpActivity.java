package com.gm180.spare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";

    private EditText et_Email;
    private EditText et_Password;
    private EditText et_Pwdchk;
    private EditText et_Birthday;
    private EditText et_PhoneNum;
    private EditText et_Name;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //View
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        et_Birthday = findViewById(R.id.et_Birthday);
        et_Name = findViewById(R.id.et_Name);
        et_PhoneNum = findViewById(R.id.et_PhoneNum);
        et_Pwdchk = findViewById(R.id.et_confirmPassword);


        //Button
        findViewById(R.id.bt_signUpComplete).setOnClickListener(this);
        findViewById(R.id.bt_certify).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount(String email, String password, String birthday, String name, String phonenumber, String pwdchk) {
        Log.d(TAG, "createAccount start!!!" );
        if (!validateForm()) {
            Toast.makeText(SignUpActivity.this,"다시한번 확인해 주세요!",Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog();
        Log.d(TAG, "Start createUserWithEmailAndPassword!!!");
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            /*
                            DATABASE 등록과정필요
                             */
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "회원가입이 실패했습니다. 다시 시도해주세요.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        // empty check
        String email = et_Email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            et_Email.setError("Required.");
            valid = false;
        } else {
            et_Email.setError(null);
        }

        String pwd = et_Password.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            et_Password.setError("Required.");
            valid = false;
        } else {
            et_Password.setError(null);
        }

        // empty check + same pwd check
        String pwdchk = et_Pwdchk.getText().toString();
        if (TextUtils.isEmpty(pwdchk)) {
            et_Pwdchk.setError("Required.");
            valid = false;
        } else if (!pwd.equals(pwdchk)){
            et_Pwdchk.setError("Not Same Password!");
            valid = false;
        } else {
            et_Pwdchk.setError(null);
        }

        String name = et_Name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            et_Name.setError("Required.");
            valid = false;
        } else {
            et_Name.setError(null);
        }

        String bd = et_Birthday.getText().toString();
        if (TextUtils.isEmpty(bd)) {
            et_Birthday.setError("Required.");
            valid = false;
        } else {
            et_Birthday.setError(null);
        }

        String phonenum = et_PhoneNum.getText().toString();
        if (TextUtils.isEmpty(phonenum)) {
            et_PhoneNum.setError("Required.");
            valid = false;
        } else {
            et_PhoneNum.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            setResult(1);
            finish();
        } else {

        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_signUpComplete) {
            Log.d(TAG, "bt_signUpComplete clicked!!!");

            String mEmail = et_Email.getText().toString();
            String mPassword = et_Password.getText().toString();
            String mBirthday = et_Birthday.getText().toString();
            String mName = et_Name.getText().toString();
            String mPhoneNum = et_PhoneNum.getText().toString();
            String mPwdchk = et_Pwdchk.getText().toString();
            createAccount(mEmail, mPassword, mBirthday,mName, mPhoneNum, mPwdchk);




        } else if(view.getId() == R.id.bt_certify) {

        }
    }
}
