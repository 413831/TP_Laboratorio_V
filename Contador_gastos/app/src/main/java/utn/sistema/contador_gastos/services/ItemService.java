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
    private Item item;
    private RequestMethod requestMethod;

    public ItemService(Handler handler, RequestMethod requestMethod)
    {
        this.handler = handler;
        this.requestMethod = requestMethod;
    }

    @Override
    public void run()
    {
        this.executeQuery(this.requestMethod);
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

    public void postItem()
    {
        HttpConnection httpConnection = HttpConnection.getInstance();
        Uri.Builder params = new Uri.Builder();
        params.appendQueryParameter("description",this.item.getDescription());
        params.appendQueryParameter("prize", this.item.getPrize().toString());
        params.appendQueryParameter("category", this.item.getCategory());
        params.appendQueryParameter("date", this.item.getDate());
        Log.d("POST RESPONSE", httpConnection.postElement(params,ItemService.POST));
    }

    public void executeQuery(RequestMethod requestMethod)
    {
        switch (requestMethod)
        {
            case GET:
                this.getItems();
                break;
            case POST:
                this.postItem();
                break;
            case PUT:
                break;
            case DELETE:
                break;
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
}
