package org.frcteam2910.c2019;

import org.frcteam2910.c2019.commands.HolonomicDriveCommand;
import org.frcteam2910.c2019.util.BeakXboxController;

/**
 * This class interfaces with the Driver/Operator Station Lead Student:
 */
public class OI {
	private BeakXboxController _driverController = new BeakXboxController(0);
	private BeakXboxController _operatorController = new BeakXboxController(1);
    private BeakXboxController _engineerController = new BeakXboxController(2);

	// =====================================================================================
	// Define Singleton Pattern
	// =====================================================================================
	private static OI _instance = new OI();

	public static OI getInstance() {
		return _instance;
	}

	// private constructor for singleton pattern
	private OI() 	
	{	
		_driverController.leftStick.whileActive(new HolonomicDriveCommand(_driverController.leftStick,_driverController.rightStick));
		_driverController.leftStick.whenReleased(new HolonomicDriveCommand(_driverController.leftStick,_driverController.rightStick));

	}
}
