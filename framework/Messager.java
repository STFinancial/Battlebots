package team017.framework;

import battlecode.common.GameActionException;
import battlecode.common.Message;
import battlecode.common.RobotController;

public class Messager {

	
	private BasePlayer bp;
	private RobotController rc;

	public Messager(BasePlayer bp){
		this.bp = bp;
		rc = bp.rc;
	}
	
	public void broadcast() throws GameActionException {
		Message m = new Message();
		// This shouldn't really go here, but for testing purposes it's fine

		switch (bp.type) {

		case ARCHON:
			// bp.rc.setIndicatorString(0, "Sending message: " + bp.radar.getNumEnemies() + ", " +
			// bp.radar.nearestEnemy.location.x + ", " + bp.radar.nearestEnemy.location.y);
			if (bp.radar.nearestEnemy != null) {
				m.ints = new int[] {Constants.MESSAGE_SECRET_ALL, bp.radar.nearestEnemy.location.x, bp.radar.nearestEnemy.location.y };
				rc.broadcast(m);
				rc.setIndicatorString(2, "Sent message on round : " + bp.round);
			}
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
