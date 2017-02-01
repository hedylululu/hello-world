// Name: Min Lu
// USC loginid: minlu
// CS 455 PA4
// Fall 2016


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * RandomTextGenerator is a generator which takes a file scanner as input,
 * and according to the frequency of words on this file to generate required-length
 * text. It has debug mode, under which a series of information will be output on 
 * console.
 * 
 * @author minlu
 *
 */
public class RandomTextGenerator {
	
	Random r;
	Prefix p;
	boolean debug;
	
	public static int RANDOM_SEED=100;
	
	/**
	 * Construct a random text generator
	 * @param prefixLength  the length of the prefix
	 * @param in  file scanner
	 */
	public RandomTextGenerator(int prefixLength, Scanner in,boolean debug){
		this.debug=debug;
		p=new Prefix(fileProcess(prefixLength, in), debug);
		if(debug) {
			r=new Random(RANDOM_SEED);
		}else{
			r=new Random();
		}
		
	}
	/**
	 * Generate a Hash Map of prefix
	 * @param prefixLength
	 * @param in scanner
	 * @return  the hash map of all prefixes and their successors
	 */
	private Map<String,ArrayList<String>> fileProcess(int prefixLength, Scanner in){
		Map<String,ArrayList<String>> prefixMap=new HashMap<String,ArrayList<String>>();
		String[] list=new String[prefixLength];  //store the current prefix
		String prefix="";
		for(int i=0;i<prefixLength;i++){
			list[i]=in.next();
			prefix=prefix+list[i]+" ";
		}

		prefixMap.put(prefix,new ArrayList<String>());
		while(in.hasNext()){
			String currWord=in.next();  //get the current word			
			prefixMap.get(prefix).add(currWord);
			String[] newlist=new String[prefixLength];
			String newprefix="";
			for(int i=0;i<newlist.length-1;i++){
				newlist[i]=list[i+1];
				newprefix=newprefix+newlist[i]+" ";
			}			
			newlist[newlist.length-1]=currWord;
			newprefix=newprefix+currWord;
			if(!prefixMap.containsKey(newprefix)){
				prefixMap.put(newprefix, new ArrayList<String>());				
			}
			list=newlist;
			prefix=newprefix;
		}
		return prefixMap;
	}
	/**
	 * Generate text with specific number of words
	 * @param numWord the number of generated words
	 * @return a String array of generated text
	 */
	public String[] generateText(int numWord){
		String[] text=new String[numWord];//store the final result
		String prefix=generateRandomPrefix();
		
		for(int i=0;i<numWord;i++){
			if(debug) System.out.println("DEBUG: prefix: "+ prefix);
			String currWord=generateRandomSuccessor(prefix);
			while(currWord==null){
				prefix=generateRandomPrefix();
				currWord=generateRandomSuccessor(prefix);
			}
			text[i]=currWord; //write the successor to result
			String[] pre=prefix.split(" ");
			prefix="";
			for(int j=0;j<pre.length-1;j++){
				prefix=prefix+pre[j+1]+" ";
			}
			prefix=prefix+currWord;				
		}
		return text;
	}
	
	
	/**
	 * Generate a prefix randomly
	 * @return a prefix
	 */
	private String generateRandomPrefix(){
		String res=p.getPrefix(r);
		if(debug) System.out.println("DEBUG: chose a new initial prefix: "+ res);
		return res;
	}

	/**generate a successor from a specific prefix's list randomly
	 * @param p prefix
	 * @return anyone successor
	 */
	private String generateRandomSuccessor(String prefix){
		return p.getRandomSucessor(r,prefix);
	}
	
}
