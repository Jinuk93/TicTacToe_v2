package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TictactoeClientSocket {
	final static String IP="127.0.0.1";
	final static int PORT = 9000;
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	public static String[] parsePacket(String line) {
	      String[] params = line.split("\\|");
	      return params;
	   }
	public TictactoeClientSocket(){
		try {
			socket = new Socket(IP,PORT);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		} catch (IOException e) {
			System.out.println("서버 연결 실패");
			e.printStackTrace();
		}
	
	}
	public void sendPacket(String packet) {
		out.println(packet);
	}
	public String recivePacket() {
		String result="";
		try {
			result = in.readLine();
			
		} catch (IOException e) {
			result="E";
			e.printStackTrace();
		}
		return result;
	}
	public void remove() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("클라이언트 종료 함수에서 오류가 생겼습니다.");
			e.printStackTrace();
		}
		
	}
}
