package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumberActivity extends AppCompatActivity {
Button btn_continue;
TextView txt_phone;
FirebaseAuth mAuth=FirebaseAuth.getInstance();;
private AlertDialog alertDialog;
ProgressBar processBar;
private Handler handler = new Handler();
public static  final String TAG= VerifyPhoneNumberActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phonenumber);
        Window window = getWindow();

//        FirebaseApp.initializeApp(/*context=*/ this);
//        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
//        firebaseAppCheck.installAppCheckProviderFactory(SafetyNetAppCheckProviderFactory.getInstance());


        // Ẩn thanh trạng thái (status bar)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        btn_continue= (Button) findViewById(R.id.btn_continue);
        txt_phone=(TextView) findViewById(R.id.txt_phone);
        processBar = (ProgressBar) findViewById(R.id.processBar);

        ///
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPhoneNumber = txt_phone.getText().toString().trim();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TaiKhoan");
                databaseReference.orderByChild("UserName").equalTo(strPhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Số điện thoại tồn tại, chuyển hướng sang activity nhập mật khẩu
                            String strPhoneNumber = txt_phone.getText().toString().trim();
                            Intent intent = new Intent(VerifyPhoneNumberActivity.this, Enter_Password.class);
                            intent.putExtra("phone_number",strPhoneNumber);
                            startActivity(intent);
                        } else { // Số điện thoại mới
                            String strPhoneNumber = txt_phone.getText().toString().trim();
                            if (strPhoneNumber.startsWith("0")) {
                                strPhoneNumber = "+84" + strPhoneNumber.substring(1);
                            }
                            goToEnterOTPActivity(strPhoneNumber);

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("FirebaseError", databaseError.getMessage());
                    }
                });

            }
        });
    }

    private void goToEnterOTPActivity(String strPhoneNumber) {
        Intent intent = new Intent(this, EnterOTPActivity.class);
        intent.putExtra("phone_number",strPhoneNumber);
        startActivity(intent);
    }


}