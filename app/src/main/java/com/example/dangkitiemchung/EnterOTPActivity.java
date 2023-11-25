package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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

import java.util.concurrent.TimeUnit;

public class EnterOTPActivity extends AppCompatActivity {
TextView txt_sendOTP,txt_sendOTPAgain,txt_OTP1,txt_OTP2,txt_OTP3,txt_OTP4,txt_OTP5,txt_OTP6, txt_contentSend;
String mPhoneNumber,mVerificationId;
FirebaseAuth mAuth;
private TextView[] editTexts;
private boolean clearedLastEditText = false;
private AlertDialog alertDialog;
private Handler handler = new Handler();
private  PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    public static  final String TAG= EnterOTPActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        hideActionBar();
        addControl();
        getDataIntent();
        setTextContent();
        inputOTP();

        mAuth = FirebaseAuth.getInstance();


        txt_sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str =  getTextOTP();
                onClickSenOTP(str);
              //  showAlertDialog();
            }
        });

        txt_sendOTPAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTextOTP();
                onClickSenOTPAgain();

            }
        });



    }
    public void inputOTP()
    {
        editTexts = new TextView[]{txt_OTP1, txt_OTP2, txt_OTP3, txt_OTP4, txt_OTP5, txt_OTP6};
        for (int i = 0; i < editTexts.length; i++) {
            final int currentIndex = i;
            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                    // Khi có ký tự được nhập, chuyển sự chú ý đến ô tiếp theo (nếu có)
                    if (count > 0 && currentIndex < editTexts.length - 1) {

                        editTexts[currentIndex + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // Kiểm tra nếu ô cuối cùng được xóa, xóa nội dung của tất cả các ô

                    if (currentIndex == editTexts.length - 1 && editable.length() == 0 ) {
                        for (TextView editText : editTexts) {
                            txt_OTP1.setText("");
                            txt_OTP2.setText("");
                            txt_OTP3.setText("");
                            txt_OTP4.setText("");
                            txt_OTP5.setText("");
                        }

                      txt_OTP1.requestFocus();
                      //txt_OTP1.setText("");
                    }

                }
            });
        }
    }

    public String getTextOTP(){
        String strOTP = txt_OTP1.getText().toString().trim() + txt_OTP2.getText().toString().trim()
                + txt_OTP3.getText().toString().trim()+ txt_OTP4.getText().toString().trim()
                +txt_OTP5.getText().toString().trim() + txt_OTP6.getText().toString().trim();
        return strOTP;
      //  System.out.println("OTP:" +strOTP);
    }

    public  void setTextContent(){
        txt_contentSend.setText("Nhập mã xác thực (OTP) được gửi đến số điện thoại \n" + mPhoneNumber);
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Đang xác thực, chờ trong giây lát")
                .setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

        // Đặt một sự kiện đóng AlertDialog sau 2 giây
         handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissAlertDialog();
            }
        }, 2000);
    }
    private void dismissAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public  void hideActionBar(){
        Window window = getWindow();
        // Ẩn thanh trạng thái (status bar)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

    }
    private  void addControl(){
        txt_sendOTP= (TextView) findViewById(R.id.txt_sendOTP);
        txt_OTP1= (TextView) findViewById(R.id.txt_OTP1);
        txt_OTP2= (TextView) findViewById(R.id.txt_OTP2);
        txt_OTP3= (TextView) findViewById(R.id.txt_OTP3);
        txt_OTP4= (TextView) findViewById(R.id.txt_OTP4);
        txt_OTP5= (TextView) findViewById(R.id.txt_OTP5);
        txt_OTP6= (TextView) findViewById(R.id.txt_OTP6);
        txt_contentSend= (TextView) findViewById(R.id.txt_contentSend);
        txt_sendOTPAgain= (TextView) findViewById(R.id.txt_senOTPAgain);
    }

    private  void onClickSenOTP(String strOTP){

        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("mã kiểm tra mVerificationId " + mVerificationId);
        System.out.println(" chuỗi strOTP " + strOTP);
        System.out.println("------------------------------------------------------------------------------------------------");
        txt_sendOTP.setTextColor(Color.RED);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, strOTP);
        signInWithPhoneAuthCredential(credential);
    }

    private  void onClickSenOTPAgain(){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        .setForceResendingToken(mForceResendingToken)
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(EnterOTPActivity.this,"OTP không hợp lệ",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                mVerificationId=verificationId;
                                mForceResendingToken=forceResendingToken;




                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private  void getDataIntent(){

            mPhoneNumber=getIntent().getStringExtra("phone_number");
            mVerificationId=getIntent().getStringExtra("verification_id");
//        System.out.println( "verification_Id" + mVerificationId);
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
                            Toast.makeText(EnterOTPActivity.this,"Thành công !",Toast.LENGTH_SHORT).show();
                            goToRegisterPersonalProfileActivity(user.getPhoneNumber());

                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(EnterOTPActivity.this,"OTP không hợp lệ !",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    public void goToRegisterPersonalProfileActivity(String phoneNumber){
        Intent intent = new Intent(EnterOTPActivity.this, RegisterPersonalProfileActivity.class);
        intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);
    }



    //--
}