package parking;

import java.util.Date;

/**
 * Created by 23878410v on 09/11/16.
 */
public class GateOutWorker extends Thread {
    private Parking parking;

    public GateOutWorker(Parking parking) {
        this.parking = parking;
    }

    @Override
    public void run() {
        while(true){
            parking.debug("GateOutWorker::run()");
            while(parking.hasQueueUnParkedCotxe() > 0){
                Cotxe c = parking.QueueUnParkedCotxe().get(0);
                c.setDateUnParked(new Date());
                parking.QueueUnParkedCotxe().remove(0);
                parking.LogCotxe().add(c);
                System.out.println("Se nos va el coche: "+c.toString());
            }
            synchronized (this){
                try{
                    this.wait();
                }catch(InterruptedException e){ }
            }
        }
    }
}
