package com.rushikesh;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.jdbc.OracleTypes;

public class MainCLass {

	public static void main(String[] args) {

		/*CREATE OR REPLACE PROCEDURE MY_TEST_PROC(dataCursor OUT sys_refcursor)
		AS
		BEGIN
			OPEN dataCursor FOR
			select * from EMPLOYEE;
		END MY_TEST_PROC;
		*/
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin");

			CallableStatement callableStatement = con.prepareCall("{call MY_TEST_PROC(?)}");
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);

			// execute getDBUSERCursor store procedure
			callableStatement.executeUpdate();

			// get cursor and cast it to ResultSet
			ResultSet rs = (ResultSet) callableStatement.getObject(1);

			// loop it like normal
			while (rs.next()) {
				System.out.print("|ID|" + rs.getInt("EMP_ID") + "|Name|" + rs.getString("EMP_NAME"));
				System.out.println("|Email|" + rs.getString("EMP_EMAIL") + "|Mobile|" + rs.getString("EMP_MOBILE"));
			}

			/*
			 * Statement stmt = con.createStatement(); ResultSet rs =
			 * stmt.executeQuery("select * from employee"); while (rs.next())
			 * System.out.println(""+rs.getString(4)+" "+rs.getInt(1) + "  " +
			 * rs.getString(2) + "  " + rs.getString(3));
			 */
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
