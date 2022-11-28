package grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Grafo<T> {
  private List<Vertice<T>> vertices;

  public Grafo() {
    vertices = new ArrayList<Vertice<T>>();
  }

  public List<Vertice<T>> getVertices() {
    return vertices;
  }

  public Vertice<T> adicionarVertice(T valor) {
    Vertice<T> novo = new Vertice<T>(valor);
    this.vertices.add(novo);
    return novo;
  }

  public Vertice<T> obterVertice(int identificador) {
    Vertice<T> v = this.vertices.get(identificador -1);
    return v;
  }

  private Vertice<T> obterVertice(T valor) {
    Vertice<T> v;
    for (int i = 0; i < this.vertices.size(); i++) {
      v = this.vertices.get(i);
      if(v.getValor().equals(valor))
        return v;
    }
    return null;
  }

  public void adicionarAresta(T origem, T destino, float peso) {
    Vertice verticeOrigem, verticeDestino;
    verticeOrigem = obterVertice(origem);

    //Se não existir origem, adiciona-la ao grafo
    if(verticeOrigem==null)
      verticeOrigem=adicionarVertice(origem);

    verticeDestino = obterVertice(destino);

    //Se não existir destino, adiciona-lo ao grafo
    if(verticeDestino==null) {
      verticeDestino=adicionarVertice(destino);
    }

    //adicionar o destino a lista de destinos da origem e o peso
    verticeOrigem.adicinarDestino(new Aresta(verticeDestino, peso));
  }

  public void obterVizinhos(int identificador) {
    ArrayList<Aresta> arestas = this.vertices.get(identificador -1).getDestinos();
    
    Aresta aresta;
    for (int i = 0; i < arestas.size(); i++) {
      aresta = arestas.get(i);
      System.out.println(aresta.getDestino().getValor().toString() + "  Peso:" + aresta.getPeso());
    }
  }

  public void buscaEmLargura(int identificador) {
    ArrayList<Vertice> marcados = new ArrayList<Vertice>();
    ArrayList<Vertice> fila = new ArrayList<Vertice>();

    //Recuperando o vertice da lista
    Vertice atual = this.vertices.get(identificador -1);
  
    fila.add(atual);

    //Enquanto houver vertice na fila a ser processados na busca
    while(fila.size() > 0) {
      //transforma o primeiro da fila no atual, imprima, remove-o e o adicione nos marcados (ja visitados)
      atual = fila.get(0);
      fila.remove(0);
      marcados.add(atual);
      System.out.println(atual.getValor().toString());
      //Pega a lista de nós adjacentes
      ArrayList<Aresta> destinos = atual.getDestinos();
      Vertice proximo;

      //Para cada nó não marcado (não visitado) próximos ao nó atual do laço, adicione-o na fila
      for (int i = 0; i < destinos.size(); i++) {
        proximo=destinos.get(i).getDestino();
        if(!marcados.contains(proximo) && !fila.contains(proximo)) {
          fila.add(proximo);
        }
      }
    }
  }


  public void dijkstra(Vertice<T> verticeOrigem, Vertice<T> verticeDestino) {
    float estimativas[] = new float[this.vertices.size()];
    int precedentes[] = new int [this.vertices.size()];
    int fechados[] = new int [this.vertices.size()]; //0 para abertos e 1 para fechados

    //distancia infinita para todos os elemenos: valor máximo para int.
    for (int i = 0; i < estimativas.length; i++) {
      estimativas[i] =  2147483000;
      fechados[i] = 0; //0 para abertos e 1 para fechados  
    }
    //Atribuir para estimativa da origem valor 0 e valor -1 para precedente.
    int indexOrigem = this.vertices.indexOf(verticeOrigem);
    estimativas[indexOrigem] = 0;
    precedentes[indexOrigem] = -1;
    
    int verticesAbertos = this.vertices.size();
    //Enquanto Houver vértice aberto:
    while(verticesAbertos > 0) {
      Vertice<T> verticeAtual = null;
      float estimativaAtual = 2147483647;
      int indexAtual = 0;
      //Pega um vertice aberto com menor estimativa.
      for (int i = 0; i < estimativas.length; i++) {
        if(fechados[i] == 0 && estimativas[i] < estimativaAtual) {
          estimativaAtual = estimativas[i];
          verticeAtual = this.vertices.get(i);
          indexAtual = i;
        }
      }
      fechados[indexAtual] = 1;
      //Para cada vizinho do vertice atual:
        if(verticeAtual != null) {
        for (int i = 0; i < verticeAtual.getDestinos().size(); i++) {
          //Pega o index do vertice do vizinho na lista vertices.
          int indexVizinho = vertices.indexOf(verticeAtual.getDestinos().get(i).getDestino());
          float pesoTotal = estimativas[indexAtual] + verticeAtual.getDestinos().get(i).getPeso();
          //Verificar se a o peso total é menor que a estimativa, se sim, então a estimativa e precedentes são atualizados.
          if(pesoTotal < estimativas[indexVizinho]) {
            estimativas[indexVizinho] = pesoTotal;
            precedentes[indexVizinho] = indexAtual;
          }
        }
    }
      verticesAbertos--;
    }
    //Imprimir a caminho mínimo da Origem até o destino
    int indexDestino = vertices.indexOf(verticeDestino);
    Vertice<T> cursor = null;
    cursor = vertices.get(precedentes[indexDestino]); //cursor começa com o precedente do destino
    //Para cada loop, o cursor recua para o seu precedente e imprime o caminho
    System.out.println("\n----------------------------------");
    System.out.println("Destino: "+ verticeDestino.getValor().toString());
    System.out.println();
    while(cursor != verticeOrigem) {
      System.out.println("Caminho: "+ cursor.getValor().toString());
      int indexCursor = vertices.indexOf(cursor);
      cursor = vertices.get(precedentes[indexCursor]);
    }
    //Imprime a origem, o comprimento do caminho mínimo
    System.out.println();
    System.out.println("Origem: " + verticeOrigem.getValor().toString());
    System.out.println();
    System.out.println("Comprimento do caminho mínimo: "+estimativas[indexDestino]);
  }

  public void prim(int origem) {
    Vertice<T> verticeInicial = this.vertices.get(origem -1);

    //Copiando vértices antigos e limpando lista de vértices
    List<Vertice<T>> verticesAntigos = this.vertices;
    this.vertices = new ArrayList<Vertice<T>>();
    
    //Adicionando vértice inicial
    this.adicionarVertice(verticeInicial.getValor());
    List<Vertice<T>> verticesAntigosAdicionados = new ArrayList<Vertice<T>>();
    verticesAntigosAdicionados.add(verticeInicial);

    //Iterando até que todos os vértices antigos estejam no grafo
    Vertice verticeOrigem = null;
    Aresta proximaAresta = null;
    while (this.vertices.size() != verticesAntigos.size()) {
    
      //Achando o vértice e aresta que serão adicionados
      for (Vertice verticeAdicionado: verticesAntigosAdicionados) {
        for (Aresta aresta: verticeAdicionado.getDestinos()) {
          if ((proximaAresta == null || aresta.getPeso() < proximaAresta.getPeso()) && !verticesAntigosAdicionados.contains(aresta.getDestino())) {
            verticeOrigem = verticeAdicionado;
            proximaAresta = aresta;
          }
        }
      }
    
      //Adicionando aresta
      this.adicionarAresta(verticeOrigem.getValor(), proximaAresta.getDestino().getValor(), proximaAresta.getPeso());
    
      //Adicionando vertice antigo na lista para busca de novas arestas
      verticesAntigosAdicionados.add(verticeOrigem);
    }
  }

  public void imprimirGrafo() {
    for (Vertice<T> vertice: vertices) {
      System.out.println("Vértice:");
      System.out.println(vertice.getValor().toString());
      System.out.println("Arestas:");
      for (Aresta aresta: vertice.getDestinos()) {
        System.out.println(aresta.getDestino().getValor().toString() + " Peso:" + aresta.getPeso());
      }
      System.out.println("");
    }
  }
}