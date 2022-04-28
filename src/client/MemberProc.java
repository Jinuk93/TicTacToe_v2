package client;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import TicTacToe_sign.MemberDAO;
import TicTacToe_sign.MemberDTO;
 
 
public class MemberProc {
   
    MemberDAO dao;
   
    //기본생성자
    public MemberProc() {
        dao = new MemberDAO();
 
    }
   
    //유저등록
    public void insertMember(){        
       
        Scanner scn = new Scanner(System.in);
       
        System.out.println("유저정보를 입력해주세요.");
        System.out.print("▶아이디 : ");
        //String name = scn.nextLine();
        String id = reInput(scn);
        System.out.print("▶비밀번호 : ");
        //String ssn = scn.nextLine();
        String pwd = reInput(scn);
        //String phoneNum = scn.nextLine();    
       
        MemberDTO dto = new MemberDTO(id, pwd);    
        boolean r = dao.insertMember(dto); //입력받은 데이터 추가
       
        if(r){
            System.out.println("유저등록이 정상적으로 완료되었습니다.");
        }else{
            System.out.println("유저등록이 정상적으로 이루지지 않았습니다.");
        }    
    }  
    
    // 로그인 
    public void loginMember() {
    	Scanner scn = new Scanner(System.in);
    	System.out.print("▶아이디 : ");
        String id = reInput(scn);
        System.out.print("▶비밀번호 : ");
        String pwd = reInput(scn);
        String name = dao.loginMember(id, pwd);
		boolean check = false;
		if (name != null) {
			check = true;
		}
		if (id.length() == 0 || pwd.length() == 0) {
			System.out.println("유저등록이 정상적으로 이루지지 않았습니다.");
			return;
		} else if (name == null) {
			System.out.println( "아이디 혹은 비밀번호가 틀렸습니다.");
			return;
		} else if(check){
			System.out.println("로그인 성공!!");
		}
    }
   
    // 저장된 유저목록 보기
    public void showMemberList(){
   
        List<MemberDTO> list = dao.getMemberList();
       
        System.out.println("                             Member List");
        System.out.println("============================================================================");
        if(list!=null&&list.size()>0){         
            System.out.println("reg.No\t  아이디 \t\t비밀번호\t\t등록일");
            System.out.println("============================================================================");
           
            for (MemberDTO dto : list){
                System.out.println(dto);
            }          
           
        }else{
            System.out.println("저장된 데이터가 없습니다. ");
        }
        System.out.println("====================================================================총 "+((list==null)?"0":list.size())+" 명=\n");
    }
   
    //유저 수정
    public void updateMember(){
       
        Scanner scn = new Scanner(System.in);
        System.out.println("수정할 유저의 유저등록번호를 입력해주세요");
        System.out.print("▶");
        String no = scn.nextLine();
        MemberDTO dto = dao.getMember(no);
        if (dto!=null) {
           
            System.out.println(dto.getInfo());
           
            System.out.println("수정작업을 계속하시겠습니까?(Y/N)");
            String input = scn.nextLine();
            if(input.equalsIgnoreCase("y")){
                System.out.println("##입력을 하시지않으면 기존의 정보가 그대로 유지됩니다.");
                System.out.print("▶수정할 아이디 : ");
                String id = scn.nextLine();
                if(id.trim().equals("")) id=dto.getId();
                System.out.print("▶수정할 비밀번호 : ");
                String pwd = scn.nextLine();
                if(pwd.trim().equals("")) pwd=dto.getPwd();
                dto =  new MemberDTO(no, id, pwd,dto.getRegistdate());
               
                boolean r = dao.updateMember(dto);
               
                if(r){
                    System.out.println("유저의 정보가 다음과 같이 수정되었습니다.");
                    System.out.println(dto.getInfo());
                }else{
                    System.out.println("유저의 정보가 정상적으로 수정 되지 않았습니다.");
                }   
            }else{
                System.out.println("수정 작업을 취소하였습니다.");
            } 
        }else{
            System.out.println("입력하신 유저등록번호에 해당하는 유저가 존재하지 않습니다.");   
        }
    }
   
    //유저 삭제
    public void deleteMember(){
       
        Scanner scn = new Scanner(System.in);
        System.out.println("삭제할 유저의 유저등록번호를 입력해주세요");
        String no = scn.nextLine();
        MemberDTO dto = dao.getMember(no);
        if (dto!=null) {
            System.out.println(dto.getInfo());
           
            System.out.println("위 유저의 정보를 정말로 삭제하시겠습니까?(Y/N)");
            String input = scn.nextLine();
            if(input.equalsIgnoreCase("y")){
                boolean r = dao.deleteMember(no);
               
                if(r){
                    System.out.println(no+"유저의 정보가 정상적으로 삭제되었습니다.");
                }else{
                    System.out.println("유저의 정보가 정상적으로 삭제 되지 않았습니다.");
                }
            }else{
                System.out.println("삭제 작업을 취소하였습니다.");
            }
        }else{
           
            System.out.println("입력하신 유저등록번호에 해당하는 유저가 존재하지 않습니다."); 
        }
    }
    
    
    // 공백 입력시, 재입력 테스트 함수
    public String reInput(Scanner scn){
       
        String str="";
        while(true){
            str = scn.nextLine();
            if(!(str==null || str.trim().equals(""))){
                break;
            }else{
                System.out.println("공백은 입력하실수없습니다. 올바른값을 입력해주세요!");
                System.out.print("▶");
            }
        } 
        return str;
    }
}