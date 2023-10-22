import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class StopNodeDW implements StopNodeInterface {
	protected String name;
	private List<EdgePathInterface> edgesGoingInto;
	private List<EdgePathInterface> edgesComingFrom;

	public StopNodeDW(String nodeLocation) {
		this.edgesGoingInto = new ArrayList<>();
		this.edgesComingFrom = new ArrayList<>();
		name = nodeLocation;
	}

	@Override
	public List<EdgePathInterface> getEdgesGoingInto() {
		return this.edgesGoingInto;
	}

	@Override
	public List<EdgePathInterface> getEdgesComingFrom() {
		return this.edgesComingFrom;
	}

	@Override
	public void addEdgeGoingInto(EdgePathInterface edge) {
		this.edgesGoingInto.add(edge);
	}

	@Override
	public void addEdgeComingFrom(EdgePathInterface edge) {
		this.edgesComingFrom.add(edge);
	}

	@Override
	public void removeEdgeGoingInto(EdgePathInterface edge) throws NoSuchElementException {
		if (!this.edgesGoingInto.remove(edge)) {
			throw new NoSuchElementException("No edge going into node");
		}
	}

	@Override
	public void removeEdgeComingFrom(EdgePathInterface edge) throws NoSuchElementException {
		if (!this.edgesComingFrom.remove(edge)) {
			throw new NoSuchElementException("No edge coming from node");
		}
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}

}
