import java.util.*;

public class QLearning {
    /**
     * Map to store Strings representing the state-action and their respective Double Q-Values
     */
    private static final Map<String, Double> qValMap = new HashMap<>();

    /**
     * Runs the Nim Q-learning algorithm a certain number of times and returns the found Q values
     */
    public static Map<String, Double> learnQValues(int p1, int p2, int p3, int simNum) {
        for (int i = 0; i < simNum; i++) {
            simulateRandomly(p1, p2, p3);
        }

        return qValMap;
    }

    /**
     * Plays a game of Nim with randomly chosen actions for both players and updates
     * Q-values using the update equations
     */
    private static void simulateRandomly(int s1, int s2, int s3) {
        ArrayList<Integer> actions;
        int r;
        while (s1 + s2 + s3 != 0) {
            //For player A:
            actions = getActions(s1, s2, s3);
            int randNum = (int) Math.floor(Math.random() * actions.size());
            int action = actions.get(randNum); //Get random action

            String stateA = "A" + s1 + s2 + s3;
            if (action < 10) stateA += "0" + action;
            else stateA += action;

            if (action < 10) s1 -= action;
            else if (action < 20) s2 -= (action - 10);
            else s3 -= (action - 20);

            String nextState = "B" + s1 + s2 + s3;
            double minQVal = 2000;

            if (s1 + s2 + s3 == 0) {
                r = -1000;
                minQVal = 0;
            } else {
                r = 0;

                // minimum over all possible actions a' of Q[s', a']
                for (int a : getActions(s1, s2, s3)) {
                    String nextStateAction = nextState;
                    if (a < 10) nextStateAction += "0" + a;
                    else nextStateAction += a;
                    double v = 0;
                    if (qValMap.containsKey(nextStateAction)) {
                        v = qValMap.get(nextStateAction);
                    } else {
                        qValMap.put(nextStateAction, v);
                    }

                    if (v < minQVal) minQVal = v;
                }
            }

            // update Q[s,a]
            double stateVal = 0;
            if (!qValMap.containsKey(stateA)) qValMap.put(stateA, stateVal);

            stateVal = r + (0.9 * minQVal);
            qValMap.put(stateA, stateVal);


            if (s1 + s2 + s3 == 0) break; // Game Over


            //For player B:
            actions = getActions(s1, s2, s3);
            randNum = (int) Math.floor(Math.random() * actions.size());
            action = actions.get(randNum); //Get random action

            String stateB = "B" + s1 + s2 + s3;
            if (action < 10) stateB += "0" + action;
            else stateB += action;

            if (action < 10) s1 -= action;
            else if (action < 20) s2 -= (action - 10);
            else s3 -= (action - 20);

            nextState = "A" + s1 + s2 + s3;
            double maxQVal = -2000;

            if (s1 + s2 + s3 == 0) {
                r = 1000;
                maxQVal = 0;
            } else {
                r = 0;

                // maximum over all possible actions a' of Q[s', a']
                for (int a : getActions(s1, s2, s3)) {
                    String nextStateAction = nextState;
                    if (a < 10) nextStateAction += "0" + a;
                    else nextStateAction += a;
                    double v = 0;
                    if (qValMap.containsKey(nextStateAction)) {
                        v = qValMap.get(nextStateAction);
                    } else {
                        qValMap.put(nextStateAction, v);
                    }

                    if (v > maxQVal) maxQVal = v;
                }
            }

            // update Q[s,a]
            stateVal = r + (0.9 * maxQVal);
            qValMap.put(stateB, stateVal);
        }
    }

    /**
     * Generates an array list of possible actions from a state
     */
    public static ArrayList<Integer> getActions(int s1, int s2, int s3) {
        ArrayList<Integer> actions = new ArrayList<>();

        for (int i = 1; i <= s1; i++) {
            actions.add(i); // pile 1 action
        }
        for (int i = 1; i <= s2; i++) {
            actions.add(10 + i); // pile 2 action
        }
        for (int i = 1; i <= s3; i++) {
            actions.add(20 + i); // pile 3 action
        }

        return actions;
    }
}
