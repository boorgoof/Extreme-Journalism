package it.unipd.dei.dbdc.DownloadAPI;

import java.nio.file.Path;

public interface APICaller {
    String callAPI();
    String getInfo();
    String possibleParams();
}
