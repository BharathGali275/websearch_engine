package SearchEngine;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
	
	private static Scanner sc = new Scanner(System.in);
	
	int ans;
	String URL;
	
	public Main()
	{
		ans = 0;
		URL = "https://www.britannica.com";
	}

	public int getAns() {
		return ans;
	}



	public void setAns(int ans) {
		this.ans = ans;
	}



	public String getURL() {
		return URL;
	}



	public void setURL(String uRL) {
		URL = uRL;
	}
	
	public boolean isValid(String URL)
    {
        /* Try creating a valid URL */
        try {
        	System.out.println("Validating URL...");
        	URL obj = new URL(URL);
            HttpURLConnection CON = (HttpURLConnection) obj.openConnection();
            //Sending the request
            CON.setRequestMethod("GET");
            int response = CON.getResponseCode();
            if(response==200) {
            	 return true;
            }else {
            	return false;
            }
           
        }
          
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main Main = new Main();
		System.out.println("************************************************************");
		System.out.println("\tWelcome to Web Search Engine\t");
		System.out.println("************************************************************\n");
		Search Ser = new Search();
		while(true)
		{	
			System.out.println("Please Enter the number given below.");
			System.out.println("1.Enter your own URL to crawl and search\n2.Search in default DB which is \"https://www.britannica.com/\"\n3.Exit");
			Main.setAns(sc.nextInt());
			switch(Main.getAns())
			{
			case 1:
				Path.deleteFiles();
				System.out.println("Please Enter the URL \"Make sure https:\\\\ is added at the start\":\n");
				Main.setURL(sc.next());
				if(!Main.isValid(Main.getURL())) {
					 System.out.println("Enterd URL " + Main.getURL() + " isn't valid");
					 System.out.println("Please try again....\n");
					 break;
				}
				System.out.println("Enterd URL " + Main.getURL() + " is valid\n");
				System.out.println("************************************************************");
				System.out.println("\tWeb Crawling Started\t");
				System.out.println("************************************************************");
				Crawler.startCrawler(Main.getURL(), 0);
				System.out.println("************************************************************");
				System.out.println("\tWeb Crawling Completed\t");
				System.out.println("************************************************************");
				Ser.searchWord();
				Path.deleteFiles();
				//Deleting Created HTML and Text files
				break;
			case 2:
				Path.deleteFiles();
				System.out.println("************************************************************");
				System.out.println("\tWeb Crawling Started\t");
				System.out.println("************************************************************");
				Crawler.startCrawler(Main.getURL(), 0);
				System.out.println("************************************************************");
				System.out.println("\tWeb Crawling Completed\t");
				System.out.println("************************************************************");
				Ser.searchWord();
				Path.deleteFiles();
				//Deleting Created HTML and Text files
				break;
			case 3:
				System.out.println("\n*****************************************************");
				System.out.println("\tThis is the END of our Final Project Demo\t");
				System.out.println("******************************************************\n");
				System.out.println("Project Done by Group 6:\nVishnu Appala\nRohith Raju\nBharath Gali\nKarnaker Reddy Asireddy\nNeeraj Suhaas Atluri");
				System.out.println("******************************************************");
				Path.deleteFiles();
				System.exit(0);
				break;
			default:
				System.out.println("\n*****************************************************");
				System.out.println("\tPlease try again\t");
				System.out.println("******************************************************\n");
				Path.deleteFiles();
				break;
			}
		}

	}

}
