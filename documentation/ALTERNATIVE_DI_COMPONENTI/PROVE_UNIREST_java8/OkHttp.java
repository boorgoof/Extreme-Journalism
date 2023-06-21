package it.unipd.dei.dbdc.ALTERNATIVE_DI_COMPONENTI.PROVE_UNIREST_java8;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttp {
    public static void main(String[] args) throws IOException {
        // GET SINCRONO
        // Creiamo il client
        OkHttpClient client = new OkHttpClient.Builder().build();

        // We need to build a Request object based on a URL and make a Call.
        // After its execution, we'll get an instance of Response back:
        /*
        Request request = new Request.Builder()
                .url("https://content.guardianapis.com" + "/analyze")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute(); // Lancia IOException
         */

        // To add query parameters to our GET request, we can take advantage of the HttpUrl.Builder, e crearlo prima per poi passarlo alla richiesta
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://content.guardianapis.com" + "/analyze").newBuilder();
        urlBuilder.addQueryParameter("page-size", "1");
        urlBuilder.addQueryParameter("api-key", ".............................");

        String url = urlBuilder.build().toString();

        Request request1 = new Request.Builder()
                .url(url)
                .build();
        Call call1 = client.newCall(request1);
        Response response1 = call1.execute();

        // Questo mi permette di ottenere il body
        System.out.println(response1.body().string());
        System.out.println(response1.body().contentType());
        // To add headers
        /*
        Request request2 = new Request.Builder()
                .url("https://content.guardianapis.com/search")
                .addHeader("Content-Type", "application/json")
                .build();

        Call call2 = client.newCall(request);
        Response response2 = call.execute();
        response.close();

        // PER SETTARE DI DEFAULT IL CLIENT
        // Un header: utilizziamo un interceptor
        // we can use to intercept and process requests before they are sent to our application code.
        // We can continue to call the addInterceptor method for as many interceptors as we require. Just remember that they will be called in the order they added.
        OkHttpClient client2 = new OkHttpClient.Builder()
                .addInterceptor(
                        new DefaultContentTypeInterceptor())
                .build();
        // Segue di default i redirect
        // Ci sono degli interceptor di logging di default
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        // Settare un timeout. OkHttp supports connect, read, and write timeouts.
        OkHttpClient client3 = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.SECONDS)
                .build();

        // Dopo request possiamo metterlo in un try catch
            try (Response response4 = client.newCall(request).execute())
            {
                if (!response4.isSuccessful()) throw new IOException("Unexpected code " + response4);

                Headers responseHeaders = response4.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response4.body().string());
        }

        // GET ASINCRONO
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(responseBody.string());
                }
            }
        });

        // RISPOSTA:
        // we need to extract the JSON from the result of the service call. For this, we can access the body via the body() method of the Response object.
        // The ResponseBody class has several options for extracting this data:
        // string(): returns the whole response body as a String; manages the encoding the same as charStream(), but if we need a different encoding, we can use source().readString(charset) instead

        // A questo punto, avendo una stringa, possiamo usare Jackson, con readValue della stringa

         */
    }
}

// to create our interceptor, all we need to is inherit from the Interceptor interface, which has one mandatory method intercept(Chain chain). Then we can go ahead and override this method with our own implementation.
class DefaultContentTypeInterceptor implements Interceptor {

    public Response intercept(Interceptor.Chain chain)
            throws IOException {

        //First, we get the incoming request by called chain.request() before printing out the headers and request URL.
        Request originalRequest = chain.request();
        String contentType = "application/json";
        Request requestWithUserAgent = originalRequest
                .newBuilder()
                .header("Content-Type", contentType)
                .build();

        //It is important to note that a critical part of every interceptor’s implementation is the call to chain.proceed(request).
        return chain.proceed(requestWithUserAgent);
    }
}