package app;

import parking.Cotxe;
import parking.Parking;
import parking.SimulatorWorker;

import java.util.Scanner;

/**
 * Created by Lluís Bayer Soler on 09/11/16.
 */
public class Main {

    private static Parking parking = null;

    public static void main(String[] args) {
        Cotxe cotxe;

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Bienvenido al menú del -Parking-");
            System.out.println("1. Iniciar Core" + (parking == null ? " (Estat: Inactiu)" : " (Estat: Actiu)"));
            System.out.println("2. Añadir Coche");
            System.out.println("3. Imprimir informe por pantalla");
            System.out.println("4. Simular entrada de Coches");
            System.out.println("9. Salir");
            if(sc.hasNextInt()){
                int option = sc.nextInt();
                switch(option){
                    case 1:
                        if(parking == null) {
                            System.out.println("- Diga'm el nombre de places a establir:");
                            while (sc.hasNext()) {
                                if (sc.hasNextInt()) {
                                    int plazas = sc.nextInt();
                                    if (plazas > 0) {
                                        parking = new Parking(plazas);
                                        break;
                                    } else {
                                        System.out.println("El nombre té que ser major a 0.");
                                    }
                                } else {
                                    System.out.println("El nombre té que ser un enter.");
                                }
                            }
                        }else{
                            System.out.println("Ja s'ha iniciat el Core.");
                        }
                        break;
                    case 2:
                        if(isObjectInstanced(parking)){
                            cotxe = new Cotxe(parking, ((int) (Math.random() * 9999) + 1000)+" "+((int) (Math.random() * 999) + 100));
                            cotxe.start();
                        }
                        break;
                    case 3:
                        if(isObjectInstanced(parking)){
                            System.out.println("------------------------------------------");
                            System.out.println(parking.getController().getInforme(true));
                            System.out.println("------------------------------------------");
                        }
                        break;
                    case 4:
                        if(isObjectInstanced(parking)){
                            for (int i = 0; i < 25; i++) {
                                SimulatorWorker sw = new SimulatorWorker(parking);
                                sw.start();
                            }
                        }
                        break;
                    case 9:
                        System.out.println("S'acaba l'execució del programa");
                        System.exit(0);
                        break;
                }

            }else{
                sc.nextLine();
            }
        }
    }


    private static Boolean isObjectInstanced(Object o){
        if(o == null){
            System.out.println("No s'ha iniciat el Core del Parking, executa la opció 1 primerament.");
            return false;
        }else{
            return true;
        }
    }

}
