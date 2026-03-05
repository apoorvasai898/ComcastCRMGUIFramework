package com.comcast.hrm.generic.databaseutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class DataBaseUtility {
	Connection conn = null;
	ResultSet result = null;
		
	//Get DBCon by passing url,username and password arguments
	public void getDbConnection(String url,String username,String password) throws SQLException
	{
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
				
			conn = DriverManager.getConnection(url,username,password);
		}catch(Exception e) {
				
		}
	}
		
	//Get the connection of the Database directly(hardcoding url,username,password
	public void getDbConnection() throws SQLException
	{
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection("jdbc:mysql://49.249.28.218:3307/ninza_hrm","root@%","root");
		}catch(Exception e) {
				
		}
		}
		
	//Close the Database Connection
	public void closeDBConnection() throws SQLException
	{
		try {
			conn.close();
		}catch(Exception e)
		{
				
		}
	}
		
	//Execute SelectQuery
	public ResultSet executeSelectQuery(String query)
	{
		try {
			Statement stat = conn.createStatement();
			result =stat.executeQuery(query);
		}catch(Exception e)
		{
				
		}
		return result;
	}
		
	//Execute NonSelectQuery
	public int executeNonSelectQuery(String query)
	{
		int result = 0;
		try {
			Statement stat = conn.createStatement();
			result = stat.executeUpdate(query);
		}catch(Exception e)
		{
				
		}
			return result;
	}
	
	//Verify the project is present/created
	public void ProjectIsCreatedOndb(ResultSet result, String projName) throws SQLException
	{
		Boolean flag = false;
		this.result = result;
		while(result.next())
		{
			if(result.getString("project_name").equals(projName))
			{
				flag = true;
				break;
			}
			else {
				continue;
			}
		}
		if(flag == true)
		{
			System.out.println(projName+": Project Created Successfully === PASS");
		}
		else {
			System.out.println(projName+ " : Project is not Created === FAIL");
		}
	}
}


