import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class Board {

    static int SIZE;

    String[][] board = new String[3][3];

    Board(final int size) {

        SIZE = size;

        board[0][0] = "x";
        board[0][1] = "o";
        board[0][2] = "x";

        board[1][0] = "x";
        board[1][1] = "o";
        board[1][2] = "x";

        board[2][0] = "x";
        board[2][1] = "x";
        board[2][2] = "o";

        init();
    }

    private void init() {
        //
        System.out.println(findDiagonal(Integer::equals));
        System.out.println(findDiagonal((i,j)-> i+j == SIZE));
        System.out.println(findCross((i,j)-> board[i][j]));
    }

    boolean findCross(BiFunction<Integer,Integer, String> fn){

        for (int i = 0; i < SIZE; i++) {
            Map<String,Integer> players = new HashMap<>();
            for (int j = 0; j < SIZE; j++) {
                compute(players,fn.apply(j,i));
            }

            if ( isWinner(players))
                return true;
        }

        return false;
    }

    void compute(Map<String,Integer> players,String key){
        players.computeIfPresent(key,(k, v)-> ++v);
        players.computeIfAbsent(key,(v)-> 1);
    }


    boolean findDiagonal(BiPredicate<Integer,Integer> fn) {
        Map<String,Integer> players = new HashMap<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(fn.test(i,j)){
                    compute(players,board[i][j]);
                }
            }
        }

        return isWinner(players);
    }

    boolean isWinner(Map<String,Integer> players){
        return  hasLine(players, "x") || hasLine(players, "o");
    }

    boolean hasLine(Map<String,Integer> players, String player){
        return players.getOrDefault(player,0) == SIZE;
    }
}
