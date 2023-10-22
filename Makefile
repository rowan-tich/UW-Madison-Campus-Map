# Running the application
run: displayFrontend.class
	java --module-path javafx-sdk-20.0.1/lib/ --add-modules javafx.controls displayFrontend


# Running All Tests
runTests: runAlgorithmEngineerTests runDataWranglerTests runFrontendDeveloperTests runBackendDeveloperTests


# Running AE Tests
runAlgorithmEngineerTests: AlgorithmEngineerTests.class MapBackend.class
	java -jar junit5.jar -cp . --select-class=AlgorithmEngineerTests

AlgorithmEngineerTests.class: GraphADT.class BaseGraph.class DijkstraGraph.class AlgorithmEngineerTests.java
	javac -cp .:junit5.jar AlgorithmEngineerTests.java

GraphADT.class: GraphADT.java
	javac GraphADT.java

BaseGraph.class: BaseGraph.java
	javac BaseGraph.java

DijkstraGraph.class: DijkstraGraph.java
	javac DijkstraGraph.java

# Running DW Tests
runDataWranglerTests: DataWranglerTESTS.class
	java -jar junit5.jar -cp . --select-class=DataWranglerTESTS

DataWranglerTESTS.class: EdgePathDW.class StopNodeDW.class GraphReaderDW.class DataWranglerTESTS.java
	javac -cp .:junit5.jar DataWranglerTESTS.java

EdgePathDW.class: EdgePathDW.java
	javac EdgePathDW.java

StopNodeDW.class: StopNodeDW.java
	javac StopNodeDW.java

GraphReaderDW.class: GraphReaderDW.java
	javac GraphReaderDW.java


# Running FD Tests
runFrontendDeveloperTests: FrontendDeveloperTests.class
	java --module-path javafx-sdk-20.0.1/lib/ --add-modules javafx.controls --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar junit5.jar -cp .:JavaFXTester.jar -c FrontendDeveloperTests

FrontendDeveloperTests.class: FrontendDeveloperTests.java displayFrontend.class
	javac --module-path javafx-sdk-20.0.1/lib/ --add-modules javafx.controls -cp .:junit5.jar:JavaFXTester.jar FrontendDeveloperTests.java

displayFrontend.class: displayFrontend.java
	javac --module-path javafx-sdk-20.0.1/lib/ --add-modules javafx.controls displayFrontend.java


# Running BD Tests
runBackendDeveloperTests: BackendDeveloperTests.class
	java -jar junit5.jar -cp . --select-class=BackendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java EdgePathDW.class GraphADT.class GraphReaderDW.class MapBackend.class StopNodeDW.class DijkstraGraph.class MapImportDW.class
	javac -cp .:junit5.jar BackendDeveloperTests.java

EdgePathDW.class: EdgePathInterface.class EdgePathDW.java
	javac EdgePathDW.java

EdgePathInterface.class: EdgePathInterface.java
	javac EdgePathInterface.java

GraphADT.class: GraphADT.java
	javac GraphADT.java

GraphReaderDW.class: GraphReaderInterface.class GraphReaderDW.java
	javac GraphReaderDW.java

GraphReaderInterface.class: GraphReaderInterface.java
	javac GraphReaderInterface.java

DijkstraGraph.class: MapAlgorithmInterface.class DijkstraGraph.java
	javac DijkstraGraph.java

MapAlgorithmInterface.class: MapAlgorithmInterface.java
	javac MapAlgorithmInterface.java

MapBackend.class: MapBackendInterface.class MapBackend.java
	javac MapBackend.java

MapBackendInterface.class: MapBackendInterface.java
	javac MapBackendInterface.java

StopNodeDW.class: StopNodeInterface.class StopNodeDW.java
	javac StopNodeDW.java

StopNodeInterface.class: StopNodeInterface.java
	javac StopNodeInterface.java

MapImportDW.class: MapImportInterface.class MapImportDW.java
	javac MapImportDW.java

MapImportInterface.class: MapImportInterface.java
	javac MapImportInterface.java

clean:
	rm *.class
