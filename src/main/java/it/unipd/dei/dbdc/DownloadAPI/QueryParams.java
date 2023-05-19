package it.unipd.dei.dbdc.DownloadAPI;

import java.util.AbstractMap;

public class QueryParams extends AbstractMap.SimpleEntry<String, String> {
    QueryParams(String k, String v)
    {
        super(k, v);
    }
}
