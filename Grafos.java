/*
Projeto: Nos Passos dos Gigantes
Autora: Luiza Hackenhaar Naziazeno
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Grafos {

    private DigrafoValorado grafo;
    private boolean[] visitados;
    private int[] anteriores;
    private int[] custo;
    private char[][] mapa;
    private int R, C;
    private int verticeInicial = -1;

    final String ANSI_PURPLE = "\u001B[35m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_CYAN = "\u001B[36m";
    final String ANSI_BLUE = "\u001B[34m";

    // --- Métodos Auxiliares (Inalterados) ---
    private int getAltura(char ch) {
        if (ch == 'S')
            return 0;
        if (ch >= 'a' && ch <= 'z')
            return ch - 'a';
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
        final String ANSI_BLUE = "\u001B[34m";

        Scanner scanner = new Scanner(System.in);

        System.out.println(ANSI_PURPLE + "=========================================");
        System.out.println("      Bem-vindo aos Passos Dos Gigantes!");
        System.out.println("=========================================");
        System.out.println(ANSI_CYAN + "Escolha um dos casos disponíveis:");
        System.out.println(ANSI_GREEN + "1 - caso20");
        System.out.println("2 - caso40");
        System.out.println("3 - caso80");
        System.out.println("4 - caso120");
        System.out.println("5 - caso150");
        System.out.println("6 - caso180");
        System.out.println("7 - caso250");
        System.out.println("8 - caso500");
        System.out.print(ANSI_CYAN + "Digite o número do caso desejado: ");

        int opcao = scanner.nextInt();
        String arquivo = "";

        switch (opcao) {
            case 1:
                arquivo = "caso020.txt";
                break;
            case 2:
                arquivo = "caso040.txt";
                break;
            case 3:
                arquivo = "caso080.txt";
                break;
            case 4:
                arquivo = "caso120.txt";
                break;
            case 5:
                arquivo = "caso150.txt";
                break;
            case 6:
                arquivo = "caso180.txt";
                break;
            case 7:
                arquivo = "caso250.txt";
                break;
            case 8:
                arquivo = "caso500.txt";
                break;
            default:
                System.out.println(ANSI_RED + "Opção inválida.");
                scanner.close();
                return;
        }

        System.out.println(ANSI_GREEN + "\nProcessando " + arquivo + "...\n");
        Grafos problema = new Grafos();
        try {
            problema.processarArquivoEConstruirGrafo(arquivo);
            if (problema.verticeInicial != -1) {
                int verticeFinal = problema.Bfs(); // Agora Bfs retorna o índice final
                problema.processarEImprimirResultado(verticeFinal); // Novo método
            }
        } catch (IOException e) {
            System.err.println(ANSI_RED + "Erro ao ler arquivo: " + e.getMessage() + ANSI_RED);
        }

        scanner.close();
    }

    public void processarArquivoEConstruirGrafo(String nomeArquivo) throws IOException {
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_BLUE = "\u001B[34m";

        String pasta = "C:\\projects\\Facul\\T2 Jb\\Nos-Passos-Dos-Gigantes\\casosdeteste\\";
        String caminhoCompleto = pasta + File.separator + nomeArquivo;
        File f = new File(caminhoCompleto);
        if (!f.exists()) {
            System.err.println(ANSI_RED + "ARQUIVO NÃO ENCONTRADO: " + f.getAbsolutePath() + ANSI_RED);
            this.verticeInicial = -1;
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoCompleto))) {
            String linha = reader.readLine();
            if (linha == null) {
                System.out.println(ANSI_RED + "Arquivo vazio." + ANSI_RED);
                return;
            }
            String[] dimensoes = linha.trim().split("\\s+");
            this.R = Integer.parseInt(dimensoes[0]);
            this.C = Integer.parseInt(dimensoes[1]);
            this.mapa = new char[R][C];
            int numVertices = R * C;
            this.grafo = new DigrafoValorado(numVertices);
            this.verticeInicial = -1;
            System.out.println(ANSI_CYAN + "Mapa " + R + "x" + C + " carregando..." + ANSI_RED);
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
                System.err.println(ANSI_RED + "Erro: Ponto 'S' não encontrado." + ANSI_RED);
                return;
            }
            System.out.println(ANSI_CYAN + "Construindo grafo..." + ANSI_RED);
            int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
            int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };
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
            System.out.println(ANSI_CYAN + "Grafo construído." + ANSI_RED);
        }
    }

    public int Bfs() { // Agora retorna o índice do 'z' encontrado
        if (grafo == null || verticeInicial == -1)
            return -1;

        int numVertices = grafo.getNumVertices();
        boolean[] visitados = new boolean[numVertices];
        int[] distancia = new int[numVertices];
        this.anteriores = new int[numVertices]; // Inicializa o array

        for (int v = 0; v < numVertices; v++) {
            distancia[v] = -1;
            this.anteriores[v] = -1; // -1 indica sem antecessor
        }

        Queue<Integer> fila = new LinkedList<>();

        fila.add(verticeInicial);
        visitados[verticeInicial] = true;
        distancia[verticeInicial] = 0;

        while (!fila.isEmpty()) {
            int v = fila.poll();

            if (mapa[toRow(v)][toCol(v)] == 'z') {
                return v; // Retorna o ÍNDICE do 'z' encontrado
            }

            for (DigrafoValorado.Aresta aresta : grafo.adjacentes(v)) {
                int w = aresta.w;
                if (!visitados[w]) {
                    visitados[w] = true;
                    distancia[w] = distancia[v] + 1;
                    this.anteriores[w] = v; // GUARDA O CAMINHO
                    fila.add(w);
                }
            }
        }
        return -1; // Não encontrou 'z'
    }

    // --- NOVO: Reconstrução do Caminho ---
    private List<Integer> reconstruirCaminho(int verticeFinal) {
        List<Integer> caminho = new ArrayList<>();
        if (verticeFinal == -1 || this.anteriores == null) {
            return caminho; // Retorna lista vazia se não achou ou não rodou BFS
        }

        int atual = verticeFinal;
        while (atual != -1) {
            caminho.add(atual);
            atual = this.anteriores[atual]; // Volta para o anterior
        }
        Collections.reverse(caminho); // Inverte para ter S -> z
        return caminho;
    }

    // --- NOVO: Impressão do Mapa Colorido ---
    private void imprimirMapaComCaminho(List<Integer> caminhoList) {
        Set<Integer> caminhoSet = new HashSet<>(caminhoList); // Para lookup rápido

        System.out.println("\n" + ANSI_CYAN + "Mapa com o Caminho Encontrado:" + ANSI_GREEN);
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                int index = PorIndex(r, c);
                char pedra = mapa[r][c];
                if (caminhoSet.contains(index)) {
                    // Pinta o caminho (S, z e o meio)
                    System.out.print(ANSI_RED + pedra + ANSI_RED);
                } else {
                    // Imprime normalmente, talvez com cinza claro (opcional)
                    System.out.print(mapa[r][c]);
                }
            }
            System.out.println(); // Nova linha
        }
        System.out.println();
    }

    // --- NOVO: Método para Processar e Imprimir ---
    private void processarEImprimirResultado(int verticeFinal) {
        List<Integer> caminho = reconstruirCaminho(verticeFinal);

        if (verticeFinal != -1 && !caminho.isEmpty()) {
            int passos = caminho.size() - 1; // Número de passos é o tamanho - 1
            System.out.println(
                    ANSI_GREEN + ">> Sucesso! Menor número de passos para chegar a 'z': " + passos + ANSI_BLUE);
            imprimirMapaComCaminho(caminho); // Chama a impressão colorida
        } else {
            System.out.println(ANSI_RED + ">> Que pena! Não foi possível encontrar um caminho até 'z'." + ANSI_RED);
        }
    }

}
