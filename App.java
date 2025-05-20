import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        //Robos robo = new Robos();

        while (continuar) {
            System.out.println("\n === Menu ===");
            System.out.println("Qual caso de teste deseja usar?");
            System.out.println("1 - Caso de teste 60");
            System.out.println("2 - Caso de teste 80");
            System.out.println("3 - Caso de teste 100");
            System.out.println("4 - Caso de teste 120");
            System.out.println("5 - Caso de teste 140");
            System.out.println("6 - Caso de teste 160");
            System.out.println("7 - Caso de teste 180");
            System.out.println("8 - Caso de teste 200");
            System.out.println("9 - Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            String arquivo = null;

            switch (opcao) {
                case "1":
                    arquivo = "C:\\Users\\luiza\\Downloads\\T1_Luiza_Hackenhaar_Naziazeno\\casos de teste\\caso60.txt";
                    break;
                case "2":
                    arquivo = "C:\\Users\\luiza\\Downloads\\T1_Luiza_Hackenhaar_Naziazeno\\casos de teste\\caso80.txt";
                    break;
                case "3":
                    arquivo = "C:\\Users\\luiza\\Downloads\\T1_Luiza_Hackenhaar_Naziazeno\\casos de teste\\caso100.txt";
                    break;
                case "4":
                    arquivo = "C:\\Users\\luiza\\Downloads\\T1_Luiza_Hackenhaar_Naziazeno\\casos de teste\\caso120.txt";
                    break;
                case "5":
                    arquivo = "C:\\Users\\luiza\\Downloads\\T1_Luiza_Hackenhaar_Naziazeno\\casos de teste\\caso140.txt";
                    break;
                case "6":
                    arquivo = "C:\\Users\\luiza\\Downloads\\T1_Luiza_Hackenhaar_Naziazeno\\casos de teste\\caso160.txt";
                    break;
                case "7":
                    arquivo = "C:\\Users\\luiza\\Downloads\\T1_Luiza_Hackenhaar_Naziazeno\\casos de teste\\caso180.txt";
                    break;
                case "8":
                    arquivo = "C:\\Users\\luiza\\Downloads\\T1_Luiza_Hackenhaar_Naziazeno\\casos de teste\\caso200.txt";
                    break;
                case "9":
                    System.out.println("Saindo...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            if (arquivo != null) {
                escolherArquivo(arquivo);
               /* System.out.println("Número de robôs: " + robo.numeroderobos(arquivo));
                Map<String, Object> resultado = robo.receita(arquivo);
                if (resultado != null) {
                    System.out.println("Resultado da dança:");
                    System.out.println("  Rodadas: " + resultado.get("rodadas"));
                    System.out.println("  Operações: " + resultado.get("operacoes"));*/
                  /*  System.out.println("  Tempo de Execução: " + resultado.get("tempoExecucao") + " nanossegundos");
                } else {
                    System.err.println("Erro: Não foi possível processar o arquivo " + arquivo + ". Dados inválidos.");
                }
            }*/
        }
        scanner.close();
    }

    public static void escolherArquivo(String arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            System.out.println("\n--- Conteúdo do Arquivo ---");
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
            System.out.println("---------------------------\n");
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo:" + e.getMessage());
        }
    }
}