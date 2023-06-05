package it.unipd.dei.dbdc.Interfaces.DownloadAPI;

import it.unipd.dei.dbdc.DownloadAPI.QueryParam;

import java.io.IOException;
import java.util.List;

public interface APIManager {
    String getParams();

    void addParams(List<QueryParam> l) throws IllegalArgumentException;

    String callAPI(String path_folder) throws IllegalArgumentException, IOException;
}
