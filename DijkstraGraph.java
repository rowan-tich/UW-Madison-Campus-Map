// --== CS400 File Header Information ==--
// Name: Rowan Tichelaar
// Email: rtichelaar@wisc.edu
// Group and Team: BF-red
// Group TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: Couldn't get @BeforeEach working again

import java.util.PriorityQueue;
import java.util.ArrayList;

// import org.junit.Test;

// import static org.junit.Assert.assertEquals;

import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes.  This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
    extends BaseGraph<NodeType,EdgeType>
    implements MapAlgorithmInterface<NodeType, EdgeType> {


    private ArrayList<NodeType> locations = new ArrayList<>();
    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph.  The final node in this path is stored in it's node
     * field.  The total cost of this path is stored in its cost field.  And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in it's node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;
        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }

        public String toString() {
            return (String) node.data;
        }
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations.  The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *         or when either start or end data do not correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        PriorityQueue<SearchNode> pathQueue = new PriorityQueue<>(); //insert edges in to this to explore the lowest cost ones first
        Hashtable<Node, SearchNode> visitedNodes = new Hashtable<>();  //to keep track of which nodes we have already been to (this how we set the distance, etc)
        
        Double currentTotalWeight = 0.0;
        Node currentNode = nodes.get(start);
        SearchNode previousSearchNode = new SearchNode(currentNode, currentTotalWeight, null);
        visitedNodes.put(nodes.get(start), previousSearchNode);

        if(currentNode == null || nodes.get(end) == null || !containsNode(start) || !containsNode(end) || !nodes.containsKey(start) || !nodes.containsKey(end)){
            throw new NoSuchElementException("End node is not reachable");
        }
        while(previousSearchNode.node != nodes.get(end)){
            for(Edge e: currentNode.edgesLeaving){
                //get new weight
                Double newTotalWeight = currentTotalWeight + e.data.doubleValue();

                //ensures we don't insert or check anything extra
                if(visitedNodes.containsKey(e.successor) && visitedNodes.get(e.successor).cost <= newTotalWeight) {
                    continue;
                }else if(visitedNodes.containsKey(e.successor) && visitedNodes.get(e.successor).cost > newTotalWeight){
                    //replaces current hash entry if weight is lower
                    SearchNode temp = new SearchNode(e.successor, newTotalWeight, previousSearchNode);
                    visitedNodes.replace(e.successor, temp);
                    pathQueue.add(temp);
                    continue;
                }
                //inserts new node
                SearchNode temp = new SearchNode(e.successor, newTotalWeight, previousSearchNode);
                visitedNodes.put(e.successor, temp);
                pathQueue.add(temp);
            }
            //gets best weight out of all current options
            previousSearchNode = pathQueue.poll();
            if(previousSearchNode == null ) {
                throw new NoSuchElementException("End node is not reachable");
            }
            currentNode = previousSearchNode.node;
            currentTotalWeight = previousSearchNode.cost;
        }

        return previousSearchNode;
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value.  This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path.  This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        LinkedList<NodeType> shortestPath = new LinkedList<>();
        if(nodes.get(start) == null || nodes.get(end) == null || !containsNode(start) || !containsNode(end) || !nodes.containsKey(start) || !nodes.containsKey(end)){
            throw new NoSuchElementException("End node is not reachable");
        }
        SearchNode endNode = computeShortestPath(start, end);

        while (endNode != null) {
            shortestPath.addFirst(endNode.node.data);
            endNode = endNode.predecessor;
        }

        return shortestPath;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path from the node containing the start data to the node containing the
     * end data.  This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        if(nodes.get(start) == null || nodes.get(end) == null || !containsNode(start) || !containsNode(end) || !nodes.containsKey(start) || !nodes.containsKey(end)){
            throw new NoSuchElementException("End node is not reachable");
        }
        SearchNode endNode = computeShortestPath(start, end);
        double totalCost = 0.0;
        totalCost = endNode.cost;
        // while (endNode != null) {
        //     totalCost += endNode.cost;
        //     endNode = endNode.predecessor;
        // }

        return totalCost;
    }



    /*
     *--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * ALL THE NEW FUNCTIONS ARE BELOW
     * THESE IMPLEMENT A BOOLEAN TO SET WHERE OR NOT YOU WANT TO TAKE THE BUS
     * 
     * FALSE: MEANS THAT YOU ARE WILLING TO TAKE THE BUS, BUT IT CAN RETURN A LIST OF NODES THAT DO NOT CONTAIN BUS EDGES IF WALKING IS THAT MUCH FASTER.
     * "THAT MUCH FASTER" IS DETERMINED BY THE DOUBLE BELOW. IT IS A PERCENTAGE OF HOW MUCH SLOWER YOUR ARE WILLING TO BE TO TAKE THE BUS.
     * 
     * TRUE: MEANS YOU WILL ABSOLUTELY NOT TAKE THE BUS AT ALL, THIS MEANS THE FUNCTIONS WILL NOT RETURN A BUS EDGE EVER.
     * --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */


    
    double pityPercent = 0.75;

    public List<NodeType> shortestPathDataMulti(List<NodeType> listOfNodes, boolean walking){
        List<NodeType> currentList = listOfNodes;
        LinkedList<NodeType> megaList = new LinkedList<>();

        for(int i = 0; i < currentList.size() - 1; i++){
            megaList.addAll(shortestPathData(currentList.get(i), currentList.get(i + 1), walking));
            if(!(i + 1 >= currentList.size() - 1)){
                megaList.removeLast();
            }
        }

        return megaList;
    }

    public double shortestPathCostMulti(List<NodeType> listOfNodes, boolean walking){
        List<NodeType> currentList = listOfNodes;
        double megaCost = 0.0;
        
        for(int i = 0; i < currentList.size() - 1; i++){
            megaCost += shortestPathCost(currentList.get(i), currentList.get(i + 1), walking);
        }

        return megaCost;
    }

    //Modified function to included boolean on whether or not it is walking
    public List<NodeType> shortestPathData(NodeType start, NodeType end, boolean walking){
        LinkedList<NodeType> shortestPath = new LinkedList<>();
        if(nodes.get(start) == null || nodes.get(end) == null || !containsNode(start) || !containsNode(end) || !nodes.containsKey(start) || !nodes.containsKey(end)){
            throw new NoSuchElementException("End node is not reachable");
        }
        SearchNode endNode = computeShortestPath(start, end, walking);

        while (endNode != null) {
            shortestPath.addFirst(endNode.node.data);
            endNode = endNode.predecessor;
        }

        return shortestPath;
    }

    //Modified function to included boolean on whether or not it is walking
    public double shortestPathCost(NodeType start, NodeType end, boolean walking){
        if(nodes.get(start) == null || nodes.get(end) == null || !containsNode(start) || !containsNode(end) || !nodes.containsKey(start) || !nodes.containsKey(end)){
            throw new NoSuchElementException("End node is not reachable");
        }
        SearchNode endNode = computeShortestPath(start, end, walking);
        double totalCost = 0.0;
        totalCost = endNode.cost;
        return totalCost;
    }

    public boolean removeEdge(NodeType pred, NodeType succ, boolean walking){
        try {
            // when an edge exists
            Edge oldEdge = getEdgeHelper(pred,succ,walking);        
            // remove it from the edge lists of each adjacent node
            oldEdge.predecessor.edgesLeaving.remove(oldEdge);
            oldEdge.successor.edgesEntering.remove(oldEdge);
            // and decrement the edge count before removing
            this.edgeCount--;
            return true;
        } catch(NoSuchElementException e) {
            // when no such edge exists, return false instead
            return false;
        }
    }
    public boolean containsEdge(NodeType pred, NodeType succ, boolean walking){
        try { getEdgeHelper(pred,succ, walking); return true; }
        catch(NoSuchElementException e) { return false; }
    }
    public EdgeType getEdge(NodeType pred, NodeType succ, boolean walking){
        return getEdgeHelper(pred,succ, walking).data;
    }

    protected Edge getEdgeHelper(NodeType pred, NodeType succ, boolean walking) {
        Node predNode = nodes.get(pred);
        // search for edge through the predecessor's list of leaving edges
        for(Edge edge : predNode.edgesLeaving)
            // compare succ to the data in each leaving edge's successor
            if(edge.successor.data.equals(succ) && edge.busPath == walking)
                return edge;
        // when no such edge can be found, throw NSE
        throw new NoSuchElementException("No edge from "+pred.toString()+" to "+
                                         succ.toString());
    }

    protected SearchNode computeShortestPath(NodeType start, NodeType end, boolean walking) {
        PriorityQueue<SearchNode> pathQueue = new PriorityQueue<>(); //insert edges in to this to explore the lowest cost ones first
        Hashtable<Node, SearchNode> visitedNodes = new Hashtable<>();  //to keep track of which nodes we have already been to (this how we set the distance, etc)
        
        Double currentTotalWeight = 0.0;
        Node currentNode = nodes.get(start);
        SearchNode previousSearchNode = new SearchNode(currentNode, currentTotalWeight, null);
        visitedNodes.put(nodes.get(start), previousSearchNode);

        // System.out.println("curr = " + currentNode);
        if(currentNode == null || nodes.get(end) == null || !containsNode(start) || !containsNode(end) || !nodes.containsKey(start) || !nodes.containsKey(end)){
            throw new NoSuchElementException("End node is not reachable");
        }
        while(previousSearchNode.node != nodes.get(end)){
            for(Edge e: currentNode.edgesLeaving){
                if(e.busPath == true && walking == true){
                    // System.out.println(e);
                    // System.out.println("curr=" + currentNode + "buspath=" + e.busPath);
                    continue;
                }
                //get new weight
                Double newTotalWeight = currentTotalWeight + e.data.doubleValue();

                Double potentialWeightMod = 1.0;
                if(e.busPath == true && walking == false){
                    potentialWeightMod = pityPercent;
                }
                //ensures we don't insert or check anything extra
                if(visitedNodes.containsKey(e.successor) && visitedNodes.get(e.successor).cost <= newTotalWeight  * potentialWeightMod) {
                    continue;
                }else if(visitedNodes.containsKey(e.successor) && visitedNodes.get(e.successor).cost > newTotalWeight  * potentialWeightMod){
                    //replaces current hash entry if weight is lower
                    SearchNode temp = new SearchNode(e.successor, newTotalWeight  * potentialWeightMod, previousSearchNode);
                    visitedNodes.replace(e.successor, temp);
                    pathQueue.add(temp);
                    continue;
                }
                //inserts new node
                SearchNode temp = new SearchNode(e.successor, newTotalWeight  * potentialWeightMod, previousSearchNode);
                visitedNodes.put(e.successor, temp);
                pathQueue.add(temp);
                
            }
            //gets best weight out of all current options
            previousSearchNode = pathQueue.poll();
            // System.out.println(previousSearchNode);
            // System.out.println(pathQueue);
            if(previousSearchNode == null ) {
                throw new NoSuchElementException("End node is not reachable");
            }
            currentNode = previousSearchNode.node;
            currentTotalWeight = previousSearchNode.cost;
        }

        return previousSearchNode;
    }

    public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight, boolean busPath) {
        // find nodes associated with node data, and return false when not found
        Node predNode = nodes.get(pred);
        Node succNode = nodes.get(succ);
        if(predNode == null || succNode == null) return false;
        try {
            // when an edge alread exists within the graph, update its weight
            Edge existingEdge = getEdgeHelper(pred,succ);
            existingEdge.data = weight;
        } catch(NoSuchElementException e) {
            // otherwise create a new edges
            Edge newEdge = new Edge(weight,predNode,succNode, !busPath); // added ! before busPath
            this.edgeCount++;
            // and insert it into each of its adjacent nodes' respective lists
            predNode.edgesLeaving.add(newEdge);
            succNode.edgesEntering.add(newEdge);
        }
        return true;
    }

    public List<NodeType> getLocations(){
        return locations;
    }

    @Override
    public boolean insertNode(NodeType data) {
        if(nodes.containsKey(data)) return false; // throws NPE when data's null
        nodes.put(data,new Node(data));
        locations.add(data);
        return true;
    }

    @Override
    public boolean removeNode(NodeType data) {
        // remove this node from nodes collection
        if(!nodes.containsKey(data)) return false; // throws NPE when data==null
        Node oldNode = nodes.remove(data);
        // remove all edges entering neighboring nodes from this one
        for(Edge edge : oldNode.edgesLeaving)
            edge.successor.edgesEntering.remove(edge);
        // remove all edges leaving neighboring nodes toward this one
        for(Edge edge : oldNode.edgesEntering)
            edge.predecessor.edgesLeaving.remove(edge);
        
        locations.remove(data);
        return true;
    }

    @Override
    public boolean containsNode(NodeType data) {
        return nodes.containsKey(data);
    }

    @Override
    public NodeType getNode(String location) {
        return null;
    }

    public Hashtable<NodeType,Node> returnHash() {
        return nodes;
    }
    

    // @Test
    // public void testDjikstra(){
    //     DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
    //     graph.insertNode("A");
    //     graph.insertNode("B");
    //     graph.insertNode("H");
    //     graph.insertNode("I");
    //     graph.insertNode("L");
    //     graph.insertNode("D");
    //     graph.insertNode("M");
    //     graph.insertNode("E");
    //     graph.insertNode("F");
    //     graph.insertNode("G");

    //     graph.insertEdge("H", "B", 6, false);
    //     graph.insertEdge("B", "M", 3, false);
    //     graph.insertEdge("M", "F", 4, false);
    //     graph.insertEdge("F", "G", 9, false);
    //     graph.insertEdge("G", "L", 7, false);
    //     graph.insertEdge("H", "I", 2, false);
    //     graph.insertEdge("I", "H", 2, false);
    //     graph.insertEdge("I", "D",1, false);
    //     graph.insertEdge("D", "G", 2, false);
    //     graph.insertEdge("D", "A", 7, false);
    //     graph.insertEdge("A", "B", 0, false);
    //     graph.insertEdge("A", "H", 8, false);
    //     graph.insertEdge("A", "M", 5, false);
    //     graph.insertEdge("M", "E", 3, false);

    //     SearchNode sn = (DijkstraGraph<NodeType, EdgeType>.SearchNode) graph.computeShortestPath("D", "I");
    //     assertEquals(sn.cost, 17, 0.1);
    // }
    // @Test
    // public void testCostAndSequence(){
    //     DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
    //     graph.insertNode("A");
    //     graph.insertNode("B");
    //     graph.insertNode("H");
    //     graph.insertNode("I");
    //     graph.insertNode("L");
    //     graph.insertNode("D");
    //     graph.insertNode("M");
    //     graph.insertNode("E");
    //     graph.insertNode("F");
    //     graph.insertNode("G");

    //     graph.insertEdge("H", "B", 6, false);
    //     graph.insertEdge("B", "M", 3, false);
    //     graph.insertEdge("M", "F", 4, false);
    //     graph.insertEdge("F", "G", 9, false);
    //     graph.insertEdge("G", "L", 7, false);
    //     graph.insertEdge("H", "I", 2, false);
    //     graph.insertEdge("I", "H", 2, false);
    //     graph.insertEdge("I", "D",1, false);
    //     graph.insertEdge("D", "G", 2, false);
    //     graph.insertEdge("D", "A", 7, false);
    //     graph.insertEdge("A", "B", 0, false);
    //     graph.insertEdge("A", "H", 8, false);
    //     graph.insertEdge("A", "M", 5, false);
    //     graph.insertEdge("M", "E", 3, false);

    //     assertEquals(graph.shortestPathData("D", "I").toString(), "[D, A, H, I]");
    //     assertEquals(graph.shortestPathCost("D", "I"), 17.0, 0.1);
    // }

    // @Test
    // public void testNoPath(){
    //     DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
    //     graph.insertNode("A");
    //     graph.insertNode("B");
    //     graph.insertNode("H");
    //     graph.insertNode("I");
    //     graph.insertNode("L");
    //     graph.insertNode("D");
    //     graph.insertNode("M");
    //     graph.insertNode("E");
    //     graph.insertNode("F");
    //     graph.insertNode("G");

    //     graph.insertEdge("H", "B", 6, false);
    //     graph.insertEdge("B", "M", 3, false);
    //     graph.insertEdge("M", "F", 4, false);
    //     graph.insertEdge("F", "G", 9, false);
    //     graph.insertEdge("G", "L", 7, false);
    //     graph.insertEdge("H", "I", 2, false);
    //     graph.insertEdge("I", "H", 2, false);
    //     graph.insertEdge("I", "D",1, false);
    //     graph.insertEdge("D", "G", 2, false);
    //     graph.insertEdge("D", "A", 7, false);
    //     graph.insertEdge("A", "B", 0, false);
    //     graph.insertEdge("A", "H", 8, false);
    //     graph.insertEdge("A", "M", 5, false);
    //     graph.insertEdge("M", "E", 3, false);

    //     String errorMessage = "";
    //     try {
    //         graph.computeShortestPath("L", "D");
    //     } catch (Exception e) {
    //         errorMessage = "End node is not reachable";
    //     }
    //     assertEquals("End node is not reachable", errorMessage);
    // }
}