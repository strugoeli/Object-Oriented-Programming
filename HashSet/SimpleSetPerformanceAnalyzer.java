import java.util.TreeSet;
import java.util.LinkedList;
import java.util.HashSet;

/**
 * This class analyses the performance the data structure:
 * OpenHashSet,ClosedHashSet,TreeSet(Java's),LinkedList(Java's) and HashSet(Java's).
 */
public class SimpleSetPerformanceAnalyzer {

	/*Constants */

	private static final String DATA2_LOCATION = "src\\data2.txt";
	private static final String DATA1_LOCATION = "src\\data1.txt";
	private static final long NANO_TO_MILL = 1000000;
	private static final int WARM_UP_ITERATION = 20000;
	private static final int CONTAIN_ITERATION = 10000;
	private static final String ADD_TEXT_DATA = "Adding text's run time from %s: \n ";
	private static final String BEGIN_CHECK = "Begin to measure %s performance \n";
	private static final String CONTAIN_RUN_MSG = "Running time to check if %s contains %S is: \n";
	private static final String[] STRUCTURE_NAMES = {"OpenHashSet", "ClosedHashSet", "TreeSet", "LinkedList",
			"HashSet"};


	/*
	Array of the tested kind of simple set
	 */
	private SimpleSet[] dataStructures;


	/**
	 * Constructor for SimpleSet array
	 */
	public SimpleSetPerformanceAnalyzer() {
		OpenHashSet openHashSet = new OpenHashSet();
		ClosedHashSet closeHashSet = new ClosedHashSet();
		CollectionFacadeSet treeSet = new CollectionFacadeSet(new TreeSet<>());
		CollectionFacadeSet linkedList = new CollectionFacadeSet(new LinkedList<>());
		CollectionFacadeSet hashSet = new CollectionFacadeSet(new HashSet<>());
		dataStructures = new SimpleSet[]{openHashSet, closeHashSet, treeSet, linkedList, hashSet};

	}

	/*
	This method measures the running time of adding the content of a given file to the data structures:
	"OpenHashSet", "ClosedHashSet", "TreeSet", "LinkedList" and "HashSet"
	 */
	private void measureAdding(String filePath) {
		String[] data = getFromFile(filePath);
		for (int i = 0; i < dataStructures.length; i++) {
			long timeBefore = System.nanoTime();
			for (String aData : data)
				this.dataStructures[i].add(aData);
			long difference = (System.nanoTime() - timeBefore) / NANO_TO_MILL;
			System.out.println(STRUCTURE_NAMES[i] + ":" + difference);
		}
	}

	/*
	This method measures the running time of adding a data to the data structures:
	"OpenHashSet", "ClosedHashSet", "TreeSet", "LinkedList" and "HashSet"
	 */
	private void measureContain(String data) {

		for (int i = 0; i < dataStructures.length; i++) {
			warmUp(data, dataStructures[i]);
			long timeBefore = System.nanoTime();
			for (int j = 0; j < CONTAIN_ITERATION; j++)
				dataStructures[i].contains(data);
			long difference = (System.nanoTime() - timeBefore) / CONTAIN_ITERATION;
			System.out.println(STRUCTURE_NAMES[i] + ":" + difference);
		}
	}

	/**
	 * warm up for execute the target operation enough times so that the JVM will
	 * have had time to run and replace the bytecode with compiled machine-code.
	 *
	 * @param str        -string we want to search in array
	 * @param simpleSet- the SimpleSet we want to check
	 */
	private void warmUp(String str, SimpleSet simpleSet) {
		for (int i = 0; i < WARM_UP_ITERATION; i++) {
			simpleSet.contains(str);
		}
	}

	/*
	This method is given a file location and returns it's written content
	 */
	private String[] getFromFile(String filePlace) {
		return Ex3Utils.file2array(filePlace);
	}


	public static void main(String[] args) {
		SimpleSetPerformanceAnalyzer data1 = new SimpleSetPerformanceAnalyzer();
		SimpleSetPerformanceAnalyzer data2 = new SimpleSetPerformanceAnalyzer();

		// Measuring adding data run time :
		System.out.println(String.format(BEGIN_CHECK, "add"));
		System.out.println(String.format(ADD_TEXT_DATA, "data1"));
		data1.measureAdding(DATA1_LOCATION);
		System.out.println(String.format(ADD_TEXT_DATA, "data2"));
		data2.measureAdding(DATA2_LOCATION);

		//Measuring contains run time
		System.out.println(String.format(BEGIN_CHECK, "contains"));
		System.out.println(String.format(CONTAIN_RUN_MSG, "hi", "data1"));
		data1.measureContain("hi");
		System.out.println(String.format(CONTAIN_RUN_MSG, "hi", "data2"));
		data2.measureContain("hi");
		System.out.println(String.format(CONTAIN_RUN_MSG, "-13170890158", "data1"));
		data1.measureContain("-13170890158");
		System.out.println(String.format(CONTAIN_RUN_MSG, "23", "data2"));
		data2.measureContain("23");


	}


}


