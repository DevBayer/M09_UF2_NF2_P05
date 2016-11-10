package parking;

import java.util.ArrayList;

/**
 * Created by Lluís Bayer Soler on 09/11/16.
 */
public class Parking {
    private Database data;
    private ControlWorker Controller;
    public GateInWorker GateIn;
    public GateOutWorker GateOut;
    boolean debug = false;

    public Parking(int numplaces) {
        this.data = new Database(numplaces);
        GateIn = new GateInWorker(this);
        GateOut = new GateOutWorker(this);
        Controller = new ControlWorker(this, GateIn, GateOut);
        Controller.setDaemon(true);
        Controller.start();
    }

    public void addQueueListCotxe(Cotxe c){
        this.debug("Entra Cotxe en QueueListCotxe -> "+c.toString());
        LogCotxe().add(c);
        QueueListCotxe().add(c);
    }

    public Boolean hasAvailableSpace(){
        if(data.QueueParkedCotxe.size() < data.numplaces){
            return true;
        }else{
            return false;
        }
    }

    public int hasQueueListCotxe(){
        return data.QueueListCotxe.size();
    }


    public synchronized ArrayList<Cotxe> QueueListCotxe(){
        return data.QueueListCotxe;
    }

    public synchronized ArrayList<Cotxe> QueueParkedCotxe(){
        return data.QueueParkedCotxe;
    }

    public synchronized ArrayList<Cotxe> QueueUnParkedCotxe(){
        return data.QueueUnParkedCotxe;
    }

    public synchronized ArrayList<Cotxe> LogCotxe(){
        return data.LogCotxe;
    }

    public int hasQueueUnParkedCotxe(){
        return data.QueueUnParkedCotxe.size();
    }


    public ControlWorker getController(){
        return this.Controller;
    }

    public void debug(String msg){
        if(debug){
            System.out.println(msg);
        }
    }

    public String getInformeLogCotxe(){
        String str = "";
        for (Cotxe c :data.LogCotxe) {
            //if(c == null){
             //   str += "Thread NULL";
            //}else {
                str += "   - " + c.toString() + "\n";
            //}
        }
        return str;
    }

}
