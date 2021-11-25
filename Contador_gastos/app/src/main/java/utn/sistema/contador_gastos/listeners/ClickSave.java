package utn.sistema.contador_gastos.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.SimpleDateFormat;
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

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateString = simpleDateFormat.format(date);

        Log.d("click", "DESCRIPTION: " + editDescription.getText().toString());
        Log.d("click", "PRIZE: " + editPrize.getText().toString().trim());
        Log.d("click", "CATEGORY: " + editCategory.getText().toString());

        Item item = new Item(editDescription.getText().toString(),
                Double.valueOf(editPrize.getText().toString().trim()),
                editCategory.getText().toString(),
                dateString);

        ItemService itemService = new ItemService(null, RequestMethod.POST);
        itemService.setItem(item);
        itemService.start();

        SharedPreferences preferences = view.getContext().getSharedPreferences("elements", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("new_item", item.toString());
        editor.commit();

        Log.d("Click","On Dismiss");
        dialogInterface.dismiss();
    }
}
