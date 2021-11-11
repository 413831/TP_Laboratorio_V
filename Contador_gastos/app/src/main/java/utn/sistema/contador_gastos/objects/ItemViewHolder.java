package utn.sistema.contador_gastos.objects;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import utn.sistema.contador_gastos.R;

public class ItemViewHolder extends RecyclerView.ViewHolder
{
    TextView txtDescription;
    TextView txtPrize;

    public ItemViewHolder(@NonNull View itemView)
    {
        super(itemView);
        this.txtDescription = itemView.findViewById(R.id.txtDescription);
        this.txtPrize = itemView.findViewById(R.id.txtPrize);
    }
}
