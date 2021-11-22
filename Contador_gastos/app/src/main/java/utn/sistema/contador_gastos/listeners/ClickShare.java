package utn.sistema.contador_gastos.listeners;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import utn.sistema.contador_gastos.objects.Item;

public class ClickShare implements View.OnClickListener
{
    Item item;

    public ClickShare(Item item)
    {
        this.item = item;
    }

    @Override
    public void onClick(View view)
    {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, this.item.toString());
        sendIntent.setType("text/plain");

        view.getContext().startActivity(Intent.createChooser(sendIntent, null));
    }
}
