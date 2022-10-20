package grafo;

import java.io.File;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        File file = new File(System.getProperty("user.dir") + "/entrada.txt");
        Scanner scan = new Scanner(file);
        
        Grafo grafo = new Grafo();
    }
}
