package it.unipd.dei.dbdc;
import it.unipd.dei.dbdc.DownloadAPI.*;

// L'unica classe che ha il main
public class UserInterface {
    public static void main(String[] args) {
        // Quale API vuoi chiamare? Dicendogli quali sono quelle disponibili

        // Passa il controllo al DownloadHandler, passandogli anche un'istanza della API da chiamare.
        // Quale API passargli sarà deciso da un Factory che crea tutte le istanze delle APICaller.
        // Deve inoltre chiedere all'utente i parametri, e passarli al factory per creare l'istanza di GuardianAPICaller
        DownloadHandler dw = new DownloadHandler(new GuardianAPICaller(new KongAPIAdapter(), "......."));
        // TODO: aggiungere logica per prendere chiave da un file
        dw.download();

    }
}
