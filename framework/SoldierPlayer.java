package team017.framework;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.Message;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

public class SoldierPlayer extends BasePlayer {

	private RobotInfo enemy;

	public SoldierPlayer(RobotController rc) {
		super(rc);
	}

	public enum MicroState {
		SWARM, ATTACK, RETREAT, RUSH, BACKOFF, WAIT;
	}

	@Override
	public void run() throws GameActionException {
		Message[] ms = rc.getAllMessages();
		for(int i = 0; i < ms.length; i++){
			if (ms[i].ints != null){
				if (ms[i].ints[0] == Constants.MESSAGE_SECRET_ALL) {
					nav.bugSetTarget(new MapLocation(ms[i].ints[1], ms[i].ints[2]));
					rc.addMatchObservation("Got a new message for me"); // not sure how to read this yet
					rc.setIndicatorString(2, "Last message on round: " + round);
				}
			}
		}
		// State machine for strategy
		if (flux > 1) {
			if (rc.roundsUntilAttackIdle() < 2) {
				radar.scan(false, true);
				enemy = radar.nearestEnemy;
			}

			if (enemy != null) {
				if (rc.roundsUntilAttackIdle() == 0 && rc.canAttackSquare(enemy.location)) {
					rc.attackSquare(enemy.location, enemy.type.level);
					nav.bugSetTarget(null);
				} else {
					nav.bugSetTarget(enemy.location);
				}
			}

			if (nav.moveReady()) {
				nav.bugMove();
			}

		}

		myYield();
	}

}
