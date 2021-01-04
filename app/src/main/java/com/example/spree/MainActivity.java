    package com.example.spree;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.View;
    import android.widget.Button;
    import android.widget.Toast;

    import com.example.spree.Models.Users;
    import com.example.spree.Prevalent.Prevalent;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import io.paperdb.Paper;

    public class MainActivity extends AppCompatActivity {

        private Button signUp, signIn;
        private ProgressDialog loadingDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            signIn = findViewById(R.id.main_login_btn);
            signUp = findViewById(R.id.main_sign_up_btn);

            Paper.init(this);

            loadingDialog = new ProgressDialog(this);

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                    startActivity(intent);


                }
            });

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                    startActivity(intent);

                }
            });


            String phone = Paper.book().read(Prevalent.userPhoneKey);
            String password = Paper.book().read(Prevalent.userPassKey);


            if (phone!="" && password!=""){

                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)){
                    allowAccess(phone, password);


                    loadingDialog.setTitle("Already logged in");
                    loadingDialog.setMessage("Please, wait a little while...");
                    loadingDialog.setCanceledOnTouchOutside(false);
                    loadingDialog.show();
                }
            }
        }

        private void allowAccess(final String phone, final String password) {


            final DatabaseReference myRef;
            myRef = FirebaseDatabase.getInstance().getReference();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Users").child(phone).exists()){
                        Users userData = snapshot.child("Users").child(phone).getValue(Users.class);

                        if(userData.getPhone().equals(phone)){
                            if(userData.getPassword().equals(password)){

                                Toast.makeText(MainActivity.this, "Already logged in!", Toast.LENGTH_SHORT).show();
                                loadingDialog.dismiss();

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = userData;

                                startActivity(intent);

                            }

                            else
                            {
                                Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                loadingDialog.dismiss();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Account with this "+phone+ " doesn't exist", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }