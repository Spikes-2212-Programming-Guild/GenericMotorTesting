// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.spikes2212.command.genericsubsystem.MotoredGenericSubsystem;
import com.spikes2212.command.genericsubsystem.commands.MoveGenericSubsystem;
import com.spikes2212.dashboard.Namespace;
import com.spikes2212.dashboard.RootNamespace;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This is a generic motor test.
 *
 * <p>To use, you should change the amounts of each {@link MotorController} so it will match your needs.<br>
 * When you enable the robot <b>on teleop</b>, you should put through the shuffleboard the
 * correct port for each {@link MotorController}</p>
 */
public class Robot extends TimedRobot {

    private final RootNamespace rootNamespace = new RootNamespace("test motors");
    private final Supplier<Double> speed = rootNamespace.addConstantDouble("testing speed", 0.1);

    /**
     * These are the available motor controllers that you can use this test on.
     */
    private int victorSPNum = 0, victorSPXNum = 0, talonSRXNum = 0, sparkMaxNum = 0;
    //@TODO change the numbers according to your needed tests

    private final Map<Namespace, VictorSP> victorSPes = new HashMap<>();
    private final Map<Namespace, WPI_VictorSPX> victorSPXes = new HashMap<>();
    private final Map<Namespace, WPI_TalonSRX> talonSRXes = new HashMap<>();
    private final Map<Namespace, CANSparkMax> sparkMaxes = new HashMap<>();

    @Override
    public void robotInit() {
        for (int i = 0; i < victorSPNum; i++) {
            Namespace namespace = rootNamespace.addChild("VictorSP " + i);
            namespace.addConstantInt("port", -1);
            victorSPes.put(namespace, null);
        }
        for (int i = 0; i < victorSPXNum; i++) {
            Namespace namespace = rootNamespace.addChild("VictorSPX " + i);
            namespace.addConstantInt("port", -1);
            victorSPXes.put(namespace, null);
        }
        for (int i = 0; i < talonSRXNum; i++) {
            Namespace namespace = rootNamespace.addChild("TalonSRX " + i);
            namespace.addConstantInt("port", -1);
            talonSRXes.put(namespace, null);

        }
        for (int i = 0; i < sparkMaxNum; i++) {
            Namespace namespace = rootNamespace.addChild("SparkMax " + i);
            namespace.addConstantInt("port", -1);
            sparkMaxes.put(namespace, null);
        }
    }

    @Override
    public void robotPeriodic() {
        rootNamespace.update();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        for (Namespace namespace : victorSPes.keySet()) {
            if (victorSPes.get(namespace) == null) {
                VictorSP victorSP = new VictorSP((int) namespace.getNumber("port"));
                victorSPes.put(namespace, victorSP);
                namespace.putData("Run " + namespace, new MoveGenericSubsystem(new MotoredGenericSubsystem("_", victorSP), speed));
            }
        }
        for (Namespace namespace : victorSPXes.keySet()) {
            if (victorSPXes.get(namespace) == null) {
                WPI_VictorSPX victorSPX = new WPI_VictorSPX((int) namespace.getNumber("port"));
                victorSPXes.put(namespace, victorSPX);
                namespace.putData("Run " + namespace, new MoveGenericSubsystem(new MotoredGenericSubsystem("_", victorSPX), speed));
            }
        }
        for (Namespace namespace : talonSRXes.keySet()) {
            if (talonSRXes.get(namespace) == null) {
                WPI_TalonSRX talon = new WPI_TalonSRX((int) namespace.getNumber("port"));
                talonSRXes.put(namespace, talon);
                namespace.putData("Run " + namespace, new MoveGenericSubsystem(new MotoredGenericSubsystem("_", talon), speed));
            }
        }
        for (Namespace namespace : sparkMaxes.keySet()) {
            if (sparkMaxes.get(namespace) == null) {
                CANSparkMax sparkMax = new CANSparkMax((int) namespace.getNumber("port"), CANSparkMaxLowLevel.MotorType.kBrushless);
                sparkMaxes.put(namespace, sparkMax);
                namespace.putData("Run " + namespace, new MoveGenericSubsystem(new MotoredGenericSubsystem("_", sparkMax), speed));
            }
        }
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }
}
