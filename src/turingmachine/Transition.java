package turingmachine;

public class Transition {

    private final TMState nextState;
    private final String writeSymbol;
    private final Direction direction;

    public Transition(
            TMState nextState,
            String writeSymbol,
            Direction direction) {

        this.nextState = nextState;
        this.writeSymbol = writeSymbol;
        this.direction = direction;
    }

    public TMState getNextState() {
        return nextState;
    }

    public String getWriteSymbol() {
        return writeSymbol;
    }

    public Direction getDirection() {
        return direction;
    }
}