import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServerSocket {

	

	public static void main(String[] args) {
	   //TODO Auto-generated method stub
            ServerSocket welcomeSocket;
            DataOutputStream socketOutput;     	
	    DataInputStream socketInput;
	    BufferedReader socketEntrada;
	    Calculadora calc = new Calculadora();
	// 1. Cria o ServerSocket que escuta conexões de clientes na porta TCP 9090	
            try {
			welcomeSocket = new ServerSocket(9090);
		  int i=0; //numero de clientes
	  
	      System.out.println ("Servidor no ar");
        // 2. Aguarda e aceita a conexão de um cliente via Socket (chamada bloqueante)
	      while(true) { 
	  
	           Socket connectionSocket = welcomeSocket.accept(); 
	           i++;
	           System.out.println ("Nova conexao");
	           
         // 3. Obtém o fluxo de entrada do Socket para ler a mensagem transmitida pelo cliente
	       //String result="ola";
               socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

        //  4. Lê a LINHA ÚNICA enviada pelo cliente (ex: "1 20.0 20.0")
                String linha = socketEntrada.readLine();
                String result = "";
                if (linha != null && !linha.isEmpty()) {
                    // Separa a mensagem lida usando o caractere de espaço " " como divisor
                    String[] partes = linha.split(" ");
                    int operacao = Integer.parseInt(partes[0]);
                    
                    //String expressao = (partes[1]);
             
// Executa o cálculo correspondente na classe Calculadora
                    switch (operacao) {
                        case 1:
                            double oper1 = Double.parseDouble(partes[1]);
                            double oper2 = Double.parseDouble(partes[2]);
                            result = String.valueOf(calc.soma(oper1, oper2));
                            break;
                        case 2:
                            double oper21 = Double.parseDouble(partes[1]);
                            double oper22 = Double.parseDouble(partes[2]);
                            result = String.valueOf(calc.subitracao(oper21, oper22));
                            break;
                        case 3:
                            double oper31 = Double.parseDouble(partes[1]);
                            double oper32 = Double.parseDouble(partes[2]);
                            result = String.valueOf(calc.divisao(oper31, oper32));
                            break;
                        case 4:
                            double oper41 = Double.parseDouble(partes[1]);
                            double oper42 = Double.parseDouble(partes[2]);
                            result = String.valueOf(calc.multiplicacao(oper41, oper42));
                            break;
                        case 5:
                            // Operação 5: Avaliar expressão enviada pelo cliente
                            // O restante da linha contém a expressão em texto (ex: "(10 + 15) * 4")
                            System.out.println("testando para saber se chegou aqui na opção 5");
                            String expressao01 = linha.substring(linha.indexOf(" ") + 1);
                            System.out.println("testando para saber o vaor da expressao" + expressao01);
                            //String expressao01 = expressao;
                            // CHAMADA DO MÉTODO DENTRO DA CLASSE CALCULADORA:
                            double resultadoExpressao = calc.avaliarExpressao(expressao01);
            
                            result = String.valueOf(resultadoExpressao);
                            break;
                        default:
                            result = "Erro: Operação inválida (" + operacao + ")";
                    }
                } else {
                    result = "Erro: Requisição vazia";
                }

               
               // 5. Obtém o fluxo de saída do Socket para responder ao cliente
                   socketOutput= new DataOutputStream(connectionSocket.getOutputStream());     	
	           // 6. Transmite a resposta via Socket finalizada com quebra de linha ('\n')
                   socketOutput.writeBytes(result+ '\n');
	           System.out.println (result);	           
	           socketOutput.flush();
	        // 7. Encerra o Socket de comunicação com este cliente específico
                   socketOutput.close();

	         System.out.println("Resultado enviado: " + result + " (Conexão encerrada)\n");           
	      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	}

}
