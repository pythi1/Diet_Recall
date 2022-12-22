package com.project.dietrecall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.dietrecall.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class home extends AppCompatActivity {
Timer t;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i1 = new Intent(home.this, RegisterActivity.class);
                startActivity(i1);
                Log.d("homeactivity","home gone");
                finish();
            }
        },2000);


        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent i1 = new Intent(home.this, MainActivity.class);
                            startActivity(i1);
                            Log.d("homeactivity","home gone");
                            finish();
                        }
                    },2000);

                }
                else
                {
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent i1 = new Intent(home.this, LoginActivity.class);
                            startActivity(i1);
                            Log.d("homeactivity","home gone");
                            finish();
                        }
                    },2000);

                }
            }


        };

    }
}
