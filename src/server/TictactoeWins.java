package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class TictactoeWins {
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "bitacademy";
	String pass = "bitacademy";
	ArrayList<String> cases= new ArrayList<String>();
	static {
		//1. Oracle�� �����ϱ����� Ŭ���� ��ü�� �޸𸮿� �ε�
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setAll() {
		//2. ����Ŭ ���� �����ּ�, ����
		//3. ����Ŭ ���� ���� ��ü ����
		try {
			Connection con = DriverManager.getConnection(url,id,pass);
			//4.sql �������� �����ϱ����� ��ü ����
			Statement statemt=con.createStatement();
			
			//5.sql�� �ۼ�
			String strCreate= "CREATE TABLE t3(\r\n"
					+ "  logs VARCHAR2(9),\r\n"
					+ "  score int\r\n"
					+ ")";
			int cnt=statemt.executeUpdate(strCreate);
			//System.out.println("result : "+cnt);
			
			//7.������ ����
			TictactoeDFS dfs= new TictactoeDFS();
			cases=dfs.getLogs();
			String strInsert="INSERT INTO t3\r\n"
					+ "VALUES (?,?)";
			PreparedStatement pstmt=con.prepareStatement(strInsert);
			for (int i=0;i<cases.size();i++) {
				String casei=cases.get(i);
				int score=9-casei.length();
				if(score==0) {
					score=1;
				}else {
					int x=2;
					for (int j=score;j>0;j--)
						x*=j;
					score=x;
				}
				pstmt.setString(1, casei);
				pstmt.setInt(2, score);
				cnt = pstmt.executeUpdate();
				//System.out.println("result : "+cnt);
			}
			
			//8. dept�� pk �������� ����
			String strAlter="ALTER TABLE t3\r\n"
					+ "  ADD CONSTRAINT t3_logs_pk PRIMARY KEY(logs)";
			cnt=statemt.executeUpdate(strAlter);
			//System.out.println("result : "+cnt);
			
			//9. ��ü ����
			statemt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("데이터베이스가 이미 설정되어있음.");
			//e.printStackTrace();
		}
		
	}
}
