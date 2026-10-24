import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Calculadora  implements ICalculadora {

	private static final long serialVersionUID = 1L;
	
	private static int chamadas = 0;

	public double soma(double a, double b) throws RemoteException {
		System.out.println("Método soma chamado " + chamadas++);
		return a + b;
	}
        public double divisao(double a, double b) throws RemoteException {
		System.out.println("Método divisao chamado " + chamadas++);
		return a / b;
	}
         
        public double multiplicacao (double a, double b) throws RemoteException {
		System.out.println("Método multiplicação chamado " + chamadas++);
		return a * b;
	}
        
        public double subitracao (double a, double b) throws RemoteException {
		System.out.println("Método subitração chamado " + chamadas++);
		return a - b;
	}
        
        public double avaliarExpressao(String expr) throws RemoteException {
        System.out.println("Método avaliarExpressao chamado (total de chamadas: " + (++chamadas) + ")");
        if (expr == null || expr.trim().isEmpty()) {
            return 0;
        }

        final String exp = expr.replaceAll("\\s+", "");

        try {
            return new Object() {
                int pos = -1, ch;

                void proximoChar() {
                    ch = (++pos < exp.length()) ? exp.charAt(pos) : -1;
                }

                boolean consumir(int charParaConsumir) {
                    while (ch == ' ') proximoChar();
                    if (ch == charParaConsumir) {
                        proximoChar();
                        return true;
                    }
                    return false;
                }

                double analisar() {
                    proximoChar();
                    double x = analisarExpressao();
                    if (pos < exp.length()) throw new RuntimeException("Caractere inesperado: " + (char)ch);
                    return x;
                }

                double analisarExpressao() {
                    double x = analisarTermo();
                    for (;;) {
                        if      (consumir('+')) x += analisarTermo();
                        else if (consumir('-')) x -= analisarTermo();
                        else return x;
                    }
                }

                double analisarTermo() {
                    double x = analisarFator();
                    for (;;) {
                        if      (consumir('*')) x *= analisarFator();
                        else if (consumir('/')) x /= analisarFator();
                        else return x;
                    }
                }

                double analisarFator() {
                    if (consumir('+')) return analisarFator();
                    if (consumir('-')) return -analisarFator();

                    double x;
                    int inicioPos = this.pos;
                    if (consumir('(')) {
                        x = analisarExpressao();
                        consumir(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                        while ((ch >= '0' && ch <= '9') || ch == '.') proximoChar();
                        x = Double.parseDouble(exp.substring(inicioPos, this.pos));
                    } else {
                        throw new RuntimeException("Caractere inválido: " + (char)ch);
                    }

                    return x;
                }
            }.analisar();
        } catch (Exception e) {
            System.err.println("Erro ao avaliar expressão via RMI: " + e.getMessage());
            return 0;
        }
    }
        

	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException  {
		// IP real do computador onde o SERVIDOR vai rodar
                //System.setProperty("java.rmi.server.hostname", "192.168.1.26");
                System.setProperty("java.rmi.server.hostname", "srvsocket"); 

                Calculadora calculadora = new Calculadora();		
		Registry reg = null;
		ICalculadora stub = (ICalculadora) UnicastRemoteObject.
				exportObject(calculadora, 1100);
		try {
			System.out.println("Creating registry...");
			reg = LocateRegistry.createRegistry(1099);
		} catch (Exception e) {
			try {
				reg = LocateRegistry.getRegistry(1099);
			} catch (Exception e1) {
				System.exit(0);
			}
		}
		reg.rebind("calculadora", stub);
	}
}
