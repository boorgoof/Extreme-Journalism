package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.DownloadAPI.DownloadHandler;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        // L'utente puo' passare da riga di comando quello che vuole fare.

        // TODO: apache commons cli per argomenti da linea di comando
        // TODO: junit
        // TODO: maven site plugin
        // TODO: maven javadoc plugin
        // TODO: shade plugin al posto di assembly plugin
        // TODO: usare CoreNLP al posto di Scanner?
        // TODO: properties

        // Se vuole download, passo a download handler
        System.out.println(ConsoleColors.BLUE + "Entering the download part..." + ConsoleColors.RESET);
        DownloadHandler.download(true, "./database");
        System.out.println(ConsoleColors.BLUE + "Exiting the download part..." + ConsoleColors.RESET);
    }
}