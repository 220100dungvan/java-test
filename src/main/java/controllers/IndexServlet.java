package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import models.bo.FileBO;

@WebServlet("/index")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		// 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  final long maxFileSize = 1024 * 1024 * 10;

	public FileBO fileBO = new FileBO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String uploadPath = "C:" + File.separator + "uploads";

	

	
		HttpSession session = request.getSession();

		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		List<String> uuid = (List<String>) session.getAttribute("uuid");
		if (uuid == null) {
			uuid = new ArrayList<>();
		}
		try {
			for (Part part : request.getParts()) {
				UUID randomUUID = UUID.randomUUID();
				String fileNameRequest = part.getSubmittedFileName();
				String fileName = randomUUID.toString() + ".pdf";
				long fileSize = part.getSize();
				if (fileSize > maxFileSize) {
					response.getWriter().write("Error: Size of PDF files are not allowed.");
					return;
				}

				if (fileName != null && !fileName.isEmpty()) {
					String filePath = uploadPath + File.separator + fileName;

					if (!part.getContentType().equalsIgnoreCase("application/pdf")) {
						response.getWriter().write("Error: Only PDF files are allowed.");
						return;
					}

					
					part.write(filePath);

					uuid.add(randomUUID.toString());
					session.setAttribute("uuid", uuid);

					
					fileBO.InsertFile(fileNameRequest, filePath, randomUUID.toString());
				
					
					System.out.println("Context Path: " + request.getContextPath());

					response.sendRedirect(request.getContextPath() + "/download?uuid=" + randomUUID.toString());
				}
			}
		} catch (Exception e) {
			response.getWriter().write("Error: " + e.getMessage());
		}
	}

}
