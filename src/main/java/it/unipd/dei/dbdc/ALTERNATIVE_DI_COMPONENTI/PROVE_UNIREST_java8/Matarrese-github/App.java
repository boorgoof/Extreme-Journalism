/*
import java.util.*;
import com.apiguardian.bean.*;
import com.mashape.unirest.http.exceptions.UnirestException;

public class App
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String api = sc.next();
        GuardianContentApi g = new GuardianContentApi(api);
        Response r;
        try
        {
            r = g.getContent("nuclear");
        }
        catch (UnirestException e) {
            System.out.println("HSIhidh");
            throw new RuntimeException(e);
        }
        System.out.println(r.getStatus());
        Article[] articles = r.getResults();
        for (Article a : articles)
        {
            System.out.println(a);
        }
    }
}

 */