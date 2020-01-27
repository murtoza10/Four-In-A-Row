
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Pavel
 */
public class Heuristic {

    //ArrayList<Integer> list;
    ArrayList<Integer> list = new ArrayList<Integer>();

    ArrayList<Integer> getColumns(int n, int difficulty, int lastmove) {
        if (difficulty == 2) {
            intermediate(n);
        } else if (difficulty == 3) {
            hard(n, lastmove);
        } else {
            easy(n);
        }
        return list;
    }

    void easy(int n) {
        for (int i = 0; i < n; i++) {
            list.add(i);
        }

    }

    void intermediate(int n) {
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        Collections.shuffle(list, new Random(System.currentTimeMillis()));
    }

    void hard(int n, int lastmove) {
        int[] tmp = new int[n];
        for (int i = 0; i < n; i++) {
            tmp[i] = i;
        }

        if (lastmove != -1) {
            tmp[lastmove] = -1;

            list.add(lastmove);
            if (inBound(n, lastmove - 1)) {
                tmp[lastmove - 1] = -1;

                list.add(lastmove - 1);
            }
            if (inBound(n, lastmove + 1)) {
                tmp[lastmove + 1] = -1;
                list.add(lastmove + 1);
            }
            for (int i = 0; i < n; i++) {
                if (tmp[i] >= 0) {
                    list.add(tmp[i]);
                }
            }
        }

    }

    int setRecurDepth(int difficulty) {
        if (difficulty == 1) {
            return 4;
        } else if (difficulty == 2) {
            return 7;
        } else {
            return 9;
        }
    }

    Boolean inBound(int n, int m) {

        if (m >= 0 && m < n) {
            return true;
        } else {
            return false;
        }
    }

}
