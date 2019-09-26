package org.frcteam2910.c2019;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frcteam2910.c2019.subsystems.*;
import org.frcteam2910.common.robot.drivers.NavX;
import org.frcteam2910.common.robot.subsystems.SubsystemManager;

public class Robot extends TimedRobot {
    private static final double UPDATE_DT = 5e-3; // 5 ms

    private final SubsystemManager subsystemManager = new SubsystemManager(
            DrivetrainSubsystem.getInstance()
    );

    private static final OI oi = new OI();

    public Robot() {
        //oi.bindButtons(autonomousSelector);
    }

    public static OI getOi() {
        return oi;
    }

    @Override
    public void robotInit() {
        SmartDashboard.putBoolean("Limelight Calibration Mode", false);

        subsystemManager.enableKinematicLoop(UPDATE_DT);
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("Gyro Pitch",
                Math.toDegrees(Superstructure.getInstance().getGyroscope().getAxis(NavX.Axis.ROLL)));
        SmartDashboard.putBoolean("Is Competition Bot",
                Superstructure.getInstance().isCompetitionBot());
        SmartDashboard.putBoolean("Is Practice Bot",
                Superstructure.getInstance().isPracticeBot());
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        Scheduler.getInstance().removeAll();
    }

    @Override
    public void disabledPeriodic() {
    }
}
