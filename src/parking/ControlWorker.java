package parking;

/**
 * Created by Lluís Bayer Soler on 10/11/16.
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

    public String getInforme(boolean extended){
        if(extended){
            return "\nControlWorker::run() QueueList: "+parking.QueueListCotxe().size()+
                    " ParkedList: "+parking.QueueParkedCotxe().size()+
                    " UnParkedList: "+parking.QueueUnParkedCotxe().size()+
                    " TotalCotxes: "+parking.LogCotxe().size()+
                    "\n"+
                    "GateInWorker::State("+GateInWorker.getState()+") "+
                    "GateOutWorker::State("+GateOutWorker.getState()+")"+
                    "\n"+
                    "Parking::hasAvailableSpace() -> "+ parking.hasAvailableSpace()+
                    "\n- Llistat Cotxes: \n" + parking.getInformeLogCotxe()+"\n";
        }else{
            return "ControlWorker::run() QueueList: "+parking.QueueListCotxe().size()+" ParkedList: "+parking.QueueParkedCotxe().size()+" UnParkedList: "+parking.QueueUnParkedCotxe().size()+" TotalCotxes: "+parking.LogCotxe().size();
        }
    }

    @Override
    public void run() {
        while(true){
            parking.debug(this.getInforme(false));
            if(parking.hasAvailableSpace() && parking.hasQueueListCotxe() > 0 && GateInWorker.getState().equals(State.WAITING)){
                parking.debug("ControlWorker::Notify GateInWorker");
                synchronized (GateInWorker) {
                    GateInWorker.notify();
                }
            }

            if(parking.hasQueueUnParkedCotxe() > 0 && GateOutWorker.getState().equals(State.WAITING)){
                parking.debug("ControlWorker::Notify GateOutWorker");
                synchronized (GateOutWorker){
                    GateOutWorker.notify();
                }
            }

            try {
                Thread.sleep(1);
            }catch(InterruptedException e){ }
        }
    }
}
