public interface EdgePathInterface {

    // public EdgePathInterface(double weight, boolean isWalkingPath, StopNodeInterface pred, StopNodeInterface succ);
    
        public double getWeight();

        public boolean isWalkingPath();

        public StopNodeInterface getPredecessor();
        
        public StopNodeInterface getSuccessor();
       
    }
    