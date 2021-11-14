package utn.sistema.contador_gastos.services;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utn.sistema.contador_gastos.objects.Item;

public class HttpConnection
{
    private static HttpConnection conexion;

    private HttpConnection()
    {

    }

    public static HttpConnection getInstance()
    {
        if(HttpConnection.conexion == null)
        {
            HttpConnection.conexion = new HttpConnection();
        }
        return HttpConnection.conexion;
    }

    public String getElements(String urlString)
    {
        String elementos = "";

        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int response = urlConnection.getResponseCode();

            Log.d("Response CODE", String.valueOf(response));
            if(response == 200)
            {
                InputStream inputStream = urlConnection.getInputStream();
                ByteArrayOutputStream byteResponse = new ByteArrayOutputStream();
                byte[] buffer = new byte[1000];
                int length = 0;

                while((length = inputStream.read(buffer)) != -1)
                {
                    byteResponse.write(buffer, 0 , length);
                }
                inputStream.close();
                return new String(byteResponse.toByteArray());
            }
            else
            {
                throw new RuntimeException("Error");
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return elementos;
    }

    public String postElement(Uri.Builder postParams, String urlString)
    {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(new Runnable() {
                @Override
                public void run()
                {
                    handler.post(() -> {
                        try
                        {
                            URL url = new URL(urlString);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("POST");

                            String query = postParams.build().getEncodedQuery();

                            OutputStream os = urlConnection.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                            writer.write(query);
                            writer.flush();
                            writer.close();
                            os.close();

                            int response = urlConnection.getResponseCode();
                            if(response==200)
                            {
                                InputStream is = urlConnection.getInputStream();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                private String readStream(InputStream is)
                {
                    try
                    {
                        ByteArrayOutputStream bo = new ByteArrayOutputStream();
                        int i = is.read();
                        while(i != -1)
                        {
                            bo.write(i);
                            i = is.read();
                        }
                        return bo.toString();
                    }
                    catch (IOException e)
                    {
                        return "";
                    }
                }
            });

        return null;
    }


}
