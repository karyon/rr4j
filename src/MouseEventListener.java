import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseEventListener implements MouseListener, MouseMotionListener{
	
	/**
	 * Coordinates of the last press of a mouse button.
	 */
	private static int mousePressedX, mousePressedY;
	/**
	 * Current coordinates of the mouse. Only updated when a mouse button is pressed and dragged.
	 */
	private static int mouseCurrX, mouseCurrY;
	
	private static int buttonPressed = MouseEvent.NOBUTTON;
	
	private static boolean drawSelectionRect = false;
	

	
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		//if some button is already pressed, ignore this additional press.
		if (buttonPressed != MouseEvent.NOBUTTON)
			return;
		buttonPressed = arg0.getButton();
		
		mousePressedX = arg0.getX();
		mousePressedY = arg0.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		//if a button is released which is not in buttonPressed, ignore this release.
		if (arg0.getButton() != buttonPressed)
			return;
		buttonPressed = MouseEvent.NOBUTTON;
		
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			if (Menu.buttonHit(arg0.getX(),arg0.getY())){
				if(Menu.isDisaim()){
					MouseHandler.singleSelect(arg0.getX(),arg0.getY());System.out.println("k");
				}
				
				return;
			}
			if(Menu.isDisaim())
				MouseHandler.clearSelection();
		}
		
		int[] selectionRect = getSelectionRect();
		drawSelectionRect = false;
		
		//handle a small drag like a click.
		if (selectionRect == null || selectionRect[2] + selectionRect[3] < 4) {
			MouseHandler.mouseClick(arg0.getX() + Painter.getOffsetX(), arg0.getY() + Painter.getOffsetY(), arg0.getButton());
			return;
		}
		
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			MouseHandler.multiSelect(selectionRect[0] + Painter.getOffsetX(), selectionRect[1] + Painter.getOffsetY(), selectionRect[2], selectionRect[3]);
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if (buttonPressed != MouseEvent.BUTTON1)
			return;
		drawSelectionRect = true;
		mouseCurrX = e.getX();
		mouseCurrY = e.getY();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	
	public static int[] getSelectionRect() {
		if (!drawSelectionRect)
			return null;
		int startX = (mousePressedX < mouseCurrX) ? mousePressedX : mouseCurrX;
		int startY = (mousePressedY < mouseCurrY) ? mousePressedY : mouseCurrY;
		int width = (mousePressedX >= mouseCurrX) ? mousePressedX - startX: mouseCurrX - startX;
		int height = (mousePressedY >= mouseCurrY) ? mousePressedY - startY: mouseCurrY - startY;
		return new int[] {startX, startY, width, height};
	}
	
}
