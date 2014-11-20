package team017.framework;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;

public abstract class BasePlayer {
	
	//Important Helper Classes
	public final RobotController rc;
	public final NavGeneral nav;
	public final Map map;
	//MapData
	//Radar
	//Messenger
	
	
	//Permanent Variables
	public final RobotType type;
	public final double maxEnergon, maxFlux;
	public final Team team;
	public final int ID;
	public final MapLocation home;
	public final int spawnRound;
	public final MapLocation spawnLoc;
	
	//Per Round Variables
	public double energon;
	public MapLocation loc, locFront, locBack;
	public Direction dir;
	public int round;
	
	
	public BasePlayer(RobotController rc) {
		this.rc = rc;
		ID = rc.getRobot().getID();
		Utility.seed(Clock.getBytecodeNum() + 1, ID + 1);
		
		type = rc.getType();
		maxEnergon = type.maxEnergon;
		maxFlux = type.maxFlux;
		home = rc.sensePowerCore().getLocation();
		team = rc.getTeam();
		spawnRound = Clock.getRoundNum();
		spawnLoc = rc.getLocation();
		
		updateRoundVariables();
		
		//Nav needs Map
		map = new Map(this);
		nav = new NavGeneral(this); 
		
	}

	public abstract void run() throws GameActionException;
	
	public void myYield() {
		rc.yield();
	}
	
	public void updateRoundVariables() {
		round = Clock.getRoundNum();
		energon = rc.getEnergon();
		loc = rc.getLocation();
		dir = rc.getDirection();
		locFront = loc.add(dir);
		locBack = loc.add(dir.opposite());
	}

	public void loop() {
		while(true) {
			
			updateRoundVariables();
			
			try {
				run();
			} catch (GameActionException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
