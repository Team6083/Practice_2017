
package org.usfirst.frc.team6083.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CANTalon;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    VictorSP motor1=new VictorSP(1);
    VictorSP motor2=new VictorSP(0);
    
    Joystick joy = new Joystick(0);
    SmartDashboard dash = new SmartDashboard();
    double speedl =0, speedr =0;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	if(joy.getRawAxis(1) < 0){
    	speedl=(-joy.getRawAxis(1)-joy.getRawAxis(0))/4;
    	speedr=(-joy.getRawAxis(1)+joy.getRawAxis(0))/4;
    	} else {
        	speedl=(-joy.getRawAxis(1)+joy.getRawAxis(0))/4;
        	speedr=(-joy.getRawAxis(1)-joy.getRawAxis(0))/4;	
    	}
    if(joy.getRawAxis(1)  == 0){
    	if(speedl >= 0.05 ){
    		 speedl=speedl-0.05;	
    					}else if(speedl <= -0.05){
    						speedl=speedl+0.05;
    					}else {
    						speedl=0;
    }   
    if(speedr >= 0.05){
    speedr=speedr-0.05;
    }else if(speedr <= -0.05){
		speedr=speedr+0.05;
	}else {
    speedr=0;
    }
    }
    if(joy.getRawButton(9)){
    speedl=speedl*2;
    speedr=speedr*2;
    }
	motor1.set(speedl);
	motor2.set(speedr);
	SmartDashboard.putNumber("left", motor1.get());
	SmartDashboard.putNumber("right", motor2.get());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
