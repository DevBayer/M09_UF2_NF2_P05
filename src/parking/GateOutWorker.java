package parking;

import java.util.Date;

/**
 * Created by Lluís Bayer Soler on 09/11/16.
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
                c.isExit = true;
                synchronized (c) {
                    c.notify();
                }
                synchronized (this) {
                    parking.QueueUnParkedCotxe().remove(0);
                }
                parking.debug("GateOutWorker::run() - Se nos va el coche: "+c.toString());
                try {
                    Thread.sleep(1);
                }catch(InterruptedException e){ }
            }
            synchronized (this){
                try{
                    parking.debug("GateOutWorker::wait()");
                    this.wait();
                }catch(InterruptedException e){ }
            }
        }
    }
}
