// Name: Min Lu
// CS 455 PA4
// Fall 2016

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GentText {
	/**Accept user inputs, do validation,on them and then 
	 * open files, read and write the generated words
	 * arguments format: [-d] prefixLength numWords sourceFile outFile 
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args){
		String[] inputs=new String[4];
		boolean debug=false;
		if(args.length==4){	 
			inputs=args;
		}else if(args.length==5){
			if(!args[0].equals("-d")){
				System.out.println("ERROR: Illegal parameters!");
				return;
			}
			for(int i=0;i<inputs.length;i++){
				inputs[i]=args[i+1];
			}
			debug=true;
		}else{
			System.out.println("ERROR: Illegal parameters!");
			return;
		}
		if(!validate(inputs)) return;
		
		int prefixLength=Integer.parseInt(inputs[0]);
		int numWord=Integer.parseInt(inputs[1]);
		String sourcename=inputs[2];
		String outname=inputs[3];
		
		try{
			File source=new File(sourcename);  //open source file and read
			Scanner in=new Scanner(source);
			if(!countNum(in,prefixLength)) return;  //compare the number of source file with prefixLength
			RandomTextGenerator rtg=new RandomTextGenerator(prefixLength,in,debug);
			String[] res=rtg.generateText(numWord);
			
			File output=new File(outname);     //open output file and write
			output.createNewFile();
			BufferedWriter out=new BufferedWriter(new FileWriter(output));
			for(int i=0;i<res.length-1;i++){
				out.write(res[i]+" ");
			}
			out.write(res[res.length-1]);
			out.flush();  //push the buffer
			out.close();  //close writer
			
		}catch(FileNotFoundException e){
			System.out.println("ERRPR: the source file doesn't exist");
		} catch (IOException e) {
			System.out.println("ERRPR: the output file can't open correctly");
		}
	}
	/**
	 * check if the prefixLength and numWord meet requirements
	 * @param inputs a string array contains all inputs
	 * @return true if meet all requirements
	 */
	public static boolean validate(String[] inputs){
		if(Integer.parseInt(inputs[0])<1){
			System.out.println("ERRROR: the prefixLength should be more than 1");
			return false;
		}
		for(int i=inputs[0].length()-1;i>=0;i--){
			if(!Character.isDigit(inputs[0].charAt(i))) {
				System.out.println("ERRROR: the prefixLength should be Integer");
				return false;
			}
		}
		if(Integer.parseInt(inputs[1])<0){
			System.out.println("ERRROR: the numWord should be more than 0");
			return false;
		}
		for(int i=inputs[1].length()-1;i>=0;i--){
			if(!Character.isDigit(inputs[1].charAt(i))) {
				System.out.println("ERRROR: the numWord should be Integer");
				return false;
			}
		}			
		return true;
	}
	/**
	 * check if the prefixLength is less than the total word number of source file
	 * @param in scanner of the source file
	 * @param prefixLength  
	 * @return true if prefixLength < total word number
	 */
	public static boolean countNum(Scanner in, int prefixLength){
		int num=0;
		while(in.hasNext()){
			num++;
			if(num>prefixLength){
				break;
			}
		}
		if(in.hasNext()) return true;
		System.out.println("ERROR: the length of prefix should be less than the length of source file");
		return false;
	}
}
