package compiladores1.model;

import java.util.Stack;

public class Conversor {

    public String converter(String exp) {
        Stack<Character> pilha = new Stack<>();
        String result = "";
        boolean flag = false;//controlar operandos consecutivos que impliquem em "."
        for (int i = 0; i < exp.length(); i++) { //ETAPA 1
            if (charValid(exp.charAt(i))) { //caso I
                if (flag) { //se o concatenador está implícito
                    while (!pilha.empty() && pilha.peek() == '*') {
                        result += pilha.pop();
                    }
                    pilha.push('.');
                    result += exp.charAt(i);
                    flag = false;
                } else { //se é só um operando sem '.' antes
                    result += exp.charAt(i);
                    flag = true;
                }
            } else if (exp.charAt(i) == '(') {//caso II
                if (flag) {
                    while (!pilha.empty() && pilha.peek() == '*') {
                        result += pilha.pop();
                    }
                    pilha.push('.');
                    flag = false;
                }
                pilha.push('(');
            } else if (exp.charAt(i) == ')') {//caso III
                while (pilha.peek() != '(') {
                    result += pilha.pop(); //desempilha até chegar no '('
                }
                pilha.pop(); //descarta o '('
                flag = true;
            } else if (isOperador(exp.charAt(i))) { //caso IV
                flag = false;
                switch (exp.charAt(i)) {
                    case '+':
                        while (!pilha.empty() && (pilha.peek() == '*' || pilha.peek() == '.')) {
                            result += pilha.pop();
                        }
                        pilha.push('+');
                        break;
                    case '.':
                        while (!pilha.empty() && pilha.peek() == '*') {
                            result += pilha.pop();
                        }
                        pilha.push('.');
                        break;
                    case '*':
                        pilha.push('*');
                        break;
                }
            }
        }
        while (!pilha.empty()) {  //ETAPA 2
            result += pilha.pop(); //limpa a pilha, colocando os operadores restantes na expressão
        }
        return result;
    }

    private static boolean charValid(char c) { //testa se é um símbolo válido
        if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122) || c == '&') {
            return true;
        }
        return false;
    }

    private static boolean isOperador(char c) { //testa se é operador
        if (c == '.' || c == '+' || c == '*') {
            return true;
        }
        return false;
    }

    public static void main(String[] args) { //main para testes
        Conversor c = new Conversor();
        System.out.println(c.converter("A+BC(AA*B)*"));

    }
}
