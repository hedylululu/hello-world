// Name: Min Lu
// USC loginid: minlu
// CS 455 PA4
// Fall 2016
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Prefix class keeps a map of prefixes and their successors
 * and it is immutable.
 * @author minlu
 *
 */

public class Prefix {
	
	private Map<String,ArrayList<String>> map;
	/* Representative Invariant
     * --key is prefixLength number of words, separated with space
     * --key cannot be duplicated
     * --value is a array list of string, which consists of all the successors of specific prefix
     * --e.g. if "blue" shows up three times as successor of "by the", there will be three "blue" in the array list
     * --if the prefix is on the end of file, the corresponding value will be an empty array list
     * --key cannot be duplicated.
     */
	private boolean debug;  //true if debug mode is on

	/**
	 * Construct a prefix with a hash map
	 * @param list  hash map with prefix as key and successors as value
	 */
	public Prefix(Map<String,ArrayList<String>> list, boolean d){
		this.map=list;
		this.debug=d;
	}
	/**
	 * select a prefix randomly from map
	 * @param r random object
	 * @return a prefix in string format 
	 */
	public String getPrefix(Random r){
		ArrayList<Map.Entry<String, ArrayList<String>>> templist=
				new ArrayList<Map.Entry<String,ArrayList<String>>>(map.entrySet());
		return templist.get(r.nextInt(map.size())).getKey();
	}
	/**
	 * Get a random successor
	 * @param r  Random object
	 * @return anyone successor of the prefix
	 */
	public String getRandomSucessor(Random r, String prefix){
		ArrayList<String> successors=showAllSuccessors(prefix);
		if(successors.size()==0) return null;		
		String res=successors.get(r.nextInt(successors.size()));
		if(debug) System.out.println("DEBUG: word generated: "+res);
		return res;
	}

	/**
	 * show all successors of specific prefix in string format
	 * @param p  prefix in string format
	 * @return successors
	 */
	private ArrayList<String> showAllSuccessors(String p){
		ArrayList<String> successors=map.get(p);
		if(debug){
			if(successors.size()==0){
				System.out.println("DEBUG: successors: <END OF FILE>");
			}else{
				String res="";
				for(int i=0;i<successors.size()-1;i++){
					res=res+successors.get(i)+" ";
				}
				res=res+successors.get(successors.size()-1);
				System.out.println("DEBUG: successors: "+res);
			}
		}	
		return successors;
	}
}
