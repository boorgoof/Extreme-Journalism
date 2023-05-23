package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;
import java.util.List;

public interface APICaller {
    String getInfo();
    String possibleParams();

    void addParams(List<QueryParam> l) throws IllegalArgumentException;
    void addParam(QueryParam q) throws IllegalArgumentException;

    String callAPI() throws IllegalArgumentException, IOException;
}
