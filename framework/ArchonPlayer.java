package team017.framework;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
//import battlecode.engine.instrumenter.lang.System;

public class ArchonPlayer extends BasePlayer {

		int ID;
	
		public ArchonPlayer(RobotController rc) {
			super(rc);
			
			map.senseAllTiles();
			rc.breakpoint();
			//Set nav mode etc
			
			//Set strategy states
			
			
			
		}
		
		@Override
		public void run() throws GameActionException {

			//State machine for strategy
			
			//Scan shit
			if (nav.lastMove == (round - 1)) {
				map.senseTileAfterMove(dir);
			}
			
			//Message info
			
			//Retreat behavior
			
			//Help Army target
			
			//Update Nav to target
			
			//Broadcast targeting info
			
			//
			nav.moveRandom();
			myYield();
		}

	
}
