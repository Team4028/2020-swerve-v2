package org.frcteam2910.c2019.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.frcteam2910.c2019.Robot;
import org.frcteam2910.c2019.subsystems.CargoGrabberSubsystem;
import org.frcteam2910.c2019.subsystems.Superstructure;

public class CargoGrabberDefaultCommand extends Command {
    private static final double EJECT_SPEED_TOP = -1.0;
    private static final double EJECT_SPEED_BOTTOM = 0.0;

    private static final double EJECT_FRONT_CARGO_SHIP_SPEED_TOP = -0.6;
    private static final double EJECT_FRONT_CARGO_SHIP_SPEED_BOTTOM = 0.0;

    private static final double INTAKE_AFTER_EJECT_TIME = 2.5;

    private static final double INTAKE_SPEED_TOP = 0.7;
    private static final double INTAKE_SPEED_BOTTOM = 0.5;

    private static final double SOFT_INTAKE_SPEED_TOP = 0.6;
    private static final double SOFT_INTAKE_SPEED_BOTTOM = 0.5;

    private final Timer timer = new Timer();

    public CargoGrabberDefaultCommand() {
        requires(CargoGrabberSubsystem.getInstance());
    }

    @Override
    protected void initialize() {
        timer.start();
    }

    @Override
    protected void execute() {
        if (Robot.getOi().primaryController.getLeftTriggerAxis().get() > 0.1) {
            double angle = Superstructure.getInstance().getGyroscope().getAngle().toRadians();

            if (angle < Math.toRadians(30.0) || angle > Math.toRadians(360.0 - 30.0)) {
                CargoGrabberSubsystem.getInstance().setTopIntakeSpeed(EJECT_FRONT_CARGO_SHIP_SPEED_TOP);
                CargoGrabberSubsystem.getInstance().setBottomIntakeSpeed(EJECT_FRONT_CARGO_SHIP_SPEED_BOTTOM);
            } else {
                CargoGrabberSubsystem.getInstance().setTopIntakeSpeed(EJECT_SPEED_TOP);
                CargoGrabberSubsystem.getInstance().setBottomIntakeSpeed(EJECT_SPEED_BOTTOM);
            }

            timer.reset();
        } else if (Robot.getOi().secondaryController.getLeftTriggerAxis().get() > 0.1 ||
                timer.get() < INTAKE_AFTER_EJECT_TIME) {
            CargoGrabberSubsystem.getInstance().setTopIntakeSpeed(INTAKE_SPEED_TOP);
            CargoGrabberSubsystem.getInstance().setBottomIntakeSpeed(INTAKE_SPEED_BOTTOM);
        } else if (CargoGrabberSubsystem.getInstance().hasCargo()) {
            CargoGrabberSubsystem.getInstance().setTopIntakeSpeed(0.06);
            CargoGrabberSubsystem.getInstance().setBottomIntakeSpeed(0.0);
        } else if (CargoGrabberSubsystem.getInstance().hasLeftCargo() ||
                CargoGrabberSubsystem.getInstance().hasRightCargo()) {
            CargoGrabberSubsystem.getInstance().setTopIntakeSpeed(SOFT_INTAKE_SPEED_TOP);
            CargoGrabberSubsystem.getInstance().setBottomIntakeSpeed(SOFT_INTAKE_SPEED_BOTTOM);
        } else {
                CargoGrabberSubsystem.getInstance().setIntakeSpeed(0.0);
        }
    }

    @Override
    protected void end() {
        timer.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}