package grafo;

import java.util.ArrayList;

public class Vertice<T> {
  private T valor;
  private ArrayList<Aresta> destinos;

  public Vertice(T valor) {
    this.valor = valor;
  }

  public T getValor() {
    return valor;
  }

  public void adicinarDestino(Aresta destino) {
    this.destinos.add(destino);
  }

public ArrayList<Aresta> getDestinos() {
    return destinos;
}
}