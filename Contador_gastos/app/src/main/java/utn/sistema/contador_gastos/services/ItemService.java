package utn.sistema.contador_gastos.services;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ItemService extends Thread
{
    Handler handler;

    public ItemService(Handler handler)
    {
        this.handler = handler;
    }

    @Override
    public void run()
    {
        this.getObjetos();
    }

    private void getObjetos()
    {
        // IP para Localhost
        String url = "http://192.168.1.36:3000/items";

        HttpConnection httpConnection = HttpConnection.getInstance();
        String s = httpConnection.obtenerElementos(url);
        Log.d("respuesta",s);
        Message message = new Message();
        message.obj = s;
        this.handler.sendMessage(message);
    }
}
