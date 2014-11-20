package team017;

import team017.framework.ArchonPlayer;
import team017.framework.BasePlayer;
import team017.framework.SoldierPlayer;
import battlecode.common.*;

//import battlecode.engine.instrumenter.lang.System;

public class RobotPlayer {

	public static void run(RobotController rc) {

		BasePlayer bp = null;

		try {
			switch (rc.getType()) {
			case ARCHON:
				bp = new ArchonPlayer(rc);
				break;
			case SOLDIER:
				bp = new SoldierPlayer(rc);
				break;
			case SCOUT:
				break;
			case DISRUPTER:
				break;
			case SCORCHER:
				break;
			default:
				bp = new ArchonPlayer(rc);
				break;
			}
		} catch (Exception e) {
			System.out.println("Exception creating robot");
			e.printStackTrace();
		}

		while (true) {
			try {
				if (bp == null) {
					// System.out.println("WHY IS BP NULL");
				} else {
					bp.loop();
				}
			} catch (Exception e) {
				System.out.println("Main loop terminated unexpectedly");
				e.printStackTrace();
			}
		}
	}
}
