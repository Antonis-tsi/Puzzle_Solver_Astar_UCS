import java.util.*;

public class UCS extends GAME {
    int NodeNums;
    
    public Node search() {
        //Priority Queue frontier για να αποθηκεύουμε τους κόμβους που δεν έχουν επεκταθεί, με βάση το κόστος (το χαμηλό κόστος έχει πρωτεραιότητα)
        //Hashset explored για να σημειώνουμε τους κόβμους που έχουμε επισκεφθεί
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        HashSet<String> explored = new HashSet<>();
        NodeNums =0;

        //Αρχικός κόμβος με cost 0, depth 0, και null parent 
        Node startNode = new Node(0, null, this, 0);
        frontier.add(startNode);

        while (!frontier.isEmpty()) {
            //Αποθηκεύουμε τον κόμβο που βγάζουμε απο το frontier(με το χαμηλότερο κόστος)
            Node currentNode = frontier.poll();
            GAME currentState = currentNode.state;

            //Ελέγχουμε αν η κατάσταση είναι ο στόχος μας,και αν είναι εκτυπώνουμε ότι βρήκαμε λύση, το σύνολο των κόμβων,το κόστος και το path
            if (currentState.isGoal()) {
                System.out.println("Number of total Nodes "+NodeNums);
                System.out.println("Cost "+currentNode.cost);
                printSolutionPath(currentNode);
                System.out.println("Solved ");
                return currentNode; //Επιστρέφουμε το goal node
            }

            //Σημειώνουμε ότι ελέγξαμε την current κατάσταση στο explored
            explored.add(Arrays.toString(currentState.getBoard()));

            //Έπεκτείνουμε τους κόβμους με τη GenerateChildren και αν η κατάσταση του νέου κόμβου δεν υπάρχει στο explored τον προσθέτουμε στο frontier 
            for (Node child : generateChildren(currentNode)) {
                String childBoardStr = Arrays.toString(child.state.getBoard());
                if (!explored.contains(childBoardStr)) {
                    NodeNums++;
                    frontier.add(child);
               }
            }
        }
        return null; //Επιστρέφει null αν δεν βρεί λύση
    }

    //Συνάρτηση generateChildren για να επεκτείνει τους κόμβους
    private List<Node> generateChildren(Node parent) {
        List<Node> children = new ArrayList<>();
        GAME parentState = parent.state;
        char[] parentBoard = parentState.getBoard();
        int emptyIndex = returnS(parentBoard);

        //Αποθηκεύουμε τις πιθανές κινήσεις για το κένο
        int[] moves = COST(emptyIndex);
        
        for (int i=0;i<moves.length;i++){
            System.out.print(moves[i]+" ");
            //System.out.println()
           // System.out.println("Cost: " + Math.abs(moves[i]));

        }
        System.out.println();
        parentState.PrintBoard();
        System.out.println(); 

        for (int move : moves) {
            
            //Για κάθε κίνηση προσθέτουμε το emptyindex με το move ωστε να βγουν οι θέσεις που μπορεί να παει
            int newIndex = emptyIndex + move;

            //Κλονοποιούμε το parent's state για να δημιουργήσουμε το νέο child state
            GAME childState = new GAME();
            char[] childBoard = parentBoard.clone();
            childState.board = childBoard;

            //Καλούμε την swap για να κάνει την αλλαγή θέσης
            swap(childState.board, emptyIndex, newIndex);

            //Υπολογίζουμε το κόστος της κίνησης
            int moveCost = Math.abs(move);

            //Δημιουργόυμε τον νέο child node και τον προσθέτουμε στη λίστα children
            Node child = new Node(parent.cost + moveCost, parent, childState, parent.depth + 1);

            children.add(child);
        }
        return children;//Επιστρέφουμε την λίστα children
    }
}
