package team017.framework;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotLevel;
import battlecode.engine.instrumenter.lang.System;

public class NavGeneral {

	RobotController rc;
	BasePlayer bp;
	MapLocation target;
	Direction targetDir;
	int tripTime;
	int expectedTripTime;
	int randCounter;
	NavStates state;
	int lastMove;

	NavBug bug;
	public NavGeneral(BasePlayer bp) {
		this.bp = bp;
		rc = bp.rc;
		// Create new subsystems for pathing algos
		bug = new NavBug(bp);
	}

	public boolean moveReady() {
		return rc.roundsUntilMovementIdle() == 0;
	}

	// Eventually we would prefer to check our Map/Radar for wall/robots
	public boolean canMove() {
		return bp.cache.canMove(bp.dir);
	}

	public void moveRandom() throws GameActionException {
		if (canMove()) {
			rc.moveForward();
			lastMove = bp.round;
		} else {
			rc.setDirection(Constants.directions[Utility.nextInt(8)]);
		}
	}

	/*
	 * Computes movement from Bug algorithm, assumes move is ready when called
	 */
	public void bugMove() throws GameActionException {
			targetDir = bug.computeMove();
			if(targetDir == Direction.NONE)
				return;
			if(bp.dir == targetDir){
				if(canMove()){
					rc.moveForward();
					lastMove = bp.round;
				}else{
					//Add some timer to try other ways if I can't move after a set number of turns
				}
			}else{
				rc.setDirection(targetDir);
				lastMove = bp.round;
			}
	}
	/*
	 * Tells if bug is currently tracking a target
	 */
	public boolean bugHasTarget(){
		return bug.hasTarget();
	}
	
	/*
	 * Sets a target for the bug algorithim
	 */
	public void bugSetTarget(MapLocation tar){
		bug.newTarget(tar);
	}
}
