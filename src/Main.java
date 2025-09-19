import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String nombre = "";
        String apellidos = "";
        MiFecha cumple = new MiFecha();
        Scanner sc = new Scanner(System.in);
        boolean valida = false;

        System.out.println("Dame tu nombre: ");
        nombre = sc.nextLine();
        System.out.println("Dame tus apellidos: ");
        apellidos = sc.nextLine();
        System.out.println("Ahora quiero tu fecha de nacimiento");



        while (!valida){
            System.out.println("El día: ");
            cumple.setDia(sc.nextInt());
            System.out.println("El mes: ");
            cumple.setMes(sc.nextInt());
            System.out.println("El año: ");
            cumple.setAnyo(sc.nextInt());
            valida = cumple.esValida();
            if (!valida){
                System.out.println("La fecha no es válida, vuelve a intentarlo");
            }
        }

        System.out.println("Hola, " + nombre + " " + apellidos);
        System.out.println("Tu fecha de nacimiento es: " + cumple.toString());







    }
}