package utn.sistema.contador_gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utn.sistema.contador_gastos.listeners.ClickPopup;
import utn.sistema.contador_gastos.objects.Item;
import utn.sistema.contador_gastos.objects.ItemAdapter;
import utn.sistema.contador_gastos.services.ItemService;
import utn.sistema.contador_gastos.services.RequestMethod;

public class ScrollableList extends AppCompatActivity implements Handler.Callback, DialogInterface.OnDismissListener
{
    List<Item> items;
    ItemAdapter adapter;
    Double total;
    PopupCreate popupCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_list);

        String title = this.getResources().getString(R.string.title_activity_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.total = 0d;

        this.items = new ArrayList<>();
        Handler handler = new Handler(this);

        this.adapter = new ItemAdapter(this.items);
        ItemService itemService = new ItemService(handler, RequestMethod.GET);
        itemService.start();

        RecyclerView recyclerView = super.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        View.OnClickListener onClickListener = new ClickPopup(getSupportFragmentManager(),"add");

        FloatingActionButton button = findViewById(R.id.btnAdd);
        button.setOnClickListener(onClickListener);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg)
    {
        try
        {
            JSONArray list = new JSONArray(msg.obj.toString());
            Log.d("JSON", list.toString());
            Date currentDate = new Date();

            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String currentDateString = simpleDateFormat.format(currentDate);

            for (int i = 0; i < list.length(); i++)
            {
                JSONObject itemJSON = list.getJSONObject(i);
                String date = itemJSON.getString("date");

                if(currentDateString.equals(date))
                {
                    Integer id = itemJSON.getInt("id");
                    String description = itemJSON.getString("description");
                    Double prize = itemJSON.getDouble("prize");
                    String category = itemJSON.getString("category");
                    Item item = new Item(id, description, prize , category, date);

                    this.items.add(item);
                    this.total += prize;
                }
            }
            this.adapter.notifyDataSetChanged();
            TextView txtTotal = findViewById(R.id.txtTotal);
            txtTotal.setText("TOTAL: $" + this.total.toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.itFecha);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(date);
        menuItem.setTitle(dateString);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(android.R.id.home == item.getItemId())
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface)
    {
        SharedPreferences preferences = super.getSharedPreferences("elements", Context.MODE_PRIVATE);
        String newItem = preferences.getString("new_item", "empty");

        Log.d("NEW ITEM", newItem);
        try
        {
            JSONObject itemJSON = new JSONObject(newItem);
            String description = itemJSON.getString("description");
            Double prize = itemJSON.getDouble("prize");
            String category = itemJSON.getString("category");
            String date = itemJSON.getString("date");
            Integer lastId = this.items.get(this.items.size()-1).getId();

            Item item = new Item(lastId++, description, prize , category, date);

            this.items.add(item);
            this.adapter.notifyDataSetChanged();
            this.total += prize;
            TextView txtTotal = findViewById(R.id.txtTotal);
            txtTotal.setText("TOTAL: $" + this.total.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}