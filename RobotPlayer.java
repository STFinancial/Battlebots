package team017;

import team017.framework.ArchonPlayer;
import team017.framework.BasePlayer;
import team017.framework.SoldierPlayer;
import battlecode.common.*;
//import battlecode.engine.instrumenter.lang.System;

public class RobotPlayer {

	public static void run(RobotController rc) {
		
		BasePlayer bp = null;
		
		
		try {
			switch (rc.getType()) {
			case ARCHON:
				bp = new ArchonPlayer(rc);
				break;
			case SOLDIER:
				break;
			case SCOUT:
				break;
			case DISRUPTER:
				break;
			case SCORCHER:
				break;
			default:
				break;
			}
		} catch (Exception e) {
			
		}
		
		while (true) {
			try {
				bp.loop();
			} catch (Exception e) {
				//System.out.println("Main loop terminated unexpectedly");
				e.printStackTrace();
			}
		}
	}
	
	public void debug(String string) {
		System.out.println(string); //Illegal class load
	}
}
