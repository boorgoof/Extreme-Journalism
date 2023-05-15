package it.unipd.dei.dbdc.ALTERNATIVE_DI_COMPONENTI.PROVE_UNIREST_java8;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UrlHTTPConnectionTest {
    public static void main(String[] args) throws IOException {
        // URL Class: only creates a connection object but doesn't establish the connection yet
        URL url = new URL("http://example.com"); // Lancia MalformedURLException

        // The HttpUrlConnection class is used for all types of requests by setting the requestMethod attribute to one of the values: GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE.
        HttpURLConnection con = (HttpURLConnection) url.openConnection(); // Lancia IOException
        con.setRequestMethod("GET"); // In realtà è quello di default

        // If we want to add parameters to a request, we have to set the doOutput property to true, then write a String of the form param1=value¶m2=value to the OutputStream of the HttpUrlConnection instance:
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes("param1=23&param2=45");
        out.flush();
        out.close();

        // Possiamo poi gestire i cookie, aggiungere header, configurare timeouts, il redirect..

        // Leggere la risposta:
        // Reading the response of the request can be done by parsing the InputStream of the HttpUrlConnection instance.
        // To execute the request, we can use the getResponseCode(), connect(), getInputStream() or getOutputStream() methods:
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();
        // Anche
    }

}
