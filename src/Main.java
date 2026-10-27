import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import lexer.Lexer;
import lexer.Token;
import turingmachine.TuringMachine;

public class Main {

    public static void main(String[] args) {

        try {

            // ==============================
            // LOAD FRENCH DICTIONARY
            // ==============================

            Lexer lexer = new Lexer(
                    "../data/liste_francais.txt"
            );

            Scanner scanner = new Scanner(
                    System.in,
                    StandardCharsets.UTF_8
            );

            System.out.println();
            System.out.println("=================================");
            System.out.println("      FRENCH LANGUAGE LEXER");
            System.out.println("=================================");

            System.out.println();
            System.out.print(
                    "Enter a French word or sentence: "
            );

            String input = scanner.nextLine();

            // ==============================
            // LEXER
            // ==============================

            List<Token> tokens =
                    lexer.tokenize(input);

            System.out.println();
            System.out.println("Input:");
            System.out.println(input);

            System.out.println();
            System.out.println("Tokens:");

            for (int i = 0; i < tokens.size(); i++) {

                Token token = tokens.get(i);

                System.out.println(
                        (i + 1)
                        + ". "
                        + token.getValue()
                        + " -> "
                        + token.getType()
                );
            }

            System.out.println();
            System.out.println("---------------------------------");

            System.out.println(
                    "Total Tokens: "
                    + lexer.getTokenCount(tokens)
            );

            System.out.println("---------------------------------");

            // ==============================
            // TURING MACHINE
            // ==============================

            TuringMachine tm =
                    new TuringMachine();

            boolean accepted =
                    tm.run(tokens);

            // ==============================
            // FINAL RESULT
            // ==============================

            System.out.println();
            System.out.println("=================================");
            System.out.println("        FINAL RESULT");
            System.out.println("=================================");

            if (accepted) {

                System.out.println(
                        "French Language: ACCEPTED"
                );

            } else {

                System.out.println(
                        "French Language: REJECTED"
                );
            }

            System.out.println(
                    "Final TM State: "
                    + tm.getCurrentState()
            );

            System.out.println(
                    "================================="
            );

            scanner.close();

        } catch (Exception e) {

            System.out.println();
            System.out.println("ERROR:");
            e.printStackTrace();
        }
    }
}