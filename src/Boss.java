import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 
 * @author Matt
 *
 */
public class Boss {

	static ArrayList<Job> jobs = new ArrayList<>();
	static ArrayList<Worker> workers = new ArrayList<>();
	static Page[] mainMem = new Page[10];

	//Constructor
	public Boss() {
		fileReader();
		createMainMem();
		createWorkers();
		
	}


	/**
	 * Reads in the text file and creates job objects
	 */
	public void fileReader() {

		File  fileName = new File("/Users/Matt/Documents/workspace/Lab 2 Paging/src/testfile");	

		try {
			Scanner in = new Scanner(fileName);
			System.out.println("File Found");

			//checks the contents of the file 	 
			int a = 0;
			do {

				String line = in.nextLine();
				int first = Integer.parseInt(line.substring(0, line.indexOf(',')));
				line = line.substring(line.indexOf(',')+1);
				int second = Integer.parseInt(line.substring(0, line.indexOf(',')));
				int third = Integer.parseInt(line.substring(line.indexOf(',')+1));
				if(second<=1000) {
					jobs.add(new Job(first,second,third));
					jobs.get(a).setNumOfPages();
					jobs.get(a).setPageMapTable();
					System.out.println("Accepted "+jobs.get(a).toString());
					a++;
				}
				else {
					System.out.println("Job "+first+" has been rejected. Memory too large.");
				}


			}while(true);

		} catch(IOException e){
			System.out.println("File not Found!!!");
			System.exit(1);

		} 
		catch(NoSuchElementException e) {
			System.out.println("All The Data Has Been Inputed\n");
		}
		catch(NumberFormatException e) {
			System.out.println("Bad Data!!!");
			System.exit(1);

		}
		catch(Exception e) {
			System.out.println("Unknown Error!!!");
			e.printStackTrace();
			System.exit(1);

		}	
	}


	public void createWorkers() {
		for(int x = 0;x<jobs.size();x++) {
			workers.add(new Worker(jobs.get(x)));
		}
	}

	public void createMainMem() {
		//creating main memory
				for(int x = 0; x <10; x++) {
					mainMem[x] = null; 
				}
	}







































	public static void main (String[] args) {
		new Boss();

	}

}

