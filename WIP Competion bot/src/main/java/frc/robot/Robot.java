// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private final DoubleSolenoid leftClaw = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  private final DoubleSolenoid rightClaw = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
  private final AHRS gyro = new AHRS(SPI.Port.kMXP);
  private final Spark leftFront = new Spark(0); //variable for front left motor
  private final Spark leftBack = new Spark(1);
  private final Spark rightFront = new Spark(3);
  private final Spark rightBack = new Spark(2);
  private final VictorSPX ElevatorMotor1 = new VictorSPX(1); //1 is a temporary placement for wiring
  private final VictorSPX ElevatorMotor2 = new VictorSPX(2); //2 is also temporary
  private final MotorControllerGroup leftGroup = new MotorControllerGroup(leftFront, leftBack);
  private final MotorControllerGroup rightGroup = new MotorControllerGroup(rightFront, rightBack);
  private final DifferentialDrive robotDrive = new DifferentialDrive (leftGroup, rightGroup);
  private final Joystick m_stick = new Joystick(0);
  private final XboxController logiController = new XboxController(1); // 1 is the USB Port to be used as indicated on the Driver Station
  
  boolean closeLeftClaw = true;
  boolean closeRightClaw = true;
  String autoName="low goal";
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
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
    if (logiController.getAButtonPressed()) {
      closeLeftClaw = !closeLeftClaw;
    }
    if (logiController.getXButtonPressed()) {
      closeRightClaw = !closeRightClaw;
    }
    leftClaw.set((closeLeftClaw?DoubleSolenoid.Value.kForward:DoubleSolenoid.Value.kReverse));
    rightClaw.set((closeRightClaw?DoubleSolenoid.Value.kForward:DoubleSolenoid.Value.kReverse));
  }
}