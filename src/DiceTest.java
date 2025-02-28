import java.util.Random;

public class DiceTest {

    private static final int TOTAL_SIMULATIONS = 10000;
    private static final int TOTAL_NUM_DICE = 5;
    private static final int MAX_DICE_VALUE = 6;
    private static final int NO_SCORE_DICE_VALUE = 3;

    private final Random random;
    private int diceCount;

    public static void main(String[] args) {
        DiceTest diceTest = new DiceTest();
        diceTest.runSimulation();
    }

    private DiceTest() {
        random = new Random();
    }

    private void runSimulation() {
        System.out.printf("Number of simulations was %d using %d dice.\n", TOTAL_SIMULATIONS, TOTAL_NUM_DICE);

        // Play games
        int[] scores = new int[TOTAL_NUM_DICE * MAX_DICE_VALUE + 1];

        long startTime = System.nanoTime();

        for (int i = 0; i < TOTAL_SIMULATIONS; i++) {
            int score = playGame();
            scores[score]++;
        }

        long endTime = System.nanoTime();

        // Output results
        for (int i = 0; i < scores.length; ++i) {
            float rate = ((float) scores[i]) / TOTAL_SIMULATIONS;
            System.out.printf("Total %d occurs %s occurred %d times.\n", i, String.valueOf(rate), scores[i]);
        }

        float totalTime = (endTime - startTime) / 1000000000.0f;
        System.out.printf("Total simulation took %f seconds.\n", totalTime);
    }

    private int playGame() {
        diceCount = TOTAL_NUM_DICE;
        int score = 0;

        while (diceCount > 0) {
            score += roll();
        }

        return score;
    }

    private int roll() {
        int numNoScoreDice = 0;
        int minDiceValue = 0;

        for (int i = 0; i < diceCount; ++i) {
            int diceValue = getRandomDiceValue();

            if (diceValue == NO_SCORE_DICE_VALUE) {
                ++numNoScoreDice;
            } else if (numNoScoreDice == 0 && (i == 0 || diceValue < minDiceValue)) {
                minDiceValue = diceValue;
            }
        }

        if (numNoScoreDice > 0) {
            diceCount -= numNoScoreDice;
            return 0;
        }

        --diceCount;
        return minDiceValue;
    }

    private int getRandomDiceValue() {
        return random.nextInt(1, MAX_DICE_VALUE + 1);
    }
}
