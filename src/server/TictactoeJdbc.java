package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TictactoeJdbc {
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "bitacademy";
	String pass = "bitacademy";
	Connection con;
	Statement statemt;
	PreparedStatement pstmt;
	public TictactoeJdbc() {
		
		Connection con;
		try {
			con = DriverManager.getConnection(url,id,pass);
			statemt=con.createStatement();
		} catch (SQLException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}
		
	}
	public boolean logTableCreate() {// 로그테이블 생성, 성공시 True, 실패시 false반환
		String strCreate="CREATE TABLE gamelogs(\r\n"
				+ "  id VARCHAR2(14),\r\n"
				+ "  logs VARCHAR2(9),\r\n"
				+ "  sdate DATE DEFAULT sysdate\r\n"
				+ ");";
		int cnt=0;
		try {
			cnt=statemt.executeUpdate(strCreate);
		} catch (SQLException e) {
			System.out.println("로그 테이블 생성실패.(이미 생성되었을 가능성 높음)");
			return false;
			//e.printStackTrace();
		}
		return true;
	}
	public boolean saveLogs( String id,String log) { //로그 저장, 성공시 True, 실패시 false반환
		String strInsertLog ="INSERT INTO gamelogs (id, logs)\r\n"
				+ "  VALUES (?,?)";
		try {
			pstmt=con.prepareStatement(strInsertLog);
			pstmt.setString(1, id);
			pstmt.setString(2, log);
			int cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("게임 로그 저장 실패");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<String[]> getLogs(String id) { //해당 id에 속하는 모든 로그 반환
		List<String[]> result = new ArrayList<String[]>();
		String setSelectLog = "SELECT logs, sdate\r\n"
				+ "  FROM gamelogs\r\n"
				+ "  WHERE id="+id+" \r\n"
				+ "  ORDER BY sdate DESC";
		try {
			ResultSet rs=statemt.executeQuery(setSelectLog);
			while(rs.next()) {
				String[] log= new String[2]; //[0] : log, [1]:sysdate
				log[0]=rs.getString(1);
				log[1]=rs.getString(2);
				result.add(log);
			}
		} catch (SQLException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}
		
		return result;
	}
	public int npSelect(String log) { //고난이도에서 다음 칸에들어올 말을 선택
		int[] scores=new int[9];
		int maxIndex=0;
		try {
			for(int i=0;i<9;i++) {
				String setSelect="SELECT NVL(SUM(score),0)\n\r"
						+"FROM t3\n\r"
						+"WHERE logs LIKE '"+log+Integer.toString(i)+"%'";
				ResultSet rs=statemt.executeQuery(setSelect);
				rs.next();
				scores[i]=rs.getInt(1);
			}
			int maxNum=scores[0];
			
			for (int i=1;i<9;i++) {
				if(scores[i]>maxNum) {
					maxNum=scores[i];
					maxIndex=i;
				}
			}
			
		}
		catch(SQLException e) {
			
			e.printStackTrace();
		}
		return maxIndex;
	}
}
