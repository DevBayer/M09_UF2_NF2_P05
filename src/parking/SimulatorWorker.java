package parking;

/**
 * Created by Bayer on 10/11/2016.
 */
public class SimulatorWorker extends Thread {

    Parking parking;

    public SimulatorWorker(Parking parking) {
        this.parking = parking;
    }

    @Override
    public void run() {
        Cotxe cotxe = new Cotxe(parking, ((int) (Math.random() * 9999) + 1000)+" "+((int) (Math.random() * 999) + 100));
        cotxe.start();
    }
}
