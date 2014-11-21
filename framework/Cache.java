package team017.framework;

import battlecode.common.*;

public class Cache {

	/*
	 * Wrapper for the following RC calls so that they never get called more
	 * than once a turn rc.senseAlliedArchons() -> MapLocation[]
	 * getAlliedArchons() rc.senseCapturablePowerNodes() -> MapLocation[]
	 * getCapturablePowerCores rc.senseAlliedPowerNodes() -> PowerNode[]
	 * getAlliedPowerNodes() rc.canMove(direction) ->
	 */

	private BasePlayer bp;
	private RobotController rc;

	private MapLocation[] alliedArchons;
	private int alliedArchonsTime = -1;

	private MapLocation closestArchon;
	private int closestArchonTime = -1;

	private int moveableDirectionsTime = -1;
	private boolean[] moveableDirections = new boolean[8];

	private boolean[] isAdjacentGameObjectGroundCached;
	private boolean[] isAdjacentGameObjectAirCached;
	private GameObject[] adjacentGameObjectsGround = new GameObject[Direction.values().length];
	private GameObject[] adjacentGameObjectsAir = new GameObject[Direction.values().length];
	private int adjacentGameObjectsTime = -1;

	private MapLocation[] capturablePowerCores;
	private int capturablePowerCoresTime = -1;

	private PowerNode[] alliedPowerNodes;
	private int alliedPowerNodesTime = -1;

	public Cache(BasePlayer bp) {
		this.bp = bp;
		rc = bp.rc;
	}

	public MapLocation[] getAlliedArchons() {
		if (bp.round > alliedArchonsTime) {
			alliedArchons = rc.senseAlliedArchons();
			alliedArchonsTime = bp.round;
		}
		return alliedArchons;
	}

	public MapLocation getClosestArchon() {
		if (bp.round > closestArchonTime) {
			closestArchon = null;
			int closestDistance = Integer.MAX_VALUE;
			for (MapLocation archon : getAlliedArchons()) {
				int distance = bp.loc.distanceSquaredTo(archon);
				// Don't count yourself as an archon
				if (!(bp.type == RobotType.ARCHON && distance == 0) && distance < closestDistance) {
					closestArchon = archon;
					closestDistance = distance;
				}
			}
			if (closestArchon == null)
				closestArchon = bp.home;
			closestArchonTime = bp.round;
		}
		return closestArchon;
	}

	/**
	 * Calls rc.CanMove for each direction Tells if direction is unblocked by
	 * terrain or another robot
	 */
	public boolean[] getMovableDirections() {
		if (bp.round > moveableDirectionsTime) {
			for (int i = 0; i < 8; i++)
				moveableDirections[i] = rc.canMove(Constants.directions[i]);
			moveableDirectionsTime = bp.round;
		}
		return moveableDirections;
	}

	public GameObject getAdjacentGameObject(Direction d, RobotLevel level) throws GameActionException {
		if (bp.round > adjacentGameObjectsTime) {
			isAdjacentGameObjectGroundCached = new boolean[Direction.values().length];
			isAdjacentGameObjectAirCached = new boolean[Direction.values().length];
			adjacentGameObjectsTime = bp.round;
		}
		switch (level) {
		case IN_AIR:
			if (isAdjacentGameObjectAirCached[d.ordinal()]) {
				return adjacentGameObjectsAir[d.ordinal()];
			} else {
				GameObject obj = rc.senseObjectAtLocation(rc.getLocation().add(d), level);
				adjacentGameObjectsAir[d.ordinal()] = obj;
				isAdjacentGameObjectAirCached[d.ordinal()] = true;
				return obj;
			}
		case ON_GROUND:
			if (isAdjacentGameObjectGroundCached[d.ordinal()]) {
				return adjacentGameObjectsGround[d.ordinal()];
			} else {
				GameObject obj = rc.senseObjectAtLocation(rc.getLocation().add(d), level);
				adjacentGameObjectsGround[d.ordinal()] = obj;
				isAdjacentGameObjectGroundCached[d.ordinal()] = true;
				return obj;
			}
		default:
			return null;
		}
	}

	public MapLocation[] getCapturablePowerCores() {
		if (bp.round > capturablePowerCoresTime) {
			capturablePowerCores = null;
			capturablePowerCoresTime = bp.round;
		}
		if (capturablePowerCores == null) {
			capturablePowerCores = rc.senseCapturablePowerNodes();
		}
		return capturablePowerCores;
	}

	public PowerNode[] getAlliedPowerNodes() {
		if (bp.round > alliedPowerNodesTime) {
			alliedPowerNodes = null;
			alliedPowerNodesTime = bp.round;
		}
		if (alliedPowerNodes == null) {
			alliedPowerNodes = rc.senseAlliedPowerNodes();
		}
		return alliedPowerNodes;
	}
}
