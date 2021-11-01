package utn.sistema.tp_laboratorio_v.services;

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
        String url = "http://192.168.1.8:3000/items";

        HttpConnection httpConnection = HttpConnection.getInstance();
        String s = httpConnection.obtenerElementos(url);
        Log.d("respuesta",s);
        // Comunicacion para leer el mensaje
        Message message = new Message();
        message.obj = s;
        // Se puede configurar una constante para conectar un hilo
        // Se guarda mensaje dentro del handler
        this.handler.sendMessage(message);
    }
}
