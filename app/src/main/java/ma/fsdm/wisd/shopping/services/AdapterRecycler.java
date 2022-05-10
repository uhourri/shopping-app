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
import ma.fsdm.wisd.shopping.activities.ProductActivity;
import ma.fsdm.wisd.shopping.entities.Product;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.Holder> {

    Context context;
    ArrayList<Product> products;

    public AdapterRecycler(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_model, parent, false);   //false signifie on vas pas attache l'item au liste durant l'instantiation, ms just apres l'utilisation de add()
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.itemReference.setText(products.get(position).getReference());
        holder.itemName.setText(products.get(position).getName());
        holder.itemImage.setImageBitmap(products.get(position).getImage());
        holder.itemPrice.setText(String.valueOf(products.get(position).getPrice())+"$");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("product", holder.itemReference.getText().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemReference;
        ImageView itemImage;
        LinearLayout layout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            itemImage=itemView.findViewById(R.id.itemImage);
            itemReference=itemView.findViewById(R.id.itemDate);
            itemName=itemView.findViewById(R.id.itemName);
            itemPrice=itemView.findViewById(R.id.itemQuantity);

            layout=itemView.findViewById(R.id.itemContainter);
        }
    }
}
