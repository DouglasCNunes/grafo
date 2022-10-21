package grafo;

public class Aresta {
  private Vertice destino;
  private float peso;

  public Aresta(Vertice destino, float peso) {
    this.destino = destino;
    this.peso = peso;
  }

  public Vertice getDestino() {
    return destino;
  }

  public float getPeso() {
    return peso;
  }  
}