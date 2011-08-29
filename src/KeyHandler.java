import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

	private static boolean ctrl = false;
	private static boolean up = false, down = false, left = false, right = false;

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_CONTROL: ctrl = true; break;
		case KeyEvent.VK_UP: up = true; break;
		case KeyEvent.VK_DOWN: down = true; break;
		case KeyEvent.VK_LEFT: left = true; break;
		case KeyEvent.VK_RIGHT: right = true; break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_CONTROL: ctrl = false; break;
		case KeyEvent.VK_UP: up = false; break;
		case KeyEvent.VK_DOWN: down = false; break;
		case KeyEvent.VK_LEFT: left = false; break;
		case KeyEvent.VK_RIGHT: right = false; break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public static boolean isUp() {
		return up;
	}

	public static boolean isDown() {
		return down;
	}

	public static boolean isLeft() {
		return left;
	}

	public static boolean isRight() {
		return right;
	}

	public static boolean isCtrl() {
		return ctrl;
	}
	
	

}
