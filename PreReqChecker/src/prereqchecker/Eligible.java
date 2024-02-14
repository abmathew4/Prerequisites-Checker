package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
        // WRITE YOUR CODE HERE
        HashMap<String, ArrayList<String>> adjList = readAdjacencyList(args[0]);
        HashSet<String> completedCourses = readCompletedCourses(args[1], adjList);
        print(args[2], adjList, completedCourses);
    }
    private static HashMap<String, ArrayList<String>>readAdjacencyList(String fileName) {
        StdIn.setFile(fileName);
        HashMap<String,ArrayList<String>> adjList = new HashMap<>();
        int courses = StdIn.readInt();
        String[] courseArray = new String[courses];
        for (int i =0; i < courses;i++) {
            String ID = StdIn.readString();
            ArrayList<String> prerequisites = new ArrayList<>();
            adjList.put(ID, prerequisites);
            courseArray[i]= ID;
        }
        int prereq =StdIn.readInt();
        for (int i =0; i < prereq; i++) {
            String ID =StdIn.readString();
            String prereqString= StdIn.readString();
            adjList.get(ID).add(prereqString);
            }
        return adjList;
    }
    private static HashSet<String> readCompletedCourses(String fileName, HashMap<String, ArrayList<String>> adjList) {
        StdIn.setFile(fileName);
        int numCourses= StdIn.readInt();
        HashSet<String> completedCourses = new HashSet<>();

        for (int i = 0; i < numCourses; i++) {
            String completed= StdIn.readString();
            findEligible2(completed, completedCourses, adjList, new HashMap<>());
        }

        return completedCourses;
    }
    private static void print(String fileName, HashMap<String, ArrayList<String>> adjList, HashSet<String> completedCourses) {
        StdOut.setFile(fileName);
        Queue<String> canTake = new LinkedList<>();
        for (String course : adjList.keySet()) {
            findEligible(adjList, new HashMap<>(), course, canTake, completedCourses);
        }
        Set<String> uniqueCourses = new HashSet<>(canTake);
        for (String course : uniqueCourses) {
            StdOut.println(course);
        }
    }
    private static void findEligible(HashMap<String, ArrayList<String>> adjList, HashMap<String, Boolean> visited, String course, Queue<String> canTake, HashSet<String> completedCourses) {
        if (visited.getOrDefault(course, false)) {
            return;
        }
        boolean canBeTaken = true;
        for (String preReq : adjList.get(course)) {
            findEligible(adjList, visited, preReq, canTake, completedCourses);
            if (!completedCourses.contains(preReq)) {
                canBeTaken = false;
            }
        }
        if (canBeTaken && !completedCourses.contains(course)) {
            canTake.add(course);
        }
        visited.put(course, true);
    }
    private static void findEligible2(String course, HashSet<String> allprereqs, HashMap<String, ArrayList<String>> adjList, HashMap<String, Boolean> visited) {
        if (visited.getOrDefault(course, false)) {
            return;
        }
        allprereqs.add(course);
        for (String preReq : adjList.get(course)) {
            findEligible2(preReq, allprereqs, adjList, visited);
        }
        visited.put(course, true);
    }
}
