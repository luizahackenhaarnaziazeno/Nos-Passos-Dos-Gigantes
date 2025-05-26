/*
Projeto: Nos Passos dos Gigantes
Autora: Luiza Hackenhaar Naziazeno
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Grafos {

    private DigrafoValorado grafo;
    private boolean[] visitados;
    private int[] anteriores;
    private char[][] mapa;
    private int R, C;
    private int verticeInicial = -1;

    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_PURPLE = "\u001B[35m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_CYAN = "\u001B[36m";
    final String ANSI_BLUE = "\u001B[34m";

    private int getAltura(char ch) {
        if (ch == 'S') return 0;
        if (ch >= 'a' && ch <= 'z') return ch - 'a';
        return Integer.MAX_VALUE;
    }

    private boolean evalido(int r, int c) {
        return (r >= 0) && (r < R) && (c >= 0) && (c < C);
    }

    private int PorIndex(int r, int c) {
        return r * C + c;
    }

    private int toRow(int index) {
        return index / C;
    }

    private int toCol(int index) {
        return index % C;
    }

    public static void main(String[] args) {

        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_RESET = "\u001B[0m";

        Scanner scanner = new Scanner(System.in);

        System.out.println(ANSI_PURPLE + "=========================================");
        System.out.println("      Bem-vindo aos Passos Dos Gigantes!");
        System.out.println("=========================================" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Escolha um dos casos disponíveis:");
        System.out.println(ANSI_GREEN + "1 - caso020.txt");
        System.out.println("2 - caso040.txt");
        System.out.println("3 - caso080.txt");
        System.out.println("4 - caso120.txt");
        System.out.println("5 - caso150.txt");
        System.out.println("6 - caso180.txt");
        System.out.println("7 - caso250.txt");
        System.out.println("8 - caso500.txt" + ANSI_RESET);
        System.out.print(ANSI_CYAN + "Digite o número do caso desejado: " + ANSI_RESET);

        int opcao = scanner.nextInt();
        String arquivo = "";

        switch (opcao) {
            case 1: arquivo = "caso020.txt"; break;
            case 2: arquivo = "caso040.txt"; break;
            case 3: arquivo = "caso080.txt"; break;
            case 4: arquivo = "caso120.txt"; break;
            case 5: arquivo = "caso150.txt"; break;
            case 6: arquivo = "caso180.txt"; break;
            case 7: arquivo = "caso250.txt"; break;
            case 8: arquivo = "caso500.txt"; break;
            default:
                System.out.println(ANSI_RED + "Opção inválida." + ANSI_RESET);
                scanner.close();
                return;
        }

        System.out.println(ANSI_GREEN + "\nProcessando " + arquivo + "..." + ANSI_RESET);
        Grafos problema = new Grafos();
        try {
            problema.processarArquivoEConstruirGrafo(arquivo);
            if (problema.verticeInicial != -1) {
                int verticeFinal = problema.Bfs();
                problema.processarEImprimirResultado(verticeFinal);
            }
        } catch (IOException e) {
            System.err.println(ANSI_RED + "Erro ao ler arquivo: " + e.getMessage() + ANSI_RESET);
        }

        scanner.close();
    }

    public void processarArquivoEConstruirGrafo(String nomeArquivo) throws IOException {
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_RESET = "\u001B[0m";

        String pasta = "casosdeteste";
        String caminhoCompleto = pasta + File.separator + nomeArquivo;
        File f = new File(caminhoCompleto);
        if (!f.exists()) {
             f = new File(nomeArquivo);
             if (!f.exists()) {
                System.err.println(ANSI_RED + "ARQUIVO NÃO ENCONTRADO: " + caminhoCompleto + " ou " + f.getAbsolutePath() + ANSI_RESET);
                this.verticeInicial = -1;
                return;
            }
            caminhoCompleto = nomeArquivo;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoCompleto))) {
            String linha = reader.readLine();
            if (linha == null) {
                System.out.println(ANSI_RED + "Arquivo vazio." + ANSI_RESET);
                return;
            }
            String[] dimensoes = linha.trim().split("\\s+");
            this.R = Integer.parseInt(dimensoes[0]);
            this.C = Integer.parseInt(dimensoes[1]);
            this.mapa = new char[R][C];
            int numVertices = R * C;
            this.grafo = new DigrafoValorado(numVertices);
            this.verticeInicial = -1;
            System.out.println(ANSI_CYAN + "Mapa " + R + "x" + C + " carregando..." + ANSI_RESET);
            int linhaAtual = 0;
            while ((linha = reader.readLine()) != null && linhaAtual < R) {
                this.mapa[linhaAtual] = linha.toCharArray();
                for (int col = 0; col < C; col++) {
                    if (this.mapa[linhaAtual][col] == 'S') {
                        this.verticeInicial = PorIndex(linhaAtual, col);
                    }
                }
                linhaAtual++;
            }
            if (this.verticeInicial == -1) {
                System.err.println(ANSI_RED + "Erro: Ponto 'S' não encontrado." + ANSI_RESET);
                return;
            }
            System.out.println(ANSI_CYAN + "Construindo grafo..." + ANSI_RESET);
            int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    int v = PorIndex(r, c);
                    int h1 = getAltura(mapa[r][c]);
                    for (int i = 0; i < 8; i++) {
                        int nr = r + dr[i];
                        int nc = c + dc[i];
                        if (evalido(nr, nc)) {
                            int w = PorIndex(nr, nc);
                            int h2 = getAltura(mapa[nr][nc]);
                            if (h2 <= h1 + 1) {
                                grafo.adicionarAresta(v, w, 1);
                            }
                        }
                    }
                }
            }
            System.out.println(ANSI_CYAN + "Grafo construído." + ANSI_RESET);
        }
    }

    public int Bfs() {
        if (grafo == null || verticeInicial == -1)
            return -1;

        int numVertices = grafo.getNumVertices();
        this.visitados = new boolean[numVertices];
        int[] distancia = new int[numVertices];
        this.anteriores = new int[numVertices];

        for (int v = 0; v < numVertices; v++) {
            distancia[v] = -1;
            this.anteriores[v] = -1;
        }

        Queue<Integer> fila = new LinkedList<>();

        fila.add(verticeInicial);
        this.visitados[verticeInicial] = true;
        distancia[verticeInicial] = 0;

        while (!fila.isEmpty()) {
            int v = fila.poll();

            if (mapa[toRow(v)][toCol(v)] == 'z') {
                return v;
            }

            for (DigrafoValorado.Aresta aresta : grafo.adjacentes(v)) {
                int w = aresta.w;
                if (!this.visitados[w]) {
                    this.visitados[w] = true;
                    distancia[w] = distancia[v] + 1;
                    this.anteriores[w] = v;
                    fila.add(w);
                }
            }
        }
        return -1;
    }

    private List<Integer> reconstruirCaminho(int verticeFinal) {
        List<Integer> caminho = new ArrayList<>();
        if (verticeFinal == -1 || this.anteriores == null) {
            return caminho;
        }

        int atual = verticeFinal;
        while (atual != -1) {
            caminho.add(atual);
            atual = this.anteriores[atual];
        }
        Collections.reverse(caminho);
        return caminho;
    }

    private void imprimirMapaComCaminho(List<Integer> caminhoList) {
        Set<Integer> caminhoSet = new HashSet<>(caminhoList);

        System.out.println("\n" + ANSI_CYAN + "Mapa com o Caminho Encontrado:" + ANSI_RESET);
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                int index = PorIndex(r, c);
                char pedra = mapa[r][c];
                if (caminhoSet.contains(index)) {
                    System.out.print(ANSI_RED + pedra + ANSI_RESET);
                } else {
                    System.out.print(mapa[r][c]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void processarEImprimirResultado(int verticeFinal) {
        List<Integer> caminho = reconstruirCaminho(verticeFinal);

        if (verticeFinal != -1 && !caminho.isEmpty()) {
            int passos = caminho.size() - 1;
            System.out.println(
                    ANSI_GREEN + ">> Sucesso! Menor número de passos para chegar a 'z': " + passos + ANSI_RESET);
            imprimirMapaComCaminho(caminho);
        } else {
            System.out.println(ANSI_RED + ">> Que pena! Não foi possível encontrar um caminho até 'z'." + ANSI_RESET);
        }
    }
}