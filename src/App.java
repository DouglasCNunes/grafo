import grafo.*;
import java.io.File;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        Grafo<Cidade> grafo = new Grafo<>();
        lerArquivo(grafo);
        
        Scanner input = new Scanner(System.in);
        int escolha;
        menu();
        escolha = input.nextInt();
        while(escolha != 7) {
            clearScreen();
            switch(escolha) {
                //Obter cidades vizinhas de uma cidade escolhida pelo usuário.
                case 1:
                    System.out.println("Digite o código de uma cidade:");
                    escolha = input.nextInt();
                    grafo.obterVizinhos(escolha);
                    break;
                //Obter todos os caminhos a partir de uma cidade escolhida pelo usuário.
                case 2:
                    System.out.println("Digite o código de uma cidade:");
                    escolha = input.nextInt();
                    grafo.buscaEmLargura(escolha);
                    break;
                case 3:
                //Algoritmo de Dijkstra
                    System.out.println("Digite a origem (código da cidade): ");
                    escolha = input.nextInt();
                    System.out.println("Digite o destino (código da cidade):");
                    // grafo.dijkstra(null, null);
                    break;
                case 4:
                //Caminho mínimo
                case 5:
                //Ford-Fulkerson
                case 6:
                //Imprimir grafo
                    grafo.imprimirGrafo();
            }
            menu();
            escolha = input.nextInt();
        }
        input.close();
    }

    static void lerArquivo(Grafo<Cidade> grafo) throws Exception {
        File file = new File("entrada.txt");
        //File file = new File(System.getProperty("user.dir") + "/entrada.txt");
        Scanner scanner = new Scanner(file);
        String line;

        //Leitura da quantidade de elementos do arquivo de entrada.
        int quantElementos = Integer.valueOf(scanner.nextLine());
        
        //Adicionando os elementos do arquivo de entrada (vértices) no grafo.
        for (int i = 0; i < quantElementos; i++) {
            line = scanner.nextLine();
            // System.out.println(line.toString());
            String[] stringParts = line.split(";");

            //Criar um nova intancia de cidade e adiciona-la no grafo.
            Cidade cidade = new Cidade(Integer.valueOf(stringParts[0]), stringParts[1]);
            grafo.adicionarVertice(cidade);
        }

        // Adicionando as arestas destinos aos vértices.
        for (int i = 0; i < quantElementos; i++) {
            //Pega os pesos das arestas no arquivo de entrada.
            line = scanner.nextLine();
            // System.out.println(line.toString());
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
                    // System.out.println("ORIGEM>>  "+origem+"  <<DESTINO>>  "+destino+"  COM  "+peso+"  DE PESO.");
                    grafo.adicionarAresta(origem, destino, peso);
                }
            }
        }
        scanner.close();
    }

    static void menu() {
        System.out.println("\n-------------- Menu --------------");
        System.out.println("\n 1 - Obter cidades vizinhas de uma cidade.");
        System.out.println(" 2 - Obter todos os caminhos a partir de uma cidade.");
        System.out.println(" 3 - Dijkstra.");
        System.out.println(" 4 - Caminho mínimo. - TODO");
        System.out.println(" 5 - Ford-Fulkerson. - TODO");
        System.out.println(" 6 - Imprimir grafo.");
        System.out.println(" 7 - Sair.");
        System.out.print("\nEscolha uma das opçoes acima: ");
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }
}
