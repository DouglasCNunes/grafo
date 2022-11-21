package grafo;

import java.util.ArrayList;
import java.util.List;

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

  private Vertice<T> obterVertice(T valor) {
    Vertice<T> v;
    for (int i = 0; i < this.vertices.size(); i++) {
      v=this.vertices.get(i);
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

  public void dijkstra(int origem, int destino) {
    Vertice<T> verticeOrigem = this.vertices.get(origem -1);
    Vertice<T> verticeDestino = this.vertices.get(destino -1);


    float estimativas[] = new float[this.vertices.size()];
    int precedentes[] = new int [this.vertices.size()];
    int fechados[] = new int [this.vertices.size()]; //0 para abertos e 1 para fechados

    //distancia infinita para todos os elemenos: valor máximo para int.
    for (int i = 0; i < estimativas.length; i++) {
      estimativas[i] =  2147483647;
      fechados[i] = 0;    
    }
    //Atribuir para estimativa da origem valor 0 e valor -1 para precedente.
    int indexOrigem = this.vertices.indexOf(verticeOrigem);
    estimativas[indexOrigem] = 0;
    precedentes[indexOrigem] = -1;
    

    int verticesAbertos = this.vertices.size() -1;
    //Enquanto Houver vértice aberto:
    while(verticesAbertos > 0) {
      Vertice<T> verticeAtual = null;
      int estimativaAtual = 2147483647;
      int indexAtual = 0;
      //Pega um vertice aberto com menor estimativa.
      for (int i = 0; i < estimativas.length; i++) {
        if(fechados[i] == 0 && estimativas[i] < estimativaAtual) {
          fechados[i] = 1;
          verticeAtual = this.vertices.get(i);
          indexAtual = i;
        }
      }
      //Para dava vizinho do vertice atual:
      for (int i = 0; i < verticeAtual.getDestinos().size(); i++) {
        //Pega o index do vertice do vizinho na lista vertices.
        int indexVizinho = vertices.indexOf(verticeAtual.getDestinos().get(i));
        float pesoTotal = estimativas[indexAtual] + verticeAtual.getDestinos().get(i).getPeso();
        //Verificar se a o peso total é menor que a estimativa, se sim, então a estimativa e precedentes são atualizados.
        if(pesoTotal < estimativas[indexVizinho]) {
          estimativas[indexVizinho] = pesoTotal;
          precedentes[i] = indexAtual;
        }
      }
    }
    //Imprimir a caminho mínimo da Origem até o destino
    int indexDestino = vertices.indexOf(verticeDestino);
    Vertice<T> cursor = null;
    cursor = vertices.get(precedentes[indexDestino]); //cursor começa com o precedente do destino
    //Para cada loop, o cursor recua para o seu precedente
    while(cursor != verticeOrigem) {
      int indexCursor = vertices.indexOf(cursor);
      cursor = vertices.get(precedentes[indexCursor]);
      System.out.println(cursor.toString());
    }
    System.out.println("Origem: "+verticeOrigem.toString());
  }

  public void prim(int origem) {
    Vertice<T> verticeOrigem = this.vertices.get(origem -1);
    //Copiando vértices antigos e limpando lista de vértices
    List<Vertice<T>> verticesAntigos = this.vertices;
    this.vertices = new ArrayList<Vertice<T>>();

    while (novosVertices.size() != this.vertices.size()) {
      // TODO
    }
    
  }

  public void imprimirGrafo() {
    for (Vertice<T> vertice: vertices) {
      System.out.println("Vértice " + vertice.getValor().toString());
      System.out.println("Arestas:");
      for (Aresta aresta: vertice.getDestinos()) {
        System.out.println(aresta.getDestino().getValor().toString() + " Peso:" + aresta.getPeso());
      }
      System.out.println("");
    }
  }
}