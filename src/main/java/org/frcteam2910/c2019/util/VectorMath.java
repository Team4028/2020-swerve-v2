package org.frcteam2910.c2019.util;

import org.frcteam2910.c2019.drivers.Mk2SwerveModule;
import org.frcteam2910.c2019.sensors.GyroNavX;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.drivers.NavX;

public class VectorMath 
{
    public GyroNavX _navX = GyroNavX.getInstance();
    public VectorMath()
    {

    }


    public Vector2 getVelocityVector(Vector2 translation, double rotation, Mk2SwerveModule module, boolean isFieldOriented)
    {
        Vector2 positionVector = module.getModulePosition();
        double finalRotation = rotation + _navX.getYaw();
        positionVector.rotateBy(rotation)
    }
}