package app;

import parking.Cotxe;
import parking.Parking;

/**
 * Created by 23878410v on 09/11/16.
 */
public class Main {
    public static void main(String[] args) {
        Parking parking = new Parking(15);
        Cotxe cotxe;
        for (int i = 0; i < 50; i++) {
            cotxe = new Cotxe(parking, "23878410V");
            cotxe.start();
            try {
                Thread.sleep((int) (Math.random() * 1500) + 0);
                cotxe = new Cotxe(parking, "23878410V");
                cotxe.start();
            }catch(InterruptedException e){}
        }
    }
}
