import java.awt.BasicStroke; //Graphics 2D ietvaros, nodro�ina vienk�r�u l�niju z�me�anu
	import java.awt.Graphics; //Abstrakta klase, kas nodro�ina vienk�r�u grafisko elementu izveidi
	import java.awt.Graphics2D; //Manto elementus no Graphics klases, un realiz� sare���t�ku grafisko elementu izpildi 
	import java.awt.event.ActionEvent; //Nodro�ina darb�bu izpildi gad�jum�, ja tiek re�istr�ta k�da defin�ta darb�ba ko uztver ActionListener
	import java.awt.event.ActionListener; //Seko defin�tu darb�bu izpildei (nospiesta poga u.c.) un re�istr�tas darb�bas rezult�t� dod sign�lu, par ��s darb�bas izpildi klasei ActionEvent 
	import java.awt.event.KeyAdapter; // Abstrakta klase, kas izpilda darb�bas ja nospiesta defin�ta tastat�ras poga
	import java.awt.event.KeyEvent; // Sa�em inform�ciju no KeyAdapter un izpilda defin�to darb�bu.
	import javax.swing.JFrame; // Ar JFrame pal�dz�bu tiek izveidots vienk�r�s  grafiskais programmas logs
	import javax.swing.JPanel; // JPanel atvieglo, logu elementu izveidi un izvieto�anu
	import javax.swing.SwingUtilities; // Da��di grafisk� interfeisa r�ki
	import javax.swing.Timer; // Noteiktos laika interv�los palai� vienu vai vair�kas ActionEvent darb�bas

	
class SineWave extends JPanel { 
		private static final long serialVersionUID = 1L;
		public int wavelength = 100, //Sinuso�das vi�na garums
	               amplitude = 50, // Vi��a amplit�da
	               phase = 0, // Sinusa funkcijas le��a nob�de jeb f�ze
	               delay = 0, // Sinuso�das laika aizture
	               thickness = 3; // Sinuso�das l�nijas biezums
	    
	    public SineWave() {
	        setFocusable(true); // Nodro�ina fokusu uz atv�rto logu
	        requestFocusInWindow(); // Pieprasa fokus��anos uz atv�rto programmas logu
	        ActionListener animate = new ActionListener() { //Izveidots klases ActionListener objekts animate
	            public void actionPerformed(ActionEvent ae) { //Metode p�rkr�so sinuso�du, kad t�s pamatl�nija no jauna ir uz�ener�ta
	                repaint(); //Veic sinuso�das p�rkr�so�anu
	            }
	        };
	        final Timer timer = new Timer(delay,animate); //Izveidots nemain�gs (final) Timer objekts
	        timer.start(); //Taimeris tiek palaists
	        addKeyListener(new KeyAdapter() {
	    	    @Override
	            public void keyPressed(KeyEvent e) { //Defin� funkcijas nor�d�tajiem tastat�ras tausti�iem
	                switch (e.getKeyCode()) { // Defin� tastat�ru pogu funkcijas
	                    case KeyEvent.VK_UP: amplitude++; // Nospie�ot "uz aug�u" tiek palielin�ta vi��a amplit�da
	                        break;
	                    case KeyEvent.VK_DOWN: amplitude--; // Nospie�ot "uz leju" tiek samazin�ta vi��a amplit�da
	                        break;
	                    case KeyEvent.VK_LEFT: wavelength--; // Nospie�ot "pa kreisi" tiek samazin�ts vi��a garums
	                        break;
	                    case KeyEvent.VK_RIGHT: wavelength++; // Nospie�ot "pa labi" tiek palielin�ts vi��a garums
	                        break;
	                    case KeyEvent.VK_L: timer.setDelay(++delay); //Nospie�ot "L" tiek palielin�ta laika aizture
	                        break;
	                    case KeyEvent.VK_A: if (delay > 0) timer.setDelay(--delay); // Nospie�ot "A" tiek samazin�ta laika aizture
	                        break;
	                    case KeyEvent.VK_R: thickness++; // Nospie�ot "R" palieina l�nijas biezumu
	                        break;
	                    case KeyEvent.VK_T: if (thickness > 0) thickness--; //Nospie�ot "T" samazina l�nijas biezumu
	                        break;
	                }
	                System.out.println("Amplituda: " + amplitude + ", Vilna garums: " + wavelength + ", Aizture: " + delay + ", Resnums: " + thickness); // Konsol� izdruk� pa�reiz�j�s parametru v�rt�bas
	            }
	        });
	    }
	    
	    @Override
	    public void paintComponent(Graphics g) { // Metode ar parametru, Graphics g, atbild par sinuso�das att�lo�anu
	        Graphics2D g2 = (Graphics2D) g; // Izveido objektu g2 izmantojot klases Graphics 2D elementu g
	        g2.setStroke(new BasicStroke(thickness)); // Uzz�m� sinuso�das l�niju biezum� ko ievad�jis programmas autors
	        super.paintComponent(g2); // Notira ieprieks zimeto sinuso�du
	        for (int x=0; x<500; x++) { 
	            double degrees = phase + x*360.0 / wavelength; 
	            int y = (int)(130+amplitude*Math.sin(degrees*Math.PI/180.0)); // Matem�tiski tiek konstru�ta sinuso�das l�kne
	            g.drawLine(x,y-1,x,y+1); // Tiek uzzim�ta sinuso�das l�kne
	        }
	        phase -= 1; // Le��a nob�de jeb f�ze tiek samazin�ta par 1 
	        if (phase >= 360) phase %= 360;  // Ja le��a nob�de >= par 360 gr�diem tad t� tiek piel�dzin�ta atlikumam no Phase/360 
	        if (phase < 0) phase += 360; // Ja jaun� le��a nob�de < par 0  tad t� tiek palielin�ta par 360 gr�diem
	    } //P�d�j�s 3 koda rindas nodro�ina anim�cijas efektu
	    
	    public static void main (String args[]) { // Galven� metode
	        SwingUtilities.invokeLater(new Runnable() { // Logs tiek atv�rts tikai tad, kad visas sinuso�das �ener��anas darb�bas ir izpild�tas
	            public void run() { // Metode izveido un atver programmas logu
	                JFrame frame = new JFrame("Raimonds Jermaks - Sinewave");
	                SineWave panel = new SineWave(); // Tiek izveidots klases SineWave objekts "panel" - logs kur� tiek att�lota sinuso�da
	                frame.setSize(500,300); // Nor�d�ti loga izm�ri
	                frame.add(panel); // Tiek �ener�ts logs ar ievad�tajiem parametriem, bet tas v�l nav redzams
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Programma tiek aptur�ta, ja aizver t�s logu
	                frame.setResizable(false); // Noliedz iesp�ju programmas izpildes laik� main�t loga izm�rus
	                frame.setVisible(true); // Logs paliek redzams
	            }
	        });
	    }
	}
