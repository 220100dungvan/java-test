package App;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import models.bean.File;
import models.bo.FileBO;
import Service.FileService;

public class Scheduler {
	private ExecutorService executorService = null;
	private FileService fileService = null;
	private FileBO fileBO = new FileBO();
	public Scheduler(FileService fileService)
	{
		this.fileService=fileService;
		this.executorService = Executors.newScheduledThreadPool(10);
	}
	
    public void start() {
    	new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        List<File> fileList = fileBO.getWaitingFiles();

                        if (fileList != null && !fileList.isEmpty()) {
                            for (File file : fileList) {
                                
                                executorService.submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            fileService.ProcessWatingFiles(file.getPath(), file.getUUID());
                                        } catch (ClassNotFoundException | SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } else {
                            System.out.println("No files found status is wait.");
                        }

                       
                        Thread.sleep(15000); 

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void shutdown() {
        executorService.shutdown();
    }
}
