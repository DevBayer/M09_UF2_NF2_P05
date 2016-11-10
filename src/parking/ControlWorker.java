package parking;

/**
 * Created by 23878410v on 10/11/16.
 */
public class ControlWorker extends Thread {

    private Parking parking;
    private GateInWorker GateInWorker;
    private GateOutWorker GateOutWorker;

    public ControlWorker(Parking parking, parking.GateInWorker gateInWorker, parking.GateOutWorker gateOutWorker) {
        this.parking = parking;
        GateInWorker = gateInWorker;
        GateOutWorker = gateOutWorker;
        this.GateInWorker.start();
        this.GateOutWorker.start();
    }

    @Override
    public void run() {
        while(true){
            System.out.println("ControlWorker::run() QueueList: "+parking.QueueListCotxe().size()+" ParkedList: "+parking.QueueParkedCotxe().size()+" UnParkedList: "+parking.QueueUnParkedCotxe().size()+" TotalCotxesQueHanPassat: "+parking.LogCotxe().size());
            if(parking.hasAvailableSpace() && parking.hasQueueListCotxe() > 0 && GateInWorker.getState().equals(State.WAITING)){
                System.out.println("ControlWorker::Notify GateInWorker");
                synchronized (GateInWorker) {
                    GateInWorker.notify();
                }
            }

            if(parking.hasQueueUnParkedCotxe() > 0 && GateOutWorker.getState().equals(State.WAITING)){
                System.out.println("ControlWorker::Notify GateOutWorker");
                synchronized (GateOutWorker){
                    GateOutWorker.notify();
                }
            }

            try {
                Thread.sleep(500);
            }catch(InterruptedException e){ }
        }
    }
}
