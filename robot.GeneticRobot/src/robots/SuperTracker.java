package robots;

import robocode.*;
import robocode.control.events.BattleStartedEvent;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
 
/**
 * SuperTracker - a Super Sample Robot by CrazyBassoonist based on the robot Tracker by Mathew Nelson and maintained by Flemming N. Larsen
 * <p/>
 * Locks onto a robot, moves close, fires when close.
 */
public class SuperTracker extends AdvancedRobot {
	int moveDirection=1;//which way to move
	/**
	 * run:  Tracker's main run function
	 * @throws FileNotFoundException 
	 */
	double distanceLimit;
	double probabilityChangeSpeed;
	double rangeRobotSpeeds;
	double minRobotSpeed;
	
	public void ChromosomeReader() throws FileNotFoundException{
		//read the chromosome
		Scanner sc = new Scanner(new java.io.File("chromosome.txt"));
		distanceLimit = Double.parseDouble(sc.next());
		probabilityChangeSpeed = Double.parseDouble(sc.next());
		rangeRobotSpeeds = Double.parseDouble(sc.next());
		minRobotSpeed = Double.parseDouble(sc.next());
		sc.close();
	}
	public void run() {
		setAdjustRadarForRobotTurn(true);//keep the radar still while we turn
		setBodyColor(new Color(128, 128, 50));
		setGunColor(new Color(50, 50, 20));
		setRadarColor(new Color(200, 200, 70));
		setScanColor(Color.white);
		setBulletColor(Color.blue);
		setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		try {
			ChromosomeReader();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		turnRadarRightRadians(Double.POSITIVE_INFINITY);//keep turning radar right
		}
 
	/**
	 * onScannedRobot:  Here's the good stuff
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		double absBearing=e.getBearingRadians()+getHeadingRadians();//enemies absolute bearing
		double latVel=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing);//enemies later velocity
		double gunTurnAmt;//amount to turn our gun
		setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//lock on the radar
		if(Math.random()>probabilityChangeSpeed){
			setMaxVelocity((rangeRobotSpeeds*Math.random())+minRobotSpeed);//randomly change speed
		}
		if (e.getDistance() > distanceLimit) {//if distance is greater than 150
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/22);//amount to turn our gun, lead just a little bit
			setTurnGunRightRadians(gunTurnAmt); //turn our gun
			setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//drive towards the enemies predicted future location
			setAhead((e.getDistance() - 140)*moveDirection);//move forward
			setFire(3);//fire
		}
		else{//if we are close enough...
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/15);//amount to turn our gun, lead just a little bit
			setTurnGunRightRadians(gunTurnAmt);//turn our gun
			setTurnLeft(-90-e.getBearing()); //turn perpendicular to the enemy
			setAhead((e.getDistance() - 140)*moveDirection);//move forward
			setFire(3);//fire
		}	
	}
	public void onHitWall(HitWallEvent e){
		moveDirection=-moveDirection;//reverse direction upon hitting a wall
	}
	
	
	/**
	 * onWin:  Do a victory dance
	 */
	public void onWin(WinEvent e) {
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}
	//writes the fitness (score) after the battle has finished
	public void onBattleEnded(BattleEndedEvent e){
		PrintWriter pw;
		try {
			pw = new PrintWriter("fitness.txt");
			double score = e.getResults().getScore();
			System.out.println(score);
			pw.print(score);
			pw.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}