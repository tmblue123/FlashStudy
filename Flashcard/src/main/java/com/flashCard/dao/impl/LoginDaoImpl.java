package com.flashCard.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.flashCard.dao.LoginDao;
import com.flashCard.model.User;

public class LoginDaoImpl implements LoginDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;
 
	public void saveLogin(User user) {
		String dbName = "jdbc:mysql://ec2-13-58-137-45.us-east-2.compute.amazonaws.com:3306/myDB";
		String dbUserName = "newremoteuser";
		String dbPassword = "password";
		  Connection conn = null;
		 try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(dbName, dbUserName, dbPassword);
            System.out.println("Database connection established");
    		String insertSQL = "INSERT INTO User (USER_NAME, PASSWORD, EMAIL,NICKNAME) VALUES ('" + user.getUsername() + "' , '" + user.getPassword() +"', '" + user.getEmail() +"', '" +  user.getNickname() +"')";
    		PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
    		boolean rs= preparedStatement.execute();
        
		 } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
		
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 

	 try {
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public User validateLogin(User login) {
		User user = null;
		String sql = "select * from User where USER_NAME='" + login.getUsername() + "' and PASSWORD='"
				+ login.getPassword() + "'";
		String dbName = "jdbc:mysql://ec2-13-58-137-45.us-east-2.compute.amazonaws.com:3306/myDB";
		String dbUserName = "newremoteuser";
		String dbPassword = "password";
		Connection conn = null;
		 try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(dbName, dbUserName, dbPassword);
           System.out.println("Database connection established");
   			PreparedStatement preparedStatement = conn.prepareStatement(sql);
   		
   		ResultSet rs= preparedStatement.executeQuery();
		while(rs.next()) {
			String username= rs.getString("USER_NAME");
			String nickname = rs.getString("NICKNAME");
			String email = rs.getString("EMAIL");
			user = new User(username, "", nickname, email);
		}
       
		 } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
		
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 

	 try {
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	return user;
}

}
class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		User user = new User();
		user.setUsername(rs.getString("USER_NAME"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setNickname(rs.getString("NICKNAME"));
		user.setEmail(rs.getString("EMAIL"));
		return user;
	}
}