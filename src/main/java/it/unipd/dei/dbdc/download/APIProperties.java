package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.resources.PropertiesTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class APIProperties
{
    private static final String name = "name";

    private static final String default_properties = "api.properties";

    public static APIManager readAPIProperties(String out_properties) throws IOException, IllegalArgumentException {
        Properties apiProps = PropertiesTools.getProperties(default_properties, out_properties);

        // 1. Cerco la property del caller, che è la classe che implementa APICaller
        String n = null;
        ArrayList<QueryParam> params = new ArrayList<>();

        Enumeration<?> enumeration = apiProps.propertyNames();
        while (enumeration.hasMoreElements())
        {
            String prop = (String) enumeration.nextElement();
            if (prop.equals(name))
            {
                n = apiProps.getProperty(prop);
            }
            else
            {
                params.add(new QueryParam(prop, apiProps.getProperty(prop)));
            }
        }
        APIContainer container = APIContainer.getInstance();

        return container.getAPIManager(n, params);
    }
}
