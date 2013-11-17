package com.cyendra.drawimage.tool;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.cyendra.drawimage.ImageFrame;
import com.cyendra.drawimage.ImageService;
import com.cyendra.drawimage.image.ExImage;

/**
 * Tool 抽象类
 * @version  1.0
 * @author cyendra
 */
public abstract class AbstractTool implements Tool {
	
	// 定义ImageFrame
	private ImageFrame frame = null;
	
	// 定义画板的宽与高
	public static int drawWidth = 0;
	public static int drawHeight = 0;
	
	// 定义默认鼠标指针
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	
	// 按下鼠标的坐标
	private int pressX = -1;
	private int pressY = -1;
	
	// 颜色
	public static Color color = Color.BLACK;
	
	/**
	 * 构造一个 Tool
	 * @param frame ImageFrame
	 */
	public AbstractTool(ImageFrame frame) {
		this.frame = frame;
		AbstractTool.drawWidth = frame.getBufferedImage().getWidth();
		AbstractTool.drawHeight = frame.getBufferedImage().getHeight();
	}
	
	/**
	 * 构造一个带鼠标图形的 Tool
	 * @param frame ImageFrame
	 * @param path String
	 */
	public AbstractTool(ImageFrame frame, String path) {
		this(frame);
		this.defaultCursor = ImageService.createCursor(path);
	}

	/**
	 * 获取绘图窗体
	 * @return ImageFrame
	 */
	public ImageFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * 获取默认鼠标指针
	 * @return Cursor 默认鼠标指针
	 */
	public Cursor getDefaultCursor() {
		return this.defaultCursor;
	}
	
	/**
	 * 设置默认鼠标指针
	 * @param cursor Cursor
	 */
	public void setDefaultCursor(Cursor cursor) {
		this.defaultCursor = cursor;
	}

	/**
	 * 设置按下鼠标的x坐标
	 * @param x int
	 */
	public void setPressX(int x) {
		this.pressX = x;
	}

	/**
	 * 设置按下鼠标的y坐标
	 * @param y int
	 */
	public void setPressY(int y) {
		this.pressY = y;
	}

	/**
	 * 获取按下鼠标的x坐标
	 * @return int pressX
	 */
	public int getPressX() {
		return this.pressX;
	}

	/**
	 * 获取按下鼠标的y坐标
	 * @return int pressY
	 */
	public int getPressY() {
		return this.pressY;
	}

