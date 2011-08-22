import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

	private static boolean ctrl = false;
	public static boolean isCtrl() {
		return ctrl;
	}


	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_CONTROL:
			ctrl = true;
			break;
		}
		
	}

	
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()){
		case KeyEvent.VK_CONTROL:
			ctrl = false;
			break;
		}
		
	}

	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
