import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NosPassosDosGigantes {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_ROYAL_BLUE = "\u001B[38;2;65;105;225m";

    private static final int[] dLinha = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] dColuna = { -1, 0, 1, -1, 1, -1, 0, 1 };

    private static boolean ehValido(int linha, int coluna, int linhas, int colunas) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    private static int getAltura(char pedra) {
        if (pedra == 'S') {
            return 'a';
        }
        return pedra;
    }

   public static List<int[]> encontrarMenorCaminho(char[][] mapa, int linhas, int colunas) {
        int[][] distancia = new int[linhas][colunas];
        int[][][] pai = new int[linhas][colunas][2];
        int startLinha = -1, startColuna = -1;
        int endLinha = -1, endColuna = -1;

        for (int i = 0; i < linhas; i++) {
            Arrays.fill(distancia[i], -1);
            for (int j = 0; j < colunas; j++) {
                pai[i][j] = null;
                if (mapa[i][j] == 'S') {
                    startLinha = i;
                    startColuna = j;
                }
            }
        }

        if (startLinha == -1) {
            System.err.println(ANSI_RED + "Erro: Ponto de partida 'S' não encontrado!" + ANSI_RESET);
            return null;
        }

        Queue<int[]> fila = new LinkedList<>();
        fila.add(new int[] { startLinha, startColuna });
        distancia[startLinha][startColuna] = 0;

        while (!fila.isEmpty()) {
            int[] vertice = fila.poll();
            int linhaAtual = vertice[0];
            int colunaAtual = vertice[1];
            int alturaAtual = getAltura(mapa[linhaAtual][colunaAtual]);

            if (mapa[linhaAtual][colunaAtual] == 'z') {
                endLinha = linhaAtual;
                endColuna = colunaAtual;
                return reconstruirCaminho(pai, startLinha, startColuna, endLinha, endColuna);
            }

            for (int i = 0; i < 8; i++) {
                int novaLinha = linhaAtual + dLinha[i];
                int novaColuna = colunaAtual + dColuna[i];

                if (ehValido(novaLinha, novaColuna, linhas, colunas) && distancia[novaLinha][novaColuna] == -1) {
                    int alturaVizinho = getAltura(mapa[novaLinha][novaColuna]);

                    if (alturaVizinho - alturaAtual <= 1) {
                        fila.add(new int[] { novaLinha, novaColuna });
                        distancia[novaLinha][novaColuna] = distancia[linhaAtual][colunaAtual] + 1;
                        pai[novaLinha][novaColuna] = new int[] { linhaAtual, colunaAtual };
                    }
                }
            }
        }

        return null;
    }

    private static List<int[]> reconstruirCaminho(int[][][] pai, int startL, int startC, int endL, int endC) {
        List<int[]> caminho = new ArrayList<>();
        int[] atual = { endL, endC };

        while (atual != null) {
            caminho.add(atual);
            if (atual[0] == startL && atual[1] == startC) {
                break;
            }
            atual = pai[atual[0]][atual[1]];
        }
        Collections.reverse(caminho);
        return caminho;
    }

    private static void imprimirMapaComCaminho(char[][] mapa, List<int[]> caminho, Set<String> visitados, int linhas,
            int colunas) {
        Set<String> pontosDoCaminho = new HashSet<>();
        if (caminho != null) {
            for (int[] ponto : caminho) {
                pontosDoCaminho.add(ponto[0] + "," + ponto[1]);
            }
        }

        System.out.println(ANSI_CYAN + "\nMapa com o Caminhamento e Caminho Encontrado:" + ANSI_RESET);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                String chave = i + "," + j;
                if (pontosDoCaminho.contains(chave)) {

                    System.out.print(ANSI_RED + mapa[i][j] + ANSI_RESET);
                } else if (visitados != null && visitados.contains(chave)) {

                    System.out.print(ANSI_YELLOW + mapa[i][j] + ANSI_RESET);
                } else {

                    System.out.print(mapa[i][j]);
                }
            }
            System.out.println();
        }

    }

    private static void processarEntrada(Scanner scanner, String sourceName) {
        int casoNum = 1;

        while (scanner.hasNextInt()) {
    
            try {
                int linhas = scanner.nextInt();
                int colunas = scanner.nextInt();
                scanner.nextLine();

                char[][] mapa = new char[linhas][colunas];
                for (int i = 0; i < linhas; i++) {
                    if (scanner.hasNextLine()) {
                        mapa[i] = scanner.nextLine().toCharArray();
                    } else {
                        System.err.println(ANSI_RED + "Erro: Entrada inesperada para " + sourceName + " (Caso "
                                + casoNum + ")" + ANSI_RESET);
                        return;
                    }
                }

                List<int[]> caminho = encontrarMenorCaminho(mapa, linhas, colunas);

                if (caminho != null) {
                    imprimirMapaComCaminho(mapa, caminho, null, linhas, colunas);

                    System.out.println(ANSI_ROYAL_BLUE + "\nO menor caminho encontrado para " + sourceName + " tem "
                            + (caminho.size() - 1) + " passos." + ANSI_RESET);
                } else {
                    imprimirMapaComCaminho(mapa, null, null, linhas, colunas);

                    System.out.println(ANSI_YELLOW + "\nCaminho não encontrado para " + sourceName + "." + ANSI_RESET);
                }

                casoNum++;

               
            } catch (Exception e) {
                System.err.println(ANSI_RED + "Ocorreu um erro ao processar " + sourceName + " (Caso " + casoNum + "): "
                        + e.getMessage() + ANSI_RESET);
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        int opcao;

        while (continuar) {
            System.out.println(ANSI_PURPLE + "=========================================");
            System.out.println("     Bem-vindo aos Passos Dos Gigantes!");
            System.out.println("=========================================" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "Escolha um dos casos disponíveis:");
            System.out.println(ANSI_GREEN + "1 - casoa.txt");
            System.out.println("2 - casob.txt");
            System.out.println("3 - casoc.txt");
            System.out.println("4 - casod.txt");
            System.out.println("5 - casoe.txt");
            System.out.println("6 - casof.txt");
            System.out.println("7 - casog.txt");
            System.out.println("8 - casoh.txt");
            System.out.println(ANSI_GREEN + "9 - Sair" + ANSI_RESET);
            System.out.print(ANSI_CYAN + "Digite o número do caso desejado: " + ANSI_RESET);

            try {
                opcao = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println(ANSI_RED + "\nEntrada inválida. Por favor, digite um NÚMERO." + ANSI_RESET);
                scanner.close();
                return;
            }
            String arquivo = "";
            switch (opcao) {
                case 1:
                    arquivo = "casoa.txt";
                    break;
                case 2:
                    arquivo = "casob.txt";
                    break;
                case 3:
                    arquivo = "casoc.txt";
                    break;
                case 4:
                    arquivo = "casod.txt";
                    break;
                case 5:
                    arquivo = "casoe.txt";
                    break;
                case 6:
                    arquivo = "casof.txt";
                    break;
                case 7:
                    arquivo = "casog.txt";
                    break;
                case 8:
                    arquivo = "casoh.txt";
                    break;
                case 9:
                    System.out.println(ANSI_ROYAL_BLUE + "\nSaindo do programa. Até logo!" + ANSI_RESET);
                    continuar = false;
                    break;
                default:
                    System.out.println(ANSI_RED + "\nOpção inválida." + ANSI_RESET);
            }

            if (!arquivo.isEmpty()) {
                System.out.println(ANSI_GREEN + "\nProcessando " + arquivo + "..." + ANSI_RESET);

                try {
                    File file = new File(
                            "C:\\projects\\Facul\\T2 Jb\\Nos-Passos-Dos-Gigantes\\casosdeteste\\" + arquivo);
                    try (Scanner fileScanner = new Scanner(file)) {
                        processarEntrada(fileScanner, arquivo);
                    }
                } catch (FileNotFoundException e) {
                    System.err.println(ANSI_RED + "Erro: Arquivo '" + arquivo
                            + "' não encontrado na pasta 'casos'! Certifique-se de que ele está na pasta correta."
                            + ANSI_RESET);
                }
            }
            System.out.println(ANSI_PURPLE + "\nPrograma finalizado." + ANSI_RESET);
        }
        scanner.close();
    }
}