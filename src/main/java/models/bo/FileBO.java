package models.bo;

import java.util.List;

import models.bean.File;
import models.dao.FileDAO;

public class FileBO {
	private FileDAO fileDAO = new FileDAO();

	public void InsertFile(String filename, String Path, String UUID) {
		fileDAO.InsertFile(filename, Path, UUID);
	}

	public String GetPath(String UUID) {
		return fileDAO.GetPath(UUID);
	}

	public String getFileName(String UUID) {
		return fileDAO.getFileName(UUID);
	}

	public boolean checkDone(String UUID) {
		return fileDAO.checkDone(UUID);
	}

	public List<File> getWaitingFiles() {
		return fileDAO.getWaitingFiles();
	}

	public void updateFileStatus(String uuid, String path) {
		fileDAO.updateFileStatus(uuid, path);
	}

}
