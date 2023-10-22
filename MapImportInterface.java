import java.util.Hashtable;
import java.util.List;

public interface MapImportInterface {

	// private List<StopNodeInterface> nodes;
    // private List<EdgePathInterface> edges;
    // private Hashtable<String, StopNodeInterface> map;

    //public MapImport() {
            //nodes = new ArrayList<>();
            //edges = new ArrayList<>();
    //}

	public void addNode(StopNodeInterface location);
    public void addEdge(EdgePathInterface edge);
    public List<StopNodeInterface> getNodes();
    public List<EdgePathInterface> getEdges();
    public void setMap(Hashtable<String, StopNodeInterface> map);
    public Hashtable<String, StopNodeInterface> getMap();
    
}
