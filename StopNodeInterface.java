import java.util.List;
import java.util.NoSuchElementException;

public interface StopNodeInterface {
    // public StopNodeInterface(String nodeLocation);
       public List<EdgePathInterface> getEdgesGoingInto();

       public List<EdgePathInterface> getEdgesComingFrom();

       public void addEdgeGoingInto(EdgePathInterface edge);

       public void addEdgeComingFrom(EdgePathInterface edge);
       
       public void removeEdgeGoingInto(EdgePathInterface edge) throws NoSuchElementException;
       
       public void removeEdgeComingFrom(EdgePathInterface edge) throws NoSuchElementException;
    }
    