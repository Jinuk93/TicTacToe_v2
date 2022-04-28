package server;

import java.util.ArrayList;
import java.util.Arrays;

public class TictactoeDFS {
	 int maps[]= new int [9];
	ArrayList<String> allLog=new ArrayList<String>();
	TictactoeGameAI ai = new TictactoeGameAI();
	int turn=0;
	static String log="";
	public ArrayList<String> getLogs(){
		return allLog;
	}
	void dfs(int nodeIndex, int turn) {
		maps[nodeIndex]=turn%2;
		log+=Integer.toString(nodeIndex);
		int winner=-1;
		if(turn>3)
			winner=ai.gameWin(maps);
		if(winner==1) {
			allLog.add(log);
		}else if(winner==-1&&turn<8) {
			for(int i=0;i<9;i++) {
				if(maps[i]==-1) {
					dfs(i,turn+1);
				}
			}
			
		}else if(winner==-1) {
			allLog.add(log);
		}
		maps[nodeIndex]=-1;
		log=log.substring(0,log.length()-1);
	}
	public TictactoeDFS(){
		Arrays.fill(maps,-1);
		for (int i=0;i<9;i++) {
			dfs(i,0);
		}
	}
}
