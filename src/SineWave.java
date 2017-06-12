import java.awt.BasicStroke; //Graphics 2D ietvaros, nodroðina vienkârðu lîniju zîmeðanu
	import java.awt.Graphics; //Abstrakta klase, kas nodroðina vienkârðu grafisko elementu izveidi
	import java.awt.Graphics2D; //Manto elementus no Graphics klases, un realizç sareþìîtâku grafisko elementu izpildi 
	import java.awt.event.ActionEvent; //Nodroðina darbîbu izpildi gadîjumâ, ja tiek reìistrçta kâda definçta darbîba ko uztver ActionListener
	import java.awt.event.ActionListener; //Seko definçtu darbîbu izpildei (nospiesta poga u.c.) un reìistrçtas darbîbas rezultâtâ dod signâlu, par ðîs darbîbas izpildi klasei ActionEvent 
	import java.awt.event.KeyAdapter; // Abstrakta klase, kas izpilda darbîbas ja nospiesta definçta tastatûras poga
	import java.awt.event.KeyEvent; // Saòem informâciju no KeyAdapter un izpilda definçto darbîbu.
	import javax.swing.JFrame; // Ar JFrame palîdzîbu tiek izveidots vienkârðs  grafiskais programmas logs
	import javax.swing.JPanel; // JPanel atvieglo, logu elementu izveidi un izvietoðanu
	import javax.swing.SwingUtilities; // Daþâdi grafiskâ interfeisa rîki
	import javax.swing.Timer; // Noteiktos laika intervâlos palaiþ vienu vai vairâkas ActionEvent darbîbas

	
class SineWave extends JPanel { 
		private static final long serialVersionUID = 1L;
		public int wavelength = 100, //Sinusoîdas viïna garums
	               amplitude = 50, // Viïòa amplitûda
	               phase = 0, // Sinusa funkcijas leòía nobîde jeb fâze
	               delay = 0, // Sinusoîdas laika aizture
	               thickness = 3; // Sinusoîdas lînijas biezums
	    
	    public SineWave() {
	        setFocusable(true); // Nodroðina fokusu uz atvçrto logu
	        requestFocusInWindow(); // Pieprasa fokusçðanos uz atvçrto programmas logu
	        ActionListener animate = new ActionListener() { //Izveidots klases ActionListener objekts animate
	            public void actionPerformed(ActionEvent ae) { //Metode pârkrâso sinusoîdu, kad tâs pamatlînija no jauna ir uzìenerçta
	                repaint(); //Veic sinusoîdas pârkrâsoðanu
	            }
	        };
	        final Timer timer = new Timer(delay,animate); //Izveidots nemainîgs (final) Timer objekts
	        timer.start(); //Taimeris tiek palaists
	        addKeyListener(new KeyAdapter() {
	    	    @Override
	            public void keyPressed(KeyEvent e) { //Definç funkcijas norâdîtajiem tastatûras taustiòiem
	                switch (e.getKeyCode()) { // Definç tastatûru pogu funkcijas
	                    case KeyEvent.VK_UP: amplitude++; // Nospieþot "uz augðu" tiek palielinâta viïòa amplitûda
	                        break;
	                    case KeyEvent.VK_DOWN: amplitude--; // Nospieþot "uz leju" tiek samazinâta viïòa amplitûda
	                        break;
	                    case KeyEvent.VK_LEFT: wavelength--; // Nospieþot "pa kreisi" tiek samazinâts viïòa garums
	                        break;
	                    case KeyEvent.VK_RIGHT: wavelength++; // Nospieþot "pa labi" tiek palielinâts viïòa garums
	                        break;
	                    case KeyEvent.VK_L: timer.setDelay(++delay); //Nospieþot "L" tiek palielinâta laika aizture
	                        break;
	                    case KeyEvent.VK_A: if (delay > 0) timer.setDelay(--delay); // Nospieþot "A" tiek samazinâta laika aizture
	                        break;
	                    case KeyEvent.VK_R: thickness++; // Nospieþot "R" palieina lînijas biezumu
	                        break;
	                    case KeyEvent.VK_T: if (thickness > 0) thickness--; //Nospieþot "T" samazina lînijas biezumu
	                        break;
	                }
	                System.out.println("Amplituda: " + amplitude + ", Vilna garums: " + wavelength + ", Aizture: " + delay + ", Resnums: " + thickness); // Konsolç izdrukâ paðreizçjâs parametru vçrtîbas
	            }
	        });
	    }
	    
	    @Override
	    public void paintComponent(Graphics g) { // Metode ar parametru, Graphics g, atbild par sinusoîdas attçloðanu
	        Graphics2D g2 = (Graphics2D) g; // Izveido objektu g2 izmantojot klases Graphics 2D elementu g
	        g2.setStroke(new BasicStroke(thickness)); // Uzzîmç sinusoîdas lîniju biezumâ ko ievadîjis programmas autors
	        super.paintComponent(g2); // Notira ieprieks zimeto sinusoîdu
	        for (int x=0; x<500; x++) { 
	            double degrees = phase + x*360.0 / wavelength; 
	            int y = (int)(130+amplitude*Math.sin(degrees*Math.PI/180.0)); // Matemâtiski tiek konstruçta sinusoîdas lîkne
	            g.drawLine(x,y-1,x,y+1); // Tiek uzzimçta sinusoîdas lîkne
	        }
	        phase -= 1; // Leòía nobîde jeb fâze tiek samazinâta par 1 
	        if (phase >= 360) phase %= 360;  // Ja leòía nobîde >= par 360 grâdiem tad tâ tiek pielîdzinâta atlikumam no Phase/360 
	        if (phase < 0) phase += 360; // Ja jaunâ leòía nobîde < par 0  tad tâ tiek palielinâta par 360 grâdiem
	    } //Pçdçjâs 3 koda rindas nodroðina animâcijas efektu
	    
	    public static void main (String args[]) { // Galvenâ metode
	        SwingUtilities.invokeLater(new Runnable() { // Logs tiek atvçrts tikai tad, kad visas sinusoîdas ìenerçðanas darbîbas ir izpildîtas
	            public void run() { // Metode izveido un atver programmas logu
	                JFrame frame = new JFrame("Raimonds Jermaks - Sinewave");
	                SineWave panel = new SineWave(); // Tiek izveidots klases SineWave objekts "panel" - logs kurâ tiek attçlota sinusoîda
	                frame.setSize(500,300); // Norâdîti loga izmçri
	                frame.add(panel); // Tiek ìenerçts logs ar ievadîtajiem parametriem, bet tas vçl nav redzams
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Programma tiek apturçta, ja aizver tâs logu
	                frame.setResizable(false); // Noliedz iespçju programmas izpildes laikâ mainît loga izmçrus
	                frame.setVisible(true); // Logs paliek redzams
	            }
	        });
	    }
	}
