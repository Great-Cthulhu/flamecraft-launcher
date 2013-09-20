package net.minecraft;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
public class OptionsPanel extends JDialog
{
  private static final long serialVersionUID = 1L;
  public JTextField memoryfield = new JTextField(4);
  public static int memory;
  public static String memorys;

  public OptionsPanel(Frame parent)
  {
    super(parent);

    setModal(true);

    JPanel panel = new JPanel(new BorderLayout());
    JLabel label = new JLabel("Настройки:", 0);
    label.setBorder(new EmptyBorder(0, 0, 16, 0));
    label.setFont(new Font("Default", 1, 16));
    panel.add(label, "North");

    JPanel optionsPanel = new JPanel(new BorderLayout());
    JPanel labelPanel = new JPanel(new GridLayout(0, 1));
    JPanel fieldPanel = new JPanel(new GridLayout(0, 1));
    optionsPanel.add(labelPanel, "West");
    optionsPanel.add(fieldPanel, "Center");

    final JButton forceButton = new JButton("Обновить клиент!");
    forceButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        GameUpdater.forceUpdate = true;
        forceButton.setText("Сейчас начнётся автоматическая скачка!");
        forceButton.setEnabled(false);
      }
    });
    labelPanel.add(new JLabel("Обновление:", 4));
    fieldPanel.add(forceButton);

    labelPanel.add(new JLabel("Расположение клиента на компьютере: ", 4));
    TransparentLabel dirLink = new TransparentLabel(Util.getWorkingDirectory().toString()) { private static final long serialVersionUID = 0L;

      public void paint(Graphics g) { super.paint(g);

        int x = 0;
        int y = 0;

        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(getText());
        int height = fm.getHeight();

        if (getAlignmentX() == 2.0F) x = 0;
        else if (getAlignmentX() == 0.0F) x = getBounds().width / 2 - width / 2;
        else if (getAlignmentX() == 4.0F) x = getBounds().width - width;
        y = getBounds().height / 2 + height / 2 - 1;

        g.drawLine(x + 2, y, x + width - 2, y); }

      public void update(Graphics g)
      {
        paint(g);
      }
    };
    labelPanel.add(new JLabel("Ip cервера по-умолчанию: ", 4));
    
    labelPanel.add(new JLabel("Выбор памяти, МБ:", 4));
    dirLink.setCursor(Cursor.getPredefinedCursor(12));
    dirLink.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent arg0) {
        try {
          Util.openLink(new URL("file://" + Util.getWorkingDirectory().getAbsolutePath()).toURI());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    dirLink.setForeground(new Color(2105599));

    fieldPanel.add(dirLink);
    fieldPanel.add(new JLabel(Config.serverip1, 2));
    
    memory = Util.getMemorySelection(); 
    if (memory == 1 )
    	memory = 1024;
    String memos = Integer.toString(memory);
    memoryfield.setText(memos);
    fieldPanel.add(memoryfield,"mb");

    panel.add(optionsPanel, "Center");
    
    JPanel okPanel = new JPanel(new BorderLayout());
    okPanel.add(new JPanel(), "Center");
    
    JButton okButton = new JButton("Done.");
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
    	memorys = memoryfield.getText();
    	int memory = Integer.parseInt(memorys);
    	if (memory != Util.getMemorySelection()) {
			Util.setMemorySelection(memory);
	        String[] args = null;
			try {
				MinecraftLauncher.main(args);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
        setVisible(false);
      }
      });
    
    JPanel skinsystemPanel = new JPanel(new BorderLayout()); 
    skinsystemPanel.add(new JPanel(), "Center");
    JButton doneButton = new JButton("Cистема скинов");
    doneButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        OptionsPanel.browse("");
        OptionsPanel.this.setVisible(true);
      }
    });
    JButton siteButton = new JButton("Сайт сервера");
    siteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        OptionsPanel.browse1("");
        OptionsPanel.this.setVisible(true);
      }
    });
    JButton forumButton = new JButton("Сохранить!");
    forumButton.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent ae) {
            memorys = memoryfield.getText();
            int memory = Integer.parseInt(memorys);
            if (memory != Util.getMemorySelection()) {
                Util.setMemorySelection(memory);
                String[] args = null;
                try {
                    MinecraftLauncher.main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            setVisible(false);
          }
          });
    
    skinsystemPanel.add(doneButton, "East");
    skinsystemPanel.setBorder(new EmptyBorder(16, 0, 0, 0));
    skinsystemPanel.add(siteButton, "Center");
    skinsystemPanel.add(forumButton, "West");

    panel.add(skinsystemPanel, "South");

    add(panel);
    panel.setBorder(new EmptyBorder(16, 24, 24, 24));
    pack();
    setLocationRelativeTo(parent);
  }

  public static void browse(String url) {
    Desktop desktop = null;
    try {
      desktop = Desktop.getDesktop();
    } catch (Exception ex) {
      System.err.println("ОС не поддердивается....");
      return;
    }
    if (!desktop.isSupported(Desktop.Action.BROWSE)) {
      System.err.println("Операция не поддерживается...");
      return;
    }
    try {
      try {
        desktop.browse(new URL(Config.skinsystem).toURI());
      }
      catch (URISyntaxException e) {
        e.printStackTrace();
      }
    } catch (IOException ex) {
      System.err.println("Ошибка в инициализации пути... " + ex.getLocalizedMessage());
      return;
    }
  }

  public static void browse1(String url)
  {
    Desktop desktop = null;
    try {
      desktop = Desktop.getDesktop();
    } catch (Exception ex) {
      System.err.println("ОС не поддердивается....");
      return;
    }
    if (!desktop.isSupported(Desktop.Action.BROWSE)) {
      System.err.println("Операция не поддерживается...");
      return;
    }
    try {
      try {
        desktop.browse(new URL(Config.site).toURI());
      }
      catch (URISyntaxException e) {
        e.printStackTrace();
      }
    } catch (IOException ex) {
      System.err.println("Ошибка в инициализации пути..." + ex.getLocalizedMessage());
      return;
    }
  }

  public static void browse2(String url)
  {
    Desktop desktop = null;
    try {
      desktop = Desktop.getDesktop();
    } catch (Exception ex) {
      System.err.println("ОС не поддердивается....");
      return;
    }
    if (!desktop.isSupported(Desktop.Action.BROWSE)) {
      System.err.println("Операция не поддерживается...");
      return;
    }
    try {
      try {
        desktop.browse(new URL(Config.forum).toURI());
      }
      catch (URISyntaxException e) {
        e.printStackTrace();
      }
    } catch (IOException ex) {
      System.err.println("Ошибка в инициализации пути..." + ex.getLocalizedMessage());
      return;
    }
  }
}
/**
Лаунчер сделан maximusorg, все вопросы по нему, а также настройка в скайп - K.N.A.Z
 */