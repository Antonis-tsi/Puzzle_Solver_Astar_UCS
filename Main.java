public class Main {
    public static void main(String[] args) {
        
        //Δημιουργούμε ένα αντικέιμενο UCS και καλούμε την search για να βρει τη λύση
        UCS ucsSolve= new UCS();
        System.out.println("UCS Solution: ");
        ucsSolve.search();
        
        //Δημιουργούμε ένα αντικέιμενο Astar και καλούμε την search για να βρει τη λύση
        Astar AstarSolve= new Astar();
        System.out.println("\nAstar Solution: ");
        AstarSolve.Asearch();

    }
}
