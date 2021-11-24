package utn.sistema.contador_gastos.objects;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import utn.sistema.contador_gastos.R;
import utn.sistema.contador_gastos.listeners.ClickPopup;
import utn.sistema.contador_gastos.listeners.ClickShare;

public class ItemViewHolder extends RecyclerView.ViewHolder
{
    TextView txtDescription;
    TextView txtPrize;
    TextView txtDate;
    TextView txtCategory;
    LinearLayout layout;

    public ItemViewHolder(@NonNull View itemView)
    {
        super(itemView);
        this.txtDescription = itemView.findViewById(R.id.txtDescription);
        this.txtPrize = itemView.findViewById(R.id.txtPrize);
        this.txtDate = itemView.findViewById(R.id.txtDate);
        this.txtCategory = itemView.findViewById(R.id.txtCategory);

        this.layout = itemView.findViewById(R.id.layoutItem);


    }
}
