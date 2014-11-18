package team017.framework;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotLevel;
import battlecode.engine.instrumenter.lang.System;

public class NavGeneral {

	MapLocation myLoc;
	MapLocation base;
	MapLocation target;
	RobotController rc;
	Direction myDir;
	
	
	public NavGeneral(RobotController rc) {
		this.rc = rc;
		myLoc = rc.getLocation();
	}

	public boolean canMove(MapLocation loc){
		if(rc.isMovementActive())
			return false;
		if(rc.getFlux() < rc.getType().moveCost)
			return false;
		if(myLoc.distanceSquaredTo(loc) > 2)
			return false;
		if(myDir != myLoc.directionTo(loc))
			return false;
		return(rc.canMove(myDir));
	}
	
	
	public void moveTowards(MapLocation loc){
		Direction newDir = myLoc.directionTo(loc);
		MapLocation nextTile = myLoc.add(newDir);
		try {
			if(canMove(nextTile)){
				rc.moveForward();
				myLoc = nextTile;
			}else{
				if(newDir != myDir){
					rc.setDirection(newDir);
					myDir = newDir;
				}
			}
		} catch (GameActionException e) {
		}
	}
	
	public void moveAwayFrom(MapLocation loc){
		Direction newDir = myLoc.directionTo(loc);
		MapLocation nextTile = myLoc.subtract(newDir);
		try {
			if(canMove(nextTile)){
				rc.moveForward();
				myLoc = nextTile;
			}else{
				if(newDir != myDir){
					//System.out.println(" trying to turn to " + newDir);
					rc.setDirection(newDir);
					myDir = newDir;
				}
			}
		} catch (GameActionException e) {
		}
	}
}
