package parking;

import java.util.Date;

/**
 * Created by 23878410v on 09/11/16.
 */
public class Cotxe extends Thread {
    private Parking parking;
    private String matricula;
    Boolean parked = false;
    int waitTime = 0;
    private Date dateParked;
    private Date dateUnParked;

    public Cotxe(Parking parking, String matricula) {
        this.parking = parking;
        this.matricula = matricula;
        this.waitTime = (int)(Math.random() * 2500) + 1000;
    }

    public void setParked(Boolean parked) {
        this.parked = parked;
    }

    public void setDateParked(Date dateParked) {
        this.dateParked = dateParked;
    }

    public void setDateUnParked(Date dateUnParked) {
        this.dateUnParked = dateUnParked;
    }

    @Override
    public void run() {
        System.out.println("Se agrega el coche "+this.matricula+" en la cola del Parking.");
        parking.addQueueListCotxe(this);
        try {
            synchronized (this) {
                this.wait();
            }
            Thread.sleep(this.waitTime);
            parking.QueueParkedCotxe().remove(this);
            this.parked = false;
            parking.QueueUnParkedCotxe().add(this);
        }catch(InterruptedException e){}


    }

    @Override
    public String toString() {
        return "Cotxe{" +
                "parking=" + parking +
                ", matricula='" + matricula + '\'' +
                ", parked=" + parked +
                ", waitTime=" + waitTime +
                ", dateParked=" + dateParked +
                ", dateUnParked=" + dateUnParked +
                '}';
    }
}
