package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TictactoeClient {
	int difficulty=1;
	String np;
	TictactoeClientSocket socket;
	BufferedReader keyboard;
	public TictactoeClient() {
		socket=new TictactoeClientSocket();
		keyboard = new BufferedReader(new InputStreamReader(System.in));
	}
	public void replayTictactoe(String logs) {
		 char[] cmap= {'0','|','1','|','2','\n','3','|','4','|','5','\n','6','|','7','|','8'};
		 char[] log=logs.toCharArray();
	  	 System.out.println("-------------------");
		 System.out.println(cmap);
		 for(int i=0;i<logs.length();i++) {
		   int p=log[i]-'0';
		   cmap[2*p]=(i%2==0)?'X':'O';
		   System.out.printf("-- turn: %d --\n",i+1);
		   System.out.println(cmap);
		   System.out.println("-------------");
	   }
	  	 System.out.println("-------------------");
		 System.out.println("\n    Replay End!\n");
	  	 System.out.println("-------------------");
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		socket.sendPacket(Integer.toString(difficulty));
		System.out.println("난이도 설정 완료 :"+difficulty);
	}
	private void setPosition(int p) {
		np=Integer.toString(p);
		socket.sendPacket(np);
	}
	private String getPacket() {
		String packet=socket.recivePacket();
		return packet;
	}
	private void printGame(int[] maps) {
		char[] cmap= {'0','|','1','|','2','\n','3','|','4','|','5','\n','6','|','7','|','8'};
		for(int i=0;i<9;i++) {
			   if(maps[i]!=-1)
			   cmap[2*i]=(maps[i]==0)?'X':'O';
		}
		System.out.println(cmap);
	}
	public void ticTacToe() {
		socket.sendPacket("Game");
		String logs="";
		int position;
		int[] maps= new int[9];
		int turn=0;
		Arrays.fill(maps, -1);
		System.out.println("--------난이도--------");
		System.out.println("1. 쉬움");
		System.out.println("2. 어려움");
		System.out.println("--------------------");
		System.out.print("input>> ");
		int menu=-1;
		String me;
		try {
			while(true) {
		        me = keyboard.readLine();
		        System.out.println('\n');
		        try {
		           menu=Integer.parseInt(me);
		        }catch(NumberFormatException e) {
		           System.out.println("제발 숫자좀 넣어주십쇼.....");
		           continue;
		        }
		        if(menu>2 ||menu<1) {
		           System.out.println("아니 1,2 둘중하나만 넣어달라구요...");
		           continue;
		        }
		        setDifficulty(menu);
		        break;
			}
		}catch(IOException e) {
			System.out.println("난이도 입출력 오류임다.");
		}
		//System.out.println("난이도 설정 종료");
		System.out.println("-------Game Start------");
		printGame(maps);
		while(true) {
			System.out.println("-------------------");
			System.out.println("▶ Player X's Turn");
			System.out.println("-------------------");
			System.out.print("Input the Position >> ");
			
			String pos;
			try {
				pos = keyboard.readLine();
				int num=-1;
	            try {
	               num = Integer.parseInt(pos);
	            }catch(NumberFormatException e) {
	               System.out.println("Please enter a number. Not Character");
	               continue;
	            }
	            if (num<0|| num>8) {
	               System.out.println("Please enter a number between 0 and 8..");
	               continue;
	            }
	            if (maps[num]!=-1) {
	               System.out.println("It's not an empty space.");
	               continue;
	            }
	            System.out.println("-------------------");
	            maps[num]=0;
	            logs+=pos;
	            setPosition(num);
	            printGame(maps);
	            //여기까지 X(player 0)의 턴 종료
	            //여기부터 X의 승패판정
	            String packet=getPacket();
	            String[] params=socket.parsePacket(packet);
	            //System.out.println(params[0]);
	    		if(params[0].equals("Win")) {
	    			//누가 이겼는지에 대한 화면을 띄우는 페이지가 켜지게 한다.
	    			//혹은 그런신호를 보내준다.
	    			//==gameover페이지가 열리게 만들어 준다.
	    			//승자는 params[1]
	    			System.out.println("-------------------");
	    			System.out.printf("☆Player %c's Win!☆\n",(params[1].equals("0"))?'X':'O');
	    			System.out.println("-------------------");
	    			break;
	    		}else if(params[0].equals("Draw")){
	    			System.out.println("-------------------");
	    			System.out.println("        Draw!");
	    			System.out.println("-------------------");
	    			break;
	    		}else{
	    			System.out.println("-------------------");
	    			System.out.println("▶ Player O's turn");
	    			System.out.println("-------------------");
	    			packet=getPacket();
	    			String[] params2=socket.parsePacket(packet);
	    			if(params2[0].equals("Win")) {
	    				logs+=params2[2];
	    				maps[Integer.parseInt(params2[2])]=1;
	    				printGame(maps);
	    				System.out.println("-------------------");
		    			System.out.printf("☆Player %c's Win!☆\n",(params2[1].equals("0"))?'X':'O');
		    			System.out.println("-------------------");
		    			break;
	    			}else {
	    				logs+=params2[1];
	    				maps[Integer.parseInt(params2[1])]=1;
	    				printGame(maps);
	    			}
	    			
	    			
	    		}
	            } catch (IOException e1) {
				// TODO 자동 생성된 catch 블록
				e1.printStackTrace();
			}
		}
	}
	
}
