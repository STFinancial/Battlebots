package team017.framework;

import battlecode.common.RobotController;
import battlecode.engine.instrumenter.lang.System;

public class ArchonPlayer extends BasePlayer {

		int ID;
	
		public ArchonPlayer(RobotController rc){
			super(rc);
			state = States.EARLY_GAME;
		}
		
		public void run(){
			while(true){
				try{
					nav.moveAwayFrom(nav.base);
					myYield();
				}catch(Exception e){
					
				}
			}
		}

	
}
