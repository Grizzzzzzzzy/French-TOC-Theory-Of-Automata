package turingmachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tape {

    private final Map<Integer, String> tape;
    private int headPosition;

    private static final String BLANK = "BLANK";

    public Tape(List<String> inputSymbols) {

        tape = new HashMap<>();
        headPosition = 0;

        for (int i = 0; i < inputSymbols.size(); i++) {
            tape.put(i, inputSymbols.get(i));
        }

        // Blank after input
        tape.put(inputSymbols.size(), BLANK);
    }

    public String read() {
        return tape.getOrDefault(headPosition, BLANK);
    }

    public void write(String symbol) {
        tape.put(headPosition, symbol);
    }

    public void move(Direction direction) {

        switch (direction) {

            case LEFT:
                headPosition--;
                break;

            case RIGHT:
                headPosition++;
                break;

            case STAY:
                break;
        }
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public String getSymbol(int position) {
        return tape.getOrDefault(position, BLANK);
    }

    public void printTape() {

        System.out.println();
        System.out.print("Tape: ");

        int min = Math.max(0, headPosition - 3);
        int max = headPosition + 3;

        for (int i = min; i <= max; i++) {

            String symbol = getSymbol(i);

            if (i == headPosition) {
                System.out.print("[" + symbol + "] ");
            } else {
                System.out.print(symbol + " ");
            }
        }

        System.out.println();
    }
}