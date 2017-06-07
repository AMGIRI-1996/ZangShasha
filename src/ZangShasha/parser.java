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
	public static void main(String args[]){
		InputStream modelIn=null;
		
		try {
			modelIn = new FileInputStream("en-parser-chunking.bin");
		  ParserModel model = new ParserModel(modelIn);
		  Parser parser = (Parser) ParserFactory.create(model);
		  String sentence = "I am hero";
		  Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
		  System.out.println(topParses.toString());
		  for (Parse p : topParses){
		       p.show();
		  		
		  }
		  		
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
