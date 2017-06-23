package ZangShasha;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ZangShasha.Logistic.Instance;

public class IOForML {
	public static List<Instance> readDataSet(String file) throws FileNotFoundException {
		List<Instance> dataset = new ArrayList<Instance>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(file));
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("#")) {
					continue;
				}
				String[] columns = line.split("\\s+");

				// skip first column and last column is the label
				int i = 1;
				float[] data = new float[columns.length-2];
				for (i=1; i<columns.length-1; i++) {
					data[i-1] = Float.parseFloat(columns[i]);
				}
				int label = Integer.parseInt(columns[i]);
				Instance instance = new Instance(label, data);
				dataset.add(instance);
			}
		} finally {
			if (scanner != null)
				scanner.close();
		}
		return dataset;
	}

}
