// Non mi sembra il massimo della vita, è più complicata e inutilmente
/*
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class RetrofitTry {
    public static void main(String[] args) {
        // Retrofit models REST endpoints as Java interfaces, making them very simple to understand and consume.
        // Retrofit won't complain about missing properties – since it only maps what we need

        // To construct an HTTP request call, we need to build our Retrofit object first:
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        // It needs the base URL which is going to be used for every service call and a converter factory – which takes care of the parsing of data we're sending and also the responses we get.

    }
}

 */
