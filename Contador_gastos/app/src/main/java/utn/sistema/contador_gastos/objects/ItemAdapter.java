package utn.sistema.contador_gastos.objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import utn.sistema.contador_gastos.R;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        Item item = this.items.get(position);

        holder.txtDescription.setText(item.getDescription());
        holder.txtPrize.setText(item.getPrize().toString());

    }

    @Override
    public int getItemCount()
    {
        return this.items.size();
    }
}
