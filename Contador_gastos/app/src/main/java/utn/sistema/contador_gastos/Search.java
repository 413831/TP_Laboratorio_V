package utn.sistema.contador_gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utn.sistema.contador_gastos.objects.Item;
import utn.sistema.contador_gastos.objects.ItemAdapter;
import utn.sistema.contador_gastos.services.ItemService;
import utn.sistema.contador_gastos.services.RequestMethod;

public class Search extends AppCompatActivity implements SearchView.OnQueryTextListener, Handler.Callback
{
    List<Item> items;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String title = this.getResources().getString(R.string.title_activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);

        this.items = new ArrayList<>();
        Handler handler = new Handler(this);

        ItemService itemService = new ItemService(handler, RequestMethod.GET);
        itemService.start();
        this.adapter = new ItemAdapter(this.items);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView = super.findViewById(R.id.recyclerview_search);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem menuItem = menu.findItem(R.id.itBuscador);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setInputType(InputType.TYPE_CLASS_DATETIME);
        searchView.setQueryHint("Enter a date");

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
    public boolean onQueryTextSubmit(String query)
    {
        Log.d("QUERY", query);
        //this.adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        Log.d("NEW TEXT", newText);
        this.adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public boolean handleMessage(@NonNull Message message)
    {
        try
        {
            JSONArray list = new JSONArray(message.obj.toString());
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
            }
            this.adapter.notifyDataSetChanged();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}