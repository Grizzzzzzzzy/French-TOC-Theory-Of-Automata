package lexer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private final Set<String> frenchWords = new HashSet<>();

    /*
     * Token pattern
     *
     * WORD:
     * French/Unicode letters + accents
     *
     * NUMBER:
     * Integer or decimal number
     *
     * PUNCTUATION:
     * Common punctuation marks
     *
     * OTHER:
     * Any remaining non-space character
     */
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "[\\p{L}\\p{M}]+(?:[''][\\p{L}\\p{M}]+)*"
            + "|\\d+(?:[.,]\\d+)?"
            + "|[.,!?;:()\\[\\]{}\"<>]"
            + "|[-]"
            + "|\\S"
    );

    public Lexer(String dictionaryPath) throws IOException {
        loadDictionary(dictionaryPath);
    }

    private void loadDictionary(String dictionaryPath) throws IOException {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(dictionaryPath),
                        StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim().toLowerCase(Locale.ROOT);

                if (line.isEmpty()) {
                    continue;
                }

                // Only load single-word dictionary entries
                if (line.contains(" ")) {
                    continue;
                }

                frenchWords.add(line);
            }
        }

        System.out.println(
                "French words loaded: " + frenchWords.size()
        );
    }

    public List<Token> tokenize(String input) {

        List<Token> tokens = new ArrayList<>();

        if (input == null || input.isBlank()) {
            return tokens;
        }

        Matcher matcher = TOKEN_PATTERN.matcher(input);

        while (matcher.find()) {

            String value = matcher.group();

            TokenType type = identifyToken(value);

            tokens.add(
                    new Token(value, type)
            );
        }

        return tokens;
    }

    private TokenType identifyToken(String token) {

        String normalized = token
                .toLowerCase(Locale.ROOT)
                .trim();

        // Number
        if (normalized.matches("\\d+(?:[.,]\\d+)?")) {
            return TokenType.NUMBER;
        }

        // Punctuation
        if (normalized.matches(
                "[.,!?;:()\\[\\]{}\"<>-]"
        )) {
            return TokenType.PUNCTUATION;
        }

        // French dictionary word
        if (frenchWords.contains(normalized)) {
            return TokenType.WORD;
        }

        // Unknown
        return TokenType.UNKNOWN;
    }

    public int getTokenCount(List<Token> tokens) {
        return tokens.size();
    }
}