package it.unipd.dei.dbdc.DownloadAPI;

import java.util.AbstractMap;

public class QueryParam extends AbstractMap.SimpleEntry<String, String> {
    public QueryParam(String k, String v)
    {
        super(k, v);
    }
}
