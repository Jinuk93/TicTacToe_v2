package client;

import java.util.InputMismatchException;
import java.util.Scanner;
 
 
public class MemberManagement {
   
    public static void main(String[] args) {
       
        MemberProc mm = new MemberProc(); //MemberProc객체 생성
           
        while (true) {
            System.out.println();
            System.out.println("============== TicTacToe Menu ==============");
            System.out.println("1. 로그인  2.회원가입 3. 회원조회");         
            System.out.println("4. 회원삭제   5. 유저정보 수정 6. 나가기 ");
            System.out.println("============== &&&&&&&&&&&&&&& ==============");
            System.out.print("메뉴를 입력하세요 : ");
           
            Scanner scn = new Scanner(System.in);
            int num=0;
            try {
                num = scn.nextInt();
                if(!(num>0 && num<7)){ //1~6외의 숫자가 입력되면 예외 강제 발생
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("입력된 값이 잘못되었습니다. [1-6] 메뉴늘 선택해주세요!");
            }
            switch (num) {
            case 1:
                mm.loginMember();//로그인         
                break;
            case 2:
                mm.insertMember(); //회원가입
                break;
            case 3:
                mm.showMemberList();//회원조회        
                break;
            case 4:
                mm.deleteMember(); //회원삭제            
                break;
            case 5:
                mm.updateMember(); //회원 정보수정
                break;
//            case 5:
//            	mm.loginMember(); // 유저 로그인
//            	break;
               
            case 6:
                System.out.println("프로그램을 종료합니다.");
                System.exit(0); //프로그램 종료
                   
            }//end switch()---------------
        }//while---------------------      
    }//main()--------------
}//class MemberManagement