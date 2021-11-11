package utn.sistema.contador_gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import utn.sistema.contador_gastos.objects.Item;
import utn.sistema.contador_gastos.objects.ItemAdapter;
import utn.sistema.contador_gastos.services.ItemService;

public class ScrollableList extends AppCompatActivity implements Handler.Callback
{
    List<Item> items;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
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
    }

    @Override
    public boolean handleMessage(@NonNull Message msg)
    {
        try
        {
            JSONArray lista = new JSONArray(msg.obj.toString());

            for (int i = 0; i < lista.length(); i++)
            {
                JSONObject personaJSON = lista.getJSONObject(i);
                Integer id = personaJSON.getInt("id");
                String description = personaJSON.getString("description");
                Double prize = personaJSON.getDouble("prize");
                String category = personaJSON.getString("category");
                Item item = new Item(id, description, prize , category);
                /** TODO
                 * Recuperar imagen de forma asíncrona
                 */
                this.items.add(item);
            }
            this.adapter.notifyDataSetChanged();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
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

}