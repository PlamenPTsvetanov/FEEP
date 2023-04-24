import java.util.*;

public class Dijkstra {
    private final static char[] ALLOWED_SYMBOLS = new char[]{'+', '-', '(', ')', '=', '*', '/', '%', '^'};
    private final static char[] ALLOWED_LETTERS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private final static char[] EXPECTED_LETTERS = new char[]{'t', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static Map<Character, Integer> priorityMap = new HashMap() {{
        put('(', Integer.MAX_VALUE);
        put(')', 2);
        put('+', 3);
        put('-', 3);
        put('*', 4);
        put('/', 4);
        put('%', 4);
        put('^', 10);
        put('?', -1);
    }};

    private static List<Character> stack = new LinkedList() {{
        add('?');
    }};

    public String task(String input) {
        //Добавяме терминиращ във входа
        checkInput(input);

        input += "#";
        String endEqualsSigns = "";
        StringBuilder output = new StringBuilder();
        int i = 0;
        for (char ch : input.toCharArray()) {
            //Ако сме стигнали терминиращ, принтираме стека под ред
            if (ch == '#') {
                for (int j = stack.size() - 1; j > 0; j--) {
                    output.append(stack.get(j));
                }
                break;
            }
            //Добавяме равно, ако има нужда
            if (ch == '=') {
                endEqualsSigns += "=";
                continue;
            }
            if (priorityMap.containsKey(ch)) {
                //Ако имаме затваряща скоба, циклим всичко до отварящата
                if (ch == ')') {
                    int j;
                    for (j = stack.size() - 1; stack.get(j) != '('; j--) {
                        output.append(stack.remove(j));
                        i--;
                    }
                    //Махаме отварящата от стека (сложили сме я за удобство)
                    stack.remove(j);
                    i--;
                    //Сменяме приоритета на следващата отваряща
                    priorityMap.put('(', Integer.MAX_VALUE);
                    continue;
                }

                int priorityIn = priorityMap.get(ch);
                if (ch == '^') {
                    priorityIn++;
                }
                int priorityStack = priorityMap.get(stack.get(i));

                boolean toAdd = true;

                boolean toAppendNewSymbol = true;
                while ((priorityIn <= priorityStack)) {
                    if ((priorityIn == 0 && ch =='(') || stack.get(i).equals('(')) {
                        break;
                    }
                    output.append(stack.remove(i));
                    if (toAppendNewSymbol) {
                        stack.add(i, ch);
                        toAppendNewSymbol = false;
                    }
                    toAdd = false;
                    priorityStack = priorityMap.get(stack.get(--i));
                }

                //Aко сме махнали повече от един елемент, връщаме пойнтъра в началото на стека
                i = stack.size() - 1;

                if (toAdd) {
                    stack.add(ch);
                    i++;
                    //След като е в стека, отварящата става с нулев приоритет и чака затваряща
                    if (ch == '(') {
                        priorityMap.put('(', 0);
                    }
                    if (ch == '^') {
                        priorityMap.put('^', priorityIn);
                    }
                }

            } else {
                //Всяка буква директно преминава от другата страна
                output.append(ch);
                for (char expectedLetter : EXPECTED_LETTERS) {
                    if (expectedLetter == ch) {
                        output.append('\'');
                    }
                }
            }
        }
        reset();


        return output.append(endEqualsSigns).toString();
    }


    private static void reset() {
        stack.clear();
        priorityMap.put('(', Integer.MAX_VALUE);
        stack.add('?');
    }


    private static boolean checkInput(String in) {
        boolean[] usedLetters = new boolean[26];
        boolean isValid = true;
        boolean[] areRepeatedLetters = {false, false};
        boolean[] areRepeatedSymbols = {false, false};
        char[] repeatSymbols = new char[2];

        int i = 0;

        int leftParentheses = 0;
        int rightParentheses = 0;

        char[] charArray = in.toCharArray();
        for (char ch : charArray) {
            isValid = traverseAllowedArr(ALLOWED_LETTERS, isValid, ch);
            if (isValid) {
                if (areRepeatedSymbols[0]) {
                    areRepeatedSymbols[0] = false;
                    areRepeatedSymbols[1] = false;
                    i = 0;
                }
                areRepeatedLetters[i++] = true;
                if (usedLetters[(ch - 'a')]) {
                    return false;
                }
                usedLetters[(ch - 'a')] = true;

                if (areRepeatedLetters[0] && areRepeatedLetters[1]) {
                    return false;
                }
                continue;
            }

            isValid = traverseAllowedArr(ALLOWED_SYMBOLS, isValid, ch);
            if (!isValid) {
                return false;
            } else {
                if (areRepeatedLetters[0]) {
                    areRepeatedLetters[0] = false;
                    i = 0;
                }
                if (ch == '(') {
                    leftParentheses++;
                } else if (ch == ')') {
                    rightParentheses++;
                }
                repeatSymbols[i] = ch;
                areRepeatedSymbols[i++] = true;
            }


            //Проверка за логика на скоби
            if (areRepeatedSymbols[0] && areRepeatedSymbols[1]) {
                if ((repeatSymbols[0] == ')' && repeatSymbols[1] == '(') ||
                        (repeatSymbols[0] == '(' && repeatSymbols[1] == ')')) {
                    return false;
                } else {
                    repeatSymbols[1] = ' ';
                    repeatSymbols[0] = ' ';
                    areRepeatedSymbols[1] = false;
                    areRepeatedSymbols[0] = false;
                    i = 0;
                }
            }

            if (leftParentheses - rightParentheses < 0) {
                return false;
            }
        }

        if (leftParentheses - rightParentheses != 0) {
            return false;
        }
        return true;
    }

    private static boolean traverseAllowedArr(char[] allowedSymbols, boolean isValid, char ch) {
        for (char allowedSymbol : allowedSymbols) {
            isValid = allowedSymbol == ch;
            if (isValid) {
                break;
            }
        }
        return isValid;
    }
}
