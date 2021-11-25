package utn.sistema.contador_gastos.objects;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utn.sistema.contador_gastos.R;
import utn.sistema.contador_gastos.listeners.ClickPopup;
import utn.sistema.contador_gastos.listeners.ClickShare;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> implements Filterable
{
    protected List<Item> items;
    protected List<Item> dataSet;

    public ItemAdapter(List<Item> items)
    {
        this.dataSet = items;
        this.items = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        this.items = new ArrayList<>(dataSet);
        Log.d("CREATE",String.valueOf(dataSet.size()) + " --- " +  String.valueOf(items.size()));
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        Item item = this.dataSet.get(position);

        holder.txtDescription.setText(item.getDescription());
        holder.txtPrize.setText(item.getPrize().toString());
        holder.txtDate.setText(item.getDate());
        holder.txtCategory.setText(item.getCategory());

        View.OnClickListener onClickListener = new ClickShare(new Item(holder.txtDescription.getText().toString(),Double.valueOf(holder.txtPrize.getText().toString()),
                holder.txtCategory.getText().toString(),holder.txtDate.getText().toString()));

        holder.imageView.setOnClickListener(onClickListener);
        holder.layout.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount()
    {
        return this.dataSet.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter()
        {
            @SuppressLint("NotifyDataSetChanged")
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Log.d("PUBLISH", String.valueOf(items.size()));
                dataSet.clear();
                dataSet = (ArrayList) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                Log.d("START", String.valueOf(items.size()));

                List<Item> filteredResults = new ArrayList<>();
                if (constraint == null || constraint.length() == 0)
                {
                    Log.d("RESET", String.valueOf(items.size()));
                    filteredResults.addAll(items);
                }
                else
                {
                    filteredResults = getFilteredResults(constraint.toString());
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<Item> getFilteredResults(String constraint)
    {
        List<Item> results = new ArrayList<>();

        for (Item item : items)
        {
            if (item.getDate().contains(constraint))
            {
                Log.d("FILTER", item.getDate() + " - " + constraint);
                results.add(item);
            }
        }
        return results;
    }
}
