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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import utn.sistema.contador_gastos.listeners.ClickPopup;
import utn.sistema.contador_gastos.objects.Item;
import utn.sistema.contador_gastos.objects.ItemAdapter;
import utn.sistema.contador_gastos.services.ItemService;

public class ScrollableList extends AppCompatActivity implements Handler.Callback
{
    List<Item> items;
    ItemAdapter adapter;
    PopupCreate popupCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List");
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.items = new ArrayList<>();
        Handler handler = new Handler(this);

        this.adapter = new ItemAdapter(this.items);
        ItemService itemService = new ItemService(handler);
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
                Item item = new Item(id, description, prize , category);

                this.items.add(item);
            }
            this.adapter.notifyDataSetChanged();
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