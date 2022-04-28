package server;

import java.util.Arrays;
import java.util.Random;

public class TictactoeGameAI {
	int difficulty;
	int[] maps= new int[9];
	int turn=1;
	public TictactoeGameAI() {
		//디폴트 생성자인데 얘로 하면 gameWint(maps)만 사용가능
	}
	public TictactoeGameAI(String difficulty) {
		this.difficulty=Integer.parseInt(difficulty);
		Arrays.fill(maps, -1);
	}
	private int nextPosition() {
		int result=-1;
		Random random=new Random();
		int x=random.nextInt(9);
		while(maps[x]!=-1) {
			x=random.nextInt(9);
		}
		result=x;
		return result;
	}
	private int nextPosition(String log) {
		TictactoeJdbc j = new TictactoeJdbc();
		int result=j.npSelect(log);
		return result;
	}
	public int gameWin() {//-1이면 이긴사람없음, 0,1은 해당 플레이어가 이김
	      int result=-1;
	      if (maps[0]!=-1 && maps[0]==maps[1] && maps[1]==maps[2])
	         result= maps[0];
	      else if(maps[3]!=-1 && maps[3]==maps[4]&&maps[4]==maps[5])
	         result = maps[3];
	      else if (maps[6] != -1 &&maps[6]==maps[7]&& maps[7]==maps[8])
	         result = maps[6];
	      else if (maps[0] != -1 && maps[0]==maps[3] && maps[3]==maps[6])
	         result = maps[0];
	      else if (maps[1]!= -1 && maps[1]==maps[4]&& maps[4]==maps[7])
	         result = maps[1];
	      else if (maps[2]!= -1 && maps[2]==maps[5]&& maps[5]==maps[8])
	         result =maps[2];
	      else if (maps[0]!= -1 && maps[0]==maps[4]&&maps[4]==maps[8])
	         result = maps[0];
	      else if (maps[2]!=-1 && maps[2]==maps[4]&& maps[4]==maps[6])
	         result = maps[2];
	      return result;
   }
	public int gameWin(int[] maps) {//-1이면 이긴사람없음, 0,1은 해당 플레이어가 이김
	      int result=-1;
	      if (maps[0]!=-1 && maps[0]==maps[1] && maps[1]==maps[2])
	         result= maps[0];
	      else if(maps[3]!=-1 && maps[3]==maps[4]&&maps[4]==maps[5])
	         result = maps[3];
	      else if (maps[6] != -1 &&maps[6]==maps[7]&& maps[7]==maps[8])
	         result = maps[6];
	      else if (maps[0] != -1 && maps[0]==maps[3] && maps[3]==maps[6])
	         result = maps[0];
	      else if (maps[1]!= -1 && maps[1]==maps[4]&& maps[4]==maps[7])
	         result = maps[1];
	      else if (maps[2]!= -1 && maps[2]==maps[5]&& maps[5]==maps[8])
	         result =maps[2];
	      else if (maps[0]!= -1 && maps[0]==maps[4]&&maps[4]==maps[8])
	         result = maps[0];
	      else if (maps[2]!=-1 && maps[2]==maps[4]&& maps[4]==maps[6])
	         result = maps[2];
	      return result;
 }
	public void setPosition(String p) {
		maps[Integer.parseInt(p)]=0;
	}
	public String getPosition(String logs) {
		int x;
		if(difficulty==1) {
			x=nextPosition();
		}
		else {
			x=nextPosition(logs);
		}
		maps[x]=1;
		return Integer.toString(x);
	}
}
