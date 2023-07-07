package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.tools.PropertiesTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 * This utility class analyzes the properties file that specifies which API to call and with what parameters.
 *
 * @see Properties
 * @see PropertiesTools
 * @see DownloadProperties
 */
public class APIProperties
{
    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created, as this is only a utility class.
     *
     */
    private APIProperties() {}

    /**
     * The key of the parameter of the properties file that specifies the {@link APIManager} to use.
     *
     */
    private static final String name = "name";

    /**
     * The function that reads the properties file and returns an {@link APIManager} which has all the
     * parameters specified in the properties file.
     *
     * @param out_properties The name of the properties file specified by the user. If null, it will be returned null.
     * @param download_properties The name of the download properties file specified by the user. It's used to initialize the {@link APIContainer}, if it is not already initialized.
     * @return The {@link APIManager} specified in the properties file with all the parameters that are inside that file.
     * @throws IOException If the API properties file is not present or the download properties file specified is invalid or if the default download properties file is missing or invalid
     * @throws IllegalArgumentException If the name or the parameters of the {@link APIManager} specified aren't correct.
     * @see PropertiesTools
     */
    public static APIManager readProperties(String out_properties, String download_properties) throws IOException, IllegalArgumentException {
        if (out_properties == null)
        {
            return null;
        }
        Properties apiProps = PropertiesTools.getOutProperties(out_properties);

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
