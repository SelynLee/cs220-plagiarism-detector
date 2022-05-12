package plagdetect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PlagiarismDetector implements IPlagiarismDetector {
	
	public int n;
	Map<String, HashSet<String>> fileNgram = new HashMap<String, HashSet<String>>();
	Map<String, Map<String, Integer>> results = new HashMap<String, Map<String, Integer>>();
	
	public PlagiarismDetector(int x) {
		// TODO implement this method
		this.n = x;
	}
	
	@Override
	public int getN() {
		// TODO Auto-generated method stub
		return n;
	}

	@Override
	public Collection<String> getFilenames() {
		// TODO Auto-generated method stub
		
		return fileNgram.keySet();
	}

	@Override
	public Collection<String> getNgramsInFile(String filename) {
		// TODO Auto-generated method stub
		
		return fileNgram.get(filename);
	}

	@Override
	public int getNumNgramsInFile(String filename) {
		// TODO Auto-generated method stub
		return fileNgram.get(filename).size();
	}

	@Override
	public Map<String, Map<String, Integer>> getResults() {
		// TODO Auto-generated method stub

		return results;
	}

	@Override
	public void readFile(File file) throws IOException {
		// TODO Auto-generated method stub
		// most of your work can happen in this method
		Scanner sc = new Scanner(file);
		
		HashSet<String> validLine= new HashSet<String>();
		
		int lineNumber = 1;
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] doc = line.split(" ");
			System.out.println(line);
			lineNumber++;
			for(int i = 0; i < doc.length - n + 1; i++) {
				String word = "";
				for(int j = i; j < i+n; j++) {
					word += doc[j] + " ";
				}
				validLine.add(word.trim());
			}
		}
		fileNgram.put(file.getName(), validLine);
		
		
	}

	@Override
	public int getNumNGramsInCommon(String file1, String file2) {
		// TODO Auto-generated method stub
		
		
		return results.get(file1).get(file2);
	}

	@Override
	public Collection<String> getSuspiciousPairs(int minNgrams) {
		// TODO Auto-generated method stub
		Set<String, String> suspiciousSet = new HashSet<String, String>();
		Map<HashMap<String, String>, Integer> suspiciousParis = new HashMap<HashMap<String, String>, Integer>();
		for(String f: fileNgram.keySet()) {
			for(String d: results.keySet()) {
				if(minNgrams >= getNumNGramsInCommon(f, d)) {
					
				};
				
			}
		}
		return null;
	}

	@Override
	public void readFilesInDirectory(File dir) throws IOException {
		// delegation!
		// just go through each file in the directory, and delegate
		// to the method for reading a file
		
		for (File f : dir.listFiles()) {
			readFile(f);
		}
		
		for (String f: fileNgram.keySet()) {
			Map<String, Integer> nCommon = new HashMap<String, Integer>();
			for(String d: fileNgram.keySet()) {
				int cnt = 0;
				for (String s: fileNgram.get(f)) {
					if(fileNgram.get(d).contains(s)) {
						cnt++;
					}
				}
				nCommon.put(d, cnt); //results<f, map<d,cnt>>
			}
			results.put(f, nCommon);
		}
	}
}