	/**
	 * 拖动鼠标
	 * @param e MouseEvent
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		dragBorder(e);// 拖动图形边界
		Graphics g = getFrame().getDrawSpace().getGraphics();
		createShape(e, g);// 画图
	}
	
	//位置常量
	protected static final boolean MID = false;
	protected static final boolean LOW = true;
	//判断鼠标是否在拖动原点上
	protected boolean mouseOn(int x,int y,boolean locx,boolean locy) {
		if (locx==MID&&!(x>(int)AbstractTool.drawWidth/2-4&&x<(int)AbstractTool.drawWidth/2+4))
			return false;
		if (locx==LOW&&!(x>AbstractTool.drawWidth-4&&x<AbstractTool.drawWidth+4))
			return false;
		if (locy==MID&&!(y>(int)AbstractTool.drawHeight/2-4&&y<(int)AbstractTool.drawHeight/2+4))
			return false;
		if (locy==LOW&&!(y>AbstractTool.drawHeight-4&&y<AbstractTool.drawHeight+4))
			return false;
		return true;
	}
	//判断鼠标是否在指定矩形内
	protected boolean mouseOn(int x,int y,int dx,int dy) {
		if (x > 0 && x < dx && y > 0 && y < dy) return true;
		return false;
	}
	
	/**
	 * 移动鼠标
	 * @param e MouseEvent
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();// 获取鼠标现在的坐标
		int y = e.getY();
		Cursor cursor = getDefaultCursor();// 获取默认鼠标指针
		if (mouseOn(x,y,LOW,LOW)) {// 如果鼠标指针在右下
			cursor = new Cursor(Cursor.NW_RESIZE_CURSOR);// 将鼠标指针改变为右下拖动形状
		}
		if (mouseOn(x,y,LOW,MID)) {// 如果鼠标指针在右中
			cursor = new Cursor(Cursor.W_RESIZE_CURSOR);// 将鼠标指针改变为右拖动形状
		}
		if (mouseOn(x,y,MID,LOW)) {// 如果鼠标指针在中下
			cursor = new Cursor(Cursor.S_RESIZE_CURSOR);// 将鼠标指针改变为下拖动形状
		}
		getFrame().getDrawSpace().setCursor(cursor);// 更新鼠标指针
	}

	/**
	 * 松开鼠标
	 * @param e MouseEvent
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		Graphics g = getFrame().getBufferedImage().getGraphics();
		createShape(e, g);// 画图
		setPressX(-1);
		setPressY(-1);
		getFrame().getDrawSpace().repaint();// 重绘
	}
	
	/**
	 * 按下鼠标
	 * @param e MouseEvent
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();// 如果位置在图片范围内，设置按下的坐标
		int y = e.getY();
		if (mouseOn(x,y,AbstractTool.drawWidth,AbstractTool.drawHeight)) {
			setPressX(x);
			setPressY(y);
		}
	}
	
	/**
	 * 点击鼠标
	 * @param e MouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Empty
	}
	
	/**
	 * 拖动图形边界
	 * @param e MouseEvent
	 */
	private void dragBorder(MouseEvent e) {
		getFrame().getBufferedImage().setIsSaved(false);
		// 获取鼠标现在的x与y坐标
		int cursorType = getFrame().getDrawSpace().getCursor().getType();
		int x = cursorType == Cursor.S_RESIZE_CURSOR ? AbstractTool.drawWidth : e.getX();
		int y = cursorType == Cursor.W_RESIZE_CURSOR ? AbstractTool.drawHeight : e.getY();
		ExImage img = null;
		// 如果鼠标指针是拖动状态
		if ((cursorType == Cursor.NW_RESIZE_CURSOR
				|| cursorType == Cursor.W_RESIZE_CURSOR || cursorType == Cursor.S_RESIZE_CURSOR)
				&& (x > 0 && y > 0)) {
			// 改变图像大小
			img = new ExImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			g.setColor(Color.WHITE);
			g.drawImage(getFrame().getBufferedImage(),0,0,AbstractTool.drawWidth,AbstractTool.drawHeight,null);
			getFrame().setBufferedImage(img);
			// 设置画布的大小
			AbstractTool.drawWidth = x;
			AbstractTool.drawHeight = y;
			// 设置viewport
			ImageService.setViewport(frame.getScroll(), frame.getDrawSpace(), x, y);
		}
	}
	
	/**
	 * 画图形
	 * @param e MouseEvent
	 * @param g Graphics
	 */
	private void createShape(MouseEvent e, Graphics g) {
		// 如果位置在画布内
		if (getPressX() > 0 && getPressY() > 0 
				&& mouseOn(e.getX(),e.getY(),AbstractTool.drawWidth,AbstractTool.drawHeight)) {
			// 将整张图片重画
			g.drawImage(getFrame().getBufferedImage(),0,0,AbstractTool.drawWidth,AbstractTool.drawHeight,null);
			// 设置颜色
			g.setColor(AbstractTool.color);
			getFrame().getBufferedImage().setIsSaved(false);
			// 画图形
			draw(g, getPressX(), getPressY(), e.getX(), e.getY());
		}
	}
	
	/**
	 * 画图形
	 * @param g Graphics
	 * @param x1 起点x坐标
	 * @param y1 起点y坐标
	 * @param x2 终点x坐标
	 * @param y2 终点y坐标
	 */
	private void draw(Graphics g, int x1, int y1, int x2, int y2) {
		// Empty
	}

}
