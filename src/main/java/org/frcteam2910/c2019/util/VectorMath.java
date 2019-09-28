package org.frcteam2910.c2019.util;

import org.frcteam2910.c2019.sensors.GyroNavX;
import org.frcteam2910.common.drivers.SwerveModule;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;


public class VectorMath 
{
    public GyroNavX _navX = GyroNavX.getInstance();
    private static VectorMath _instance = new VectorMath();
    private VectorMath()
    {
    }

    public static VectorMath getInstance()
    {
        return _instance;
    }


    public Vector2 getVelocityVector(SwerveModule module, Vector2 translation, double rotation, boolean isFieldOriented)
    {
        double heading = Math.toRadians(_navX.getYaw());
        Vector2 positionVector = module.getModulePosition();
        double finalRotation = rotation + heading;
        positionVector = positionVector.rotateBy(Rotation2.fromRadians(finalRotation));
        Vector2 finalPositionVector = positionVector.add(translation);
        Vector2 initialpos = positionVector.rotateBy(Rotation2.fromRadians(heading));
        Vector2 outputVector = finalPositionVector.subtract(initialpos);
        return outputVector;

    }

    public double getSpeedCommand(Vector2 outputVector)//#TODO set a speed scaling
    {
        return outputVector.length;
    }
    
    public double getAngleCommand(Vector2 outputVector)
    {
        double angle = Math.atan2(outputVector.y, outputVector.x);
        return angle;
    }

}