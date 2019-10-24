package org.frcteam2910.c2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frcteam2910.c2019.Robot;
import org.frcteam2910.c2019.subsystems.Chassis;
import org.frcteam2910.c2019.util.BeakXboxController;
import org.frcteam2910.c2019.util.BeakXboxController.Thumbstick;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;

public class HolonomicDriveCommand extends Command 
{
    Chassis _chassis = Chassis.getInstance();
    Thumbstick _leftStick, _rightStick;
    double forward, strafe, rotation;
    boolean isFieldOriented;
    public HolonomicDriveCommand(Thumbstick leftStick, Thumbstick rightStick) 
    {
        requires(_chassis);
        _leftStick=leftStick;
        _rightStick=rightStick;
    }

    @Override
    protected void execute() 
    {
        forward = _leftStick.getY();
        strafe = _leftStick.getX();
        Vector2 translationVector = new Vector2(forward, strafe);
        rotation = _rightStick.getX();
        isFieldOriented=true;
        _chassis.holonomicDrive(translationVector, rotation, isFieldOriented);
        SmartDashboard.putNumber("Forward", forward);
        SmartDashboard.putNumber("Strafe", strafe);
        SmartDashboard.putNumber("Rotation", rotation);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
