import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter expression kind of \"a[+-*/]b\", where both numbers are either Arabic or Roman" +
                " in the range from 1 to 10");
        System.out.println(Main.calc(scanner.nextLine()));
    }

    public static String calc(String input) throws Exception {

        char[] operations = {'+', '-', '*', '/'};//это пригодится позже
        if (!(Pattern.matches("[0-9]+[+-/*//]{1}[0-9]+", input)
                || Pattern.matches("[IVX]+[+-/*//]{1}[IVX]+", input))) {
            throw new Exception("You didn't enter expression kind of \"a[+-*/]b\",where both numbers are either Arabic or Roman" +
                    " in the range from 1 to 10");//здесь идёт проверка того, что выражение правильного вида
        }
        int[] numbers = {0, 0};//a и b
        boolean[] types = {false, false};//типы a и b соответственно,false, если число арабское, true, если римское
        StringTokenizer tokenizer = new StringTokenizer(input, "+-*// ");//разделяем строку на два элемента
        String token;
        for (int i = 0; i < 2; i++) {
            token = tokenizer.nextToken();
            try {
                //определяем, римское число или арабское
                for (Roman_numeral rom : Roman_numeral.values()) {
                    if (rom.toString().equals(token)) {//если число римское
                        numbers[i] = rom.ordinal() + 1;
                        types[i] = true;
                    }
                }
                if (types[i] == false) {//если число не римское
                    numbers[i] = Integer.valueOf(token);
                }
                if ((numbers[i] < 1) || (numbers[i] > 10)) {
                    throw new Exception("You entered the number, which less than 1 or more than 10");
                }
            } catch (
                    NumberFormatException e) {//отлавливает ошибку в 34 строке. Вообще, маска с рег.выражением уже проработала
                //неблагоприятный исход(неполучение целого числа), но компилятор ругается, так что пусть будет
                throw new Exception("You didn't enter two numbers.");
            }
        }
        if (types[0] != types[1]) {//проверяет, одного ли типа числа
            throw new Exception("You can't do Arabic and Roman numbers at the same time");
        }
        char operation = '0';
        for (char op : operations) {//определяет, какую операцию надо сделать
            if (input.indexOf(op) != -1) operation = op;
        }
        int result_int;
        if (types[0] == false) {//если числа арабские
            switch (operation) {
                case '+':
                    result_int = numbers[0] + numbers[1];
                    break;
                case '-':
                    result_int = numbers[0] - numbers[1];
                    break;
                case '*':
                    result_int = numbers[0] * numbers[1];
                    break;
                case '/':
                    result_int = numbers[0] / numbers[1];
                    break;
                default:
                    throw new Exception("Unknown exception");
            }
            return String.valueOf(result_int);
        } else {//если числа римские
            switch (operation) {
                case '+':
                    result_int = numbers[0] + numbers[1];
                    break;
                case '-':
                    result_int = numbers[0] - numbers[1];

                    break;
                case '*':
                    result_int = numbers[0] * numbers[1];
                    break;
                case '/':
                    result_int = numbers[0] / numbers[1];
                    break;
                default:
                    throw new Exception("Unknown exception");
            }
            if (result_int <= 0) {
                throw new Exception("Calculation result of 2 Roman numerals can't be negative or null");
            }
            return Roman_numeral.find(result_int);
        }
    }
}