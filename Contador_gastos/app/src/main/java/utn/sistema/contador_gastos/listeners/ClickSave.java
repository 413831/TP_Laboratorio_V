package utn.sistema.contador_gastos.listeners;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import java.util.Date;

import utn.sistema.contador_gastos.R;
import utn.sistema.contador_gastos.objects.Item;
import utn.sistema.contador_gastos.services.ItemService;
import utn.sistema.contador_gastos.services.RequestMethod;

public class ClickSave implements AlertDialog.OnClickListener
{
    View view;

    public ClickSave(View view)
    {
        this.view = view;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i)
    {
        EditText editDescription = (EditText) this.view.findViewById(R.id.editDescription);
        EditText editPrize = (EditText) this.view.findViewById(R.id.editPrize);
        EditText editCategory = (EditText) this.view.findViewById(R.id.editCategory);

        Date date = new Date();

        Log.d("click", "DESCRIPTION: " + editDescription.getText().toString());
        Log.d("click", "PRIZE: " + editPrize.getText().toString().trim());
        Log.d("click", "CATEGORY: " + editCategory.getText().toString());

        Item item = new Item(editDescription.getText().toString(),
                Double.valueOf(editPrize.getText().toString().trim()),
                editCategory.getText().toString(),
                date.toString());
        ItemService itemService = new ItemService(null, RequestMethod.POST);
        itemService.setItem(item);
        itemService.start();
        dialogInterface.dismiss();
    }
}
