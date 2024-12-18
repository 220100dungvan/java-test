package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utils.MysqlConnection;
import models.bean.File;

public class FileDAO {
	private Connection conn = MysqlConnection.cretateConnection();

	public void InsertFile(String filename, String Path, String UUID) {
		try {
			String sql = "INSERT INTO file (uuid,filename,path) VALUES (?,?,?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, UUID);
			pstmt.setString(2, filename);
			pstmt.setString(3, Path);

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Thêm thành công!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String GetPath(String UUID) {
		try {
			String sql = "SELECT path FROM file WHERE uuid=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, UUID);
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("path");
			}
			System.out.println("Không tìm thấy file có UUID là: " + UUID);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	public String getFileName(String UUID) {
		try {
			String sql = "SELECT filename from file where uuid=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, UUID);
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("filename");
			}
			System.out.println("Không tìm thấy file có UUID là: " + UUID);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	public boolean checkDone(String UUID) {
		try {
			String sql = "SELECT * FROM file WHERE uuid=? and status = 'done' ";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, UUID);
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				return true;
			}
			System.out.println("Không tìm thấy file có UUID là: " + UUID);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public List<File> getWaitingFiles() {
		List<File> fileList = new ArrayList<File>();
		try {
			String sql = "SELECT * FROM file WHERE status='wait'";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet resultSet = pstmt.executeQuery();

			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String uuid = resultSet.getString("uuid");
				String path = resultSet.getString("path");
				String filename = resultSet.getString("filename");
				String status = resultSet.getString("status");
				File file = new File(id, filename, uuid, status, path);
				fileList.add(file);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return fileList;
	}
	
	public void updateFileStatus(String uuid, String path) {
		try {
			String sql = "UPDATE file SET status='done',path=? WHERE uuid=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, path);
			pstmt.setString(2, uuid);
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Cập nhật thành công!");
			} else {
				System.out.println("Không có file nào được cập nhật.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
