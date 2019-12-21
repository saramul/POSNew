package test;

import util.MySQLHelper;

public class TestDBConnection {

	public static void main(String[] args) {
		if(MySQLHelper.openDB()!=null) {
			System.out.println("DB Connected!");
			MySQLHelper.closeDB();
		}else {
			System.out.println("Unable to connect DB!");
		}
	}

}
