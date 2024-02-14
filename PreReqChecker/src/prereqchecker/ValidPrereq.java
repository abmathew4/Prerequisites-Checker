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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
	    // WRITE YOUR CODE HERE
        HashMap<String, ArrayList<String>> adjlist = new HashMap<String, ArrayList<String>>();
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        readAdjacencyList(args[0], adjlist, visited);
        String course1 = read(args[1]);
        output(args[2], course1, args[1], adjlist, visited);
    }
    private static void readAdjacencyList(String inputFile, HashMap<String, ArrayList<String>> adjlist, HashMap<String, Boolean> visited) {
        StdIn.setFile(inputFile);
        int courses = StdIn.readInt();
        String[] allCourses = new String[courses];
        for (int i = 0; i < courses; i++) {
            String ID = StdIn.readString();
            ArrayList<String> list = new ArrayList<String>();
            adjlist.put(ID, list);
            visited.put(ID, false);
            allCourses[i] = ID;
        }
        int prereq = StdIn.readInt();
        for (int i = 0; i < prereq; i++) {
            String ID = StdIn.readString();
            String prereqstring = StdIn.readString();
            adjlist.get(ID).add(prereqstring);
        }
    }
    private static String read(String inputFile) {
        StdIn.setFile(inputFile);
        return StdIn.readString();
    }
    private static void output(String outputFile, String course1, String inputFile, HashMap<String, ArrayList<String>> adjlist, HashMap<String, Boolean> visited) {
        StdOut.setFile(outputFile);
        HashSet<String> allprereqs = new HashSet<String>();
        findValidprereq(course1, allprereqs, adjlist, visited);
        if (allprereqs.contains(course1)) {
            StdOut.println("NO");
        } else {
            StdOut.println("YES");
        }
    }
    private static void findValidprereq(String course, HashSet<String> allprereqs, HashMap<String, ArrayList<String>> adjlist, HashMap<String, Boolean> visited){
        if (adjlist.get(course).size()==0) return;
        for (String prereq : adjlist.get(course)){
            if (visited.get(prereq)==false){
                allprereqs.add(prereq);
                findValidprereq(prereq, allprereqs, adjlist, visited);
            }
        }
        visited.put(course, true);
    }
}
