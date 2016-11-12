package parking;

import java.util.Date;

/**
 * Created by Lluís Bayer Soler on 09/11/16.
 */
public class GateInWorker extends Thread {
    private Parking parking;

    public GateInWorker(Parking parking) {
        this.parking = parking;
    }

    @Override
    public void run() {
        while(true){
            parking.debug("GateInWorker::run()");

            while(parking.hasQueueListCotxe() > 0){
                    Cotxe c = parking.QueueListCotxe().get(0);
                synchronized (this) {
                   //if(c != null) {
                        parking.QueueListCotxe().remove(c);
                        parking.QueueParkedCotxe().add(c);
                        c.setDateParked(new Date());
                        c.setParked(true);
                        synchronized (c) {
                            c.notify();
                        }
                   //}
                }

                parking.debug("GateInWorker::Tenemos Coches en ESPERA, LO REMOVEMOS y LO AÑADIMOS EN PARKED!!!");
                try {
                    Thread.sleep(1);
                }catch(InterruptedException e){ }
            }

            if(parking.hasQueueListCotxe() > 0){
                parking.debug("GateInWorker::Uuuups! No tenemos espacio, nos vamos para la espera a ser notificados =)");
            }

            synchronized (this){
                try{
                    parking.debug("GateInWorker::wait()");
                    this.wait();
                }catch(InterruptedException e){ }
            }
        }
    }
}
