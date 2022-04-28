package server;

public class TictactoeServerMain {
	public static void main(String[] args) {
		TictactoeWins w = new TictactoeWins();
		w.setAll();

		TictactoeServer server=new TictactoeServer();
		server.connect();
	}
}
