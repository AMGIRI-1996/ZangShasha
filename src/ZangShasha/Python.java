package ZangShasha;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

public class Python {
	
	public static float similarityCalculator(String w1,String w2,String w3){
		float x = 0f;
		try {
			String word_1 = w1;
			String word_2 = w2;
			Process process = Runtime.getRuntime().exec("python3 ython.py "+word_1+" "+word_2+" "+w3);
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = "";
			
			while((line=inputReader.readLine())!=null){
				x = Float.parseFloat(line);
			}
			while((line=errorReader.readLine())!=null){
				System.out.println(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		return x;
	}

	public static void main(String[] args){
		float x = similarityCalculator("bad", "good","n");
		System.out.println(x);
	}

}
