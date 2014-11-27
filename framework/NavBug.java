package team017.framework;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class NavBug {

	private BasePlayer bp;
	private RobotController rc;

	private enum WallState {
		LEFT, NONE, RIGHT;
		public WallState flip() {
			switch (this) {
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			default:
				return NONE;
			}
		}

	}

	public MapLocation target = null;
	public MapLocation nextStop;

	// If we are currently following a wall or not
	private WallState wallState;
	// If we already tried going the otherway
	boolean secondWall;
	// Distance from target when we hit wall
	int wallStartingDistance;
	// Direction we try first on wall hit
	WallState startingWallState = WallState.LEFT;
	// Direction to wall we are currently following
	Direction wallDirection;

	public NavBug(BasePlayer basePlayer) {
		this.bp = basePlayer;
		rc = bp.rc;
		reset();
	}

	public void newTarget(MapLocation target) {
		this.target = target;
		reset();
	}

	private void reset() {
		wallState = WallState.NONE;
		secondWall = false;
	}

	public boolean hasTarget(){
		return target != null;
	}
	/*
	 * Trys to grab next move from simple Bug Algorithm Returns ideal move direction
	 * Currently returns Direction.NONE if no move is found/at target (could null instead?)
	 */
	public Direction computeMove() {
		if (target == null) {
			return Direction.NONE;
		}		
		
		rc.setIndicatorString(0, "dx: " + (target.x - bp.loc.x) + " dy: " + (target.y - bp.loc.y) + " Tracking: " + wallState);
		if(wallState != WallState.NONE)
			rc.setIndicatorString(0, "dx: " + (target.x - bp.loc.x) + " dy: " + (target.y - bp.loc.y) + " Tracking: " + wallState + " wallDirection: " + wallDirection + " Next stop: " + (nextStop.x - bp.loc.x) + " , " + (nextStop.y - bp.loc.y) );
		if (bp.loc.x == target.x && bp.loc.y == target.y) {
			//We made it!
			target = null;
			return Direction.NONE;
		}
		if (Math.abs(bp.loc.x - target.x) <= 1 && Math.abs(bp.loc.y - target.y) <= 1) {
			return bp.loc.directionTo(target);
		}

		int distance = bp.loc.distanceSquaredTo(target);
		Direction dir = bp.loc.directionTo(target);
		if (wallState != WallState.NONE) {
			// Currently following a Wall
			if (distance < wallStartingDistance) {
				// We are closer after hugging the wall
				reset();
			} else if (bp.loc.x != nextStop.x || bp.loc.y != nextStop.y) {
				// We have not yet made it to our next stop
				dir = bp.loc.directionTo(nextStop);
				if (bp.cache.canMove(dir)) {
					return dir;
				} else {
					wallDirection = dir;
				}
			} else if (bp.cache.canMove(wallDirection)) {
				// Perhaps our wall was a unit and that unit has left
				reset();
			} else if (!secondWall) {
				// Hit the fan on our first try, haven't done a second try the opposite way yet
				// Need to figure out what validates "hitting the fan"
			}
		}

		if (wallState == WallState.NONE) {
			// Not following a Wall
			if (bp.cache.canMove(dir))
				return dir;
			// Something is in the way
			wallStartingDistance = distance;
			wallState = startingWallState;
			wallDirection = dir;
		}

		
		for (int i = -1; i < 6; i++) {
			
			//Direction ordinal increasing by 1 is turning 45 degrees right
			int tracking = -1;
			if (wallState == WallState.RIGHT) {
				tracking = 1;
			}
			
			//Start at wall direction minus 45, scan by 45 degrees
			dir = Constants.directions[(tracking * i + wallDirection.ordinal() + 8) % 8];
			if (bp.cache.canMove(dir)) {
				if (wallState == WallState.RIGHT) {
					wallDirection = dir.rotateLeft();
				} else {
					wallDirection = dir.rotateRight();
				}
				nextStop = bp.loc.add(dir);
				return dir;
			}
		}

		return Direction.NONE;

	}

}
