import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

	private static boolean ctrl = false;
	public static boolean isCtrl() {
		return ctrl;
	}


	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_CONTROL: ctrl = true; break;
		case KeyEvent.VK_UP: Painter.setDirection(Painter.DIRECTION_UP); break;
		case KeyEvent.VK_DOWN: Painter.setDirection(Painter.DIRECTION_DOWN); break;
		case KeyEvent.VK_LEFT: Painter.setDirection(Painter.DIRECTION_LEFT); break;
		case KeyEvent.VK_RIGHT: Painter.setDirection(Painter.DIRECTION_RIGHT); break;
		}
		
	}

	
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()){
		case KeyEvent.VK_CONTROL:
			ctrl = false;
			break;

		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT: Painter.setDirection(Painter.DIRECTION_NONE); break;
		}
		
	}

	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
