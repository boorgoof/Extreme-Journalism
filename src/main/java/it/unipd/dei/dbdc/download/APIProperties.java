package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.analyze.interfaces.Analyzer;
import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.resources.PropertiesTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 * This utility class analyzes the properties file that specifies which API to call and with what parameters.
 *
 * @see Properties
 * @see PropertiesTools
 */
public class APIProperties
{
    /**
     * The name of the default properties file. It is present in the folder resources.
     *
     */
    private static final String default_properties = "api.properties";

    /**
     * The key of the parameter of the properties file that specifies the {@link APIManager} to use
     *
     */
    private static final String name = "name";

    /**
     * The function that reads the properties file and returns an {@link APIManager} which has all the
     * parameters specified in the properties file.
     *
     * @param out_properties The name of the properties file specified by the user. If null, the default properties file will be used.
     * @param download_properties The name of the download properties file specified by the user. It's used to initialize the {@link APIContainer}
     * @return The {@link APIManager} specified in the properties file with all the parameters.
     * @throws IOException If both the API properties files or both of the Download properties files are not present
     * @throws IllegalArgumentException If the name or the parameters of the {@link APIManager} specified aren't correct.
     */
    public static APIManager readAPIProperties(String out_properties, String download_properties) throws IOException, IllegalArgumentException {
        Properties apiProps = PropertiesTools.getProperties(default_properties, out_properties);

        String n = null;
        ArrayList<QueryParam> params = new ArrayList<>();

        //It adds all the couples key-value specified in the properties file as parameters to the specified APIManager.
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
        APIContainer container = APIContainer.getInstance(download_properties);

        return container.getAPIManager(n, params);
    }
}
