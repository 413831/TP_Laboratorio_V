package utn.sistema.contador_gastos.listeners;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import utn.sistema.contador_gastos.PopupCreate;

public class ClickPopup implements View.OnClickListener
{
    FragmentManager fragmentManager;
    String tag;

    public ClickPopup(FragmentManager fragmentManager, String tag)
    {
        this.fragmentManager = fragmentManager;
        this.tag = tag;
    }


    @Override
    public void onClick(View view)
    {
        PopupCreate popupCreate = new PopupCreate();
        popupCreate.show(this.fragmentManager,this.tag);
    }
}
