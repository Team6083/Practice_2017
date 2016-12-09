package Systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase {
	static final int motor1_channel = 0;
	static final int motor2_channel = 1;
	static final int frontmotor1_channel = 1;
	static final int Joystick_port = 0;
	static final int joy_select = 1;//0 is XBox 1 is 3DPro
	
	static final double error_range = 0.04;
	
    static VictorSP motor1;
    static VictorSP motor2;
    static VictorSP frontmotor1;
    static Joystick joy;
    
    static double speedl = 0, speedr = 0, speed_down_value = 4.0, joy_left_x, joy_left_y;
    static boolean speedup = false;
    static int status_frontmotor1 = 0;
    
    
    public static void init(){
    	motor1 = new VictorSP(motor1_channel);
    	motor2 = new VictorSP(motor2_channel);
    	frontmotor1 = new VictorSP(frontmotor1_channel);
    	joy= new Joystick(Joystick_port);
    	SmartDashboard.putNumber("speed_down_value", speed_down_value);
    }
    
    public static void dashboard(){
		SmartDashboard.putNumber("left", motor1.get());
		SmartDashboard.putNumber("right", motor2.get());
		speed_down_value = SmartDashboard.getNumber("speed_down_value");
		SmartDashboard.putBoolean("speedup", speedup);
    }
    
    public static void teleOp(){
    	dashboard();
    	
    	if(joy.getRawAxis(1) < 0){
    		joy_left_y=-joy.getRawAxis(1);
    		joy_left_y=joy_left_y-joy_left_y%error_range;
    	}
    	else {
    		joy_left_y=joy.getRawAxis(1);
    		joy_left_y=joy_left_y-joy_left_y%error_range;
    		joy_left_y=-joy_left_y;
    	}
    	
    	if(joy.getRawAxis(0) < 0){
    		joy_left_x=-joy.getRawAxis(0);
    		joy_left_x=joy_left_x%error_range;
    		joy_left_x=-joy_left_x;
    	}
    	else {
    		joy_left_x=joy.getRawAxis(0);
    		joy_left_x=joy_left_x%error_range;
    	}
    	
    	if(joy.getRawAxis(1) < 0){
    		speedl=(joy_left_y+joy_left_x)/speed_down_value;
    		speedr=(joy_left_y-joy_left_x)/speed_down_value;
    	} 
    	else {
        	speedl=(joy_left_y-joy_left_x)/speed_down_value;
        	speedr=(joy_left_y+joy_left_x)/speed_down_value;	
    	}
    	
    	if(joy.getRawButton(9)){//press button 9 for boost
    		speedl=speedl*2;
    		speedr=speedr*2;
    	}
    	
    	motor1.set(speedl);//set motor speed
    	motor2.set(speedr);
    }
   
}
