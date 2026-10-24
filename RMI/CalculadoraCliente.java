import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CalculadoraCliente {

    public static void main(String[] args) {
        String ipServidor = "192.168.1.26";
        int opcao = -1;

        try {
            Registry reg = LocateRegistry.getRegistry(ipServidor, 1099);
            ICalculadora calc =
                (ICalculadora) reg.lookup("calculadora");

            Scanner scanner = new Scanner(System.in);

            System.out.println("=== CLIENTE JAVA LEVE (RMI) ===");

            while (opcao != 0) {
                System.out.println("\n---------------------------------------------");
                System.out.println("1 - Operacao Simples (soma, sub, mult, div)");
                System.out.println("2 - Expressao Numerica (ex: (10 + 15) * 4)");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opcao: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Opcao invalida! Digite 0, 1 ou 2.");
                    scanner.nextLine();
                    continue;
                }

                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                if (opcao == 1) {
                    System.out.print("Digite o primeiro numero: ");
                    double n1 = lerNumero(scanner);

                    System.out.print("Digite a operacao (+, -, *, /): ");
                    char op = scanner.next().charAt(0);

                    System.out.print("Digite o segundo numero: ");
                    double n2 = lerNumero(scanner);

                    switch (op) {
                        case '+':
                            System.out.println(
                                "Resultado: " + calc.soma(n1, n2));
                            break;

                        case '-':
                            System.out.println(
                                "Resultado: " + calc.subitracao(n1, n2));
                            break;

                        case '*':
                            System.out.println(
                                "Resultado: " + calc.multiplicacao(n1, n2));
                            break;

                        case '/':
                            if (n2 == 0) {
                                System.out.println(
                                    "Erro: divisao por zero!");
                            } else {
                                System.out.println(
                                    "Resultado: " + calc.divisao(n1, n2));
                            }
                            break;

                        default:
                            System.out.println(
                                "Operacao invalida! Use +, -, * ou /.");
                    }

                    scanner.nextLine(); // Limpa o buffer

                } else if (opcao == 2) {
                    System.out.print("Digite a expressao numerica: ");
                    String expressao = scanner.nextLine();

                    System.out.println(
                        "Resultado: " + calc.avaliarExpressao(expressao));

                } else if (opcao == 0) {
                    System.out.println("Encerrando o cliente...");

                } else {
                    System.out.println("Opcao invalida! Tente novamente.");
                }
            }

            scanner.close();

        } catch (RemoteException | NotBoundException e) {
            System.out.println("Erro ao conectar com o servidor RMI: " + e);
        }
    }

    private static double lerNumero(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print(
                "Numero invalido! Digite novamente: ");
            scanner.next();
        }

        return scanner.nextDouble();
    }
}


