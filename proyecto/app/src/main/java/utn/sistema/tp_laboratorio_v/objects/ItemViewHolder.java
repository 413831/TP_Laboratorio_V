package utn.sistema.tp_laboratorio_v.objects;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import utn.sistema.tp_laboratorio_v.R;

public class ItemViewHolder extends RecyclerView.ViewHolder
{
    TextView txtDescripcion;
    TextView txtValor;

    public ItemViewHolder(@NonNull View itemView)
    {
        super(itemView);
        this.txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
        this.txtValor = itemView.findViewById(R.id.txtValor);
    }
}
