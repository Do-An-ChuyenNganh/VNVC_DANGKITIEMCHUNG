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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
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

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class Enter_Password extends AppCompatActivity {

    TextView txt_password;
    Button btn_login;
    String mPhoneNumber="";
    TextView txt_forgetPassword;
    FirebaseAuth mAuth;
    private AlertDialog alertDialog;
    private Handler handler = new Handler();
    public static  final String TAG= EnterOTPActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        Window window = getWindow();
        // Ẩn thanh trạng thái (status bar)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        txt_forgetPassword= (TextView)  findViewById(R.id.txt_forgetPassword);
        txt_password = (TextView) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        mAuth=FirebaseAuth.getInstance();



        Intent intent = getIntent();
        mPhoneNumber  = intent.getStringExtra("phone_number");

        btn_login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if (intent != null) {
                 String password  = txt_password.getText().toString().trim();
                 if (mPhoneNumber != null) {
                     checkPassword(mPhoneNumber, password);
                 }
             }
         }
     });
        txt_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhoneNumber.startsWith("0")) {
                    mPhoneNumber = "+84" + mPhoneNumber.substring(1);
                }
                System.out.println("Số điện thoại sau khi đổi mã vùng-----------------------" + mPhoneNumber);
                onClickVerityPhoneNumber(mPhoneNumber);
            }
        });
    }
    public void onClickVerityPhoneNumber(String strPhoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhoneNumber)       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(Enter_Password.this,"Yêu cầu không hợp lệ",
                                            Toast.LENGTH_SHORT).show();
                                } else if (e instanceof FirebaseTooManyRequestsException) {
                                    Toast.makeText(Enter_Password.this,"Số lần xác thực vượt quá yêu cầu",
                                            Toast.LENGTH_SHORT).show();
                                } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
                                    Toast.makeText(Enter_Password.this,"Mã Captcha không hợp lệ",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(Enter_Password.this,"Số điện thoại không hợp lệ",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                goToEnterOTPActivity(strPhoneNumber,verificationId);
                            }
                        })           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    public void goToMenuMain(String phoneNumber){
        Intent intent = new Intent(Enter_Password.this, MenuMainActivity.class);
        intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);
    }
    private void goToEnterOTPActivity(String strPhoneNumber, String verificationId) {
        Intent intent = new Intent(this, EnterOTPActivity.class);
        intent.putExtra("phone_number",strPhoneNumber);
        intent.putExtra("verification_id",verificationId);
        String  flag="1";
        intent.putExtra("flag",flag);
        startActivity(intent);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            goToMenuMain(user.getPhoneNumber());
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Enter_Password.this,"Mã xác minh không hợp lệ",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Đang xác thực, vui lòng chờ trong giây lát")
                .setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

        // Đặt một sự kiện đóng AlertDialog sau 2 giây
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissAlertDialog();
            }
        }, 2500);
    }
    private void dismissAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
    private void checkPassword(String phoneNumber, String enteredPassword1) {
        System.out.println("mat khau :" + enteredPassword1);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        databaseReference.orderByChild("UserName").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            boolean kt = true;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Số điện thoại tồn tại trong cơ sở dữ liệu
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String passwordFromDatabase = snapshot.child("PassWord").getValue(String.class);
                        // Kiểm tra mật khẩu
                        if (enteredPassword1.equals(passwordFromDatabase)) {
                            Intent mainIntent = new Intent(Enter_Password.this, MenuMainActivity.class);
                            System.out.println("Nhap dung:---------: "+ phoneNumber );
                            mainIntent.putExtra("phone_number",phoneNumber);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(mainIntent);
                            finish();


                        }
                        else {
                            Toast.makeText(Enter_Password.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi truy vấn đến cơ sở dữ liệu
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });

        // Giả định: Trả về false nếu không tìm thấy số điện thoại trong cơ sở dữ liệu

    }

}