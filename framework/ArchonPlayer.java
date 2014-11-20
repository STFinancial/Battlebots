package team017.framework;

import battlecode.common.GameActionException;
import battlecode.common.GameObject;
import battlecode.common.RobotController;
import battlecode.common.RobotLevel;
//import battlecode.engine.instrumenter.lang.System;
import battlecode.common.RobotType;

public class ArchonPlayer extends BasePlayer {

	int ID;

	public ArchonPlayer(RobotController rc) {
		super(rc);

		map.senseAllTiles();
		// Set nav mode etc

		// Set strategy states

	}

	@Override
	public void run() throws GameActionException {

		// State machine for strategy

		// Scan shit
		if (nav.lastMove == (round - 1)) {
			// map.senseTileAfterMove(dir);
		}

		// Message info

		// Retreat behavior

		// Help Army target

		// Update Nav to target

		// Broadcast targeting info

		//
		if (nav.moveReady()) {
			if (flux > 180 && nav.canMove()) {
				rc.spawn(RobotType.SOLDIER);
			} else {
				nav.moveRandom();
			}
		}
		
		fluxer.manageFlux();
		
		myYield();
	}

}
