package ma.fsdm.wisd.shopping.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ma.fsdm.wisd.shopping.R;
import ma.fsdm.wisd.shopping.entities.Command;
import ma.fsdm.wisd.shopping.services.AdapterRecyclerCommand;
import ma.fsdm.wisd.shopping.services.DatabaseManager;

public class CommandsActivity extends AppCompatActivity {

    Button validateButton, clearButton;
    RecyclerView recyclerView;

    DatabaseManager database;
    ArrayList<Command> commands;
    AdapterRecyclerCommand adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);

        validateButton = findViewById(R.id.validateButton);
        clearButton = findViewById(R.id.clearButton);
        recyclerView = findViewById(R.id.recyclerView);

        database = new DatabaseManager(this, "shopping.db", null, 1);
        commands = new ArrayList<>();
        adapterRecycler = new AdapterRecyclerCommand(this, commands);

        recyclerView.setAdapter(adapterRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        int idUser = preferences.getInt("idUser", 1);

        commands.clear();
        commands.addAll(database.searchCommandsByUser(idUser));
        adapterRecycler.notifyDataSetChanged();

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = 0;
                for(Command command : commands){
                    total += command.getQuantity()*command.getProduct().getPrice();
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(CommandsActivity.this);
                alert.setTitle("Validation");
                alert.setMessage("Total of Product is : "+total+"\n Do u want to clear your busket?");

                alert.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        commands.clear();
                        adapterRecycler.notifyDataSetChanged();
                        database.deleteCommandsByUser(idUser);
                        Toast.makeText(CommandsActivity.this, "Cleared", Toast.LENGTH_LONG).show();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CommandsActivity.this, "Canceled", Toast.LENGTH_LONG).show();
                    }
                });

                alert.show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CommandsActivity.this);
                alert.setTitle("Validation");
                alert.setMessage("Do u want to clear your busket?");

                alert.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        commands.clear();
                        adapterRecycler.notifyDataSetChanged();
                        database.deleteCommandsByUser(idUser);
                        Toast.makeText(CommandsActivity.this, "Cleared", Toast.LENGTH_LONG).show();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CommandsActivity.this, "Canceled", Toast.LENGTH_LONG).show();
                    }
                });

                alert.show();
            }
        });
    }
}