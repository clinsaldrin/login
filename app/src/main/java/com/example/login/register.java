package com.example.login;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private EditText Email;
    private Button Register;
    private TextView Signin;
    private FirebaseAuth firebaseauth;
    private ProgressDialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Name = (EditText) findViewById(R.id.etname);
        Password = (EditText) findViewById(R.id.etpassword);
        Email = (EditText) findViewById(R.id.etemail);
        Register = (Button) findViewById(R.id.btnreg);
        Signin = (TextView) findViewById(R.id.tvregin);
        firebaseauth = FirebaseAuth.getInstance();
        progressdialog = new ProgressDialog(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result = false;

                String name = Name.getText().toString();
                String password = Password.getText().toString();
                String email = Email.getText().toString();

                if (((String) name).isEmpty() || ((String) password).isEmpty() || ((String) email).isEmpty()) {

                    progressdialog.dismiss();

                    Toast.makeText(register.this, "fill all", Toast.LENGTH_SHORT).show();

                }
                else {
                    result = true;
                }
                if (result == true) {

                    String user_email = Email.getText().toString().trim();
                    String user_password = Password.getText().toString().trim();
                    progressdialog.setMessage("Sending E-Mail verification");
                    progressdialog.show();


                    firebaseauth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                FirebaseUser firebaseuser = firebaseauth.getInstance().getCurrentUser();
                                if (firebaseuser != null){
                                    firebaseuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressdialog.dismiss();
                                                Toast.makeText(register.this,"Succefully registered, please verify", Toast.LENGTH_SHORT).show();
                                                firebaseauth.signOut();
                                                startActivity(new Intent(register.this, login.class));
                                                finish();
                                            }
                                            else {
                                                progressdialog.dismiss();
                                                Toast.makeText(register.this,"Email hasn't been sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }
                            else{
                                progressdialog.dismiss();
                                Toast.makeText(register.this, "Failed", Toast.LENGTH_SHORT).show();

                            }



                        }
                    });

                }
            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, login.class));
                finish();

            }
        });

    }

}
