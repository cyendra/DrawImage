package com.cyendra.drawimage.tool;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.cyendra.drawimage.ImageFrame;

/**
 * 喷墨工具
 * @author cyendra
 * @version 1.0
 */
public class AtomizerTool extends AbstractTool {
	private static Tool tool = null;

	private AtomizerTool(ImageFrame frame) {
		super(frame, "img/atomizercursor.gif");
	}

	public static Tool getInstance(ImageFrame frame) {
		if (tool == null) {
			tool = new AtomizerTool(frame);
		}
		return tool;
	}

	/**
	 * 按下鼠标
	 * @param e MouseEvent
	 */
	public void mousePressed(MouseEvent e) {
		int x = e.getX();// 如果位置在图片范围内，设置按下的坐标
		int y = e.getY();
		if (super.mouseOn(x,y,AbstractTool.drawWidth,AbstractTool.drawHeight)) {
			setPressX(x);
			setPressY(y);
			Graphics g = getFrame().getBufferedImage().getGraphics();
			draw(e, g);
		}
	}

	/**
	 * 拖动鼠标
	 * @param e MouseEvent
	 */
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		Graphics g = getFrame().getBufferedImage().getGraphics();
		draw(e, g);
	}

	/**
	 * 画图
	 * @param e MouseEvent
	 * @param g Graphics
	 */
	public void draw(MouseEvent e, Graphics g) {
		int x = 0;
		int y = 0;
		// 喷枪大小
		int size = 8;
		// 喷枪点数
		int count = 10;
		if (getPressX() > 0 && getPressY() > 0
				&& e.getX() < AbstractTool.drawWidth
				&& e.getY() < AbstractTool.drawHeight) {
			g.setColor(AbstractTool.color);
			for (int i = 0; i < count; i++) {
				x = new Random().nextInt(size) + 1;
				y = new Random().nextInt(size) + 1;
				g.fillRect(e.getX() + x, e.getY() + y, 1, 1);
			}
			setPressX(e.getX());
			setPressY(e.getY());
			getFrame().getDrawSpace().repaint();
		}
	}
}
