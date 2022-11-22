package com.jspiders.musicplayerjdbc.player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class MusicPlayer {
	static boolean loop = true;
	static Song song;
	static SongOperation songOperation = new SongOperation();
	
	public static void main(String[] args) {
		musicMenu();
		while (loop) {
			musicMenu();
		}
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(songOperation.connection != null) {
				try {
					songOperation.connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(songOperation.statement != null) {
				try {
					songOperation.statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(songOperation.resultSet != null) {
				try {
					songOperation.resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(songOperation.preparedStatement != null) {
				try {
					songOperation.preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(songOperation.fileReader != null) {
				try {
					songOperation.fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			songOperation.scanner.close();
			
		}
		
	}
	public static void musicMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Menu :- \n1.Display All Songs \n2.Play \n3.Add/Remove \n4.Edit \n5.Exit");
		System.err.println("choose option :-");
		int key = sc.nextInt();
		switch (key) {
		
		case 1: {
			songOperation.SongTable();
			loop = false;
			musicMenu();
			break;
		}

		
		case 2: {
			System.out.println("1.choose song \n2.all songs \n3.random song \n4.go back.");
			System.err.println("choose option :-");
			int key1 = sc.nextInt();
			switch (key1) {
			
			case 1: {
				songOperation.ChooseSongPlay();
				break;
			}
			case 2: {
				songOperation.playAllSong();
				break;
			}
			case 3: {
				songOperation.playRandom();
				break;
			}
			case 4: {
				musicMenu();
				break;
			}
			default :
				System.out.println("invalid input");
				break;
			}
			break;
		}
		case 3: {
			System.out.println("1.Add song \n2.Remove song \n3. go back");
			System.err.println("choose option :-");
			int key2 = sc.nextInt();
			switch (key2) {
				case 1: {
					songOperation.addSongs();
					break;
				}
				case 2: {
					songOperation.removeSong();
					break;
				}
				case 3: {
					loop = false;
					musicMenu();
					break;
				}
				default : {
					System.out.println("invalid input");			
				}
			}
			break;
		}
		case 4: {
			songOperation.update();
			loop = false;
			musicMenu();
			break;
		}
		
		case 5:
			loop=false;
			break;
		default : {
			System.out.println("invalid input");			
		}
		}	
	}
}

