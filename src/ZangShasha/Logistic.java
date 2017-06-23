package ZangShasha;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Logistic {

	/** the learning rate */
	private double rate;

	/** the weight to learn */
	private static double[] weights;

	/** the number of iterations */
	private int ITERATIONS = 3000;
	
	public static  int variables=22;
	public static parser parse=null;
	public Logistic(int n) {
		this.rate = 0.15;
		weights = new double[n];
		//variables=n;
	}

	private static double sigmoid(double z) {
		return 1.0 / (1.0 + Math.exp(-z));
	}

	public void train(List<Instance> instances) {
		for (int n=0; n<ITERATIONS; n++) {
			double lik = 0.0;
			for (int i=0; i<instances.size(); i++) {
				float[] x = instances.get(i).x;
				double predicted = classify(x);
				int label = instances.get(i).label;
				for (int j=0; j<weights.length; j++) {
					weights[j] = weights[j] + rate * (label - predicted) * x[j];
				}
				// not necessary for learning
				lik += label * Math.log(classify(x)) + (1-label) * Math.log(1- classify(x));
			}
			System.out.println("iteration: " + n + " " + Arrays.toString(weights) + " mle: " + lik);
		}
		
		BufferedWriter output = null;
        try {
            File file = new File("weights.txt");
            output = new BufferedWriter(new FileWriter(file));
            for (int j=0; j<weights.length; j++) {
            	output.write(weights[j]+"\n");
            }
            
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
          if ( output != null ) {
            try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }
        }
			
		
	}

	private double classify(float[] x) {
		//initWeight("weights.txt");
		double logit = .0;
		for (int i=0; i<weights.length;i++)  {
			logit += weights[i] * x[i];
		}
		return sigmoid(logit);
	}

	private static void initWeight(String file) {
		Scanner scanner = null;
		try {
			
			scanner = new Scanner(new File(file));
			int j=0;
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				weights[j++]=Float.parseFloat(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (scanner != null)
				scanner.close();
		}
		
	}

	public static class Instance {
		public int label;
		public float[] x;

		public Instance(int label, float[] x) {
			this.label = label;
			this.x = x;
		}
	}
	/***
	 * 0-5 verb
	 * 6-8 adjective
	 * 9-11 adverb
	 * 12-17 noun
	 * 18-21 question
	 * 22-23 preposition
	 * 24 determiner 
	 */
	public static  String[] getParts(String[] arr1){
		String[] fin1 = new String[variables];
		for(int i=0;i<arr1.length;i++){
			switch(arr1[i]){
			//verb related
				case "VB": //pack
					fin1[0]=arr1[i+2];
					break;
				case "VBZ"://is(third person singular present)
					fin1[1]=arr1[i+2];
					break;
				case "VBD"://past tense
					fin1[2]=arr1[i+2];
					break;
				case "VBN"://past participate
					fin1[3]=arr1[i+2];
					break;
				case "VBG"://doing,slowely(present participate)
					fin1[4]=arr1[i+2];
					break;
				case "VBP"://are,love(non 3rd person singular present)
					fin1[5]=arr1[i+2];
					break;
			
			//adjective		
				case "JJ"://quick
					fin1[6]=arr1[i+2];
					break;
				case "JJR"://quick
					fin1[7]=arr1[i+2];
					break;
				case "JJS"://quick
					fin1[8]=arr1[i+2];
					break;
				
			
			//adverb
				case "RB"://here,as,often
					fin1[9]=arr1[i+2];
					break;
				case "RBR"://healthy
					fin1[10]=arr1[i+2];
					break;
				case "RBS"://healthy
					fin1[11]=arr1[i+2];
					break;
				
			//noun related
				case "NN"://noun
					fin1[12]=arr1[i+2];
					break;
				case "PRP"://he,she
					fin1[13]=arr1[i+2];
					break;
				case "NNP"://proper noun singular
					fin1[14]=arr1[i+2];
					break;
				case "NNPS"://proper noun plural
					fin1[15]=arr1[i+2];
					break;
				case "NNS"://noun,plural
					fin1[16]=arr1[i+2];
					break;
				case "PRP$"://my
					fin1[17]=arr1[i+2];
					
			//question related
				case "WRB"://how(whadverb)
					fin1[18]=arr1[i+2];
					break;
				case "WDT"://how(whadverb)
					fin1[19]=arr1[i+2];
					break;				
				case "WP"://what,where(wh pronoun)
					fin1[20]=arr1[i+2];
					break;
				case "WP$"://(posessive wh pronoun)
					fin1[21]=arr1[i+2];
					break;
			/*
				
			//preposion or conjuction
				case "IN": //over,with,of
					fin1[22]=arr1[i+2];
					break;
				case "CC"://coordinating conjuction
					fin1[23]=arr1[i+2];
					break;
				
			case "DT"://the
					fin1[24]=arr1[i+2];
					break;
				
				*/	
				
		
			}
		}
		return fin1;
		
	}
	public static List<Instance> readDataSet(String file,int type) throws FileNotFoundException {
		parse=new parser();
		List<Instance> dataset = new ArrayList<Instance>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(file));
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("#")) {
					continue;
				}
				String[] columns = line.split("#");
				
				String s1=columns[3];
				String s2=columns[4];
				System.out.println(s1+" "+s2);
				int label;
				//if(type==0)
				label = Integer.parseInt(columns[0]);
				//else label=-1;
				String tr1=parse.getTree(s1);
				String tr2=parse.getTree(s2);
				
				String[] arr1 = tr1.split("\\s+");
				String[] arr2 = tr2.split("\\s+");
				
				String[] fin1 = getParts(arr1);
				String[] fin2 = getParts(arr2);
				
				
				float[] data = new float[variables];
				for (int i=0; i<variables; i++) {
					if(fin1[i]==null||fin2[i]==null){
						//data[i]=0;
						if(fin1[i]==null&&fin2[i]==null){
							data[i]=0;
						}else{
							switch(i){
							//verb
							case 0:
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
								if(fin1[i]==null){
									float avg=0;
									for(int j=0;j<=5;j++){
										if(fin1[j]!=null){
											avg+=Python.similarityCalculator(fin1[j], fin2[i],"v");
										}
									}
									avg/=6.00;
									data[i]=avg;
								}else{
									float avg=0;
									for(int j=0;j<=5;j++){
										if(fin2[j]!=null){
											avg+=Python.similarityCalculator(fin1[i], fin2[j],"v");
										}
									}
									avg/=6.00;
									data[i]=avg;
								}
								break;
								//adjective	
							case 6:
							case 7:
							case 8:
							
								if(fin1[i]==null){
									float avg=0;
									for(int j=6;j<=8;j++){
										if(fin1[j]!=null){
											avg+=Python.similarityCalculator(fin1[j], fin2[i],"a");
										}
									}
									avg/=3.00;
									data[i]=avg;
								}else{
									float avg=0;
									for(int j=6;j<=8;j++){
										if(fin2[j]!=null){
											avg+=Python.similarityCalculator(fin1[i], fin2[j],"a");
										}
									}
									avg/=3.00;
									data[i]=avg;
								}
								break;
						//adverb	
							case 9:
							case 10:
							case 11:
							
								if(fin1[i]==null){
									float avg=0;
									for(int j=9;j<=11;j++){
										if(fin1[j]!=null){
											avg+=Python.similarityCalculator(fin1[j], fin2[i],"r");
										}
									}
									avg/=3.00;
									data[i]=avg;
								}else{
									float avg=0;
									for(int j=9;j<=11;j++){
										if(fin2[j]!=null){
											avg+=Python.similarityCalculator(fin1[i], fin2[j],"r");
										}
									}
									avg/=3.00;
									data[i]=avg;
								}
								break;
								
						//noun
							case 12:
							case 13:
							case 14:
							case 15:
							case 16:
							case 17:
								if(fin1[i]==null){
									float avg=0;
									for(int j=12;j<=17;j++){
										if(fin1[j]!=null){
											avg+=Python.similarityCalculator(fin1[j], fin2[i],"n");
										}
									}
									avg/=6.00;
									data[i]=avg;
								}else{
									float avg=0;
									for(int j=12;j<=17;j++){
										if(fin2[j]!=null){
											avg+=Python.similarityCalculator(fin1[i], fin2[j],"n");
										}
									}
									avg/=6.00;
									data[i]=avg;
								}
								break;
						//question	
							case 18:
							case 19:
							case 20:
							case 21:
							
								data[i]=0;
								break;
									
								
							
						}
						}
						
						/***
						 * 0-5 verb
						 * 6-8 adjective
						 * 9-11 adverb
						 * 12-17 noun
						 * 18-21 question
						 * 22-23 preposition
						 * 24 determiner 
						 */
						
						
					}else{
						switch(i){
						//verb
						case 0:
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
							data[i]=Python.similarityCalculator(fin1[i], fin2[i],"v");
							break;
					//adjective	
						case 6:
						case 7:
						case 8:
							data[i]=Python.similarityCalculator(fin1[i], fin2[i],"a");
							break;
					//adverb	
						case 9:
						case 10:
						case 11:
							data[i]=Python.similarityCalculator(fin1[i], fin2[i],"r");
							break;
							
					//noun
						case 12:
						case 13:
						case 14:
						case 15:
						case 16:
						case 17:
							data[i]=Python.similarityCalculator(fin1[i], fin2[i],"n");
							break;
					//question	
						case 18:
						case 19:
						case 20:
						case 21:
							if(fin1[i].equals(fin2[i]))
							data[i]=1;
							else data[i]=0;
							break;
					
				}
				}
				
				Instance instance = new Instance(label, data);
				dataset.add(instance);
			}
		}
		}catch (Exception e){
			
		}
		finally {
		
			if (scanner != null)
				scanner.close();
		}
		return dataset;
	}


	public static void main(String... args) throws FileNotFoundException {
		Logistic logistic = new Logistic(22);
		//List<Instance> instances = readDataSet("msr-train-mini.txt",0);
		
		//logistic.train(instances);
		
		initWeight("weights.txt");
		List<Instance> instances2 = readDataSet("msr-test-mini.txt",1);
		System.out.println(instances2.size());
		double thresh=0.1,max=0.1,mac=0.0;
		double alpha=0.0001;
		int lco=0,lwr=0;
		for(int j=0;j<=9000;j++){
			int co=0;
			int wr=0;
			for (int i=0; i<instances2.size(); i+=22) {
				float[] x = instances2.get(i).x;
				int label=instances2.get(i).label;
				double pro= logistic.classify(x);
				int pL=pro>=thresh?1:0;
				if(label==pL)co++;
				else wr++;
				
				
				
			}
			double ac=(double)((double)co/((double)co+(double)wr));
			if(co>lco){
				max=thresh;
				mac=ac;
				lco=co;
				lwr=wr;
			}
			
			thresh+=alpha;
			System.out.println("threshhold = "+thresh+" , accuracy = "+ac);
		}
		System.out.println("max is at "+ max+" , which is "+mac);
		
		
	}

}
