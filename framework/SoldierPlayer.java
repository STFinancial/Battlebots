package team017.framework;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class SoldierPlayer extends BasePlayer {

	public SoldierPlayer(RobotController rc) {
		super(rc);
	}

	@Override
	public void run() throws GameActionException {

		// State machine for strategy

		if (flux > 10) {
			if(nav.moveReady())
				nav.moveRandom();
		}

		myYield();
	}

}
