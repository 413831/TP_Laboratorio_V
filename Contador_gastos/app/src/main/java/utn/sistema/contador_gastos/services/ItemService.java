package utn.sistema.contador_gastos.services;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import utn.sistema.contador_gastos.objects.Item;

public class ItemService extends Thread
{
    Handler handler;
    private static final String GET = "http://192.168.1.36:3000/items";
    private static final String POST = "http://192.168.1.36:3000/nuevoItem";

    public ItemService(Handler handler)
    {
        this.handler = handler;
    }

    @Override
    public void run()
    {
        this.getItems();
    }

    private void getItems()
    {
        HttpConnection httpConnection = HttpConnection.getInstance();
        String s = httpConnection.getElements(ItemService.GET);
        Log.d("respuesta",s);
        Message message = new Message();
        message.obj = s;
        this.handler.sendMessage(message);
    }

    public void postItem(Item item)
    {
        HttpConnection httpConnection = HttpConnection.getInstance();
        Uri.Builder params = new Uri.Builder();
        params.appendQueryParameter("id",item.getId().toString());
        params.appendQueryParameter("description",item.getDescription());
        params.appendQueryParameter("prize", item.getPrize().toString());
        params.appendQueryParameter("category", item.getCategory());
        params.appendQueryParameter("date", item.getDate());
        httpConnection.postElement(params,ItemService.POST);
    }
}
