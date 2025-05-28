import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Arrays; // Importado para preencher a matriz de distância

/**
 * Classe principal para resolver o problema da Calçada dos Gigantes.
 * Usa um menu interativo e uma abordagem BFS baseada no exemplo.
 */
public class CalcadaDosGigantes {

    // Códigos ANSI para cores no console
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    // Vetores para representar os 8 movimentos possíveis (vizinhos)
    private static final int[] dLinha = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] dColuna = {-1, 0, 1, -1, 1, -1, 0, 1};

  
    private static boolean ehValido(int linha, int coluna, int linhas, int colunas) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

 
    private static int getAltura(char pedra) {
        if (pedra == 'S') {
            return 'a'; 
        }
        return pedra;
    }

     
    public static int encontrarMenorCaminho(char[][] mapa, int linhas, int colunas) {
        int[][] distancia = new int[linhas][colunas]; 
        int startLinha = -1, startColuna = -1;

        
        for (int i = 0; i < linhas; i++) {
            Arrays.fill(distancia[i], -1); 
            for (int j = 0; j < colunas; j++) {
                if (mapa[i][j] == 'S') {
                    startLinha = i;
                    startColuna = j;
                }
            }
        }

        
        if (startLinha == -1) {
            System.err.println(ANSI_RED + "Erro: Ponto de partida 'S' não encontrado no mapa!" + ANSI_RESET);
            return -1;
        }

     
        Queue<int[]> fila = new LinkedList<>(); 

        fila.add(new int[]{startLinha, startColuna}); 
        distancia[startLinha][startColuna] = 0; 

        while (!fila.isEmpty()) {
            int[] vertice = fila.poll(); 
            int linhaAtual = vertice[0];
            int colunaAtual = vertice[1];
            int alturaAtual = getAltura(mapa[linhaAtual][colunaAtual]);

           
            if (mapa[linhaAtual][colunaAtual] == 'z') {
                return distancia[linhaAtual][colunaAtual];
            }

            for (int i = 0; i < 8; i++) {
                int novaLinha = linhaAtual + dLinha[i];
                int novaColuna = colunaAtual + dColuna[i];

              
                if (ehValido(novaLinha, novaColuna, linhas, colunas) && distancia[novaLinha][novaColuna] == -1) {
                    int alturaVizinho = getAltura(mapa[novaLinha][novaColuna]);

                   
                    if (alturaVizinho - alturaAtual <= 1) {
                        fila.add(new int[]{novaLinha, novaColuna});
                        distancia[novaLinha][novaColuna] = distancia[linhaAtual][colunaAtual] + 1;
                    }
                }
            }
        }
      

        return -1; 
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
                        System.err.println(ANSI_RED + "Erro: Entrada inesperada para " + sourceName + " (Caso " + casoNum + ")" + ANSI_RESET);
                        return;
                    }
                }

                int resultado = encontrarMenorCaminho(mapa, linhas, colunas);
                System.out.println(ANSI_YELLOW + "O menor valor encontrado para " + sourceName + " foi: " 
                                 + (resultado != -1 ? resultado : "Caminho não encontrado") + ANSI_RESET);
                casoNum++;

            } catch (Exception e) {
                System.err.println(ANSI_RED + "Ocorreu um erro ao processar " + sourceName + " (Caso " + casoNum + "): " + e.getMessage() + ANSI_RESET);
                e.printStackTrace();
                break;
            }
        }
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); 
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
        System.out.println("8 - casoh.txt" );
        System.out.println( "9 - Sair" + ANSI_RESET);
        System.out.print(ANSI_CYAN + "Digite o número do caso desejado: " + ANSI_RESET);

        int opcao = -1;
        try {
            opcao = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println(ANSI_RED + "\nEntrada inválida. Por favor, digite um NÚMERO." + ANSI_RESET);
            scanner.close();
            return;
        }

        String arquivo = "";
        while (opcao != 9) {
            switch (opcao) {
            case 1: arquivo = "casoa.txt"; break;
            case 2: arquivo = "casob.txt"; break;
            case 3: arquivo = "casoc.txt"; break;
            case 4: arquivo = "casod.txt"; break;
            case 5: arquivo = "casoe.txt"; break;
            case 6: arquivo = "casof.txt"; break;
            case 7: arquivo = "casog.txt"; break;
            case 8: arquivo = "casoh.txt"; break;
            default:
                System.out.println(ANSI_RED + "\nOpção inválida." + ANSI_RESET);
                arquivo = "";
            }

            if (!arquivo.isEmpty()) {
            System.out.println(ANSI_GREEN + "\nProcessando " + arquivo + "..." + ANSI_RESET);

            try {
                File file = new File("C:\\projects\\Facul\\T2 Jb\\Nos-Passos-Dos-Gigantes\\casosdeteste\\" + arquivo);
                try (Scanner fileScanner = new Scanner(file)) {
                processarEntrada(fileScanner, arquivo);
                }
            } catch (FileNotFoundException e) {
                System.err.println(ANSI_RED + "Erro: Arquivo '" + arquivo + "' não encontrado na pasta 'casos'! Certifique-se de que ele está na pasta correta." + ANSI_RESET);
            }
            }

            // Mostra o menu novamente
            System.out.println(ANSI_CYAN + "\nEscolha um dos casos disponíveis:");
            System.out.println(ANSI_GREEN + "1 - casoa.txt");
            System.out.println("2 - casob.txt");
            System.out.println("3 - casoc.txt");
            System.out.println("4 - casod.txt");
            System.out.println("5 - casoe.txt");
            System.out.println("6 - casof.txt");
            System.out.println("7 - casog.txt");
            System.out.println("8 - casoh.txt" + ANSI_RESET);
            System.out.println("9 - Sair");
            System.out.print(ANSI_CYAN + "Digite o número do caso desejado: " + ANSI_RESET);

            try {
            opcao = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
            System.out.println(ANSI_RED + "\nEntrada inválida. Por favor, digite um NÚMERO." + ANSI_RESET);
            scanner.next(); // Limpa entrada inválida
            opcao = -1;
            }
            arquivo = "";
        }
        System.out.println(ANSI_PURPLE + "\nPrograma finalizado." + ANSI_RESET);

        System.out.println(ANSI_GREEN + "\nProcessando " + arquivo + "..." + ANSI_RESET);

        try {
            File file = new File("C:\\projects\\Facul\\T2 Jb\\Nos-Passos-Dos-Gigantes\\casosdeteste\\"+ arquivo);
            try (Scanner fileScanner = new Scanner(file)) {
            processarEntrada(fileScanner, arquivo);
            }
        } catch (FileNotFoundException e) {
            System.err.println(ANSI_RED + "Erro: Arquivo '" + arquivo + "' não encontrado na pasta 'casos'! Certifique-se de que ele está na pasta correta." + ANSI_RESET);
        }

        scanner.close(); 

        System.out.println(ANSI_PURPLE + "\nPrograma finalizado." + ANSI_RESET);
    }
}