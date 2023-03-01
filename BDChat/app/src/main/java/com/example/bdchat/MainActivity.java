package com.example.bdchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bdchat.Adapter.FragmentAdapter;
import com.example.bdchat.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding mainBinding;

   FirebaseAuth auth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        auth=FirebaseAuth.getInstance();

        mainBinding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        mainBinding.tablayout.setupWithViewPager(mainBinding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.setings:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.groupChat:

            case R.id.logout:
                auth.signOut();
                Intent intent=new Intent(MainActivity.this,SingInActivity.class);
                startActivity(intent);
                break;
        }
        
        
        return super.onOptionsItemSelected(item);
    }
}