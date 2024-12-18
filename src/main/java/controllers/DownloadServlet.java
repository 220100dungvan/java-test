package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.bo.FileBO;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	public FileBO fileBO = new FileBO();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/download.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uuid = request.getParameter("uuid");
		String pathFile = "";
		String filename = "";

		pathFile = fileBO.GetPath(uuid);
		filename = fileBO.getFileName(uuid);

		File file = new File(pathFile);
		if (!file.exists()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().write("File not found.");
			return;
		}

		
		response.setContentType("application/octet-stream");
		String encodedFilename = java.net.URLEncoder.encode(filename, "UTF-8").replace("+", "%20"); // Mã hóa filename
		response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename + ".docx");


		
		try (InputStream inputStream = new FileInputStream(file);
				OutputStream outputStream = response.getOutputStream()) {

			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}
	}

}
