package team017;

import team017.framework.ArchonPlayer;
import team017.framework.SoldierPlayer;
import battlecode.common.*;
import battlecode.engine.instrumenter.lang.System;

public class RobotPlayer {

	public static void run(RobotController rc) {
		switch(rc.getType()){
		case ARCHON:
			System.out.println("New Archon!"); //perfectly fine
			(new ArchonPlayer(rc)).run();
			break;
		case DISRUPTER:
			break;
		case SCORCHER:
			break;
		case SCOUT:
			break;
		case SOLDIER:
			new SoldierPlayer(rc);
			break;
		case TOWER:
			while(true){}
		default:
			break;

		}
	}
	
	public void debug(String string){
		System.out.println(string); //Illegal class load
	}
}
