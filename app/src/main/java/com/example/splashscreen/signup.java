package com.example.splashscreen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class signup extends AppCompatActivity {
    EditText usermail,userpassword,confpass,rdate;
    Button registration;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextView signin;
    EditText edate ;
    DatePickerDialog.OnDateSetListener setListener ;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        edate = findViewById(R.id.date_picker_actions);
        signin=(TextView)findViewById(R.id.signin);
        usermail = findViewById(R.id.usermail);
        userpassword=findViewById(R.id.userpass);
        confpass=findViewById(R.id.userconfpass);
        rdate=findViewById(R.id.date_picker_actions);
        registration = findViewById(R.id.regist);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        edate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        signup.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(signup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1 ;
                        String date = dayOfMonth +"/"+ month +"/"+ year;
                        edate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(signup.this,MainActivity.class);
                startActivity(j);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = usermail.getText().toString().trim();
                String password = userpassword.getText().toString().trim();

                String confirmationpassword = confpass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    usermail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    userpassword.setError("password is Required.");
                    return;
                }
                if (password.length()<5){
                    userpassword.setError("Password must be minimum 5 characters");
                }
                if (TextUtils.isEmpty(confirmationpassword)) {
                    confpass.setError("confirmend password");
                }
                if (password.equals(confirmationpassword)){
                    progressBar.setVisibility(view.VISIBLE);
                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(signup.this,"User created , Login to continue",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(signup.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(signup.this,"wrong password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
