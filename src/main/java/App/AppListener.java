package App;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import Service.FileService;
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application started!");

        FileService fileService = new FileService(10);

        Scheduler scheduler = new Scheduler(fileService);
        
        scheduler.start();

        sce.getServletContext().setAttribute("scheduler", scheduler);
    }

   
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application stopped!");

       
        Scheduler scheduler = (Scheduler) sce.getServletContext().getAttribute("scheduler");
        if (scheduler != null) {
            scheduler.shutdown();  
        }
    }
}
