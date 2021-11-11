package utn.sistema.contador_gastos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import utn.sistema.contador_gastos.listeners.ClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.addListenerOnButton(ScrollableList.class, findViewById(R.id.btnList));
        this.addListenerOnButton(Create.class, findViewById(R.id.btnCreate));
        this.addListenerOnButton(Search.class, findViewById(R.id.btnSearch));
    }

    public void addListenerOnButton(Class destination, Button button)
    {
        Button btnDisplay = button;
        View.OnClickListener onClickListener = new ClickListener(this, destination);

        btnDisplay.setOnClickListener(onClickListener);
    }
}