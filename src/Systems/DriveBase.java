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
	
    static VictorSP motor1;
    static VictorSP motor2;
    static VictorSP frontmotor1;
    static Joystick joy;
    
    static double speedl = 0, speedr = 0, speed_down_value = 4.0, a, b;
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
		if(status_frontmotor1 == 1){
			SmartDashboard.putString("frontmotor1_status", "UP");
		}
		else if(status_frontmotor1 == 2){
			SmartDashboard.putString("frontmotor1_status", "DOWN");
		}
		else{
			SmartDashboard.putString("frontmotor1_status", "STOP");
		}
    }
    
    public static void teleOp(){
    	dashboard();
    	if(joy.getRawAxis(1) < 0){
    		a=-joy.getRawAxis(1);
    		a=a-a%0.04;
    	}else {
    		a=joy.getRawAxis(1);
    		a=a-a%0.04;
    		a=a*-1;
    	}
    	if(joy.getRawAxis(0) < 0){
    		b=-joy.getRawAxis(0);
    		b=b%0.04;
    		b=-1*b;
    	}else {
    		b=joy.getRawAxis(0);
    		b=b%0.04;
    	}
    	if(joy.getRawAxis(1) < 0){
    	speedl=(a+b)/4;
    	speedr=(a-b)/4;
    	} else {
        	speedl=(a-b)/4;
        	speedr=(a+b)/4;	
    	}
    if(joy.getRawButton(9)){
    speedl=speedl*2;
    speedr=speedr*2;
    }
    if(joy.getRawButton(5)){
    	frontmotor1.set(0.5);
    	status_frontmotor1 = 2;
    //	frontmotor2.set(-0.5);
    }else if(joy.getRawButton(6)){
    	frontmotor1.set(-0.5);
    	status_frontmotor1 = 1;
    	//frontmotor2.set(0.5);
    }else {
    	frontmotor1.set(0);
   // 	frontmotor2.set(0);
    }
	motor1.set(speedl);
	motor2.set(speedr);
	SmartDashboard.putNumber("left", motor1.get());
	SmartDashboard.putNumber("right", motor2.get());
    }
   
}
