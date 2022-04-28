package TicTacToe_sign;

import java.util.Formatter;

public class MemberDTO { 

	private String id;
	private String pwd;
	private String no;
	private String registdate;
	//no sysdate gamelog 
	
    public MemberDTO() {
   
    }

    public MemberDTO(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }

    public MemberDTO(String no, String id, String pwd, String registdate) {
    	super();
    	this.no = no;
    	this.id = id;
    	this.pwd = pwd;
    	this.registdate = registdate;
    }
	
//--------Getter, Setter--------
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	 public String getRegistdate() {
	        return registdate;
	    }
	 
	    public void setRegistdate(String registdate) {
	        /*
	        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            registdate = textFormat.format(textFormat.parse(registdate));
	        } catch (ParseException e) {
	        }
	        */
	        this.registdate = registdate; 
	    }
	    
	    @Override
	    public String toString() {
	        Formatter fm = new Formatter();
	        String meminfo = fm.format("%5s\t  %-7s\t%-16s %-14s", no, id,pwd,registdate).toString();
	        return meminfo;
	    }
	    
    public String getInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("\r\n");
        sb.append("[ "+no+ " ] 정보====\n");
        sb.append("아이디 : "+id+"\n");
        sb.append("비밀번호 : "+pwd+"\n");
        sb.append("registdate : "+registdate+"\n");
               
        return sb.toString();
    }

//	public String getWin() {
//		return win;
//	}
//	public void setWin(String win) {
//		this.win = win;
//	}
//	public String getLose() {
//		return lose;
//	}
//	public void setLose(String lose) {
//		this.lose = lose;
//	}
}