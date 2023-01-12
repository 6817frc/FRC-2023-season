// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

import java.lang.Math;
import edu.wpi.first.wpilibj.IterativeRobotBase;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private final DoubleSolenoid Left = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  private final DoubleSolenoid Right = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
  private final Spark leftFront = new Spark(0); //variable for front left motor
  private final Spark leftBack = new Spark(1);
  private final Spark rightFront = new Spark(3);
  private final Spark rightBack = new Spark(2);
  private final MotorControllerGroup leftGroup = new MotorControllerGroup(leftFront, leftBack);
  private final MotorControllerGroup rightGroup = new MotorControllerGroup(rightFront, rightBack);
  private final DifferentialDrive robotDrive = new DifferentialDrive (leftGroup, rightGroup);
  private final Joystick m_stick = new Joystick(0);
  private final XboxController logiController = new XboxController(1); // 1 is the USB Port to be used as indicated on the Driver Station
  private NetworkTable datatable;
  String autoName="low goal";
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    datatable = inst.getTable("datatable");
    rightBack.setInverted(true);
    rightFront.setInverted(true);
    robotDrive.setSafetyEnabled(true);
    
  }
  @Override
  public void teleopInit () {
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    robotDrive.arcadeDrive((-logiController.getRawAxis(5)),(logiController.getRawAxis(4)/1.5));
   
  }
}
