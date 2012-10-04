package squaredraw;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SquarePanel extends JPanel implements MouseListener {
	private Point x;
	private Color fillColor;

	public SquarePanel() {
		super();
		fillColor = Color.WHITE;
		setPreferredSize(new Dimension(200, 80));
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		x = arg0.getPoint();
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		x = arg0.getPoint();
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(fillColor);
		if (x!=null) {
			g.fillRect(x.x, x.y, 50, 50);
		}

	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color color) {
		this.fillColor = color;
	}


}
