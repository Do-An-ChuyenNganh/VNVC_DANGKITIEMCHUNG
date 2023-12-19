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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class EnterOTPActivity extends AppCompatActivity {
TextView txt_sendOTP,txt_sendOTPAgain,txt_OTP1,txt_OTP2,txt_OTP3,txt_OTP4,txt_OTP5,txt_OTP6, txt_contentSend,txt_changePhoneNumber,txt_hotline;
String mPhoneNumber,mVerificationId;
ProgressBar processBar2;
FirebaseAuth   mAuth = FirebaseAuth.getInstance();;
private TextView[] editTexts;
private boolean clearedLastEditText = false;
private AlertDialog alertDialog;
String  flag="0";
Long timeoutSeconds= 60L;
private Handler handler = new Handler();
private  PhoneAuthProvider.ForceResendingToken mForceResendingToken;
public static  final String TAG= EnterOTPActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        hideActionBar();
        addControl();
        mPhoneNumber=getIntent().getStringExtra("phone_number");
        flag= getIntent().getStringExtra("flag");

        setTextContent();
        inputOTP();
        onClickSenOTP(mPhoneNumber,false);

        txt_sendOTP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("flag có null hay không" + flag);
                if(flag != null) {
                    String stringOTP = getTextOTP();
                    if(stringOTP.trim().isEmpty()){
                        Toast.makeText(EnterOTPActivity.this,"Vui lòng nhập OTP !",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, stringOTP);
                    signInWithPhoneAuthCredential2(credential);
                    //
                }
                else{
                    String stringOTP = getTextOTP();
                    if(stringOTP.trim().isEmpty()){
                       Toast.makeText(EnterOTPActivity.this,"Vui lòng nhập OTP !",Toast.LENGTH_SHORT).show();
                       return;
                   }
                   else {

                       PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, stringOTP);
                       signInWithPhoneAuthCredential(credential);
                   }
                }
            }
        });
        txt_sendOTPAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSenOTP(mPhoneNumber,true);
            }
        });
        txt_changePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnterOTPActivity.this, VerifyPhoneNumberActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        txt_hotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    }

    public  void setTextContent(){
        txt_contentSend.setText("Nhập mã xác thực (OTP) được gửi đến số điện thoại \n" + mPhoneNumber);
    }
//    private void showAlertDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Đang xác thực, chờ trong giây lát")
//                .setCancelable(false);
//
//        alertDialog = builder.create();
//        alertDialog.show();
//
//        // Đặt một sự kiện đóng AlertDialog sau 2 giây
//         handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dismissAlertDialog();
//            }
//        }, 2000);
//    }
//    private void dismissAlertDialog() {
//        if (alertDialog != null && alertDialog.isShowing()) {
//            alertDialog.dismiss();
//        }
//    }

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
        processBar2= (ProgressBar) findViewById(R.id.processBar2);
        txt_changePhoneNumber= (TextView) findViewById(R.id.txt_changePhoneNumber);
        txt_hotline= (TextView) findViewById(R.id.txt_hotline);
    }


    public void startResendTimer(){
        txt_sendOTPAgain.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                txt_sendOTPAgain.setText("Gửi lại OTP trong " + timeoutSeconds+ " giây");
                if(timeoutSeconds<=0)
                {
                    timeoutSeconds=60L;
                    timer.cancel();
                    runOnUiThread(()->{
                        txt_sendOTPAgain.setEnabled(true);
                    });
                }
            }
        },0,1000);
    }

    private  void onClickSenOTP(String phoneNumber, Boolean isResend){
        startResendTimer();
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(timeoutSeconds,TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                      //  signInWithPhoneAuthCredential(phoneAuthCredential);
                        setInProgress(false);
                    }
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(EnterOTPActivity.this,"Thất bại !",Toast.LENGTH_SHORT).show();
                        setInProgress(false);
                    }
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        mVerificationId=s;
                        mForceResendingToken=forceResendingToken;
                        Toast.makeText(EnterOTPActivity.this," Đã gửi OTP  !",Toast.LENGTH_SHORT).show();
                        setInProgress(false);
                    }
                });
                if(isResend){
                        PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(mForceResendingToken).build());
                }
                else{
                    PhoneAuthProvider.verifyPhoneNumber(builder.build());
                }
    }

    void setInProgress(boolean inProgress)
    {
        if(inProgress)
        {
            processBar2.setVisibility(View.VISIBLE);
            txt_sendOTP.setVisibility(View.GONE);
        }
        else{
            processBar2.setVisibility(View.GONE);
            txt_sendOTP.setVisibility(View.VISIBLE);
        }
    }

    private void signInWithPhoneAuthCredential2(PhoneAuthCredential credential) {
        setInProgress(true);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(EnterOTPActivity.this,"Thành công !",Toast.LENGTH_SHORT).show();
                            goToSetPassWord(user.getPhoneNumber());
                        } else {
                            setInProgress(false);
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(EnterOTPActivity.this,"OTP không hợp lệ !",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        setInProgress(true);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(EnterOTPActivity.this,"Xác thực thành công !",Toast.LENGTH_SHORT).show();
                            goToRegisterPersonalProfileActivity(user.getPhoneNumber());
                        } else {
                            setInProgress(false);
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(EnterOTPActivity.this,"OTP không hợp lệ !",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void goToRegisterPersonalProfileActivity(String phoneNumber){
        Intent intent = new Intent(EnterOTPActivity.this, RegisterPersonalProfileActivity.class);
        intent.putExtra("phone_number",phoneNumber);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void goToSetPassWord(String phoneNumber) {
        Intent intent = new Intent(EnterOTPActivity.this, SetPasswordActivity.class);
        intent.putExtra("phone_number", phoneNumber);
        startActivity(intent);
    }
}