package utn.sistema.contador_gastos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import utn.sistema.contador_gastos.listeners.ClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        ((TextView) actionBar.getCustomView().findViewById(R.id.tvTitle)).setText("Welcome");

        this.addListenerOnButton(ScrollableList.class, super.findViewById(R.id.btnList));
        this.addListenerOnButton(Search.class, super.findViewById(R.id.btnSearch));
    }

    public void addListenerOnButton(Class destination, Button button)
    {
        Button btnDisplay = button;
        View.OnClickListener onClickListener = new ClickListener(this, destination);

        btnDisplay.setOnClickListener(onClickListener);
    }
}