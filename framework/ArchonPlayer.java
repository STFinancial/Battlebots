package team017.framework;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.GameObject;
import battlecode.common.RobotController;
import battlecode.common.RobotLevel;
//import battlecode.engine.instrumenter.lang.System;
import battlecode.common.RobotType;

public class ArchonPlayer extends BasePlayer {

	int ID;

	public ArchonPlayer(RobotController rc) {
		super(rc);

		try {
			nav.moveRandom();
		} catch (GameActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map.senseAllTiles(); // Cost a shit ton, skips first turn
	}

	@Override
	public void run() throws GameActionException {
		if (round < Constants.TURTLE_TILL) {
			if (loc.distanceSquaredTo(cache.getClosestArchon()) < GameConstants.PRODUCTION_PENALTY_R2) {
				rc.setIndicatorString(0, "Distance to nearest Archon: " + loc.distanceSquaredTo(cache.getClosestArchon()) + " should be "
						+ GameConstants.PRODUCTION_PENALTY_R2);
				if (nav.moveReady()) {
					nav.moveRandom();
				}
			} else {
				if (nav.moveReady()) {
					if (!nav.canMove()) {
						nav.moveRandom();
					} else if (flux > 120) {
						rc.spawn(RobotType.SOLDIER);
					}
				}
			}

			radar.scan(true, true);
			fluxer.manageFlux();

		} else {
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
			if (round % 2 == 0)
				radar.scan(true, true);

			if (nav.moveReady()) {
				if (flux > 140 && nav.canMove()) {
					rc.spawn(RobotType.SOLDIER);
				} else {
					nav.moveRandom();
				}
			}

			fluxer.manageFlux();

		}

		myYield();
	}

}
