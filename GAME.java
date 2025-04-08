import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public  class GAME{
    
    //Πίνακας για τις καταστάσεις του παζλ και hashmap
    public char[] board;
    private HashMap<Character,Integer> map =new HashMap<>();
    
    //Constructor που ορίζει την αρχική κατάσταση και με την hashmap δίνουμε value  στον κάθε χαρακτήρα για τον έλεγχο του στόχου μας
    public GAME(){
        this.board = new char[7];
        if(board.length != 7){
            throw new IllegalArgumentException("Invalid board");
        }
        else {
            for( int i=0;i<7;i++){
                if(i<3){
                    this.board[i]='B'; //BLACK
                }
                if(i>=3 && i<6){
                    this.board[i]='W'; //WHITE
                }
                if(i==6){
                    this.board[i]='S'; //KENO
                }
                map.put('B',-10);
                map.put('W',10);
                map.put('S',0);
            }
        }
    }
    //Επιστρέφει clone σε περιπτώση που δεν θελουμε αλλαγές στον πίνακα
    public char[] getBoardClone(){
        return this.board.clone(); 
    }

    //Επιστρέφει τον πίνακα(κατάσταση του παζλ)
    public char[] getBoard(){
        return this.board;
    }

    //Επιστρέφει το μήκος του πίνακα
    public int BoardLength(){
        return board.length;
    }
    
    //Ελέγχει αν έχουμε φτάσει στον στόχο μας με την χρήση των values
    public boolean isGoal(){
        int sum=0;
        for(int i=0;i<7;i++){
            sum += map.get(board[i]);
            if(sum==30){
                return true;
            }
        }
        return false;
    }
    
    //Επιστρέφει τη θέση του κενού
    public int returnS(char [] board){
        for (int i=0;i<board.length;i++){
            if(board[i]=='S'){
                return i;
            }
        }
        return -1;
    }

    //Αλλάζει την τιμή των θέσεων, το i παίρνει την τιμή του j και ανάποδα
    public void swap(char[] board, int i,int j){
        char temp= board[i];
        board[i] = board[j];
        board[j] = temp;
    }

    //Εκτυπώνει τον πίνακα(κατάσταση του παζλ)
    public void PrintBoard(){
        for(int i=0;i<board.length;i++){
            System.out.print(board[i]+" ");
        }
        System.out.println();
    }

    //Εκτυπώνει το solution path 
    public void printSolutionPath(Node goalNode) {
        
        List<Node> path = new ArrayList<>();
        Node current = goalNode;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        //Εκτυπώνουμε το path ανάποδα (start to goal)
        Collections.reverse(path);
        System.out.println("Solution Path:");
        for (Node node : path) {
            node.state.PrintBoard();
        }
    }

    //Επιστρέφει τον πίνακα των πιθανών κινήσεων για την αντίστοιχη θεση
    public int[] COST(int position){ 
        
        List<Integer> validMoves = new ArrayList<>();
        char[] boardClone = this.getBoardClone();
        
        //Δημιουργόυμε τις κινήσεις στα αριστερά -1,-2,-3
        for (int k = 3; k >= 1; k--) {
            if (position - k >= 0) {  //Για να μην ξεφύγει από τα όρια
                validMoves.add(-k);
            }
        }

        //Δημιουργόυμε τις κινήσεις στα δεξια +1, +2, +3
        for (int k = 1; k <= 3; k++) {
            if (position + k < boardClone.length) { //Για να μην ξεφύγει από τα όρια
                validMoves.add(k);
            }
        }
        
        //Αποθηκεύουμε στον πίνακα costs
        int[] costs = new int[validMoves.size()];
        for ( int i = 0; i < validMoves.size(); i++) {
            costs[i] = validMoves.get(i);
        }
        return costs;
    }
}
