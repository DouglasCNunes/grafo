package grafo;

import java.util.List;

public class Grafo<T> {
  private List<Vertice<T>> vertices;

  public void addVertice(Vertice<T> vertice) {
    vertices.add(vertice);
  }

  public List<Vertice<T>> getVertices() {
    return vertices;
  }
}