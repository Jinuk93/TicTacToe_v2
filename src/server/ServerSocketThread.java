package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ServerSocketThread extends Thread {
	Socket socket;
	TictactoeServer server;
	BufferedReader in;
	PrintWriter out;
	String name;
	String difficulty;
	
	public ServerSocketThread(TictactoeServer server,Socket socket) {
		this.server=server;
		this.socket=socket;
		System.out.println(socket.getInetAddress() + "님이 입장했습니다.");

	}
	public static String[] parsePacket(String line) {
		String[] params = line.split("\\|");
		return params;
	}
	public String ticTacToe() {
		System.out.println("서버 틱택토 스타트!");
		String logs="";
		try {
			int position;
			int[] maps= new int[9];
			int turn=0;
			Arrays.fill(maps, -1);
			difficulty=in.readLine();
			TictactoeGameAI bot=new TictactoeGameAI(difficulty);
			System.out.println("난이도 설정완료!");
			int winner=-1;
			while(true) {
				String p=in.readLine();
				bot.setPosition(p);
				logs+=p;
				winner=bot.gameWin();
				//System.out.println("turn : "+turn);
				if(winner!=-1) {
					String send=String.format("Win|%d",winner);
					out.println(send);
					break;
				}else if(turn==8) {
					out.println("Draw");
					break;
				}else {
					out.println("Next");
				}
				turn++;
				String bp=bot.getPosition(logs);
				logs+=bp;
				winner=bot.gameWin();
				//System.out.println("turn : "+turn);
				if(winner!=-1) {
					String send=String.format("Win|%d|%s",winner,bp);
					out.println(send);
					break;
				}else {
					String send="Next|"+bp;
					out.println(send);
				}
				turn++;
				
			}
		} catch (IOException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}
		return logs;
	}
	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			System.out.println("스레드가 시작 되었습니다.");
			while(true) {
				String line=in.readLine();
				String[] params=parsePacket(line);
				String gameLogs="";
				System.out.println(params[0]);
				if(params[0].equals("Game")) {
					gameLogs=ticTacToe();	
				}else if (params[0].equals("Load")) {
					//JDBC로 params[1]에 들어간 uid를 읽어들여 시간 역순으로 리플레이 파일 불러오기.
					//이후 불러온 파일이 선택되면 해당파일 로그 보내기.
				}else if(params[0].equals("Save")) {
					//저장처리
				}else if(params[0].equals("Sign")) {
					//아이디 관련 처리
				}
				else{
					break;
				}
			}
			
		}catch(IOException e) {
			System.out.println(socket.getInetAddress() +"퇴장했습니다");
			server.removeClient(this);
		}finally {
			try {
				socket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
