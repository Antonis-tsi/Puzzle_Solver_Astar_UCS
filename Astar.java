import java.util.*;

public class Astar extends GAME{
    
    public int Nodenums;
    
    public Node Asearch(){
       
        //Βάζουμε στο priority queue το heuristic και το κόστος
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost + heuristic(a)));
        Nodenums=0;
        
        //Αρχικός κόμβος με cost 0, depth 0, και null parent 
        Node start = new Node(0,null,this,0);
        frontier.add(start);

        while (!frontier.isEmpty()) {
            
            //Αποθηκεύουμε τον κόμβο που βγάζουμε από το frontier
            Node currentNode = frontier.poll();
            GAME currentState = currentNode.state;

            //Ελέγχουμε αν η κατάσταση είναι ο στόχος μας,και αν είναι εκτυπώνουμε ότι βρήκαμε λύση, το σύνολο των κόμβων,το κόστος και το path
            if (currentState.isGoal()) {
                System.out.println("Number of total Nodes "+Nodenums);
                System.out.println("Cost "+currentNode.cost);
                printSolutionPath(currentNode);
                System.out.println("Solved ");
                return currentNode; //Επιστρέφουμε το goal node
            }

            //Έπεκτείνουμε τους κόβμους με τη GenerateChildrenAstar και τους προσθέτουμε στο frontier 
            for (Node child : generateChildrenAstar(currentNode)) {
                    Nodenums++;
                    frontier.add(child);
            }
        }
        return null; //Επιστρέφει null αν δεν βρεί λύση
    }
    
    private List<Node> generateChildrenAstar(Node parent) {
        
        List<Node> children = new ArrayList<>();
        GAME parentState = parent.state;
        char[] parentBoard = parentState.getBoard();
        int emptyIndex = returnS(parentBoard);

        //Αποθηκεύουμε τις πιθανές κινήσεις για το κένο
        int[] moves = COST(emptyIndex);
       
        /*for (int i=0;i<moves.length;i++){
            System.out.print(moves[i]+" ");
        }
        System.out.println();
        parentState.PrintBoard();
        System.out.println();*/

        
        for (int move : moves) {
            
            //Για κάθε κίνηση προσθέτουμε το emptyindex με το move ωστε να βγουν οι θέσεις που μπορεί να παει
            int newIndex = emptyIndex + move;
            
            //Κλονοποιούμε το parent's state για να δημιουργήσουμε το νέο child state
            GAME childState = new GAME();
            char[] childBoard = parentBoard.clone();
            childState.board = childBoard;

            //Καλούμε την swap για να κάνει την αλλαγή θέσης
            swap(childState.board, emptyIndex, newIndex);

            //Υπολογίζουμε το κόστος της κίνησης,έχουμε απόλυτο γιατί έχουμε τις κινήσεις -3 -2 -1 +1 +2 +3
            int moveCost = Math.abs(move);
            
            //Δημιουργόυμε τον νέο child node και τον προσθέτουμε στη λίστα children
            Node child = new Node(parent.cost + moveCost , parent, childState, parent.depth + 1);
            children.add(child);
        }
        return children;//Επιστρέφουμε την λίστα children
    }

    //Συνάρτηση για να υπολογίσουμε το heuristic
    private int heuristic(Node currentNode){
        int heuristicCost =0;
        char[] BoardReference = currentNode.getBoard();
        for(int i=0;i<this.BoardLength();i++){
            if(BoardReference[i]=='W'){
                heuristicCost+=i+1;
            }
        }
        return heuristicCost;//Επιστρέφει το heuristiccost
    }
}
