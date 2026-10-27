package turingmachine;

import java.util.HashMap;
import java.util.Map;

public class TransitionFunction {

    private final Map<String, Transition> transitions;

    public TransitionFunction() {
        transitions = new HashMap<>();
    }

    private String key(TMState state, String symbol) {
        return state.name() + "|" + symbol;
    }

    public void addTransition(
            TMState state,
            String readSymbol,
            TMState nextState,
            String writeSymbol,
            Direction direction) {

        transitions.put(
                key(state, readSymbol),
                new Transition(
                        nextState,
                        writeSymbol,
                        direction
                )
        );
    }

    public Transition getTransition(
            TMState state,
            String readSymbol) {

        return transitions.get(
                key(state, readSymbol)
        );
    }
}