package com.example.drawerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.example.drawerapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    View headerView;
    ImageView headerImage;
    TextView headerTitle, headerGmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*============================== Hooks ===================================*/
        headerView = binding.navigationView.getHeaderView(0);
        headerImage = headerView.findViewById(R.id.headerImage);
        headerTitle = headerView.findViewById(R.id.headerTitle);
        headerGmail = headerView.findViewById(R.id.headerGmail);


        headerTitle.setText("TRADING");
        headerGmail.setText("Patience & Consistency\nare the key to success");

    /*================================ Control DrawerLayout Menu ================================*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, binding.drawerLayout, binding.toolBar, R.string.drawer_close, R.string.drawer_open
        );
        binding.drawerLayout.addDrawerListener(toggle);

        /*================================== Control ToolBar Menu =====================================*/
        binding.toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.shareBarID) {
                    shareApp(MainActivity.this);
                }
                return false;
            }
        });
        /*================================== Control Navigation Drawer Menu =====================================*/
        binding.navigationView.bringToFront();
        binding.navigationView.setCheckedItem(R.id.home_ID);

//        Fragment fragment = new First_TabFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home_ID) {
                    Toast.makeText(MainActivity.this, "Welcome to Home item", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new First_TabFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.rateUs) {
                    rateUsOnGooglePlay();
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.share_ID) {
                    shareApp(MainActivity.this);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.visitus_ID) {
                    Fragment fragment = new Item_Fragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
                    Item_Fragment.WEB_URL = "https://www.xzoona.com/";
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });
        /*==================================== Fragment Controll ===============================*/
        Fragment firstFragment = new First_TabFragment();
        FragmentTransaction firstFragmentTransaction = getSupportFragmentManager().beginTransaction();
        firstFragmentTransaction.replace(R.id.frameLayout, firstFragment).commit();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                Fragment fragment = null;
                if (tabPosition == 0) {
                    fragment = new First_TabFragment();
                } else if (tabPosition == 1) {
                    fragment = new Second_TabFragment();
                } else {
                    fragment = new Third_TabFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /*================================== RATe APP ======= ======================*/
    private void rateUsOnGooglePlay() {

        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
    /*================================== Drawer BackPress Control ======================*/
    @Override
    public void onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*================================== Internet Connection Checker =============================*/
    public static boolean isConnected(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        return (wifiConn != null && mobileConn.isConnected() || mobileConn != null && wifiConn.isConnected());
    }
    /*==========================**======== Share My App ============================********=*/
    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
}