package turingmachine;

import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class TuringMachine {

    private final TransitionFunction transitionFunction;

    private TMState currentState;

    private Tape tape;

    public TuringMachine() {

        transitionFunction =
                new TransitionFunction();

        buildTransitionFunction();

        currentState = TMState.Q0;
    }

    private void buildTransitionFunction() {

        /*
         * =====================================
         * q0 + valid WORD
         * =====================================
         *
         * Read WORD
         * Write WORD
         * Move RIGHT
         * Stay in q0
         */

        transitionFunction.addTransition(
                TMState.Q0,
                "WORD",
                TMState.Q0,
                "WORD",
                Direction.RIGHT
        );

        /*
         * =====================================
         * q0 + PUNCTUATION
         * =====================================
         */

        transitionFunction.addTransition(
                TMState.Q0,
                "PUNCTUATION",
                TMState.Q0,
                "PUNCTUATION",
                Direction.RIGHT
        );

        /*
         * =====================================
         * q0 + NUMBER
         * =====================================
         */

        transitionFunction.addTransition(
                TMState.Q0,
                "NUMBER",
                TMState.Q0,
                "NUMBER",
                Direction.RIGHT
        );

        /*
         * =====================================
         * q0 + BLANK
         * =====================================
         *
         * End of input
         * Therefore ACCEPT
         */

        transitionFunction.addTransition(
                TMState.Q0,
                "BLANK",
                TMState.ACCEPT,
                "BLANK",
                Direction.STAY
        );

        /*
         * =====================================
         * q0 + UNKNOWN
         * =====================================
         *
         * Invalid French token
         * Therefore REJECT
         */

        transitionFunction.addTransition(
                TMState.Q0,
                "UNKNOWN",
                TMState.REJECT,
                "UNKNOWN",
                Direction.STAY
        );
    }

    public boolean run(List<Token> tokens) {

        currentState = TMState.Q0;

        /*
         * Convert tokens into tape symbols
         */
        List<String> symbols = new ArrayList<>();

        for (Token token : tokens) {

            symbols.add(
                    token.getType().name()
            );
        }

        /*
         * Create TM tape
         */
        tape = new Tape(symbols);

        System.out.println();
        System.out.println("=================================");
        System.out.println("       TURING MACHINE");
        System.out.println("=================================");

        System.out.println();
        System.out.println("Initial State: q0");

        /*
         * TM execution
         */
        int step = 0;

        while (currentState != TMState.ACCEPT
                && currentState != TMState.REJECT) {

            String readSymbol = tape.read();

            System.out.println();
            System.out.println(
                    "Step " + step
            );

            System.out.println(
                    "Current State: "
                    + currentState
            );

            System.out.println(
                    "Head Position: "
                    + tape.getHeadPosition()
            );

            System.out.println(
                    "Read Symbol: "
                    + readSymbol
            );

            tape.printTape();

            /*
             * Get transition from
             * transition function
             */
            Transition transition =
                    transitionFunction.getTransition(
                            currentState,
                            readSymbol
                    );

            /*
             * No transition = reject
             */
            if (transition == null) {

                currentState = TMState.REJECT;

                System.out.println(
                        "No transition found."
                );

                break;
            }

            /*
             * WRITE
             */
            tape.write(
                    transition.getWriteSymbol()
            );

            /*
             * STATE CHANGE
             */
            currentState =
                    transition.getNextState();

            /*
             * MOVE HEAD
             */
            tape.move(
                    transition.getDirection()
            );

            System.out.println(
                    "Write Symbol: "
                    + transition.getWriteSymbol()
            );

            System.out.println(
                    "Move: "
                    + transition.getDirection()
            );

            System.out.println(
                    "Next State: "
                    + currentState
            );

            step++;

            /*
             * Safety limit
             */
            if (step > 10000) {

                currentState = TMState.REJECT;

                System.out.println(
                        "Maximum steps exceeded."
                );

                break;
            }
        }

        System.out.println();
        System.out.println("---------------------------------");

        if (currentState == TMState.ACCEPT) {

            System.out.println(
                    "TM RESULT: ACCEPT"
            );

            System.out.println(
                    "Final State: qAccept"
            );

            return true;

        } else {

            System.out.println(
                    "TM RESULT: REJECT"
            );

            System.out.println(
                    "Final State: qReject"
            );

            return false;
        }
    }

    public TMState getCurrentState() {
        return currentState;
    }
}