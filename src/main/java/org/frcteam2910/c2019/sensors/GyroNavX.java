/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frcteam2910.c2019.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.sensors.visionLLInterpretation.LimeLightInterpreter;
//import frc.robot.sensors.visionLLInterpretation.ThreeDimensionalIsometry;

/**
 * This class exposes the OnBoard Navigation Sensor Lead Student:
 */
public class GyroNavX {
    //=====================================================================================
	// Define Singleton Pattern
	//=====================================================================================
	// define class level working variables
	public enum SCORING_TARGET {
		CARGOSHIP_FRONT,
		CARGOSHIP_SIDE_ROCKET,
		ROCKET_FRONT,
		ROCKET_BACK,
		FEEDER_STATION
	}

	public enum SIDE {
		LEFT,
		RIGHT
	}

	private static final double CARGOSHIP_FRONT_ANGLE = 0;
    private static final double CARGOSHIP_SIDE_ROCKET_ANGLE = -90;
	private static final double ROCKET_FRONT_ANGLE = 28.75;
	private static final double ROCKET_BACK_ANGLE = 151.25;
	private static final double FEEDER_STATION_ANGLE = 180;


	private static final double CARGOSHIP_FRONT_LEFT_TARGET_ALPHA_DEGREES = 0;
	private static final double CARGOSHIP_FRONT_LEFT_TARGET_BETA_DEGREES = 0;
	private static final double CARGOSHIP_FRONT_LEFT_TARGET_GAMMA_DEGREES = 0;
	private static final double CARGOSHIP_FRONT_RIGHT_TARGET_ALPHA_DEGREES = 0;
	private static final double CARGOSHIP_FRONT_RIGHT_TARGET_BETA_DEGREES = 0;
	private static final double CARGOSHIP_FRONT_RIGHT_TARGET_GAMMA_DEGREES = 0;
	
	private static final double CARGOSHIP_SIDE_ROCKET_LEFT_TARGET_ALPHA_DEGREES = 0;
	private static final double CARGOSHIP_SIDE_ROCKET_LEFT_TARGET_BETA_DEGREES = 0;
	private static final double CARGOSHIP_SIDE_ROCKET_LEFT_TARGET_GAMMA_DEGREES = 0;
	private static final double CARGOSHIP_SIDE_ROCKET_RIGHT_TARGET_ALPHA_DEGREES = 0;
	private static final double CARGOSHIP_SIDE_ROCKET_RIGHT_TARGET_BETA_DEGREES = 0;
	private static final double CARGOSHIP_SIDE_ROCKET_RIGHT_TARGET_GAMMA_DEGREES = 0;

	private static final double ROCKET_FRONT_LEFT_TARGET_ALPHA_DEGREES = 0;
	private static final double ROCKET_FRONT_LEFT_TARGET_BETA_DEGREES = 0;
	private static final double ROCKET_FRONT_LEFT_TARGET_GAMMA_DEGREES = 0;
	private static final double ROCKET_FRONT_RIGHT_TARGET_ALPHA_DEGREES = 0;
	private static final double ROCKET_FRONT_RIGHT_TARGET_BETA_DEGREES = 0;
	private static final double ROCKET_FRONT_RIGHT_TARGET_GAMMA_DEGREES = 0;

	private static final double ROCKET_BACK_LEFT_TARGET_ALPHA_DEGREES = 0;
	private static final double ROCKET_BACK_LEFT_TARGET_BETA_DEGREES = 0;
	private static final double ROCKET_BACK_LEFT_TARGET_GAMMA_DEGREES = 0;
	private static final double ROCKET_BACK_RIGHT_TARGET_ALPHA_DEGREES = 0;
	private static final double ROCKET_BACK_RIGHT_TARGET_BETA_DEGREES = 0;
	private static final double ROCKET_BACK_RIGHT_TARGET_GAMMA_DEGREES = 0;

	private static final double FEEDER_STATION_LEFT_TARGET_ALPHA_DEGREES = 0;
	private static final double FEEDER_STATION_LEFT_TARGET_BETA_DEGREES = 0;
	private static final double FEEDER_STATION_LEFT_TARGET_GAMMA_DEGREES = 0;
	private static final double FEEDER_STATION_RIGHT_TARGET_ALPHA_DEGREES = 0;
	private static final double FEEDER_STATION_RIGHT_TARGET_BETA_DEGREES = 0;
	private static final double FEEDER_STATION_RIGHT_TARGET_GAMMA_DEGREES = 0;

	private static final double NAVX_TO_LIMELIGHT_ALPHA_DEGREES = 0;
	private static final double NAVX_TO_LIMELIGHT_BETA_DEGREES = 0;
	private static final double NAVX_TO_LIMELIGHT_GAMMA_DEGREES = 0;
	//private static final ThreeDimensionalIsometry NAVX_TO_LIMELIGHT_TRNASLATIONLESS_ISOMETRY = new ThreeDimensionalIsometry(0, 0, 0, LimeLightInterpreter.deg2rad(NAVX_TO_LIMELIGHT_ALPHA_DEGREES),  LimeLightInterpreter.deg2rad(NAVX_TO_LIMELIGHT_BETA_DEGREES),  LimeLightInterpreter.deg2rad(NAVX_TO_LIMELIGHT_GAMMA_DEGREES));
	

	private double _currentAngle2;

	private AHRS _navXSensor;
	
	
	private static GyroNavX _instance = new GyroNavX();

	private boolean _isReversed = false;
	
	public static GyroNavX getInstance() {
		return _instance;
	}

	public void setReversed(boolean isReversed){
		_isReversed = isReversed;
	}
	
	// private constructor for singleton pattern
	private GyroNavX() {	
		try {          
		_navXSensor = new AHRS(SPI.Port.kMXP); // Communication via RoboRIO MXP (SPI) 
		} catch (RuntimeException ex ) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
	}

	public  double getTargetAngle(SCORING_TARGET target, SIDE side){
		double scoringTargetAngle = 0;
		double sideFactor = 0;
		switch(target){
			case CARGOSHIP_FRONT:
				scoringTargetAngle = CARGOSHIP_FRONT_ANGLE;
				break;
			case CARGOSHIP_SIDE_ROCKET:
				scoringTargetAngle = CARGOSHIP_SIDE_ROCKET_ANGLE;
				break;
			case ROCKET_FRONT:
				scoringTargetAngle = ROCKET_FRONT_ANGLE;
				break;
			case ROCKET_BACK:
				scoringTargetAngle = ROCKET_BACK_ANGLE;
				break;
			case FEEDER_STATION:
				scoringTargetAngle = FEEDER_STATION_ANGLE;
				break;
		}

		switch(side) {
			case LEFT:
				sideFactor = -1;
				break;
			case RIGHT:
				sideFactor = 1;
				break;
		}
		return scoringTargetAngle * sideFactor;
	}

	
    public double getYaw() { 
		if (_isReversed){
			if(_navXSensor.getYaw()>=0){
				return _navXSensor.getYaw()-180;
			}
			else
			{
				return _navXSensor.getYaw()+180;
			}
		} else {
			return _navXSensor.getYaw();
		} 
	}
	
	public void zeroYaw() { 
		_navXSensor.zeroYaw(); 
	}
	
	public double getPitch() { //Axis Perpendicular to the Front/Back of the robot
		return _navXSensor.getPitch();
	}

	//=====================================================================================
	// Helper Methods
	//=====================================================================================  
	public void updateDashboard() {
		SmartDashboard.putNumber("Angle2", _currentAngle2);
	}
}