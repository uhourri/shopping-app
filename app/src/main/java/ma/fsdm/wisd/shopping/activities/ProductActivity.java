package ma.fsdm.wisd.shopping.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ma.fsdm.wisd.shopping.R;
import ma.fsdm.wisd.shopping.entities.Command;
import ma.fsdm.wisd.shopping.entities.Product;
import ma.fsdm.wisd.shopping.entities.User;
import ma.fsdm.wisd.shopping.services.DatabaseManager;

public class ProductActivity extends AppCompatActivity {

    TextView nameText, descText, refText, priceText, quantityText;
    Button addButton, backButton;
    ImageView imageView;

    DatabaseManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        nameText = findViewById(R.id.nameZone);
        descText = findViewById(R.id.descZone);
        refText = findViewById(R.id.refZone);
        priceText = findViewById(R.id.priceZone);
        quantityText = findViewById(R.id.quantityText);

        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);

        imageView = findViewById(R.id.imageView);

        database = new DatabaseManager(this, "shopping.db", null, 1);

        Intent intent = getIntent();
        Product product = database.searchProduct(intent.getStringExtra("product"));

        nameText.setText(product.getName());
        descText.setText(product.getDescription());
        refText.setText(product.getReference());
        priceText.setText(String.valueOf(product.getPrice())+"$");
        imageView.setImageBitmap(product.getImage());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                int idUser = preferences.getInt("idUser", 0);
                User user = database.searchUserById(idUser);
                Product product = database.searchProduct(refText.getText().toString());
                Command command = new Command(user,product, Integer.parseInt(quantityText.getText().toString()));
                database.addCommand(command);
                ProductActivity.this.finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.this.finish();
            }
        });

    }
}