package Systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase {
    static VictorSP motor1=new VictorSP(1);
    static VictorSP motor2=new VictorSP(0);
    static Joystick joy = new Joystick(0);
    static SmartDashboard dash = new SmartDashboard();
    static double speedl =0, speedr =0;
    
    
    public static void teleOp(){
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
    		}
    		else if(speedl <= -0.05){
    			speedl=speedl+0.05;
    		}
    		else {
    			speedl=0;
    		}   
    		if(speedr >= 0.05){
    			speedr=speedr-0.05;
    		}
    		else if(speedr <= -0.05){
    			speedr=speedr+0.05;
    		}
    		else {
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
}
