
public class EdgePathDW implements EdgePathInterface {
	private double weight;
	private boolean isWalkingPath;
	private StopNodeInterface predecessor;
	private StopNodeInterface successor;

	public EdgePathDW(double weight, boolean isWalkingPath, StopNodeInterface pred, StopNodeInterface succ) {
		this.weight = weight;
		this.isWalkingPath = isWalkingPath;
		this.predecessor = pred;
		this.successor = succ;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public boolean isWalkingPath() {
		return isWalkingPath;
	}

	@Override
	public StopNodeInterface getPredecessor() {
		return predecessor;
	}

	@Override
	public StopNodeInterface getSuccessor() {
		return successor;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setWalkingPath(boolean isWalkingPath) {
		this.isWalkingPath = isWalkingPath;
	}

}
