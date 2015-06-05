/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adams_garbage;

/**

 @author adam
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UnsupportedLookAndFeelException;

public class colorSpiral extends JPanel implements ActionListener{

    //play with the vals.
    public static int NUM_DOTS=54;
    public int SCREEN_SIZE=900;
	public int SCREEN_X=1080;
	public int SCREEN_Y=1080;
    public double SECONDS_PER_CYCLE=60;
    public int DOT_WIDTH=10;
    public static Color[] colors;
    public static Timer timer;
	public static final boolean FAST_INSIDE=true;
    public double angle=Math.PI*1.89;
    public int[] x,y;
	public static JFrame frame;
	public JFrame main;
    public static JTextField NUMBER_DOTS=new JTextField("number of dots (default is 45)"),
			SCREEN_WIDTH=new JTextField("Screen Width (default is 900)"),
			SCREEN_HEIGHT=new JTextField("Screen Height (default is 900)"),
			DOT_SIZE=new JTextField("Width of each dot (default is 10)"),
			SPEED=new JTextField("number of seconds for full cycle (default is 60)");
	public static JButton start;
	public static colorSpiral adam;
    public static void main(String[] args) {
		frame=new JFrame("Frame");
		frame.setSize(300, 300);
		JPanel params=new JPanel();
		params.setLayout(new BoxLayout(params, BoxLayout.Y_AXIS));
		params.setSize(300,300);
		NUMBER_DOTS.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                NUMBER_DOTS.setText("");
            }

			@Override
			public void focusLost(FocusEvent e) {
				if(NUMBER_DOTS.getText().isEmpty())
					NUMBER_DOTS.setText("Number of dots (default is 45)");
			}
        });
		NUMBER_DOTS.setColumns(10);
		//params.add(new JTextField("Number of Dots (enter value below)"));
		params.add(NUMBER_DOTS);
		SCREEN_HEIGHT.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                SCREEN_HEIGHT.setText("");
            }

			@Override
			public void focusLost(FocusEvent e) {
				if(SCREEN_HEIGHT.getText().isEmpty())
					SCREEN_HEIGHT.setText("Screen height (default is 900)");
			}
        });SCREEN_HEIGHT.setColumns(10);
		//params.add(new JTextField("Screen height (enter value below)"));
		params.add(SCREEN_HEIGHT);
		SCREEN_WIDTH.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                SCREEN_WIDTH.setText("");
            }

			@Override
			public void focusLost(FocusEvent e) {
				if(SCREEN_WIDTH.getText().isEmpty())
					SCREEN_WIDTH.setText("Screen width (default is 900)");
			}
        });SCREEN_WIDTH.setColumns(10);
		//params.add(new JTextField("Screen height (enter value below)"));
		params.add(SCREEN_WIDTH);
		DOT_SIZE.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                DOT_SIZE.setText("");
            }

			@Override
			public void focusLost(FocusEvent e) {
				if(DOT_SIZE.getText().isEmpty())
				DOT_SIZE.setText("Width of each dot (default is 10)");
			}
        });DOT_SIZE.setColumns(10);
		//params.add(new JTextField("Dot width (enter value below)"));
		params.add(DOT_SIZE);
		SPEED.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                SPEED.setText("");
            }

			@Override
			public void focusLost(FocusEvent e) {
			if(SPEED.getText().isEmpty())
				SPEED.setText("Seconds per full revolution (default is 60)");
			}
        });SPEED.setColumns(10);
		//params.add(new JTextField("Seconds per full revolution (enter value below)"));
		params.add(SPEED);
        params.setVisible(true);
        //params.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adam=new colorSpiral();
		start=new JButton("Start");
		start.addActionListener(adam);
		params.add(start);
		frame.add(params);
		frame.setVisible(true);
    }
    public void init(){

		main=new JFrame("adams_garbage");
        main.setSize(adam.SCREEN_X,adam.SCREEN_Y);
		main.setUndecorated(true);
        main.setVisible(true);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        adam.setBackground(Color.BLACK);
        adam.setSize(adam.SCREEN_X,adam.SCREEN_Y);
        timer = new Timer(5, adam);
		timer.setActionCommand("timer");
        timer.start();
		initColors(
				10,10,//red
				250,10,//green
				10,250//blue
		);
		
        for(int a=0;a<adam.NUM_DOTS;a++)
        {
            adam.x[a]=adam.SCREEN_X-a*adam.DOT_WIDTH;
            adam.y[a]=(adam.SCREEN_Y)/2;
        }
        main.add(adam);
        adam.setVisible(true);
	}
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
		for(int a=0;a<NUM_DOTS;a++){
			g2d.setColor(colors[a]);
			g2d.fillOval(x[a]-DOT_WIDTH/2, 
					y[a]-DOT_WIDTH/2,
					DOT_WIDTH,
					DOT_WIDTH);
		}
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("timer")){
                    try {
                        move();
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(colorSpiral.class.getName()).log(Level.SEVERE, null, ex);
                    }
        repaint();
		}
		else {
			if(timer!=null)timer.stop();
			if(main!=null){main.removeAll();
			main.setVisible(false);
			}
			try{SCREEN_X=Integer.parseInt(SCREEN_WIDTH.getText());}catch(NumberFormatException ex){SCREEN_X=900;}
			try{SCREEN_Y=Integer.parseInt(SCREEN_HEIGHT.getText());}catch(NumberFormatException ex){SCREEN_Y=900;}
			try{DOT_WIDTH=Integer.parseInt(DOT_SIZE.getText());}catch(NumberFormatException ex){DOT_WIDTH=10;}
			try{NUM_DOTS=Integer.parseInt(NUMBER_DOTS.getText());}catch(NumberFormatException ex){NUM_DOTS=45;}
			y=new int[NUM_DOTS];
			x=new int[NUM_DOTS];
			colors=new Color[NUM_DOTS];
			
			try{SECONDS_PER_CYCLE=Integer.parseInt(SPEED.getText());}catch(NumberFormatException ex){SECONDS_PER_CYCLE=60;}
			init();}
    }
    
    public void move() throws LineUnavailableException
    {
        angle+=Math.PI/(100*SECONDS_PER_CYCLE);
        angle=angle%(2.0*Math.PI);
		double angleA;
        for(int a=0;a<NUM_DOTS;a++)
        {
            
			angleA=((angle+0)*(NUM_DOTS+(FAST_INSIDE?-a:a)))%(Math.PI*2.0);
//                        if(angleA<=Math.PI/(100*SECONDS_PER_CYCLE)*(NUM_DOTS+(FAST_INSIDE?-a:a)))
//                            playSound(a);
            x[a]=(int) (Math.round((SCREEN_X/2-(NUM_DOTS-a)*DOT_WIDTH)*Math.cos(angleA)))+SCREEN_X/2;
            y[a]=(int) (Math.round((SCREEN_Y/2-(NUM_DOTS-a)*DOT_WIDTH)*Math.sin(angleA)))+SCREEN_Y/2;
        }
    }
	public static void initColors(int startRed,int endRed, int startBlue, int endBlue, int startGreen, int endGreen)
	{
		for(int a=0;a<NUM_DOTS;a++)
			colors[a]=new Color((endRed-startRed)*a/NUM_DOTS+startRed,
								(endGreen-startGreen)*a/NUM_DOTS+startGreen,
								(endBlue-startBlue)*a/NUM_DOTS+startBlue
		);
        }
void playSound(int index) throws LineUnavailableException {
    byte[] buf = new byte[ 1 ];;
    AudioFormat af = new AudioFormat( (float )44100, 8, 1, true, false );
    SourceDataLine sdl = AudioSystem.getSourceDataLine( af );
    sdl.open();
    sdl.start();
    for( int i = 0; i < 10 * (float )((318600-300)*index/NUM_DOTS+100300) / 1000; i++ ) {
        double angle = i / ( (float ) ((318600-300)*index/NUM_DOTS+100300)/ 440 ) * 2.0 * Math.PI;
        buf[ 0 ] = (byte )( Math.sin( angle ) * 10 );
        sdl.write( buf, 0, 1 );
    }
    sdl.drain();
    sdl.stop();
    
    }
}