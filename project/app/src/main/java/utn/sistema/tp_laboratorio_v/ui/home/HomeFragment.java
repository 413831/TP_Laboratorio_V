package utn.sistema.tp_laboratorio_v.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import utn.sistema.tp_laboratorio_v.R;
import utn.sistema.tp_laboratorio_v.databinding.FragmentHomeBinding;
import utn.sistema.tp_laboratorio_v.objects.Category;
import utn.sistema.tp_laboratorio_v.objects.Item;
import utn.sistema.tp_laboratorio_v.objects.ItemAdapter;
import utn.sistema.tp_laboratorio_v.services.ItemService;

public class HomeFragment extends Fragment implements Handler.Callback {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    List<Item> items;
    ItemAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.items = new ArrayList<>();
        Handler handler = new Handler(this);

        this.adapter = new ItemAdapter(this.items);
        ItemService itemService = new ItemService(handler);
        itemService.start();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        try
        {
            JSONArray lista = new JSONArray(msg.obj.toString());

            for (int i = 0; i < lista.length(); i++)
            {
                JSONObject productoJSON = lista.getJSONObject(i);
                Integer id = productoJSON.getInt("id");
                String descripcion = productoJSON.getString("descripcion");
                Double valor = productoJSON.getDouble("valor");
                //Category categoria = new Category(productoJSON.getString("categoria"));
                Category categoria = new Category();
                Item item = new Item(id,descripcion,valor,categoria);
                this.items.add(item);
            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}