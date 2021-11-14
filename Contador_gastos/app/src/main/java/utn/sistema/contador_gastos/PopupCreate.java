package utn.sistema.contador_gastos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Date;

import utn.sistema.contador_gastos.listeners.ClickSave;
import utn.sistema.contador_gastos.objects.Item;
import utn.sistema.contador_gastos.services.ItemService;

public class PopupCreate extends AppCompatDialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Activity activity = super.getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add item");

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View v = layoutInflater.inflate(R.layout.dialog,null);
        //TODO Listener
        DialogInterface.OnClickListener onClickListener = new ClickSave(v);
        builder.setPositiveButton("Add",onClickListener);
        builder.setNegativeButton("Cancel", null);

        builder.setView(v);

        return builder.create();
    }


}
