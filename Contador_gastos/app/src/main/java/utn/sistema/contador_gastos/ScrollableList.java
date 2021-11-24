package utn.sistema.contador_gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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

public class ScrollableList extends AppCompatActivity implements Handler.Callback
{
    List<Item> items;
    ItemAdapter adapter;
    Double total;
    PopupCreate popupCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Today's expenses");
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

            for (int i = 0; i < list.length(); i++)
            {
                JSONObject itemJSON = list.getJSONObject(i);

                Integer id = itemJSON.getInt("id");
                String description = itemJSON.getString("description");
                Double prize = itemJSON.getDouble("prize");
                String category = itemJSON.getString("category");
                String date = itemJSON.getString("date");
                Item item = new Item(id, description, prize , category, date);

                this.items.add(item);
                this.total += prize;
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

}