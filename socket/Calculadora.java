public class Calculadora {
    public String sayHello(String nome, String sobrenome) {
        return "Fala "+ nome + " " + sobrenome;
    }
    public double soma(double oper1, double oper2) {
        return oper1 + oper2;
    }
    public double subitracao(double oper1, double oper2) {
        return oper1 - oper2;
    }
    public double multiplicacao(double oper1, double oper2) {
        return (oper1 * oper2);
    }
    public double divisao(double oper1, double oper2) {
        return (oper1 / oper2);
    }

    public double avaliarExpressao(String expr) {
        if (expr == null || expr.trim().isEmpty()) {
            return 0;
        }

        // Remove todos os espaços em branco da expressão
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

                // Trata Adição e Subtração
                double analisarExpressao() {
                    double x = analisarTermo();
                    for (;;) {
                        if      (consumir('+')) x += analisarTermo();
                        else if (consumir('-')) x -= analisarTermo();
                        else return x;
                    }
                }

                // Trata Multiplicação e Divisão (Precedência)
                double analisarTermo() {
                    double x = analisarFator();
                    for (;;) {
                        if      (consumir('*')) x *= analisarFator();
                        else if (consumir('/')) x /= analisarFator();
                        else return x;
                    }
                }

                // Trata Números e Parênteses
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
            System.err.println("Erro ao avaliar expressão: " + e.getMessage());
            return 0;
        }
    }
}
    
