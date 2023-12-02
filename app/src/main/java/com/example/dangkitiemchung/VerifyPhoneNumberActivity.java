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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
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
FirebaseAuth mAuth;
private AlertDialog alertDialog;
private Handler handler = new Handler();
public static  final String TAG= VerifyPhoneNumberActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phonenumber);
        Window window = getWindow();
        // Ẩn thanh trạng thái (status bar)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        btn_continue= (Button) findViewById(R.id.btn_continue);
        txt_phone=(TextView) findViewById(R.id.txt_phone);
        mAuth=FirebaseAuth.getInstance();

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
                        } else {
                            String strPhoneNumber = txt_phone.getText().toString().trim();
                            // Số điện thoại không tồn tại, xử lý tại đây
                            if (strPhoneNumber.startsWith("0")) {
                                strPhoneNumber = "+84" + strPhoneNumber.substring(1);
                            }
                            showAlertDialog();
                            onClickVerityPhoneNumber(strPhoneNumber);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Xử lý khi có lỗi truy vấn đến cơ sở dữ liệu
                        Log.e("FirebaseError", databaseError.getMessage());
                    }
                });

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
                                Toast.makeText(VerifyPhoneNumberActivity.this,"Số điện thoại không hợp lệ",
                                Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                goToEnterOTPActivity(strPhoneNumber,verificationId);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void goToRegisterPersonalProfileActivity(String phoneNumber){
        Intent intent = new Intent(VerifyPhoneNumberActivity.this, RegisterPersonalProfileActivity.class);
        intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);
    }
    private void goToEnterOTPActivity(String strPhoneNumber, String verificationId) {
        Intent intent = new Intent(this, EnterOTPActivity.class);
        intent.putExtra("phone_number",strPhoneNumber);
        intent.putExtra("verification_id",verificationId);
        startActivity(intent);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            goToRegisterPersonalProfileActivity(user.getPhoneNumber());
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyPhoneNumberActivity.this,"Số điện thoại không hợp lệ",Toast.LENGTH_SHORT).show();
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

}