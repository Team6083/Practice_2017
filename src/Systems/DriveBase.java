package Systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase {
	static final int motor1_channel = 0;
	static final int motor2_channel = 1;
	static final int Joystick_port = 0;
	static final int joy_select = 1;//0 is XBox 1 is 3DPro
	
    static VictorSP motor1;
    static VictorSP motor2;
    static Joystick joy;
    static double speedl = 0, speedr = 0, speed_down_value = 4.0;
    static boolean speedup = false;
    
    final static String joy_xbox = "Xbox";
    final static String joy_3dpro = "3D Pro";
    static String joy_mode_Selected;
    static SendableChooser joy_mode;
    
    public static void init(){
    	motor1 = new VictorSP(motor1_channel);
    	motor2 = new VictorSP(motor2_channel);
    	joy= new Joystick(Joystick_port);
    	SmartDashboard.putNumber("speed_down_value", speed_down_value);
    	joy_mode = new SendableChooser();
    	joy_mode.addDefault("3D Pro", joy_3dpro);
    	joy_mode.addObject("Xbox", joy_xbox);
        SmartDashboard.putData("joy_control_mode", joy_mode);
    }
    
    public static void dashboard(){
		SmartDashboard.putNumber("left", motor1.get());
		SmartDashboard.putNumber("right", motor2.get());
		speed_down_value = SmartDashboard.getNumber("speed_down_value");
		SmartDashboard.putBoolean("speedup", speedup);
		joy_mode_Selected = (String) joy_mode.getSelected();
		
    }
    
    public static void teleOp(){
    	dashboard();
    	switch(joy_mode_Selected) {
    		case joy_xbox:
            //Control with XBox remote 
    			
                break;
        	case joy_3dpro:
        	default:
        	//Control with 3DPro remote
        		if(joy.getRawAxis(1) < 0){
            		speedl=(-joy.getRawAxis(1)-joy.getRawAxis(0)-joy.getRawAxis(2))/speed_down_value;//left wheel's speed
            		speedr=(-joy.getRawAxis(1)+joy.getRawAxis(0)+joy.getRawAxis(2))/speed_down_value;//right wheel's speed
            	} else {
                	speedl=(-joy.getRawAxis(1)+joy.getRawAxis(0)-joy.getRawAxis(2))/speed_down_value;//left wheel's speed
                	speedr=(-joy.getRawAxis(1)-joy.getRawAxis(0)+joy.getRawAxis(2))/speed_down_value;//right wheel's speed
            	}
                break;
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
    		speedup = true;
    	}
    	else{
    		speedup = false;
    	}
		motor1.set(speedl);
		motor2.set(speedr);
    }
}
