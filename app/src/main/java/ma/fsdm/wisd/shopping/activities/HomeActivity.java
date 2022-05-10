package ma.fsdm.wisd.shopping.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ma.fsdm.wisd.shopping.R;
import ma.fsdm.wisd.shopping.entities.User;
import ma.fsdm.wisd.shopping.services.DatabaseManager;

public class HomeActivity extends AppCompatActivity {

    TextView headerText, footerText;
    Button exploreButton, commandsButton, logoutButton;
    DatabaseManager database;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        System.out.println("Hello, from Home");

        exploreButton = findViewById(R.id.exploreButton);
        commandsButton = findViewById(R.id.commandsButton);
        logoutButton = findViewById(R.id.logoutButton);
        headerText = findViewById(R.id.headerText);
        footerText = findViewById(R.id.footerText);

        preferences = getApplicationContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = preferences.edit();
        database = new DatabaseManager(this, "shopping.db", null, 1);



        if(preferences.contains("idUser")){
            System.out.println("Test 1");

            User user = database.searchUserById(preferences.getInt("idUser", 0));
            headerText.setText("HOME");
            if(user != null){
                headerText.setText("Hello "+user.getFirstName()+" "+user.getLastName()+", in ur shopping app, try to explore more product.");
                footerText.setText(user.getEmail());
            }

        }else{

            ArrayList<User> users = database.showAllUsers();
            for(User user : users){
                System.out.println(user);
            }

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductsActivity.class);
                startActivity(intent);
            }
        });

        commandsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CommandsActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("idUser");
                editor.apply();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}