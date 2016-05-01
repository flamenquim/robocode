package genetics;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSetup;
import robocode.control.RobotSpecification;

public class BattleFieldGenerator {
	
	public BattleFieldGenerator(){
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("/home/flamenquim/robocode"));
		engine.setVisible(true);
		
		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
		
		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 0.1;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		
		RobotSpecification[] modelRobots = engine.getLocalRepository("robots.SuperTracker*,robots.SuperRamFire*");
		
		RobotSpecification[] existingRobots = new RobotSpecification[2];
		existingRobots[0] = modelRobots[0];
		existingRobots[1] = modelRobots[1];
		
		RobotSetup[] robotSetups = new RobotSetup[2]; 
		//position of the first robot
		double InitialAgentRow = 32;
		double InitialAgentCol = 32;
		robotSetups[0]=new RobotSetup(InitialAgentRow, InitialAgentCol, 0.0);
		// --||-- second robot
		InitialAgentRow = 768;
		InitialAgentCol = 568;
		robotSetups[1]=new RobotSetup(InitialAgentRow, InitialAgentCol, 0.0);
		
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numberOfRounds, inactivityTime, gunCoolingRate, sentryBorderSize, 
																	hideEnemyNames, existingRobots, robotSetups); 
		engine.runBattle(battleSpec, true);
		engine.close();
	}
}
