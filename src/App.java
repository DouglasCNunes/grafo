import grafo.*;
import java.io.File;
import java.util.Scanner;



public class App {

    static void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    static void menu() {
        System.out.println("\n-------------- Menu --------------");
        System.out.println("\n 1 - Obter cidades vizinhas de uma cidade.");
        System.out.println(" 2 - Obter todos os caminhos a partir de uma cidade.");
        System.out.println(" 3 - Sair.");
        System.out.print("\nEscolha uma das opçoes acima: ");
    }   


    public static void main(String[] args) throws Exception {
        //Leitura do arquivo de entrada.
        File file = new File("C:/Users/Douglas/Desktop/Codigos/grafo/src/entrada.txt");
        Scanner scan = new Scanner(file);

        Grafo<Cidade> grafo = new Grafo<>();
        String line;

        //Leitura da quantidade de elementos do arquivo de entrada.
        int quantElementos = Integer.valueOf(scan.nextLine());
        
        //Adicionar os elementos do arquivo de entrada(vértices) no grafo.
        for (int i = 0; i < quantElementos; i++) {
            line = scan.nextLine();
            System.out.println(line.toString()); //////
            String[] stringParts = line.split(";");

            //Criar um nova intancia de cidade e adiciona-la no grafo.
            Cidade cidade = new Cidade(Integer.valueOf(stringParts[0]), stringParts[1]);
            grafo.adicionarVertice(cidade);
        }
        
        //Adiciona as arestas ao grafo.
        for (int i = 0; i < quantElementos; i++) {
            //Pega os pesos das arestas no arquivo de entrada.
            line = scan.nextLine();
            System.out.println(line.toString()); ////////
            //Pega o valor de origem.
            Cidade origem = grafo.getVertices().get(i).getValor();
            String[] stringParts = line.split(";");
            //Para cada peso, adicionar uma aresta se for diferente de 0.
            for (int j = 0; j < stringParts.length; j++) {
                //Converter dados do arquivo de entrada em float.
                stringParts[j] = stringParts[j].replace(',','.');
                Float peso = Float.valueOf(stringParts[j]);
                //Pegar o valor de destino.
                Cidade destino = grafo.getVertices().get(j).getValor();
                //Se aresta foi diferente de 0, adicionar destino com peso na origem.
                if(Float.valueOf(stringParts[j]) != 0) {
                    System.out.println("ORIGEM>>  "+origem+"  <<DESTINO>>  "+destino+"  COM  "+peso+"  DE PESO.");
                    grafo.adicionarAresta(origem, destino, peso);
                }
            }
        }
        scan.close();
        grafo.buscaEmLargura();

        menu();
        Scanner input = new Scanner(System.in);
        int escolha;
        escolha = input.nextInt();
        while(escolha != 3);
            clearScreen();
            switch(escolha) {
                //Obter cidades vizinhas de uma cidade escolhida pelo usuário.
                case 1:
                    System.out.println("Digite o código de uma cidade:");
                    escolha = input.nextInt();
                    
                    break;

                //Obter todos os caminhos a partir de uma cidade escolhida pelo usuário.
                case 2:
                    System.out.println("Digite o código de uma cidade:");
                    escolha = input.nextInt();
                    break;
            }
    }
}
