package utn.sistema.contador_gastos.listeners;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import utn.sistema.contador_gastos.R;
import utn.sistema.contador_gastos.objects.Item;
import utn.sistema.contador_gastos.services.ItemService;

public class ClickSave implements DialogInterface.OnClickListener
{
    Activity activity;

    public ClickSave(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i)
    {
        EditText editDescription = this.activity.findViewById(R.id.editDescription);
        EditText editPrize = this.activity.findViewById(R.id.editPrize);
        EditText editCategory = this.activity.findViewById(R.id.editCategory);

        Date date = new Date();

        Item item = new Item(editDescription.getText().toString(),
                Double.valueOf(editPrize.getText().toString()),
                editCategory.getText().toString(),
                date.toString());
        ItemService itemService = new ItemService(null);
        itemService.postItem(item);
    }
}
