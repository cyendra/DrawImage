package com.cyendra.drawimage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.cyendra.drawimage.action.ImageAction;
import com.cyendra.drawimage.image.ExImage;
import com.cyendra.drawimage.tool.Tool;
import com.cyendra.drawimage.tool.ToolFactory;

/**
 * 画图窗体
 * @author cyendra
 * @version 1.0
 */
public class ImageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//业务逻辑
	private ImageService service = new ImageService();
	
	//初始化屏幕的尺寸
	private Dimension screenSize = service.getScreenSize();
	
	//设置默认画板
	private JPanel drawSpace = createDrawSpace();
	
	//设置缓冲图片
	private ExImage bufferedImage = new ExImage((int) screenSize.getWidth() / 2,
												(int) screenSize.getHeight() / 2,
												BufferedImage.TYPE_INT_RGB);
	
	//设置当前使用的工具
	private Tool tool = null;
	
	//设置画图对象
	Graphics g = bufferedImage.getGraphics();
	
	//颜色显示面板
	private JPanel currentColorPanel = null;
	
	//颜色选择器
	private JColorChooser colorChooser = getColorChooser();
	
	//菜单的事件监听器
	ActionListener menuListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			service.menuDo(ImageFrame.this, e.getActionCommand());
		}
	};
	
	//默认JScrollPane
	private JScrollPane scroll = null;
	
	// 工具栏
	JPanel toolPanel = createToolPanel();
	
	// 颜色面板
	JPanel colorPanel = createColorPanel();
	
	/**
	 * 默认构造器
	 */
	public ImageFrame() {
		super();
		initialize();
	}

	/**
	 * 初始化 ImageFrame
	 */
	public void initialize() {
		this.setTitle("画图");
		service.initDrawSpace(this);
		tool = ToolFactory.getToolInstance(this, Tool.PENCIL_TOOL);
		MouseMotionListener motionListener = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				tool.mouseDragged(e);
			}
			public void mouseMoved(MouseEvent e) {
				tool.mouseMoved(e);
			}
		};
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				tool.mouseReleased(e);
			}
			public void mousePressed(MouseEvent e) {
				tool.mousePressed(e);
			}
			public void mouseClicked(MouseEvent e) {
				tool.mouseClicked(e);
			}
		};
		drawSpace.addMouseMotionListener(motionListener);
		drawSpace.addMouseListener(mouseListener);
		createMenuBar();
		scroll = new JScrollPane(drawSpace);
		ImageService.setViewport(scroll,drawSpace,bufferedImage.getWidth(),bufferedImage.getHeight());
		this.add(scroll, BorderLayout.CENTER);
		this.add(toolPanel, BorderLayout.WEST);
		this.add(colorPanel, BorderLayout.SOUTH);
		this.pack();
	}

	
	/**
	 * 获取颜色选择器
	 * @return JColorChooser
	 */
	public JColorChooser getColorChooser() {
		if (colorChooser == null) {
			colorChooser = new JColorChooser();
		}
		return colorChooser;
	}
	
	/**
	 * 创建菜单栏
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		String[] menuArr = { "文件(F)", "查看(V)", "颜色(C)", "帮助(H)" };
		String[][] menuItemArr = { { "新建(N)", "打开(O)", "保存(S)", "-", "退出(X)" },
								   { "工具箱(T)", "颜料盒(C)" }, 
								   { "编辑颜色" }, { "帮助主题", "关于" } };
		for (int i = 0; i < menuArr.length; i++) {
			JMenu menu = new JMenu(menuArr[i]);
			for (int j = 0; j < menuItemArr[i].length; j++) {
				if (menuItemArr[i][j].equals("-")) {
					menu.addSeparator();
				}
				else {
					JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
					menuItem.addActionListener(menuListener);
					menu.add(menuItem);
				}
			}
			menuBar.add(menu);
		}
		this.setJMenuBar(menuBar);
	}
	

	/**
	 * 创建简单颜色选择板
	 * @return JPanel
	 */
	public JPanel createColorPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JToolBar toolBar = new JToolBar("颜色");
		toolBar.setFloatable(false);
		toolBar.setMargin(new Insets(2, 2, 2, 2));
		toolBar.setLayout(new GridLayout(2, 6, 2, 2));
		Color[] colorArr = { Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY,
							 Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW };
		JButton[] panelArr = new JButton[colorArr.length];
		currentColorPanel = new JPanel();
		currentColorPanel.setBackground(Color.BLACK);
		currentColorPanel.setPreferredSize(new Dimension(20, 20));
		for (int i = 0; i < panelArr.length; i++) {
			panelArr[i] = new JButton(new ImageAction(colorArr[i],currentColorPanel));
			panelArr[i].setBackground(colorArr[i]);
			toolBar.add(panelArr[i]);
		}
		panel.add(currentColorPanel);
		panel.add(toolBar);
		return panel;
	}

	/**
	 * 创建工具栏 
	 * @return JPanel
	 */
	private JPanel createToolPanel() {
		JPanel panel = new JPanel();
		JToolBar toolBar = new JToolBar("工具");
		toolBar.setOrientation(toolBar.VERTICAL);
		toolBar.setFloatable(true);
		toolBar.setMargin(new Insets(2, 2, 2, 2));
		toolBar.setLayout(new GridLayout(5, 2, 2, 2));
		String[] toolarr = { Tool.PENCIL_TOOL, Tool.BRUSH_TOOL, Tool.COLORPICKED_TOOL,
							 Tool.ATOMIZER_TOOL, Tool.ERASER_TOOL, Tool.LINE_TOOL, Tool.POLYGON_TOOL, 
							 Tool.RECT_TOOL,Tool.ROUND_TOOL, Tool.ROUNDRECT_TOOL };
		for (int i = 0; i < toolarr.length; i++) {
			ImageAction action = new ImageAction(new ImageIcon("img/" + toolarr[i] + ".jpg"), toolarr[i], this);
			JButton button = new JButton(action);
			toolBar.add(button);
		}
		panel.add(toolBar);
		return panel;
	}
	
	/**
	 * 创建画板
	 * @return JPanel
	 */
	private JPanel createDrawSpace() {
		JPanel drawSpace = new DrawSpace();
		// 设置drawSpace的大小
		drawSpace.setPreferredSize(new Dimension((int) screenSize.getWidth(),
				(int) screenSize.getHeight() - 150));
		return drawSpace;
	}
	
	/**
	 * 获取画布
	 * @return JPanel
	 */
	public JPanel getDrawSpace() {
		return this.drawSpace;
	}

	/**
	 * 设置图片
	 * @param bufferedImage MyImage
	 */
	public void setBufferedImage(ExImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	/**
	 * 获取图片 
	 * @return ExImage
	 */
	public ExImage getBufferedImage() {
		return this.bufferedImage;
	}
	
	/**
	 * 获取JScroolPane
	 * @return JScrollPane
	 */
	public JScrollPane getScroll() {
		return this.scroll;
	}

	/**
	 * 获取颜色显示面板
	 * @return JPanel
	 */
	public JPanel getCurrentColorPanel() {
		return this.currentColorPanel;
	}
	
	/**
	 * 获取工具栏
	 * @return JPanel
	 */
	public JPanel getToolPanel() {
		return this.toolPanel;
	}
	
	/**
	 * 设置工具
	 * @param tool Tool
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	/**
	 * 获取工具
	 * @return Tool
	 */
	public Tool getTool() {
		return this.tool;
	}
	
	/**
	 * 获取颜色面板
	 * @return JPanel
	 */
	public JPanel getColorPanel() {
		return this.colorPanel;
	}
	

	/**
	 * 获取screenSize
	 * @return Dimension
	 */
	public Dimension getScreenSize() {
		return this.screenSize;
	}
	
	// 画图区域
	public class DrawSpace extends JPanel {
		private static final long serialVersionUID = 1L;
		/**
		 * 重写void paint( Graphics g )方法
		 * @param g Graphics
		 */
		public void paint(Graphics g) {
			service.repaint(g, bufferedImage);// draw
		}
	}
	
}
