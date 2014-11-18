package team017.framework;

import battlecode.common.RobotController;

public abstract class BasePlayer {
	
	RobotController rc;
	States state;
	NavGeneral nav;
	
	public BasePlayer(RobotController rc) {
		this.rc = rc;
		nav = new NavGeneral(rc); 
		nav.base = rc.sensePowerCore().getLocation();
	}

	public abstract void run();
	
	public void myYield(){
		rc.yield();
	}
	
	
}
