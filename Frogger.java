
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Frogger extends JFrame implements KeyListener {
    
    int score = 0;
    
    Image imageMario = null;

    JPanel p = new JPanel();
    
    int x = 450, y = 500;
    
    class Mario {
        int x, y;
    }
    
    ArrayList<Mario> marios = new ArrayList<Mario>();
    ArrayList<Mario> marios2 = new ArrayList<Mario>();
   
    public Frogger() {
        setTitle("Frogger");
        p.setBounds(0, 0, 1000, 600);
        this.setLayout(null);
        this.setBounds(p.getBounds());
        this.getRootPane().add(p);
        
        this.show();
        this.addKeyListener(this);
        this.play();
        
        this.initMarios();
        this.initMarios2();
    }
    
    private void initMarios() {
        for(int i=0; i<10; i++) {
            Random random = new Random();
            int v = -random.nextInt(1000);
            int w = 0 + random.nextInt(600);
            Mario mario = new Mario();
            mario.x = v;
            mario.y = w;
            marios.add(mario);
        }
    }
    
    private void initMarios2() {
        for(int i=0; i<10; i++) {
            Random random = new Random();
            int v = 1000+random.nextInt(1000);
            int w = 0 + random.nextInt(600);
            Mario mario = new Mario();
            mario.x = v;
            mario.y = w;
            marios2.add(mario);
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            x-=3;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x+=3;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            y+=3;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            y-=3;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyExited(KeyEvent e) {
        
    }
    
    public void play() {
        try {
            imageMario = ImageIO.read(ClassLoader.getSystemResource("mario.png"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        Graphics g = p.getGraphics();
        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        for (int i = 0; i < marios.size(); i++) {
                            g.drawImage(imageMario, marios.get(i).x, marios.get(i).y, 30, 30, null);
                            if ((marios.get(i).x <= x && marios.get(i).x + 30 >= x) && (marios.get(i).y <= y && marios.get(i).y + 30 >= y)) {
                                System.exit(-1);
                            }
                            marios.get(i).x+=2;
                        }
                        for (int i = 0; i < marios2.size(); i++) {
                            g.drawImage(imageMario, marios2.get(i).x, marios2.get(i).y, 30, 30, null);
                            if ((marios2.get(i).x <= x && marios2.get(i).x + 30 >= x) && (marios2.get(i).y <= y && marios2.get(i).y + 30 >= y)) {
                                System.exit(-1);
                            }
                            marios2.get(i).x-=2;
                        }

                        boolean f = false;
                        for (int i = 0; i < marios.size(); i++) {
                            f = false;
                            if(marios.get(i).x > 0)
                                f = true;
                            else
                                f = false;
                        }

                        if(f) {
                            initMarios();
                        }
                        
                        boolean f2 = false;
                        for (int i = 0; i < marios2.size(); i++) {
                            f2 = false;
                            if(marios2.get(i).x < 1000)
                                f2 = true;
                            else
                                f2 = false;
                        }
                        
                        if(f2) {
                            initMarios2();
                        }
                        
                        if(y < -50)
                            y = 700;
                        
                        Image image = ImageIO.read(ClassLoader.getSystemResource("jet.png"));
                        g.drawImage(image, x, y, 30, 30, null);
                    } catch (IOException iOException) {
                    }
                    try {
                        Thread.sleep(25);
                        g.setColor(Color.GREEN);
                        g.fillRect(0, 0, 1000, 600);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    
    public static void main(String arg[]) {
        Frogger f = new Frogger();
    }
}