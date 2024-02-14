package prereqchecker;
import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prereqStrings (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

	// WRITE YOUR CODE HERE
    StdIn.setFile(args[0]);
    int courses =StdIn.readInt();
    Map<String,List<String>> adjlist =new HashMap<>();
    for (int i =0; i< courses; i++) {
        String ID =StdIn.readString();
        adjlist.put(ID, new ArrayList<>());
    }
    int prereq = StdIn.readInt();
    for (int i =0; i < prereq; i++) {
        String ID =StdIn.readString();
        String prereqString= StdIn.readString();
        adjlist.get(ID).add(prereqString);
        }
        StdOut.setFile(args[1]);
        for (Map.Entry<String,List<String>>entry : adjlist.entrySet()) {
            String ID= entry.getKey();
            List<String> prereqStrings =entry.getValue();
            StdOut.print(ID+ " ");
            for (String prereqString :prereqStrings) {
                StdOut.print(prereqString + " ");
            }
            StdOut.println();
        }
    }
}
