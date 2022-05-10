package ma.fsdm.wisd.shopping.services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ma.fsdm.wisd.shopping.R;
import ma.fsdm.wisd.shopping.activities.CommandsActivity;
import ma.fsdm.wisd.shopping.activities.ProductActivity;
import ma.fsdm.wisd.shopping.entities.Command;
import ma.fsdm.wisd.shopping.entities.Product;

public class AdapterRecyclerCommand extends RecyclerView.Adapter<AdapterRecyclerCommand.Holder> {

    Context context;
    ArrayList<Command> commands;

    public AdapterRecyclerCommand(Context context, ArrayList<Command> commands) {
        this.context = context;
        this.commands = commands;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.command_model, parent, false);   //false signifie on vas pas attache l'item au liste durant l'instantiation, ms just apres l'utilisation de add()
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.itemDate.setText(commands.get(position).getDate());
        holder.itemName.setText(commands.get(position).getProduct().getName());
        holder.itemImage.setImageBitmap(commands.get(position).getProduct().getImage());
        holder.itemQuantity.setText(String.valueOf(commands.get(position).getQuantity()));
/*
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommandsActivity.class);
                intent.putExtra("refProduct", product.getReference());
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return commands.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity, itemDate;
        ImageView itemImage;
        LinearLayout layout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            itemImage=itemView.findViewById(R.id.itemImage);
            itemDate=itemView.findViewById(R.id.itemDate);
            itemName=itemView.findViewById(R.id.itemName);
            itemQuantity=itemView.findViewById(R.id.itemQuantity);

            layout=itemView.findViewById(R.id.itemContainter);
        }
    }
}
