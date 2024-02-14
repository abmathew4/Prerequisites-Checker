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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }
        // WRITE YOUR CODE HERE
        HashMap<String, ArrayList<String>> adjlist = new HashMap<String, ArrayList<String>>();
        HashMap<String, Boolean> completedcourses = new HashMap<String, Boolean>();
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        populate(args[0], adjlist, completedcourses, visited);
        NeedtoTake1(args[1], args[2], adjlist, completedcourses, visited);
    }
    private static void populate(String fileName, HashMap<String, ArrayList<String>> adjlist,HashMap<String, Boolean> completedcourses, HashMap<String, Boolean> visited) {
        StdIn.setFile(fileName);
        int courses =StdIn.readInt();
        for (int i = 0; i< courses; i++) {
            boolean taken =false;
            String ID= StdIn.readString();
            ArrayList<String> list = new ArrayList<String>();
            adjlist.put(ID, list);
            completedcourses.put(ID, taken);
            visited.put(ID, taken);
        }
        int prereq = StdIn.readInt();
        for (int i = 0; i < prereq; i++) {
            String course = StdIn.readString();
            String prereqString = StdIn.readString();
            adjlist.get(course).add(prereqString);
        }
    }
    private static void NeedtoTake1(String inputFile, String outputFile, HashMap<String, ArrayList<String>> adjlist, HashMap<String, Boolean> completedcourses, HashMap<String, Boolean> visited) {
        StdIn.setFile(inputFile);
        StdOut.setFile(outputFile);
        String needtotake = StdIn.readString();
        int taken = StdIn.readInt();
        HashSet<String> completedCourses = new HashSet<String>();
        for (int i = 0; i < taken; i++) {
            String completed = StdIn.readString();
            for (String prereqs : adjlist.get(completed)) {
                findNeedtotake2(prereqs, completedCourses, adjlist, completedcourses);
            }
            completedCourses.add(completed);
        }
        HashSet<String> prereqs = new HashSet<>();
        findNeedtotake(needtotake, prereqs, adjlist, visited);
        Set<String> allprereqs = prereqs;
        Set<String> completed = completedCourses;
        for (String courses : allprereqs) {
            if (!completed.contains(courses)) {
                StdOut.println(courses);
            }
        }
    }
    private static void findNeedtotake(String course, HashSet<String> allprereqs, HashMap<String, ArrayList<String>> adjlist, HashMap<String, Boolean> completedcourses){
        if (completedcourses.get(course)==true) return;
        for (String prereq:adjlist.get(course)){
            if (completedcourses.get(prereq)==false){
                allprereqs.add(prereq);
                findNeedtotake(prereq, allprereqs, adjlist, completedcourses);
            }
            completedcourses.put(course, true);
        }
    }
    private static void findNeedtotake2(String course, HashSet<String> allprereqs, HashMap<String, ArrayList<String>> adjlist, HashMap<String, Boolean> visted){
        if (visted.get(course)==true) return;
        allprereqs.add(course);
        for (String prereqs :adjlist.get(course)){
            if (visted.get(prereqs)==false){
                allprereqs.add(prereqs);
                findNeedtotake2(prereqs, allprereqs, adjlist, visted);
            }
        }
        visted.put(course, true);
    }
}