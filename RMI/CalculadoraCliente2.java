import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class CalculadoraCliente {
	
	public static void main(String[] args) {
		String ipServidor = "192.168.1.26";
                Registry reg = null;
		ICalculadora calc;		
		try {
			reg = LocateRegistry.getRegistry(ipServidor, 1099);
			calc = (ICalculadora) reg.lookup("calculadora");
			System.out.println(calc.soma(3,2));
                        System.out.println(calc.subitracao(3, 2));
                        System.out.println(calc.divisao(3, 2));
                        System.out.println(calc.multiplicacao(3, 2));
                        System.out.println(calc.avaliarExpressao("(3*10)+47*2"));
		} catch (RemoteException | NotBoundException e) {
				System.out.println(e);
				System.exit(0);
		}
	}		

}
