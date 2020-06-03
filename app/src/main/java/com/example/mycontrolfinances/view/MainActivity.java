package com.example.mycontrolfinances.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import com.example.mycontrolfinances.Fragments.HomeFragment;
import com.example.mycontrolfinances.R;
import com.example.mycontrolfinances.database.CarregamentoInicial;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    DrawerLayout mDrawerLayout ;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.drawerLayout,new Fragment()).commit();
        }

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        CarregamentoInicial carrega = new CarregamentoInicial();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showFragments(new HomeFragment());


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.home :{
                showFragments(new HomeFragment());
                break;
            }
            case R.id.terceiros :{
              //  showFragments(new TerceirosFragment());
                startActivity(new Intent(MainActivity.this , listaTerceirosActivity.class));
                //showFragments(new AddTerceirosFragment());
                break;
            }
            case R.id.cartao :{
                startActivity(new Intent(MainActivity.this , ListCartaoActivity.class));
               // showFragments(new CartaoFragment());
                break;
            }
            case R.id.contaCorrente :{
                startActivity(new Intent(MainActivity.this , ListContaCorrenteActivity.class));
                // showFragments(new CartaoFragment());
                break;
            }
            case R.id.cadTerceiros :{
                startActivity(new Intent(MainActivity.this , listaTerceirosActivity.class));
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    private void showFragments(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout,fragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}
