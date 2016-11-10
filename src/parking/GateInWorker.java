package parking;

import java.util.Date;

/**
 * Created by 23878410v on 09/11/16.
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

            while(parking.hasAvailableSpace() && parking.hasQueueListCotxe() > 0){
                Cotxe c = parking.QueueListCotxe().get(0);
                parking.QueueListCotxe().remove(c);
                parking.QueueParkedCotxe().add(c);
                synchronized (c) {
                    c.setDateParked(new Date());
                    c.setParked(true);
                    c.notify();
                }

                parking.debug("GateInWorker::Tenemos Coches en ESPERA, LO REMOVEMOS y LO AÑADIMOS EN PARKED!!!");
            }

            if(parking.hasQueueListCotxe() > 0){
                System.out.println("GateInWorker::Uuuups! No tenemos espacio, nos vamos =)");
            }

            synchronized (this){
                try{
                    System.out.println("GateInWorker::wait()");
                    this.wait();
                }catch(InterruptedException e){ }
            }
        }
    }
}
