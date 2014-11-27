package team017.framework;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

public class SoldierPlayer extends BasePlayer {

	public SoldierPlayer(RobotController rc) {
		super(rc);
	}

	public enum MicroState {
		SWARM, ATTACK, RETREAT, RUSH, BACKOFF, WAIT;
	}
	
	@Override
	public void run() throws GameActionException {

		// State machine for strategy
		if (flux > 5) {
			if (rc.roundsUntilAttackIdle() < 2) {
				radar.scan(false, true);
				RobotInfo enemy = radar.nearestEnemy;
				if (enemy != null) {
					if (rc.roundsUntilAttackIdle() == 0 && rc.canAttackSquare(enemy.location)) {
						rc.attackSquare(enemy.location, enemy.type.level);
					} else if (nav.moveReady()) {
						Direction enemyDir = loc.directionTo(enemy.location);
						if (dir != enemyDir && enemyDir != Direction.OMNI) {
							rc.setDirection(enemyDir);
						}
					}
				}
			}
		}

		if (flux > 10) {
			if (nav.moveReady()) {
				nav.moveRandom();
			}
		}

		myYield();
	}

}
