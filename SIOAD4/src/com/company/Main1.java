package com.company;

import java.util.Scanner;

public class Main1 {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String message = "Если хотите ввести Вашу формулу, нажмите 1\nВыход = 2";
        boolean running = true;
        do {
            System.out.println(message);
            switch (scanner.nextLine()) {
                case "1":
                    example();
                    break;
                case "2":
                    running = false;
                    break;
                default:
                    System.out.println("Ошибка! Введите 1 или 2.");
                    break;
            }
        } while (running);
    }

    private static void example() {
        String bufferLine = returnCorrectLine();
        System.out.println("Нажмите 1, чтобы преобразовать в постфиксную форму, иначе в префиксную");
        if ("1".equals(scanner.nextLine())) {
            toPostfix(bufferLine);
        } else {
            toPrefix(bufferLine);
        }
    }

    public static String returnCorrectLine() {
        System.out.println("Введите вашу формулу");
        String bufferLine = scanner.nextLine();
        StringBuilder correctLine = new StringBuilder();
        int rang = 0;
        for (int i = 0; i < bufferLine.length(); i++) {
            if (bufferLine.charAt(i) == ' ') {
                continue;
            }
            if ((bufferLine.charAt(i) >= 'a' && bufferLine.charAt(i) <= 'z') || bufferLine.charAt(i) == '(') {
                rang++;
            }
            if (bufferLine.charAt(i) == '+' || bufferLine.charAt(i) == '-' || bufferLine.charAt(i) == '/' || bufferLine.charAt(i) == '*' || bufferLine.charAt(i) == '^' || bufferLine.charAt(i) == ')') {
                rang--;
            }
            correctLine.append(bufferLine.charAt(i));
        }
        if (rang != 1) {
            System.out.println("Ошибка! Некорректный ввод!");
            correctLine = new StringBuilder(returnCorrectLine());
        }
        return correctLine.toString();
    }

    public static void toPostfix(String input) {
        System.out.println(postfix(input));
    }

    public static void toPrefix(String input) {
        String rev1 = reverse(input);
        System.out.print("Обратная запись: ");
        System.out.println(rev1);

        String handled = postfix(rev1);
        System.out.print("Постфиксная форма: ");
        System.out.println(handled);

        String rev2 = reverse(handled);
        System.out.print("Префиксная форма: ");
        System.out.println(rev2);
    }

    public static String postfix(String input) {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        char ch;
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                popOperators(stack, output, priorityOf(ch));
            } else if (isOperand(ch)) {
                output.append(ch);
            } else {
                int priority = priorityOf(ch);
                if (!stack.isEmpty() && priority <= priorityOf(stack.peek()))
                    popOperators(stack, output, priority);
                stack.push(ch);
            }
        }
        while (!stack.isEmpty())
            output.append(stack.pop());
        return output.toString();
    }

    private static void popOperators(Stack<Character> stack, StringBuilder output, int priority) {
        char c;
        while (!stack.isEmpty() && (c = stack.pop()) != '(') {
            if (priority <= priorityOf(c)) {
                output.append(c);
            } else {
                stack.push(c);
                break;
            }
        }
    }

    private static boolean isOperand(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static int priorityOf(char c) {
        int buffer;
        switch (c) {
            case '(':
                buffer = 0;
                break;
            case ')':
                buffer = 1;
                break;
            case '+':
            case '-':
                buffer = 2;
                break;
            case '*':
            case '/':
                buffer = 3;
                break;
            case '^':
                buffer = 4;
                break;
            default:
                buffer = -1;
        }
        return buffer;
    }


    public static String reverse(String input) {
        StringBuilder builder = new StringBuilder();
        for (int i = input.length() - 1; i > -1; i--)
            builder.append(reverseChar(input.charAt(i)));
        return builder.toString();
    }

    private static char reverseChar(char c) {
        return c == '(' ? ')' : c == ')' ? '(' : c;
    }
}