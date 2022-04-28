package TicTacToe_sign;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
public class MemberDAO {
   
    private static Connection conn;
   
    private PreparedStatement pstmt;
    private CallableStatement cstmt;
    private ResultSet rs;
   
    private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "bitacademy";
	private String password = "bitacademy";
	//MemberDAO
	//테이블 이름 : t_member, 	
	//사용자 이름 : bitacademy
	//비밀 번호 : bitacademy
	//컬럼 : no, id, pwd, win, lose, m_registdate
	
	public MemberDAO() {
		try {
			Class.forName(driver);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
    public boolean insertMember(MemberDTO dto){    
       
        boolean result = false;            
        try {
            getConnection();
           
            String sql = "INSERT INTO T_Member VALUES (LPAD(seq_member_no.nextval,4,'0'),:id,:pwd,sysdate)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
           
            pstmt.setString(1,dto.getId());
            pstmt.setString(2,dto.getPwd());
           
            int r = pstmt.executeUpdate();
           
            if(r>0) result = true;
           
        } catch (Exception e) {
            System.out.println("유저등록에 실패했습니다 :insertMember()=> "+e.getMessage());
        }finally{          
            dbClose();
        }      
        return result;
    }      
   
 // 로그인
 	public String loginMember(String id, String pwd){
 		String name = null;
 		String sql = "select * from t_member where m_id=? and m_pwd=?";
 		getConnection();
 		
 		try {
 			pstmt = conn.prepareStatement(sql);//생성
 			pstmt.setString(1, id);
 			pstmt.setString(2, pwd);
 			
 			rs = pstmt.executeQuery();//실행
 		
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}finally {
 			try {
 				if(rs != null) rs.close();
 				if(pstmt != null) pstmt.close();
 				if(conn != null) conn.close();
 			}catch(SQLException e) {
 				e.printStackTrace();
 			}
 		}	
 		return id; //이거 틀릴수도...
 	}
 	
 	//중복아이디를 체크하는 부분
 	public String idCheck(String id) {
 		String sql = "select * from t_member where m_id=?";
 		getConnection();
 		String idcheck = null;
 		
 		try {
 			pstmt = conn.prepareStatement(sql);//생성
 			pstmt.setString(1, id);
 			
 			rs = pstmt.executeQuery();//실행
 			
 			if(rs.next()) idcheck = rs.getString("id");
 			
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}finally {
 			try {
 				if(rs != null) rs.close();
 				if(pstmt != null) pstmt.close();
 				if(conn != null) conn.close();
 			}catch(SQLException e) {
 				e.printStackTrace();
 			}
 		}
 		return idcheck;
 	}
    
 
    public MemberDTO getMember(String no){
        MemberDTO dto =null;
        try {
            getConnection();
           
            String sql = "SELECT m_no, m_id, m_pwd, m_registdate FROM T_Member WHERE m_no = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, no);
            ResultSet r = pstmt.executeQuery();
           
            if(r.next()) {
                String m_no = r.getString("m_no");
                String m_id = r.getString("m_id");
                String m_pwd = r.getString("m_pwd");
                String m_registdate = r.getDate("m_registdate").toString();
                dto = new MemberDTO(m_no, m_id, m_pwd, m_registdate);
            }
           
        } catch (Exception e) {
            System.out.println(":deleteMember()=> "+e.getMessage());
        }finally{          
            dbClose();
        }      
       
        return dto;
    }
 
    public List<MemberDTO> getMemberList(){
        List<MemberDTO> list = new ArrayList<MemberDTO>();
       
        try {
            getConnection();
           
            String sql = "SELECT m_no, m_id, m_pwd, m_registdate FROM T_Member ORDER BY m_registdate DESC";
 
            PreparedStatement pstmt = conn.prepareStatement(sql);          
            ResultSet r = pstmt.executeQuery();
           
            while(r.next()) {
                String m_no = r.getString("m_no");
                String m_id = r.getString("m_id");
                String m_pwd = r.getString("m_pwd");
                String m_registdate = r.getDate("m_registdate").toString();
                list.add(new MemberDTO(m_no, m_id, m_pwd, m_registdate));
            }
           
        } catch (Exception e) {
            System.out.println("getMemberList()=> "+e.getMessage());
        }finally{          
            dbClose();
        }  
       
        return list;
    }
   
    public boolean updateMember(MemberDTO dto){
       
        boolean result = false;            
        try {
            getConnection();
           
            String sql = "UPDATE T_Member SET m_id=id , m_pwd=pwd  WHERE m_no=no";
            PreparedStatement pstmt = conn.prepareStatement(sql);
           
            pstmt.setString(1,dto.getId());
            pstmt.setString(2,dto.getPwd());
            pstmt.setString(3,dto.getNo());        
           
            int r = pstmt.executeUpdate();
           
            if(r>0) result = true;
           
        } catch (Exception e) {
            System.out.println("수정에 실패했습니다 :updateMember()=> "+e.getMessage());
        }finally{          
            dbClose();
        }      
        return result;
    }
   
    public boolean deleteMember(String id){        
        boolean result = false;            
        try {
            getConnection();
           
            String sql = "DELETE FROM T_Member WHERE m_no = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            int r = pstmt.executeUpdate();
           
            if(r>0) result = true;
           
        } catch (Exception e) {
            System.out.println("삭제에 실패했습니다:deleteMember()=> "+e.getMessage());
        }finally{          
            dbClose();
        }      
        return result;
    }//deleteMember()--------------
   
    public void dbClose(){      
     
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("close():" + e.getMessage());
            }
        }
         
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("close():" + e.getMessage());
            }
        }
       
        if (cstmt != null) {
            try {
                cstmt.close();
            } catch (SQLException e) {
                System.out.println("close():" + e.getMessage());
            }
        }           
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("close():" + e.getMessage());
            }
        }    
        conn = null;        
    }//dbClose()---------
}