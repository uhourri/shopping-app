package ma.fsdm.wisd.shopping.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ma.fsdm.wisd.shopping.R;
import ma.fsdm.wisd.shopping.entities.Product;
import ma.fsdm.wisd.shopping.services.AdapterRecycler;
import ma.fsdm.wisd.shopping.services.DatabaseManager;

public class ProductsActivity extends AppCompatActivity {

    TextView keywordText;
    Button searchButton;
    RecyclerView recyclerView;

    DatabaseManager database;
    ArrayList<Product> products;
    AdapterRecycler adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        keywordText = findViewById(R.id.keywordText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);

        database = new DatabaseManager(this, "shopping.db", null, 1);
        products = new ArrayList<>();
        adapterRecycler = new AdapterRecycler(this, products);

        recyclerView.setAdapter(adapterRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        database.deleteAllProducts();
        database.addProduct(new Product("APL123", "Apple AirPods (2nd Generation)", "AirPods with Charging Case: More than 24 hours listening time, up to 18 hours talk time; AirPods (single charge)", 3000, BitmapFactory.decodeResource(getResources(), R.drawable.airpods)));
        database.addProduct(new Product("INTL019", "Intel Dual Band Wireless-AC 7265", "Delivers up to 3x faster Wi-Fi speeds (up to 867 Mbps1) than 802.11n, with more bandwidth per stream 433 Mbps)", 1003, BitmapFactory.decodeResource(getResources(), R.drawable.wireless)));
        database.addProduct(new Product("SEG122", "Seagate Portable 2TB External Hard Drive", "Easily store and access 2TB to content on the go with the Seagate Portable Drive, a USB external hard drive", 1220, BitmapFactory.decodeResource(getResources(), R.drawable.seagate)));
        database.addProduct(new Product("CUR209", "Cuero DHK 18 Inch Vintage Handmade Leather", "FULL GRAIN LEATHER : Our bags are made from full grain leather and lined with durable canvas and are Handcrafted", 801, BitmapFactory.decodeResource(getResources(), R.drawable.cuero)));
        database.addProduct(new Product("SAB87", "Sabrent 4-Port USB 2.0 Data Hub", "Gain Four, Downstream Ports Which Offer High-Speed (480Mbps), Full-Speed (12Mbps), And Low-Speed (1.5Mbps).", 1302, BitmapFactory.decodeResource(getResources(), R.drawable.sabrent)));
        database.addProduct(new Product("CYB76", "CYBERPOWERPC Gamer Xtreme VR Gaming PC", "Destroy the competition with the CYBERPOWERPC Gamer Xtreme VR series of gaming desktops", 988, BitmapFactory.decodeResource(getResources(), R.drawable.gamer)));
        database.addProduct(new Product("RK776", "Roku Streaming Stick 4K 2021", "Roku® Streaming Stick® 4K+ is faster and more powerful than ever and comes with our best remote.", 1008, BitmapFactory.decodeResource(getResources(), R.drawable.roku)));
        database.addProduct(new Product("ACR51", "Acer Swift 3 Thin & Light Laptop", "Perform at your very best with the lightweight Swift 3, the absolute device for accomplishing all you need on the move", 430, BitmapFactory.decodeResource(getResources(), R.drawable.acer)));
        database.addProduct(new Product("HP009", "HP V28 4K Monitor", "Designed for picture-perfect viewing. Immerse yourself in entertainment with the V28 4K monitor ", 287, BitmapFactory.decodeResource(getResources(), R.drawable.hp)));
        database.addProduct(new Product("NIN677", "Nintendo Switch", "Browse and buy digital games on the Nintendo game store, and automatically download them to your Nintendo Switch", 1012, BitmapFactory.decodeResource(getResources(), R.drawable.nintendo)));
        database.addProduct(new Product("DLL90", "Dell SE2722HX - 27-inch FHD", "27-inch Full HD monitors featuring three-sided slim bezels and a compact footprint.", 3215, BitmapFactory.decodeResource(getResources(), R.drawable.dell)));
        */

        showAllProducts();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.clear();
                products.addAll(database.searchProductByKeyword(keywordText.getText().toString()));
                adapterRecycler.notifyDataSetChanged();
            }
        });

    }

    void showAllProducts(){
        products.clear();
        products.addAll(database.showAllProducts());
        adapterRecycler.notifyDataSetChanged();
    }
}