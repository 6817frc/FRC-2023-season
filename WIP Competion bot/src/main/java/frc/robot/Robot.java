// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private final DoubleSolenoid leftClaw = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  private final DoubleSolenoid rightClaw = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
  private final WPI_VictorSPX leftFront =  new WPI_VictorSPX(1); //variable for front left motor
  private final WPI_VictorSPX leftBack = new WPI_VictorSPX(2);
  private final WPI_VictorSPX rightFront =  new WPI_VictorSPX(3);
  private final WPI_VictorSPX rightBack =  new WPI_VictorSPX(4);
  private final WPI_TalonSRX ElevatorMotor1 =  new WPI_TalonSRX(0); //1 is a temporary placement for wiring
  private final WPI_TalonSRX ElevatorMotor2 =  new WPI_TalonSRX(2); //2 is also temporary
  private final MotorControllerGroup leftGroup = new MotorControllerGroup( leftFront, leftBack);
  private final MotorControllerGroup rightGroup = new MotorControllerGroup(rightFront, rightBack);
  private final DifferentialDrive robotDrive = new DifferentialDrive (leftGroup, rightGroup);
  private final Joystick m_stick = new Joystick(0);
  private final XboxController logiController = new XboxController(1); // 1 is the USB Port to be used as indicated on the Driver Station
  Timer timer = new Timer();

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
  
    /*
    rightFront.configFactoryDefault();
    rightBack.configFactoryDefault();
    leftFront.configFactoryDefault();
    leftBack.configFactoryDefault();

    // set up followers
    rightBack.follow(rightFront);
    leftBack.follow(leftFront);

    // [3] flip values so robot moves forward when stick-forward/LEDs-green
    rightFront.setInverted(true); // !< Update this
    leftFront.setInverted(false); // !< Update this
    
     // set the invert of the followers to match their respective master controllers
    
      rightBack.setInverted(InvertType.FollowMaster);
      leftBack.setInverted(InvertType.FollowMaster);

      
      //[4] adjust sensor phase so sensor moves positive when Talon LEDs are green
      
      rightFront.setSensorPhase(true);
      leftFront.setSensorPhase(true);
      */
  }
  @Override
  public void autonomousInit () {
  timer.reset();
  timer.start();
  }

  @Override
  public void autonomousPeriodic() {
    if (timer.get() < 2) {
      robotDrive.arcadeDrive(0, 0.25);
    } else {
      robotDrive.arcadeDrive(0, 0);
    }
  }

  @Override
  public void teleopInit () {

  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.


    robotDrive.arcadeDrive(-m_stick.getY(), -m_stick.getZ());
    ElevatorMotor1.set(-logiController.getRawAxis(5));
    ElevatorMotor2.set(-logiController.getRawAxis(1));
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