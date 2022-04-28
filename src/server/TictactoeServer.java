package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TictactoeServer {
	final static int PORT = 9000;
	ServerSocket serverSocket;
	Socket socket;
	List<Thread> list;

	public TictactoeServer() {
		list=new ArrayList<Thread>();
		System.out.println("서버가 시작되었습니다.");
	}
	public void connect() {
		try {
			serverSocket = new ServerSocket(PORT);//서버 소켓연결
			serverSocket.setReuseAddress(true);//N:1일때 포트를 자동으로 연결
			
			while(true) {
				socket=serverSocket.accept();//연결대기
				ServerSocketThread thread = new ServerSocketThread(this, socket);
				addClient(thread);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println("클라이언트가 퇴장하였습니다.");
		} 
	}
	private synchronized void addClient(ServerSocketThread thread) {
		list.add(thread);
		System.out.println("Client 1명 입장. 총 인원 :"+list.size());
	}
	public synchronized void removeClient(Thread thread) {
		list.remove(thread);
		System.out.println("Client 1명 퇴장. 총 인원 :"+list.size());
	}
	
}
