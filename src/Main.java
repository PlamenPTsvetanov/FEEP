import java.util.*;

public class Main {
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

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Dijkstra dijkstra = new Dijkstra();

        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("exit")){
            System.out.println(dijkstra.task(input));
            input = scanner.nextLine();
        }
    }
}