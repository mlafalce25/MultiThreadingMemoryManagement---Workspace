/**
 * 
 * @author Matt
 *
 */
public class Worker extends Thread{

	private Job job = null;
	private boolean isWorking;
	private Lock lock = new Lock();


	public Worker(Job job2) {
		this.job = job2;			
		isWorking = true;
		start();
	}



	@Override
	public void run() {
		try {
			lock.lock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Worker " + Thread.currentThread().getId()+" starting ");

		while(isWorking) {
			boolean c = true;
			int openSpots = 0;


			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------				

			for(int x = 0; x <10; x++) {							//loop through memory locations
				if(Boss.mainMem[x] == null) {
					openSpots++;
				}	
			}

			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------						
		
			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------						

			//allocation
			if(job.processing == false) {
				if(openSpots>=job.getNumOfPages()) {
					for(int b=0;b<job.getNumOfPages();b++) {
						for(int x = 0; x <10; x++) {							//loop through memory locations				
							if(Boss.mainMem[x] == null && job.pageMapTable[b].placed == false) {
								Boss.mainMem[x] = job.pageMapTable[b];
								job.pageMapTable[b].placed = true;
								System.out.println("Placed Job: "+job.getID()+" > Page: "+job.pageMapTable[b].getPageNum()+" > in frame number "+x);
								break;
							}
						}
					}
					job.setStartTime(System.currentTimeMillis());
					job.setEndTime();
					job.processing = true;
					System.out.println();
					System.out.println("Current Table:");
					for(int cur = 0;cur<10;cur++) {
						if(Boss.mainMem[cur] == null) {
							System.out.println("Frame Number "+cur+" | Job:   | Page:  ");

						}
						else {
							System.out.println("Frame Number "+cur+" | Job: "+Boss.mainMem[cur].getJobID()+" | Page: "+Boss.mainMem[cur].getPageNum());
						}
					}
					System.out.println("----------------------------------------------End of Iteration");
					System.out.println();
				}
			}	

			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			//deallocation
				if(job.getEndTime() <= System.currentTimeMillis()/* && jobs.get(g).processing == true*/) {
					for(int n=0;n<10;n++) {
						if(Boss.mainMem[n]!=null) {
							if(job.getID() == Boss.mainMem[n].getJobID()) {
								System.out.println("JOB: "+Boss.mainMem[n].getJobID()+" PAGE: "+Boss.mainMem[n].getPageNum() + " HAS BEEN EJECTED");
								Boss.mainMem[n].setEjected(true);
								Boss.mainMem[n] = null;

							}
						}
					}

				}	

			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			//job termination
			boolean amIDone = true;
				for(int d = 0; d <job.getNumOfPages(); d++) {
					if(job.pageMapTable[d].ejected == false) {
						amIDone = false;
						break;
					}
				}
				if(amIDone == true) {
					System.out.println("JOB: "+job.getID()+" HAS BEEN REMOVED");
					System.out.println();
					System.out.println("Current Table:");
					for(int cur = 0;cur<10;cur++) {
						if(Boss.mainMem[cur] == null) {
							System.out.println("Frame Number "+cur+" | Placed Job:   | Page:  ");

						}
						else {
							System.out.println("Frame Number "+cur+" | Placed Job: "+Boss.mainMem[cur].getJobID()+" | Page: "+Boss.mainMem[cur].getPageNum());
						}
					}
					System.out.println("----------------------------------------------End of Iteration");

					terminate();

				}
			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			lock.unlock();
		}

	}

	public void terminate() {
		isWorking = false;
	}
}
