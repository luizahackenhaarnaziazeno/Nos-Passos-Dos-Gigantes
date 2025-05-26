import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class DigrafoValorado {

    public static class Aresta {
        public final int w;
        public final int peso;

        public Aresta(int w, int peso) {
            this.w = w;
            this.peso = peso;
        }
    }

    private final int V;
    private final ArrayList<List<Aresta>> adj;

    public DigrafoValorado(int V) {
        this.V = V;
        this.adj = new ArrayList<>(V);
        for (int v = 0; v < V; v++) {
            this.adj.add(new LinkedList<>());
        }
    }

    public int getNumVertices() {
        return V;
    }

    public void adicionarAresta(int v, int w, int peso) {
        adj.get(v).add(new Aresta(w, peso));
    }

    public Iterable<Aresta> adjacentes(int v) {
        return adj.get(v);
    }
}
