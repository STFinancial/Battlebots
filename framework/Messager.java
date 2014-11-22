package team017.framework;

import battlecode.common.GameActionException;
import battlecode.common.Message;

public class Messager {
	
	
	public void broadcast(BasePlayer bp) throws GameActionException {
		Message m = new Message();
		//This shouldn't really go here, but for testing purposes it's fine
		
		switch (bp.type) {
		
		case ARCHON:
			m.ints = new int[]{bp.radar.getNumEnemies()};
			bp.rc.broadcast(m);
			break;
		case SOLDIER:
			
			break;
		case DISRUPTER:
			
			break;
		case SCOUT:
			
			break;
		case SCORCHER:
			
			break;
		default:
			
		}
	}
}
