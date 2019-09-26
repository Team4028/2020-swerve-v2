package org.frcteam2910.c2019;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import org.frcteam2910.c2019.autonomous.AutonomousSelector;
import org.frcteam2910.c2019.commands.*;
import org.frcteam2910.c2019.subsystems.DrivetrainSubsystem;
import org.frcteam2910.c2019.vision.api.Gamepiece;
import org.frcteam2910.common.robot.commands.ZeroFieldOrientedCommand;
import org.frcteam2910.common.robot.input.DPadButton;
import org.frcteam2910.common.robot.input.XboxController;

public class OI {
    public final XboxController primaryController = new XboxController(0);

    public final XboxController secondaryController = new XboxController(1);

    public OI() {
        primaryController.getLeftXAxis().setInverted(true);
        primaryController.getRightXAxis().setInverted(true);

        primaryController.getRightXAxis().setScale(0.75);
    }

    public void bindButtons(AutonomousSelector autonomousSelector) {

        primaryController.getRightBumperButton().whenReleased(new ConditionalCommand(
                new FollowTrajectoryCommand(autonomousSelector.getTrajectoryQueue()::remove)
        ) {
            @Override
            protected boolean condition() {
                System.out.printf("Checking %d%n", autonomousSelector.getTrajectoryQueue().size());
                return DriverStation.getInstance().isAutonomous() && !autonomousSelector.getTrajectoryQueue().isEmpty();
            }
        });


        // Field oriented zero
        primaryController.getBackButton().whenPressed(new ZeroFieldOrientedCommand(DrivetrainSubsystem.getInstance()));

    }
}

