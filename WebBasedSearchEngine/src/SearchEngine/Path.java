package SearchEngine;

import java.io.File;

public class Path {
	public final static String htmlDirectoryPath = "C:/Users/appal/eclipse-workspace/WebBasedSearchEngine/WebPages/webfiles/";
	public final static String txtDirectoryPath = "C:/Users/appal/eclipse-workspace/WebBasedSearchEngine/WebPages/textfiles/";
	
	public static void deleteFiles() {
		File files = new File(Path.txtDirectoryPath);
		File[] ArrayofFiles = files.listFiles();

		for (int i = 0; i < ArrayofFiles.length; i++) {
			ArrayofFiles[i].delete();
		}
		
		File HTMLFiles= new File(Path.htmlDirectoryPath);
		File[] fileArrayhtml = HTMLFiles.listFiles();

		for (int i = 0; i < fileArrayhtml.length; i++) {
			
			fileArrayhtml[i].delete();
		}
	}
}
