package com.example.cookingeasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cookingeasy.fragment.AboutFragment;
import com.example.cookingeasy.fragment.HomeFragment;
import com.example.cookingeasy.fragment.PersonFragment;
import com.example.cookingeasy.fragment.SearchFragment;
import com.example.cookingeasy.fragment.SettingsFragment;
import com.example.cookingeasy.fragment.ShareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        //navigation drawer

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START); // Đóng drawer nếu nó đang mở
                } else {
                    // Thực hiện hành động back mặc định
                    // Gọi phương thức này nếu bạn muốn quay lại hoặc đóng ứng dụng.
                    if (isEnabled()) {
                        setEnabled(false);
                        onBackPressed(); // Gọi hành vi back mặc định
                    }
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        if (savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_home);
//        }


        //bottom navigation

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new HomeFragment()).commit();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        fragmentTransaction.addToBackStack(null);
        bottomNavigationView.setOnItemSelectedListener(item->{
            if(item.getItemId() == R.id.bottom_home){
                FragmentTransaction replaceFragment = fragmentManager.beginTransaction();
                replaceFragment.replace(R.id.fragment_container, new HomeFragment()).commit();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                replaceFragment.addToBackStack(null);
                return true;
            } else if (item.getItemId() == R.id.bottom_search) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                FragmentManager replaceFragment = getSupportFragmentManager();
                FragmentTransaction transaction = replaceFragment.beginTransaction();
                transaction.replace(R.id.fragment_container, new SearchFragment()).commit();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                fragmentTransaction.addToBackStack(null);
                return true;
            } else if(item.getItemId() == R.id.bottom_settings) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            }
            else if(item.getItemId() == R.id.bottom_person) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonFragment()).commit();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            }
            return false;
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id_frag = item.getItemId();
        if (id_frag == R.id.nav_home){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (id_frag == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
        } else if (id_frag == R.id.nav_share) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShareFragment()).commit();
        } else if (id_frag == R.id.nav_about){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
        }
        else {
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}