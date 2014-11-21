package team017.framework;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

public class FluxTransfer {

	private BasePlayer bp;
	private RobotController rc;

	public FluxTransfer(BasePlayer bp) {
		this.bp = bp;
		rc = bp.rc;
	}

	public void manageFlux() {
		if (bp.flux < Constants.FLUX_MIN)
			return;
		bp.radar.scan(true, false);
		for (int i = 0; i < bp.radar.allyAdjNum; i++) {
			RobotInfo info = bp.radar.allyAdj[i];
			if (info.type == RobotType.TOWER)
				continue;
			if (info.flux < Constants.FLUX_DECENT) {
				double toTransfer = Math.min(Constants.FLUX_DECENT - info.flux, bp.flux - Constants.FLUX_MIN);
				if (toTransfer > 5) {
					bp.flux -= toTransfer;
					try {
						bp.rc.transferFlux(info.location, info.type.level, toTransfer);
					} catch (GameActionException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
