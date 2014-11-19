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
	int tripTime;
	int expectedTripTime;
	int randCounter;
	NavStates state;
	
	
	
	public NavGeneral(BasePlayer basePlayer) {
		this.bp = basePlayer;
		rc = bp.rc;
		//Create new subsystems for pathing algos
	}

	//returns ideal direction to move for target
	public Direction getIdealTargetDirection(){
		
		
		return null;
	}
	
	//returns usable direction to move to target (Say if unit is in ideal direction)
	public Direction getUsableTargetDirection(){
		
		return null;
	}
	
	public void moveRandom() throws GameActionException {
		if (rc.roundsUntilMovementIdle() == 0) {
			if (rc.canMove(bp.dir))	{
				rc.moveForward();
			} else {
				rc.setDirection(Constants.directions[Utility.nextInt(8)]);
			}
		}
	}
	
}
