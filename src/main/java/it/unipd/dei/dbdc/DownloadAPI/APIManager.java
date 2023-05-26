package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;
import java.util.List;

public interface APIManager {
    String getAPIName();
    String getParams();

    void addParams(List<QueryParam> l) throws IllegalArgumentException;

    String callAPI(String path_folder) throws IllegalArgumentException, IOException;
}
