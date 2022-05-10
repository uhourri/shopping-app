package ma.fsdm.wisd.shopping.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ma.fsdm.wisd.shopping.R;
import ma.fsdm.wisd.shopping.entities.User;
import ma.fsdm.wisd.shopping.services.DatabaseManager;

public class LoginActivity extends AppCompatActivity {

    EditText loginText, passwordText;
    Button loginButton, registerButton;

    DatabaseManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginText = findViewById(R.id.loginText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        database = new DatabaseManager(this, "shopping.db", null, 1);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(database.login(loginText.getText().toString(), passwordText.getText().toString())){
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    User user = database.searchUserByUserName(loginText.getText().toString());

                    editor.putInt("idUser", user.getId());
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "You're welcome in your market :)", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Login or Password is incorrect :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



    }
}