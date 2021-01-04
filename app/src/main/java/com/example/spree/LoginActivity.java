        package com.example.spree;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;

        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.spree.Models.Users;
        import com.example.spree.Prevalent.Prevalent;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import com.rey.material.widget.CheckBox;


        import io.paperdb.Paper;

        public class LoginActivity extends AppCompatActivity {

            private EditText inputPhoneNumber, inputPassword;
            private Button loginButton;
            private ProgressDialog loadingDialog;
            private String parentDBName = "Users";

            private TextView adminLink, notAdminLink;

            private CheckBox checkBoxRememberMe;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login);

                inputPhoneNumber = findViewById(R.id.login_phone_number_input);
                inputPassword = findViewById(R.id.login_password_input);

                loginButton = findViewById(R.id.login_btn);
                loadingDialog = new ProgressDialog(this);

                checkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
                Paper.init(this);

                adminLink = findViewById(R.id.admin_panel_link);
                notAdminLink = findViewById(R.id.not_admin_panel_link);

                //onClickListeners

                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginUser();
                    }
                });


                adminLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginButton.setText("Login Admin");
                        adminLink.setVisibility(View.INVISIBLE);
                        notAdminLink.setVisibility(View.VISIBLE);
                        parentDBName = "Admins";
                    }
                });


                notAdminLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginButton.setText("Login");
                        notAdminLink.setVisibility(View.INVISIBLE);
                        adminLink.setVisibility(View.VISIBLE);
                        parentDBName = "Users";
                    }
                });

            }

            private void loginUser() {

                String phone = inputPhoneNumber.getText().toString();
                String password = inputPassword.getText().toString();

                 if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "Please, enter your mobile number.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "Please, enter your password.", Toast.LENGTH_SHORT).show();
                } else {

                     loadingDialog.setTitle("Login Account");
                     loadingDialog.setMessage("Please, wait while we are checking the credentials.");
                     loadingDialog.setCanceledOnTouchOutside(false);
                     loadingDialog.show();

                     allowAccessToUser(phone, password);

                 }

            }

            private void allowAccessToUser(final String phone, final String password) {



                if(checkBoxRememberMe.isChecked()){
                    Paper.book().write(Prevalent.userPhoneKey, phone);
                    Paper.book().write(Prevalent.userPassKey, password);

                }

                final DatabaseReference myRef;
                myRef = FirebaseDatabase.getInstance().getReference();


                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(parentDBName).child(phone).exists()){
                            Users userData = snapshot.child(parentDBName).child(phone).getValue(Users.class);

                            if(userData.getPhone().equals(phone)){
                                if(userData.getPassword().equals(password)){

                                   if(parentDBName.equals("Admins")){
                                       Toast.makeText(LoginActivity.this, "Admin logged in successfully!", Toast.LENGTH_SHORT).show();
                                       loadingDialog.dismiss();

                                       Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                       startActivity(intent);
                                   }

                                   else if (parentDBName.equals("Users")) {
                                       Toast.makeText(LoginActivity.this, "User logged in successfully!", Toast.LENGTH_SHORT).show();
                                       loadingDialog.dismiss();

                                       Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                       Prevalent.currentOnlineUser = userData;



                                       startActivity(intent);
                                       //ActivityCompat.finishAffinity(LoginActivity.this);


                                   }
                                }

                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                    loadingDialog.dismiss();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Account with this "+phone+ " doesn't exist", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }