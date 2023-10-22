import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MapImportDW implements MapImportInterface {
	private List<StopNodeInterface> nodes;
	private List<EdgePathInterface> edges;
	private Hashtable<String, StopNodeInterface> map;

	public MapImportDW() {
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
	}

	@Override
	public void addNode(StopNodeInterface location) {
		nodes.add(location);
	}

	@Override
	public void addEdge(EdgePathInterface edge) {
		edges.add(edge);
	}

	@Override
	public List<StopNodeInterface> getNodes() {
		return nodes;
	}

	@Override
	public List<EdgePathInterface> getEdges() {
		return edges;
	}

	@Override
	public void setMap(Hashtable<String, StopNodeInterface> map) {
		this.map = map;

	}

	@Override
	public Hashtable<String, StopNodeInterface> getMap() {
		return map;

	}

}
