package parking;

import java.util.ArrayList;

/**
 * Created by 23878410v on 10/11/16.
 */
public class Database {
    public final int numplaces;
    public ArrayList<Cotxe> QueueParkedCotxe = new ArrayList<>();
    public ArrayList<Cotxe> QueueListCotxe = new ArrayList<>();
    public ArrayList<Cotxe> QueueUnParkedCotxe = new ArrayList<>();
    public ArrayList<Cotxe> LogCotxe = new ArrayList<>();

    public Database(int numplaces) {
        this.numplaces = numplaces;
    }
}
