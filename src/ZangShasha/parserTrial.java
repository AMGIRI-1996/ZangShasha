package ZangShasha;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.parser.chunking.Parser;


public class parserTrial {
	public static void main(String args[]){
		InputStream modelIn=null;
		
		try {
			modelIn = new FileInputStream("en-parser-chunking.bin");
		  ParserModel model = new ParserModel(modelIn);
		  Parser parser = (Parser) ParserFactory.create(model);
		  String sentence = "How are you ?";
		  String sentence2 = "He is a bad boy";
		  sentence=sentence.toLowerCase();
		  sentence2=sentence2.toLowerCase();
		  Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
		  Parse topParses2[] = ParserTool.parseLine(sentence2, parser, 1);

		 // System.out.println(topParses.toString());
		  String str1=null,str2=null;
		  for (Parse p : topParses){
			  str1= p.show2();
		  		
		  }
		  for (Parse p : topParses2){
			  str2= p.show2();
		  		
		  }
		  Tree tree1 = new Tree(str1);
		  Tree tree2 = new Tree(str2);
		  
		//  int distance1 = Tree.ZhangShasha(tree1, tree2);
		System.out.println(str1+"\n"+str2+"\n"+"Distance is " );

		  		
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
		
		
		
	}
}
