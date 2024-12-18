package Service;
import java.io.File;
import java.sql.SQLException;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

import models.bo.FileBO;
public class FileService {
	
    private int maxThreads;
    
    public FileService(int maxThreads) {
        this.maxThreads = maxThreads;
    }
    public void ProcessWatingFiles(String Path,String UUID) throws ClassNotFoundException, SQLException
    {
    	ConverntPdfToDocx(Path, UUID);
    }
	public void ConverntPdfToDocx(String Path, String UUID) throws SQLException, ClassNotFoundException
	{
    	FileBO fileBO = new FileBO();
		
		String pdfPath = Path;
		
	
		String docxPath = "C:" + File.separator + "uploads"+ File.separator + UUID + ".docx";
	
		
       System.out.println(pdfPath);
        try {
            
            Document pdfDocument = new Document(pdfPath);

            pdfDocument.save(docxPath, SaveFormat.DocX);
            System.out.println("Chuyển đổi thành công: " + docxPath);
        	fileBO.updateFileStatus(UUID,docxPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
