package it.unipd.dei.dbdc.DownloadAPI;

import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APIManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;


public class APIProperties
{
    private static final String name = "name";

    public static APIManager readAPIProperties(String properties_file, APIContainer container) throws IOException, IllegalArgumentException {
        Properties appProps = DownloadProperties.getProperties(properties_file);

        // 1. Cerco la property del caller, che è la classe che implementa APICaller
        String n = null;
        ArrayList<QueryParam> params = new ArrayList<>();

        Enumeration<?> enumeration = appProps.propertyNames();
        while (enumeration.hasMoreElements())
        {
            String prop = (String) enumeration.nextElement();
            if (prop.equals(name))
            {
                n = appProps.getProperty(prop);
            }
            else
            {
                params.add(new QueryParam(prop, appProps.getProperty(prop)));
            }
        }

        return container.getAPIManager(n, params);
    }
}
