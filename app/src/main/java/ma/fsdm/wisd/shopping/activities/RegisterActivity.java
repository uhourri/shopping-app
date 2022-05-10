package ma.fsdm.wisd.shopping.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ma.fsdm.wisd.shopping.R;
import ma.fsdm.wisd.shopping.entities.User;
import ma.fsdm.wisd.shopping.services.DatabaseManager;

public class RegisterActivity extends AppCompatActivity {
    EditText firstNameText, lastNameText, emailText, loginText, passwordText;
    Button loginButton, registerButton;

    DatabaseManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        emailText = findViewById(R.id.emailText);
        loginText = findViewById(R.id.loginText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        database = new DatabaseManager(this, "shopping.db", null, 1);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setFirstName(firstNameText.getText().toString());
                user.setLastName(lastNameText.getText().toString());
                user.setEmail(emailText.getText().toString());
                user.setUsername(loginText.getText().toString());
                user.setPassword(passwordText.getText().toString());

                if(database.register(user)){
                    Toast.makeText(RegisterActivity.this, "The new user added :)", Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "The user already exists :(", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
