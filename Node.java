class Node {
    
    //Κόστος,parent,κατάσταση,βάθος
    public int cost;
    public Node parent;
    public GAME state;
    public int depth;

    //Constructor με ορίσματα
    public Node(int cost,Node parent,GAME state,int depth){
        this.cost=cost;
        this.parent=parent;
        this.state=state;
        this.depth=depth;
    }
    
    //Συνάρτησεις set και get 
    public void steNextNode(Node node){
        this.parent=node;
    }

    public Node getNextNode(Node node){
        this.parent=node;
        return null;
    }

    public char[] getBoard() {
        return state.getBoard(); 
    }
    
    public Node getParentNode( ){
        return this.parent;
    }
}
