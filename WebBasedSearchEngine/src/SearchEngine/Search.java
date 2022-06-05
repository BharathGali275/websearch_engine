package SearchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Search {
	
	private static Scanner sc = new Scanner(System.in);
	static ArrayList<String> key = new ArrayList<String>();
	static HashMap<String, Integer> numbers = new HashMap<String, Integer>();
	// Positions and Occurrences of the words
	
	public void searchWord() {
		
				// Hash table is used instead of Hash Map as it don't allow null value in insertion
				Hashtable<String, Integer> FileList = new Hashtable<String, Integer>();
				
				String choice = "y";
				do {
					System.out.println("***********************************************");
					System.out.println("\tEnter a word you want to search\t");
					System.out.println("***********************************************");
					String wordToSearch = sc.next();
					System.out.println("***********************************************");
					int WordFrequency = 0;
					int TotFiles = 0;
					FileList.clear();
					
					try {
						System.out.println("\nSearching...");
						File Files = new File(Path.txtDirectoryPath);

						File[] ArrayofFiles = Files.listFiles();

						for (int i = 0; i < ArrayofFiles.length; i++) {

							In data = new In(ArrayofFiles[i].getAbsolutePath());

							String txt = data.readAll();
							data.close();
							Pattern p = Pattern.compile("::");
							String[] file_name = p.split(txt);
							WordFrequency = wordSearch(txt, wordToSearch.toLowerCase(), file_name[0]); // search word in txt files
							if (WordFrequency != 0) 
							{
								FileList.put(file_name[0], WordFrequency);
								TotFiles++;
							}
							
						}

						if(TotFiles>0) 
						{
						System.out.println("\nTotal Number of Files containing word : " + wordToSearch + " is : " + TotFiles);
						System.out.println("\n or were u searching for these words");
						Search.suggestAltWord(wordToSearch.toLowerCase());
						}
						else 
						{
							System.out.println("\n File not found! containing word : "+ wordToSearch);
							Search.suggestAltWord(wordToSearch.toLowerCase()); // suggest another word if entered word not found
							continue;
						}

						Search.rankFiles(FileList, TotFiles); 				   //rank the files based on frequency of word count
						

					} 
					catch (Exception e) 
					{
						System.out.println("Exception:" + e);
					}
					System.out.println("\n Do you want return to search another word(y/n)?");
					choice = sc.next();
				} while (choice.equalsIgnoreCase("y"));
		
	}
	
		public static int wordSearch(String data, String word, String fileName) {
			int counter = 0;

			int offset = 0;
			BoyerMoore boyermoore = new BoyerMoore(fileName);
			
			for (int location = 0; location <= data.length(); location += offset + word.length()) {
				offset = BoyerMoore.search(word, data.substring(location));
				if ((offset + location) < data.length()) {
					counter++;
				}
			}
			if (counter != 0) {
				System.out.println("Found in HTML file --> " + fileName+" --> "+counter+" times"); // Founded from which HTML file..
				System.out.println("-------------------------------------------------------------------------");																					
			}
			return counter;
		}

		// Merge-sort for ranking of the pages
		public static void rankFiles(Hashtable<?, Integer> files, int occur) {

			// Transfer as List and sort it
			ArrayList<Map.Entry<?, Integer>> fileList = new ArrayList<Map.Entry<?, Integer>>(files.entrySet());

			Collections.sort(fileList, new Comparator<Map.Entry<?, Integer>>() {

				public int compare(Map.Entry<?, Integer> obj1, Map.Entry<?, Integer> obj2) {
					return obj1.getValue().compareTo(obj2.getValue());
				}
			});

			Collections.reverse(fileList);

			if (occur != 0) {
				
				System.out.println("------Top 10 search results -----");

				int noOfFetch = 10;
				int j = 0;
				int i=1;
				while (fileList.size() > j && noOfFetch > 0) {
					
					
					if(fileList.get(j).getKey()!=null) {
					System.out.println("(" + i + ") " + fileList.get(j).getKey() + "------Word Searched found " + fileList.get(j).getValue() + " many times");
					j++;
					i++;
					}
					noOfFetch--;
					
				}
			} 

		}

		public static void suggestAltWord(String wordToSearch) {
			String line = " ";
			String regex = "[a-z0-9]+";

			// Create a Pattern object
			Pattern pattern = Pattern.compile(regex);
			// Now create matcher object.
			Matcher matcher = pattern.matcher(line);
			int fileNumber = 0;

			File dir = new File(Path.txtDirectoryPath);
			File[] fileArray = dir.listFiles();
			for (int i = 0; i < fileArray.length; i++) {
				try {
					findWord(fileArray[i], fileNumber, matcher, wordToSearch);
					fileNumber++;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			Integer allowedDistance = 1; // Edit distance allowed
			boolean matchFound = false; // set to true if word found with edit distance equal to allowedDistance

			
			int i = 0;
			for (Map.Entry entry : numbers.entrySet()) {
				if (allowedDistance == entry.getValue()) {
					
					i++;
					
					if(i==1)
					System.out.println("Did you mean? ");
					
					System.out.print("(" + i + ") " + entry.getKey() + "\n");
					matchFound = true;
				}
			}
			if (!matchFound)
				System.out.println("Entered word cannot be found....");
		}

		// finds strings with similar pattern and calls edit distance() on those strings
		public static void findWord(File sourceFile, int fileNumber, Matcher match, String str)
				throws FileNotFoundException, ArrayIndexOutOfBoundsException {
			try {
				BufferedReader my_rederObject = new BufferedReader(new FileReader(sourceFile));
				String line = null;

				while ((line = my_rederObject.readLine()) != null) {
					match.reset(line);
					while (match.find()) {
						key.add(match.group());
					}
				}

				my_rederObject.close();
				for (int p = 0; p < key.size(); p++) {
					numbers.put(key.get(p), editDistance(str.toLowerCase(), key.get(p).toLowerCase()));
				}
			} catch (Exception e) {
				System.out.println("Exception:" + e);
			}

		}

		public static int editDistance(String str1, String str2) {
			int len1 = str1.length();
			int len2 = str2.length();

			int[][] my_array = new int[len1 + 1][len2 + 1];

			for (int i = 0; i <= len1; i++) {
				my_array[i][0] = i;
			}

			for (int j = 0; j <= len2; j++) {
				my_array[0][j] = j;
			}

			// iterate though, and check last char
			for (int i = 0; i < len1; i++) {
				char c1 = str1.charAt(i);
				for (int j = 0; j < len2; j++) {
					char c2 = str2.charAt(j);

					if (c1 == c2) {
						my_array[i + 1][j + 1] = my_array[i][j];
					} else {
						int replace = my_array[i][j] + 1;
						int insert = my_array[i][j + 1] + 1;
						int delete = my_array[i + 1][j] + 1;

						int min = replace > insert ? insert : replace;
						min = delete > min ? min : delete;
						my_array[i + 1][j + 1] = min;
					}
				}
			}

			return my_array[len1][len2];
		}

}
