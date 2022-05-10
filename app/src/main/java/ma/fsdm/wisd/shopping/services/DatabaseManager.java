package ma.fsdm.wisd.shopping.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import ma.fsdm.wisd.shopping.entities.*;

public class DatabaseManager extends SQLiteOpenHelper{

    public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table product(reference text primary key, name text, description text, price integer, image blob)");
        db.execSQL("create table user(id integer primary key autoincrement, username text unique, password text, first_name text, last_name text, email text)");
        db.execSQL("create table command(id_user integer, ref_product text, quantity integer, date text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean register(User user){

        if(searchUserByUserName(user.getUsername()) != null)
            return false;

        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("first_name", user.getFirstName());
        values.put("last_name", user.getLastName());
        values.put("email", user.getEmail());

        database.insert("user", null, values);

        return true;
    }

    public boolean login(String username, String password){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select count(*) from user where username=? and password=?", new String[]{username, password});
        if(cursor.moveToFirst()){
            if(cursor.getInt(0) == 1)
                return true;
            else
                return false;
        }else{
            return false;
        }
    }

    public User searchUserById(int id){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from user where id=?", new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setFirstName(cursor.getString(3));
            user.setLastName(cursor.getString(4));
            user.setEmail(cursor.getString(5));
            return user;
        }else{
            return null;
        }
    }

    public User searchUserByUserName(String username){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from user where username=?", new String[]{username});
        if(cursor.moveToFirst()){
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setFirstName(cursor.getString(3));
            user.setLastName(cursor.getString(4));
            user.setEmail(cursor.getString(5));
            return user;
        }else{
            return null;
        }
    }

    public ArrayList<Product> showAllProducts(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from product", null);
        ArrayList<Product> products = new ArrayList<>();

        while(cursor.moveToNext()){
            Product product = new Product();
            product.setReference(cursor.getString(0));
            product.setName(cursor.getString(1));
            product.setDescription(cursor.getString(2));
            product.setPrice(cursor.getInt(3));
            product.setImage(BitmapFactory.decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length));
            products.add(product);
        }

        return products;
    }

    public ArrayList<User> showAllUsers(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from user", null);
        ArrayList<User> users = new ArrayList<>();

        while(cursor.moveToNext()){
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setFirstName(cursor.getString(3));
            user.setLastName(cursor.getString(4));
            user.setEmail(cursor.getString(5));
            users.add(user);
        }

        return users;
    }

    public Product searchProduct(String reference){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from product where reference=?", new String[]{reference});
        if(cursor.moveToFirst()){
            Product product = new Product();
            product.setReference(cursor.getString(0));
            product.setName(cursor.getString(1));
            product.setDescription(cursor.getString(2));
            product.setPrice(cursor.getInt(3));
            product.setImage(BitmapFactory.decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length));
            return product;
        }else{
            return null;
        }
    }

    public ArrayList<Product> searchProductByKeyword(String keyword){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from product where name like ? or description like ?", new String[]{"%"+keyword+"%", "%"+keyword+"%"});
        ArrayList<Product> products = new ArrayList<>();
        while(cursor.moveToNext()){
            Product product = new Product();
            product.setReference(cursor.getString(0));
            product.setName(cursor.getString(1));
            product.setDescription(cursor.getString(2));
            product.setPrice(cursor.getInt(3));
            product.setImage(BitmapFactory.decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length));
            products.add(product);
        }
        return products;
    }

    public void deleteAllProducts(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("product", null, null);
    }

    public void addProduct(Product product){
        SQLiteDatabase database = getWritableDatabase();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap image = product.getImage();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();

        ContentValues values = new ContentValues();
        values.put("reference", product.getReference());
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("image", bytes);

        database.insert("product", null, values);
    }

    public ArrayList<Product> searchProductsByUser(int idUser){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(
                " select reference, name, description, price, image" +
                    " from command" +
                    " inner join product" +
                    " on reference = ref_product" +
                    " where id_user=?", new String[]{String.valueOf(idUser)});

        ArrayList<Product> products = new ArrayList<>();
        while(cursor.moveToNext()){
            Product product = new Product();
            product.setReference(cursor.getString(0));
            product.setName(cursor.getString(1));
            product.setDescription(cursor.getString(2));
            product.setPrice(cursor.getInt(3));
            product.setImage(BitmapFactory.decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length));
            products.add(product);
        }
        return products;
    }

    public Command searchCommand(int idUser, String refProduct){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(
                " select id_user, ref_product, quantity, date" +
                        " from command" +
                        " where id_user=?" +
                        " and ref_product=?", new String[]{String.valueOf(idUser), refProduct});
        if(cursor.moveToFirst()){
            Command command = new Command();
            command.setUser(searchUserById(cursor.getInt(0)));
            command.setProduct(searchProduct(cursor.getString(1)));
            command.setQuantity(cursor.getInt(2));
            command.setDate(cursor.getString(3));
            return command;
        }else{
            return null;
        }
    }

    public ArrayList<Command> searchCommandsByUser(int idUser){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(
                    " select id_user, ref_product, quantity, date" +
                        " from command" +
                        " where id_user=?", new String[]{String.valueOf(idUser)});

        ArrayList<Command> commands = new ArrayList<>();
        while(cursor.moveToNext()){
            Command command = new Command();
            command.setUser(searchUserById(cursor.getInt(0)));
            command.setProduct(searchProduct(cursor.getString(1)));
            command.setQuantity(cursor.getInt(2));
            command.setDate(cursor.getString(3));
            commands.add(command);
        }
        return commands;
    }

    public void deleteCommandsByUser(int idUser){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("command", "id_user=?", new String[]{String.valueOf(idUser)});
    }

    public void addCommand(Command command){
        SQLiteDatabase database = getWritableDatabase();

        String ref = command.getProduct().getReference();
        int user = command.getUser().getId();

        Command existsCommand = searchCommand(user, ref);
        ContentValues values = new ContentValues();
        values.put("id_user", command.getUser().getId());
        values.put("ref_product", command.getProduct().getReference());
        values.put("date", command.getDate());

        if(existsCommand == null){
            values.put("quantity", command.getQuantity());
            database.insert("command", null, values);
        }else{
            values.put("quantity", command.getQuantity()+existsCommand.getQuantity());
            database.update("command", values, "id_user=? and ref_product=?", new String[]{String.valueOf(user), ref});
        }

    }

}