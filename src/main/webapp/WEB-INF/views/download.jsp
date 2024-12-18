<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File List</title>
<style>
body {
	font-family: 'Arial', sans-serif;
	background-color: #f8f9fa;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.container {
	background-color: #fff;
	padding: 40px;
	border-radius: 10px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
	text-align: center;
	width: 80%;
	max-width: 1000px;
}

h1 {
	color: #333;
	font-size: 28px;
	margin-bottom: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

table, th, td {
	border: 1px solid #ddd;
}

th, td {
	padding: 12px;
	text-align: left;
}

th {
	background-color: #28a745; /* Màu xanh lá cây cho tiêu đề */
	color: white;
}

tr:nth-child(even) {
	background-color: #f9f9f9;
}

tr:hover {
	background-color: #f1f1f1;
}

.btn {
	background-color: #28a745; /* Màu xanh lá cây cho nút */
	color: white;
	font-size: 16px;
	padding: 8px 16px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s ease, transform 0.2s ease;
}

.btn:hover {
	background-color: #218838; /* Màu xanh lá cây đậm khi hover */
	transform: translateY(-2px);
}

.btn:active {
	transform: translateY(1px);
}

.btn-container {
	margin-top: 20px;
}

.btn-container form {
	display: inline-block;
	margin-right: 10px;
}

.reload-message {
	font-size: 14px;
	color: #888;
	margin-top: 10px;
}

/* Giới hạn chiều rộng cho cột Action */
td.action-column {
	width: 150px; /* Giới hạn chiều rộng cột Action */
	text-align: center;
}
</style>
<script>
	// Hàm reload tự động sau mỗi 10 giây
	function reload() {
		setTimeout(function() {
			location.reload(); // Tự động reload trang sau 3 giây
		}, 3000); // 3 giây
	}

	// Gọi reload khi trang đã load xong
	window.onload = function() {
		reload();
	}
</script>
</head>
<body>
	<div class="container">
		<h1>File List</h1>
		<table>
			<thead>
				<tr>
					<th>File Name</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<%
				try {
					// Lấy session và UUID của người dùng
					List<String> userFiles = (List<String>) session.getAttribute("uuid");

					if (userFiles != null && !userFiles.isEmpty()) {
						// Tạo query filter theo UUID
						StringBuilder queryBuilder = new StringBuilder("SELECT * FROM file WHERE uuid IN (");
						for (int i = 0; i < userFiles.size(); i++) {
					queryBuilder.append("?");
					if (i < userFiles.size() - 1) {
						queryBuilder.append(",");
					}
						}
						queryBuilder.append(")");

						// Kết nối và thực thi query
					Class.forName("com.mysql.cj.jdbc.Driver");
						try (java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/ltm1812",
						"root", "12345"); java.sql.PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {

					// Gán giá trị UUID vào query
					for (int i = 0; i < userFiles.size(); i++) {
						pstmt.setString(i + 1, userFiles.get(i));
					}

					try (java.sql.ResultSet rs = pstmt.executeQuery()) {
						while (rs.next()) {
							String uuid = rs.getString("uuid");
							String filename = rs.getString("filename");
							String status = rs.getString("status");

							// Hiển thị thông tin file và trạng thái
				%>
				<tr>
					<td><%=filename%></td>
					<td><%=status%></td>
					<td class="action-column">
						<%
						if ("done".equals(status)) { // Nếu trạng thái là 'done', hiển thị nút
						%>
						<form action="download" method="POST">
							<input type="hidden" name="uuid" value="<%=uuid%>">
							<button type="submit" class="btn">Download</button>
						</form> <%
 }
 %>
					</td>
				</tr>
				<%
				}
				}
				}
				} else {
				%>
				<tr>
					<td colspan="3">No files found for the current session.</td>
				</tr>
				<%
				}
				} catch (Exception e) {
				e.printStackTrace();
				%>
				<tr>
					<td colspan="3">An error occurred while fetching file list.</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

		<div class="btn-container">
			<form action="index" method="GET">
				<button type="submit" class="btn">Go to Index</button>
			</form>
		</div>

		<div class="reload-message">
			<p>Page will reload in 3 seconds.</p>
		</div>
	</div>

</body>
</html>
