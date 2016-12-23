package Systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase {
	static final int motor1_channel = 1;
	static final int motor2_channel = 0;
	static final int Joystick_port = 0;
	static final int joy_select = 0;//0 is XBox 1 is 3DPro
	
	static final double error_range = 0.04;
	
    static VictorSP motor1;
    static VictorSP motor2;
    static Joystick joy;
    static double speedl = 0, speedr = 0, speed_down_value = 4.0, joy_left_x, joy_left_y;
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
		SmartDashboard.putNumber("X",joy_left_x);
		SmartDashboard.putNumber("Y",joy_left_y);
		speed_down_value = SmartDashboard.getNumber("speed_down_value");
		SmartDashboard.putBoolean("speedup", speedup);
		joy_mode_Selected = (String) joy_mode.getSelected();
		
    }
    
    public static void teleOp(){
    	dashboard();
    	
    	
    	
    	
    	switch(joy_mode_Selected) {
    		case joy_xbox:
            //Control with XBox remote 
    			if(joy.getRawAxis(1) <= -error_range){
    	    		joy_left_y=-joy.getRawAxis(1);
    	    		joy_left_y=joy_left_y*100;
    	    		joy_left_y=joy_left_y-(joy_left_y%(error_range*100));
    	    		joy_left_y=joy_left_y/100;
    	    	}else if(joy.getRawAxis(1)>=error_range){
    	    		joy_left_y=joy.getRawAxis(1);
    	    		joy_left_y=joy_left_y*100;
    	    		joy_left_y=joy_left_y-(joy_left_y%(error_range*100));
    	    		joy_left_y=joy_left_y/100;
    	    		joy_left_y=-joy_left_y;
    	    	}else {
    	    		joy_left_y=0;
    	    	}
    	    	if(joy.getRawAxis(0) <= -error_range){
    	    		joy_left_x=-joy.getRawAxis(0);
    	    		joy_left_x=joy_left_x*100;
    	    		joy_left_x=joy_left_x-(joy_left_x%(error_range*100));
    	    		joy_left_x=joy_left_x/100;
    	    		joy_left_x=-joy_left_x;
    	    	}else if(joy.getRawAxis(0) >= error_range){
    	    		joy_left_x=joy.getRawAxis(0);
    	    		joy_left_x=joy_left_x*100;
    	    		joy_left_x=joy_left_x-(joy_left_x%(error_range*100));
    	    		joy_left_x=joy_left_x/100;
    	    	}else{
    	    		joy_left_x=0;
    	    	}
    	    	//	speedl=(joy_left_y+joy_left_x);
        	    //	speedr=(joy_left_y-joy_left_x);
        	    	speedr=(joy_left_y+joy_left_x)/speed_down_value;
        	    	speedl=(joy_left_y-joy_left_x)/speed_down_value;
        	  
        	    if(joy.getRawButton(9)){
        	    speedl=speedl*2;
        	    speedr=speedr*2;
        	    }
        		motor1.set(speedl);
        		motor2.set(speedr);
                break;
        	case joy_3dpro:
        		
        	default:
        		//fix joystick's error
        		if(joy.getRawAxis(1) < 0){
            		if(joy.getRawAxis(1)>-error_range) joy_left_y = 0;
            		else joy_left_y = joy.getRawAxis(1);
            	}
            	else {
            		if(joy.getRawAxis(1)< error_range) joy_left_y = 0;
            		else joy_left_y = joy.getRawAxis(1);
            	}
            	
            	if(joy.getRawAxis(0) < 0){
            		if(joy.getRawAxis(0)>error_range) joy_left_x = 0;
            		else joy_left_x = joy.getRawAxis(0);
            	}
            	else {
            		if(joy.getRawAxis(0)< error_range) joy_left_x = 0;
            		else joy_left_x = joy.getRawAxis(0);
            	}
            	//fix joystick's error
        		if(joy.getRawAxis(1) < 0){
            		if(joy.getRawAxis(1)>-error_range) joy_left_y = 0;
            		else joy_left_y = joy.getRawAxis(1);
            	}
            	else {
            		if(joy.getRawAxis(1)< error_range) joy_left_y = 0;
            		else joy_left_y = joy.getRawAxis(1);
            	}
            	
            	if(joy.getRawAxis(0) < 0){
            		if(joy.getRawAxis(0)>error_range) joy_left_x = 0;
            		else joy_left_x = joy.getRawAxis(0);
            	}
            	else {
            		if(joy.getRawAxis(0)< error_range) joy_left_x = 0;
            		else joy_left_x = joy.getRawAxis(0);
            	}
            	
        	//Control with 3DPro remote
        		if(joy.getRawAxis(1) < 0){
            		speedl=(-joy_left_y-joy_left_x-joy.getRawAxis(2));//left wheel's speed
            		speedr=(-joy_left_y+joy_left_x+joy.getRawAxis(2));//right wheel's speed
            	} else {
                	speedl=(-joy_left_y+joy_left_x-joy.getRawAxis(2));//left wheel's speed
                	speedr=(-joy_left_y-joy_left_x+joy.getRawAxis(2));//right wheel's speed
            	}
                //test
        		speedl=speedl/speed_down_value;
        		speedr=speedr/speed_down_value;
            	
            	if(joy.getRawButton(9)){//press button 9 for boost
            		speedl=speedl*2;
            		speedr=speedr*2;
            		speedup = true;
            	}
            	else{
            		speedup = false;
            	}
        		motor1.set(speedl);
        		motor2.set(speedr);
                break;
    	}
    	/*
		speedl=speedl/speed_down_value;
		speedr=speedr/speed_down_value;
    	
    	if(joy.getRawButton(9)){//press button 9 for boost
    		speedl=speedl*2;
    		speedr=speedr*2;
    		speedup = true;
    	}
    	else{
    		speedup = false;
    	}
		motor1.set(speedl);
		motor2.set(speedr);*/
    }
}
