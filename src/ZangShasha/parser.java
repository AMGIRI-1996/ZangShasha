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


public class parser {
	InputStream modelIn=null;
	ParserModel model=null;
	Parser parser =null;
	parser(){
		
		try{
			modelIn = new FileInputStream("en-parser-chunking.bin");
			ParserModel model = new ParserModel(modelIn);
			Parser parser = (Parser) ParserFactory.create(model);
		}catch (IOException e) {
			  e.printStackTrace();
		}
		
	}
	public String getTree(String sentence){
	  sentence=sentence.toLowerCase();
	  try{
			modelIn = new FileInputStream("en-parser-chunking.bin");
			ParserModel model = new ParserModel(modelIn);
			Parser parser = (Parser) ParserFactory.create(model);
			 Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
			 
			  String str1=null;
			  for (Parse p : topParses){
				  str1= p.show2();	
			  }
			  return str1;
		}catch (IOException e) {
			  e.printStackTrace();
		}finally{
			if (modelIn != null) {
			    try {
			      modelIn.close();
			    }
			    catch (IOException e) {
			    }
			  }
		}
	 return "-1";
	}
	public static void main(String args[]){
	  /*String sentence = "he is eating slowly";
	  String sentence2 = "ram is eating";
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
	  
	  int distance1 = Tree.ZhangShasha(tree1, tree2);
	  System.out.println(str1+"\n"+str2+"\n"+"Distance is " + distance1);*/
	
	}
	void end(){
		if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
	}
}
