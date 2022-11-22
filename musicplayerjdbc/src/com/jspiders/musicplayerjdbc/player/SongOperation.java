package com.jspiders.musicplayerjdbc.player;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class SongOperation {
	 
	static SongOperation songOperation= new SongOperation();
	
	public static Connection connection;
	public static Statement statement;
	public static PreparedStatement preparedStatement;
	public static ResultSet resultSet;
	public static FileReader fileReader;
	public static Properties properties=new Properties();
	public static String filePath="E:\\J2EE\\musicplayerjdbc\\src\\resources\\db_info.properties";
	public static int result;
	public static String query;
	public static Scanner scanner=new Scanner(System.in);
	public static int count=0;
	
	public static ArrayList<Integer>list=new ArrayList<Integer>();
	
	
	public int countSong() {
		int count=0;
		try {
			fileReader=new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("driverPath"));
			
			connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
			statement=connection.createStatement();
			
			query="select id from song_info";
			
			resultSet=statement.executeQuery(query);
			
			while (resultSet.next()) {
				list.add(resultSet.getInt(1));
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void addSongs() {
		System.out.println("enter no. of song to add");
		int no = scanner.nextInt();
		for (int i = 1; i < no; i++) {
			count++;
			try {
				fileReader=new FileReader(filePath);
				properties.load(fileReader);
				Class.forName(properties.getProperty("driverPath"));
				
				connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
				statement=connection.createStatement();
				
				System.out.println("Enter Song Id : ");
				int id=scanner.nextInt();
				
				System.out.println("Enter Song Name : ");
				String songName=scanner.next();
				
				System.out.println("Enter Singer Name : ");
				String singerName=scanner.next();
				
				System.out.println("Enter Song Length : ");
				double songLength=scanner.nextDouble();
				
				query = "insert into song_info values(?,?,?,?)";
				preparedStatement= connection.prepareStatement(query);
				
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, songName);
				preparedStatement.setString(3, singerName);
				preparedStatement.setDouble(4, songLength);
				
				result = preparedStatement.executeUpdate();
				System.out.println("Song Added Successfully");
				
				MusicPlayer.musicMenu();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void displayListOfSong() {
		
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("driverPath"));
			
			connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
			statement=connection.createStatement();
			
			query="select * from song_info";
			
			resultSet=statement.executeQuery(query);
			
			while (resultSet.next()) {
				System.out.println(resultSet.getInt(1)+" : "+ resultSet.getNString(2));
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void removeSong() {
		
		try {
			System.out.println("Present Songs Are...");
			
			songOperation.displayListOfSong();
			
			System.out.println("Enter Id To Delete Song");
			int id=scanner.nextInt();
			query="delete from song_info where id="+id+"";
			preparedStatement=connection.prepareStatement(query);
			result=preparedStatement.executeUpdate();
			
			System.out.println("Song Deleted");
			
			count--;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void ChooseSongPlay() {
		
		try {
			songOperation.displayListOfSong();
			
			fileReader=new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("driverPath"));
			connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
			statement=connection.createStatement();
			
			System.out.println("enter id to play song");
			
			int id=scanner.nextInt();
		
			int count=songOperation.countSong();
			
			
				if (id<=count) {
					query="select name from song_info where id="+id+"";
					resultSet = statement.executeQuery(query);
					
					while (resultSet.next()) {
						System.out.println(resultSet.getString(1)+" song playing");
						Thread.sleep(2000);
					}
					
				} else {
					System.out.println("enter proper id");
					songOperation.ChooseSongPlay();
				}
				
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void playAllSong() {
		
		try {
			fileReader=new FileReader(filePath);
			
			properties.load(fileReader);
			
			Class.forName(properties.getProperty("driverPath"));
			
			connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
			
			statement=connection.createStatement();
			
			query="select * from song_info";
			
			resultSet=statement.executeQuery(query);
			
			while (resultSet.next()) {
				System.out.println(resultSet.getString(2)+" is playing");
				Thread.sleep(2000);
			}
			
			MusicPlayer.musicMenu();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void playRandom() {
		
		try {
			
			fileReader=new FileReader(filePath);
			
			properties.load(fileReader);
			
			Class.forName(properties.getProperty("driverPath"));
			
			connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
			
			query="select id from song_info";
						
			statement=connection.createStatement();
			
			resultSet=statement.executeQuery(query);
			
			songOperation.countSong();
			
			double id = (Math.random() * (list.size()));
			
			query="select name from song_info where id="+list.get((int)id);
			
			connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
			
			//query="select id from song_info";
						
			statement=connection.createStatement();
			
			resultSet=statement.executeQuery(query);
			
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1)+" is playing");
				Thread.sleep(2000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void update() {
		
		try {
		
			fileReader=new FileReader(filePath);
			
			properties.load(fileReader);
			
			Class.forName(properties.getProperty("driverPath"));
			
			connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
			
			statement=connection.createStatement();
			
			System.out.println("present songs are...");
			songOperation.displayListOfSong();
			
			System.out.println("select a song to update");
			int id=scanner.nextInt();
			
			int count=songOperation.countSong();
			
			if (id<=count) {
				
			
			System.out.println("update following\n1.name\n2.singer\n3.length\n");
			int update=scanner.nextInt();
			
			if (update<=3) {
				
				switch (update) {
				case 1:
					System.out.println("enter new song Name");
					String name=scanner.next();
					query="update song_info set name=? where id="+id;
					
					preparedStatement=connection.prepareStatement(query);
					preparedStatement.setString(1, name);
					result=preparedStatement.executeUpdate();
					
					System.out.println("song update successfully...");
					
					break;
					
				case 2:
					System.out.println("enter singer Name");
					String singer=scanner.next();
					query="update song_info set singer=? where id="+id;
					
					preparedStatement=connection.prepareStatement(query);
					preparedStatement.setString(1, singer);
					result=preparedStatement.executeUpdate();
					
					System.out.println("song update successfully...");
					
					break;
					
				case 3:
					System.out.println("enter length of song");
					double length=scanner.nextDouble();
					query="update sonsong_infog set singer=? where id="+id;
					
					preparedStatement=connection.prepareStatement(query);
					preparedStatement.setDouble(1, length);
					result=preparedStatement.executeUpdate();
					
					System.out.println("song update successfully...");
					
					break;

				default:
					System.out.println("Invalid Input");
					songOperation.update();
					break;
				}
				
			} else {

				System.out.println("Invalid Input");
				songOperation.update();
			}
			}else {
				System.out.println("select proper id");
				songOperation.update();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void SongTable() {
		
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("driverPath"));
			
			connection=DriverManager.getConnection(properties.getProperty("dburl"), properties);
			statement=connection.createStatement();
			
			query="select * from song_info";
			
			resultSet=statement.executeQuery(query);
			System.out.println("*id *Name *singer *length");
			
			int value=0;
			while (resultSet.next()) {
				System.out.println(resultSet.getInt(1)+" "+
									resultSet.getString(2)+" "+
									resultSet.getString(3)+" "+
								    resultSet.getDouble(4));
				value++;
			}
			if (value==0) {
				System.out.println("Table is empty insert song");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
