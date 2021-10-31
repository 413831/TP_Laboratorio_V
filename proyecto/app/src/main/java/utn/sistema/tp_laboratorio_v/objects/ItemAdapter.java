package utn.sistema.tp_laboratorio_v.objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import utn.sistema.tp_laboratorio_v.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>
{
    List<Item> items;

    public ItemAdapter(List<Item> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        Item item = this.items.get(position);

        holder.txtDescripcion.setText(item.getDescripcion());
        holder.txtValor.setText(item.getValor().toString());

    }

    @Override
    public int getItemCount()
    {
        return this.items.size();
    }
}
