package com.example.bankee_mvvm;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.bankee_mvvm.ui.Fragment.Card_Fragment;
import com.example.bankee_mvvm.ui.Fragment.History_Fragment;
import com.example.bankee_mvvm.ui.Fragment.Home_Fragment;
import com.example.bankee_mvvm.ui.Fragment.Profile_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;
    FrameLayout frame_layout;
    FloatingActionButton fAb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottom_nav=findViewById(R.id.bottom_nav);
        frame_layout=findViewById(R.id.frame_layout);
        fAb = findViewById(R.id.favBtn);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Home_Fragment()).commit();

        bottom_nav.setOnItemSelectedListener(item -> {

            Fragment selectedFragment=null;
            boolean isActive=false;

            if(item.getItemId()==R.id.home){

                selectedFragment=new Home_Fragment();
                isActive=true;

            }
            else if(item.getItemId()==R.id.history){

                selectedFragment=new History_Fragment();
                isActive=true;

            }
            else if(item.getItemId()==R.id.my_card){

                selectedFragment=new Card_Fragment();
                isActive=true;

            }
            else if(item.getItemId()==R.id.profile){

                selectedFragment=new Profile_Fragment();
                isActive=true;

            }

            if (selectedFragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();

            }

            return isActive;
        });

    }
}