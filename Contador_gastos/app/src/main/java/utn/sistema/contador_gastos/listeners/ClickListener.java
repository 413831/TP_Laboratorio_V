package utn.sistema.contador_gastos.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ClickListener implements View.OnClickListener
{

    Activity activity;
    Class destination;

    public ClickListener(Activity activity, Class destination)
    {
        this.activity = activity;
        this.destination = destination;
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(this.activity, this.destination);

        this.activity.startActivity(intent);
    }
}
