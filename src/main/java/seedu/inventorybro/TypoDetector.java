package seedu.inventorybro;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Detects potential typos in user-entered commands using a weighted edit distance
 * with QWERTY keyboard Manhattan distance as the substitution cost.
 */
public class TypoDetector {
    private static final List<String> KNOWN_COMMANDS = Arrays.asList(
            "addItem", "addCategory", "deleteCategory", "listCategories", "deleteItem", "editDescription", "editPrice",
            "editCategory", "editQuantity", "transact", "filterItem", "showHistory", "listItems", "findItem",
            "help", "exit"
    );
    private static final double TYPO_THRESHOLD_FACTOR = 0.2;
    private static final Map<Character, int[]> KEY_POSITIONS = buildKeyPositions();

    private static Map<Character, int[]> buildKeyPositions() {
        Map<Character, int[]> positions = new HashMap<>();
        String[] rows = {"qwertyuiop", "asdfghjkl", "zxcvbnm"};
        for (int row = 0; row < rows.length; row++) {
            for (int col = 0; col < rows[row].length(); col++) {
                positions.put(rows[row].charAt(col), new int[]{row, col});
            }
        }
        return positions;
    }

    /**
     * Finds the closest known command to the given input word.
     * Returns the command if its weighted edit distance is below the typo threshold.
     *
     * @param inputWord The first word of the user's input.
     * @return An Optional with the closest command name, or empty if none qualifies.
     */
    public Optional<String> findClosestMatch(String inputWord) {
        String lowerInput = inputWord.toLowerCase();
        String bestCommand = null;
        double bestDistance = Double.MAX_VALUE;
        for (String command : KNOWN_COMMANDS) {
            double dist = calculateWeightedEditDistance(lowerInput, command.toLowerCase());
            if (dist < bestDistance) {
                bestDistance = dist;
                bestCommand = command;
            }
        }
        if (bestCommand != null && isBelowTypoThreshold(bestDistance, inputWord, bestCommand)) {
            return Optional.of(bestCommand);
        }
        return Optional.empty();
    }

    private boolean isBelowTypoThreshold(double distance, String input, String command) {
        double maxLen = Math.max(input.length(), command.length());
        return distance < TYPO_THRESHOLD_FACTOR * maxLen;
    }

    private double calculateWeightedEditDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        double[][] dp = new double[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = computeCell(dp, s1, s2, i, j);
            }
        }
        return dp[m][n];
    }

    private double computeCell(double[][] dp, String s1, String s2, int i, int j) {
        double deleteCost = dp[i - 1][j] + 1.0;
        double insertCost = dp[i][j - 1] + 1.0;
        double subCost = dp[i - 1][j - 1] + keyboardDistance(s1.charAt(i - 1), s2.charAt(j - 1));
        return Math.min(deleteCost, Math.min(insertCost, subCost));
    }

    private double keyboardDistance(char a, char b) {
        if (a == b) {
            return 0.0;
        }
        int[] posA = KEY_POSITIONS.get(a);
        int[] posB = KEY_POSITIONS.get(b);
        if (posA == null || posB == null) {
            return 1.0;
        }
        return Math.abs(posA[0] - posB[0]) + Math.abs(posA[1] - posB[1]);
    }
}
