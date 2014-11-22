package team017.framework;

import battlecode.common.GameActionException;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

public class Radar {

	/*
	 * Works along side the MAP class to store battlefied information However Radar focuses on
	 * Moving Units both allied and enemy Counts Numbers of each side, aids in Fight vs Retreat
	 * calculations
	 */
	private static final int MAX_ROBOT_ID = 4096; // no clue what it actually is

	private BasePlayer bp;
	private RobotController rc;

	public Robot[] robots;
	int lastScanRound = -1;
	boolean allyScanNeeded, enemyScanNeeded;

	// Ally data
	private RobotInfo[] allyInfoById = new RobotInfo[MAX_ROBOT_ID];
	private int[] allyInfoScanTime = new int[MAX_ROBOT_ID];

	private int allyNum;
	public int allyAdjNum;
	public RobotInfo[] allyAdj = new RobotInfo[17]; // 8 ground spots and 9 air spots

	// Enemy data
	private RobotInfo[] enemyInfoById = new RobotInfo[MAX_ROBOT_ID];
	private int[] enemyInfoScanTime = new int[MAX_ROBOT_ID];
	private int[] enemyArchonId = new int[6];

	private int enemyNum;
	private int enemyArchonNum;

	public RobotInfo nearestEnemy;
	public int nearestEnemyR2;

	public Radar(BasePlayer bp) {
		this.bp = bp;
		rc = bp.rc;

		resetAllyData();
		resetEnemyData();

	}

	public void scan(boolean ally, boolean enemy) {

		// Protection against calling scan twice in one round
		// Allows to specify enemy, ally or both on scan
		if (lastScanRound < bp.round) {
			allyScanNeeded = true;
			enemyScanNeeded = true;
			lastScanRound = bp.round;
			robots = rc.senseNearbyGameObjects(Robot.class);
		}

		if (ally) {
			if (allyScanNeeded) {
				allyScanNeeded = false;
				resetAllyData();
			} else {
				ally = false;
			}
		}

		if (enemy) {
			if (enemyScanNeeded) {
				enemyScanNeeded = false;
				resetEnemyData();
			} else {
				enemy = false;
			}
		}

		if (ally || enemy) {
			for (int i = 0; i < robots.length; i++) {
				Robot r = robots[i];
				try {
					if (bp.team == r.getTeam()) {
						if (ally) {
							addAlly(rc.senseRobotInfo(r));
						}
					} else {
						if (enemy) {
							addEnemy(rc.senseRobotInfo(r));
						}
					}

				} catch (GameActionException e) {
					rc.addMatchObservation(e.toString());
					e.printStackTrace();
				}
			}
			rc.setIndicatorString(1, allyNum + " allies " + allyAdjNum + " adj " + enemyNum + " enemy " + enemyArchonNum + " enemy Archons");
		}
	}

	// When we do a new Ally scan
	private void resetAllyData() {
		allyNum = 0;
		allyAdjNum = 0;
		allyScanNeeded = false;
	}

	// When we do a new Enemy scan
	private void resetEnemyData() {
		enemyNum = 0;
		enemyArchonNum = 0;
		enemyScanNeeded = false;
		nearestEnemy = null;
	}

	private void addEnemy(RobotInfo info) {

		if (info.type == RobotType.TOWER)
			return; // We track towers in the Map Class

		int id = info.robot.getID();
		enemyInfoById[id] = info;
		enemyInfoScanTime[id] = bp.round;
		enemyNum++;

		if (nearestEnemy == null) {
			nearestEnemy = info;
			nearestEnemyR2 = bp.loc.distanceSquaredTo(info.location);
		} else {
			int distance = bp.loc.distanceSquaredTo(info.location);
			if (distance < nearestEnemyR2) {
				nearestEnemy = info;
				nearestEnemyR2 = distance;
			}
		}

		switch (info.type) {
		case ARCHON:
			enemyArchonId[enemyArchonNum++] = id;
			break;
		case DISRUPTER:
		case SCORCHER:
		case SCOUT:
		case SOLDIER:
		default:
		}
	}

	private void addAlly(RobotInfo info) {

		if (info.type == RobotType.TOWER)
			return; // We track towers in the Map Class

		int id = info.robot.getID();
		allyInfoById[id] = info;
		allyInfoScanTime[id] = bp.round;

		allyNum++;

		if (info.location.isAdjacentTo(bp.loc)) {
			allyAdj[allyAdjNum++] = info;
		}
	}

	public int getTeamStrength() {
		return allyNum - enemyNum;
	}
	
	/**
	 * 
	 * @return - The number of enemies shown on the current robot's radar.
	 */
	public int getNumEnemies() {
		return enemyNum;
	}

}
