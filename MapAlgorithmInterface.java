import java.util.List;

public interface MapAlgorithmInterface<NodeType, EdgeType extends Number> extends GraphADT<NodeType, EdgeType> {

    // Modified function to included boolean on whether or not it is walking
    public List<NodeType> shortestPathData(NodeType start, NodeType end, boolean walking);

    // Modified function to included boolean on whether or not it is walking
    public double shortestPathCost(NodeType start, NodeType end, boolean walking);

    // Instead of specifying the start and end nodes this will recursively call the shortestPathData 
    // function to iterate through the nodes list and compile the list of nodes to go through. 
    // Index 0 of the list will be the start node and index -1 will be the end node.
    public List<NodeType> shortestPathDataMulti(List<NodeType> nodes, boolean walking);
    
    // Instead of specifying the start and end nodes this will recursively call the shortestPathData 
    // function to iterate through the nodes list and compile the weight of the nodes going through. 
    // Index 0 of the list will be the start node and index -1 will be the end node.
    public double shortestPathCostMulti(List<NodeType> locations, boolean walking);
    
    public boolean removeEdge(NodeType pred, NodeType succ, boolean walking);

    public boolean containsEdge(NodeType pred, NodeType succ, boolean walking);

    public EdgeType getEdge(NodeType pred, NodeType succ, boolean walking);

    public NodeType getNode(String location);
    
    public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight, boolean walking);

    public List<NodeType> getLocations();
    
}
    