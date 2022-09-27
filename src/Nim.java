// Name: Mwahhid Majeed
// Section: Tuesday/Thursday
// I have neither given nor received unauthorized aid on this program.

import java.util.*;

public class Nim {

    public static void main(String[] args) {
        // Prompt user for input
        Scanner scan = new Scanner(System.in);
        System.out.print("Number in pile 0? ");
        int p1 = Integer.parseInt(scan.nextLine());
        System.out.print("Number in pile 1? ");
        int p2 = Integer.parseInt(scan.nextLine());
        System.out.print("Number in pile 2? ");
        int p3 = Integer.parseInt(scan.nextLine());

        System.out.print("\nNumber of games to simulate? ");
        int simNum = Integer.parseInt(scan.nextLine());

        System.out.printf("\nInitial board is %d-%d-%d, simulating %d games.\n \n", p1, p2, p3, simNum);
        // Call the Q learning algorithm to find the Q-values
        Map<String, Double> qValMap = QLearning.learnQValues(p1, p2, p3, simNum);
        // Sorts and displays the Q-values in an ascending order
        System.out.println("Final Q-values:\n");
        ArrayList<String> keys = new ArrayList<>(qValMap.keySet());
        Collections.sort(keys);
        for (String k : keys) {
            System.out.println("Q[" + k + "] = " + qValMap.get(k));
        }

        System.out.println();
        int playAgain = 1;
        while (playAgain == 1) { // Play Nim
            playNim(qValMap, scan, p1, p2, p3);
            System.out.print("\nPlay again? (1) Yes (2) No:  ");
            playAgain = Integer.parseInt(scan.nextLine());
        }
    }

    /** Plays a game of Nim between User and Computer */
    private static void playNim(Map<String, Double> qValMap, Scanner scan, int p1, int p2, int p3) {
        System.out.print("Who moves first, (1) User or (2) Computer? ");
        int firstPlayer = Integer.parseInt(scan.nextLine());

        while (p1 + p2 + p3 != 0) {
            if (firstPlayer == 1) {
                // User's turn:
                System.out.printf("\nPlayer A (user)'s turn; board is (%d, %d, %d).", p1, p2, p3);
                int[] val = userTurn(scan);
                if (val[0] == 0) p1 -= val[1];
                else if (val[0] == 1) p2 -= val[1];
                else p3 -= val[1];

                if (p1 + p2 + p3 == 0) {
                    System.out.println("\nGame over.\nWinner is (computer)");
                    return;
                }

                //Computer's turn:
                System.out.printf("\nPlayer B (computer)'s turn; board is (%d, %d, %d).", p1, p2, p3);
                String currState = "B" + p1 + p2 + p3;
                int action = computerTurn(qValMap, currState, QLearning.getActions(p1, p2, p3), "B");

                if (action < 10) {
                    p1 -= action;
                    System.out.printf("\nComputer chooses pile 0 and removes %d.\n", action);
                }
                else if (action < 20) {
                    p2 -= (action - 10);
                    System.out.printf("\nComputer chooses pile 1 and removes %d.\n", action - 10);
                }
                else {
                    p3 -= (action - 20);
                    System.out.printf("\nComputer chooses pile 2 and removes %d.\n", action - 20);
                }

                if (p1 + p2 + p3 == 0) {
                    System.out.println("\nGame over.\nWinner is A (user)");
                    return;
                }

            } else {
                //Computer's turn:
                System.out.printf("\nPlayer A (computer)'s turn; board is (%d, %d, %d).", p1, p2, p3);
                String currState = "A" + p1 + p2 + p3;
                int action = computerTurn(qValMap, currState, QLearning.getActions(p1, p2, p3), "A");

                if (action < 10) {
                    p1 -= action;
                    System.out.printf("\nComputer chooses pile 0 and removes %d.\n", action);
                }
                else if (action < 20) {
                    p2 -= (action - 10);
                    System.out.printf("\nComputer chooses pile 1 and removes %d.\n", action - 10);
                }
                else {
                    p3 -= (action - 20);
                    System.out.printf("\nComputer chooses pile 2 and removes %d.\n", action - 20);
                }

                if (p1 + p2 + p3 == 0) {
                    System.out.println("\nGame over.\nWinner is B (user).");
                    return;
                }

                // User's turn:
                System.out.printf("\nPlayer B (user)'s turn; board is (%d, %d, %d).", p1, p2, p3);
                int[] val = userTurn(scan);
                if (val[0] == 0) p1 -= val[1];
                else if (val[0] == 1) p2 -= val[1];
                else p3 -= val[1];

                if (p1 + p2 + p3 == 0) {
                    System.out.println("\nGame over.\nWinner is A (computer).");
                    return;
                }
            }
        }
    }

    /** Prompts user for inputs to play their turn */
    private static int[] userTurn(Scanner scan) {
        int[] val = new int[2];
        System.out.print("\nWhat pile? ");
        val[0] = Integer.parseInt(scan.nextLine());
        System.out.print("How many? ");
        val[1] = Integer.parseInt(scan.nextLine());
        return val;
    }

    /** Figures out and returns the best action for the computer-controlled player in that state */
    private static int computerTurn(Map<String, Double> qValMap, String currState,
                                    ArrayList<Integer> allActions, String player) {
        int action = 0;
        double actionVal;
        if (player.equals("A")) actionVal = -2000;
        else actionVal = 2000;

        for (int a : allActions) {
            String state = currState;
            if (a < 10) state += "0" + a;
            else state += a;

            double v = qValMap.get(state);
            if ((player.equals("A")) && v > actionVal) {
                action = a;
                actionVal = v;
            }
            else if ((player.equals("B")) && v < actionVal) {
                action = a;
                actionVal = v;
            }
        }

        return action;
    }
}
