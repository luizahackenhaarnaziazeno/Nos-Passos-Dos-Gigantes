/*
Projeto: Nos Passos dos Gigantes
Autora: Luiza Hackenhaar Naziazeno
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;



public class Grafos {

    private DigrafoValorado grafo;
    private boolean[] visitados;
    private int[] anteriores;
    private int[] custo;
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

        try {
            processarArquivo(arquivo);
        } catch (IOException e) {
            System.err.println(ANSI_RED + "Erro ao ler arquivo: " + e.getMessage());
        }

        scanner.close();
    }
    
    public static void processarArquivo(String caminhoArquivo) throws IOException {
        String pasta = "C:\\projects\\Facul\\T2 Jb\\Nos-Passos-Dos-Gigantes\\casosdeteste\\"; // Defina o nome da pasta onde estão os arquivos
        BufferedReader reader = new BufferedReader(new FileReader(pasta + caminhoArquivo));
        String linha;
        while ((linha = reader.readLine()) != null) {
            System.out.println(linha);
        }
        reader.close();
    }


    public void Bfs(DigrafoValorado grafo, int origem){
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_BLUE = "\u001B[34m";

        this.grafo = grafo;
        visitados = new boolean[grafo.getNumVertices()];
        anteriores = new int[grafo.getNumVertices()];
        custo = new int[grafo.getNumVertices()];
        for (int v = 0; v < anteriores.length; v++) {
            anteriores[v] = -1;
        }

        Queue<Integer> fila = new LinkedList<>();
        fila.add(origem);
        while (!fila.isEmpty()) {
            int v = fila.poll();
            visitados[v] = true;
            for(DigrafoValorado.Aresta aresta: grafo.adjacentes(v)) {
                if(!visitados[aresta.w]) {
                    fila.add(aresta.w);
                    anteriores[aresta.w] = v;
                    custo[aresta.w] = custo[v] + aresta.peso;
                    visitados[aresta.w] = true;
                }
            }
        }

        System.out.println("v  visitados  anteriores");
        for (int v = 0; v < grafo.getNumVertices(); v++) {
            System.out.println(
                ANSI_PURPLE + v + "  " +
                (visitados[v] ? ANSI_GREEN + visitados[v] : ANSI_RED + visitados[v]) + ANSI_PURPLE + "  " +
                ANSI_BLUE + anteriores[v] + ANSI_PURPLE + "  " +
                ANSI_CYAN + custo[v] + ANSI_PURPLE
            );
        }
    }
    
}
