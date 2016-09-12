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
                    while (!pilha.empty()) {
                        if (pilha.peek() == '*' || pilha.peek() == '.') {
                            result += pilha.pop();
                        } else {
                            break;
                        }
                    }
                    pilha.push('.');
                    result += exp.charAt(i);
                    flag = true;
                } else { //se é só um operando sem '.' antes
                    result += exp.charAt(i);
                    flag = true;
                }
            } else if (exp.charAt(i) == '(') {//caso II
                if (flag) {
                    while (!pilha.empty()) {
                        if (pilha.peek() == '*' || pilha.peek() == '.') {
                            result += pilha.pop();
                        } else {
                            break;
                        }
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
                switch (exp.charAt(i)) {
                    case '+':
                        flag = false;
                        while (!pilha.empty()) {
                            if (pilha.peek() == '*' || pilha.peek() == '.' || pilha.peek() == '+') {
                                result += pilha.pop();
                            } else {
                                break;
                            }
                        }
                        pilha.push('+');
                        break;
                    case '.':
                        flag = false;
                        while (!pilha.empty()) {
                            if (pilha.peek() == '*' || pilha.peek() == '.') {
                                result += pilha.pop();
                            } else {
                                break;
                            }
                        }
                        pilha.push('.');
                        break;
                    case '*':
                        while (!pilha.empty()) {
                            if (pilha.peek() == '*') {
                                result += pilha.pop();
                            } else {
                                break;
                            }
                        }
                        pilha.push('*');
                        flag = true;
                        break;
                }
            }
        }
        while (!pilha.empty()) {  //ETAPA 2
            if (pilha.peek() != '(') {
                result += pilha.pop(); //limpa a pilha, colocando os operadores restantes na expressão
            } else {
                throw new Error("Um '(' sobrou na pilha!");
            }
        }
        return result;
    }

    public boolean conferePosfixa(String s) {
        char op1, op2;
        Stack<Character> pilha = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (charValid(s.charAt(i))) {
                pilha.push(s.charAt(i));
            } else //binario
             if (s.charAt(i) == '+' || s.charAt(i) == '.') {
                    op2 = pilha.pop();
                    if (!pilha.empty()) {
                        op1 = pilha.pop();
                        pilha.push('0');
                    } else {
                        throw new IllegalAccessError("Operando não encontrado");
                    }
                } else {
                    op1 = pilha.pop();
                    pilha.push('0');
                }
        }
        op1 = pilha.pop();
        if (pilha.empty()) {
            return true;
        }
        return false;
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
        String r = c.converter("(a+b)*aa");
        System.out.println(r);
        try {
            if (c.conferePosfixa(r)) {
                System.out.println("DEU CERTO");
            } else {
                System.out.println("DEU RUIM");
            }
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

    }
}
