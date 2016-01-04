import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.glass.events.KeyEvent;

public class TxtEncrypt {
	
	public static void main(String args[]) throws IOException{
	     MyFrame f = new MyFrame();
		}
	
}

class MyFrame extends JFrame  {
	private JTextField encKeyBox, encKeyBox2;
	private JButton encryptButton, decryptButton, affineButton, caesarButton, vigenereButton;
    private JMenu menu;
    private JMenuBar menuBar;
    private String cipher = "caesar";
  public MyFrame(){ 
    setSize(600,300);
    setTitle("Text File Cipher");  
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    
	Container container = getContentPane();
	container.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	
		JPanel p1=new JPanel();
		c.gridx = 1;
		c.gridy = 1;
		//c.anchor = GridBagConstraints.CENTER;
		container.add(p1, c);
		JPanel p2 = new JPanel();
		c.gridx = 0;
		c.gridy = 2;
		container.add(p2, c);
		JPanel p3 = new JPanel();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.anchor = GridBagConstraints.FIRST_LINE_START;
		container.add(p3, c);
		JPanel p4=new JPanel();
		c.gridx = 2;
		c.gridy = 2;
		container.add(p4, c);
		JPanel p5 = new JPanel();
		c.gridx = 1;
		c.gridy = 0;
		//c.anchor = GridBagConstraints.PAGE_START;
		
		container.add(p5, c);
		
		JPanel p6 = new JPanel();
		
		JPanel p7 = new JPanel();
		c.gridx = 2;
		c.gridy = 0;
		container.add(p7, c);
		
    encKeyBox = new JTextField("0",5);
    affineButton = new JButton("Affine Cipher");
    p3.add(affineButton);
    caesarButton = new JButton("Caesar Cipher");
    p5.add(caesarButton);
    vigenereButton = new JButton("Vigenere Cipher");
    p7.add(vigenereButton);
	p1.setLayout(new FlowLayout());
    p1.add(new Label("Encryption Key"));
    p1.add(encKeyBox);
    encryptButton = new JButton("Encrypt");
    p2.add(encryptButton);
    decryptButton = new JButton("Decrypt");
    p4.add(decryptButton);
    

    encryptButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        try {
        	if(cipher.equals("caesar"))
			writeTxt(caesarEncrypt());
        	else if(cipher.equals("affine"))
            writeTxt(affineEncrypt());
        	else
        	{
        	writeTxt(vigenereEncrypt());
        	}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      }

    });
  
    decryptButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          try {
        	  if(cipher.equals("caesar"))
  			    caesarDecrypt();
        	  else if(cipher.equals("affine"))
        		affineDecrypt();
        	  else
        		vigenereDecrypt();
  		} catch (IOException e1) {
  			// TODO Auto-generated catch block
  			e1.printStackTrace();
  		}
        }
      });
    
    affineButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	setVisible(false);
            cipher = "affine";
			c.gridx = 0;
			c.gridy = 1;
			container.add(p1, c);
			c. gridx = 2;
			c.gridy = 1;
			container.add(p6, c);
			
			encKeyBox2 = new JTextField("0", 3);
		    p6.add(encKeyBox2);
		    
		    setVisible(true);
          }
        });
    caesarButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	setVisible(false);
            cipher = "caesar";
			c.gridx = 1;
			c.gridy = 1;
			container.add(p1, c);
			p1.add(encKeyBox);
			p6.remove(encKeyBox2);
		    setVisible(true);
          }
        });
    
    vigenereButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            cipher = "vigenere";
            setVisible(false);
			c.gridx = 1;
			c.gridy = 1;
			container.add(p1, c);
			p1.add(encKeyBox);
			p6.remove(encKeyBox2);
		    setVisible(true);
          }
        });

    setVisible(true);
  
}
  
  private static String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }
        //System.out.println(stringBuilder.toString());
	    return stringBuilder.toString();
	}
	
	
	private  String caesarEncrypt() throws IOException{
		String pt = readFile("UserInfo.txt");
		String keyString = encKeyBox.getText();
		pt = pt.toUpperCase(Locale.US);
		
		 int i;
		 char c;
		 int x, y;
		 int ek;
		 try{  //This attempts to convert from String to int
		      ek = Integer.parseInt(keyString);
		    }catch(Exception exc){
		      return "ERROR";
		    }
		 char[] charArray = new char[pt.length()];
		 for(i=0; i<pt.length(); i++){  
		      c = pt.charAt(i);
		      
		      
		      int asc = (int) c;
		      x = asc - 32;
		      if(x >= 0 && x < 59)
		      {
		      y = (x + ek) % 59;
		      if(y < 0)
		    	  y += 59;
		      asc = y + 32;
		      c = (char) asc;
		      }
		      charArray[i] = c;  
		    }
		 String ct = new String(charArray);
		 //System.out.println(ct);
		return ct;
	}
	
	
	private void writeTxt(String s) throws IOException{
		FileWriter file = new FileWriter("UserInfo.txt");
		//System.out.println(s);
		char c;
		char[] charArray = new char[s.length()];
		 for(int i=0; i<s.length(); i++){
			 c = s.charAt(i);
			 charArray[i] = c;  
		 }
		 file.flush();
		file.write(charArray);
		file.close();
		
	}
	
	private void caesarDecrypt() throws IOException{
		PrintWriter writer = new PrintWriter("DecryptedFile.txt", "UTF-8");
		FileWriter file = new FileWriter("DecryptedFile.txt");
		String ct = readFile("UserInfo.txt");
		writeTxt(ct);
		String keyString = encKeyBox.getText();
		ct = ct.toUpperCase(Locale.US);
         int i;
		 char c;
		 int x, y;
		 int ek;
		 try{  
		      ek = Integer.parseInt(keyString);
		    }catch(Exception exc){
		      return;
		    }
		 char[] charArray = new char[ct.length()];
		 for(i=0; i<ct.length(); i++){  
		      c = ct.charAt(i);
		      
		      
		      int asc = (int) c;
		      x = asc - 32;
		      if(x >= 0 && x < 59)
		      {
		      y = (x - ek) % 59;
		      if(y < 0)
		    	  y += 59;
		      asc = y + 32;
		      c = (char) asc;
		      }
		      charArray[i] = c;  
		    }
		 file.flush();
	     file.write(charArray);
	     file.close();

	}
	
	private String affineEncrypt() throws IOException{
		String pt = readFile("UserInfo.txt");
		String keyString = encKeyBox.getText();

	    int a, b;

	    try{
	      a = Integer.parseInt(keyString);
	    }catch(Exception exc){
	      JOptionPane.showMessageDialog(this, "The keys must be an integer.");
	      return "";
	    }
	    String key2String = encKeyBox2.getText();
	    try{
		      b = Integer.parseInt(key2String);
		    }catch(Exception exc){
		      JOptionPane.showMessageDialog(this, "The keys must be an integer.");
		      return "";
		    }
	    
	   // if(isItPrime(a, 26))
	    //{
	    int i;
	    char c;
	    int x, y;
	    char[] charArray = new char[pt.length()];
	    pt = pt.toUpperCase(Locale.US);

	    //Do encryption work here
	    for(i=0; i<pt.length(); i++){
	    	
	      c = pt.charAt(i);
	     x = charToCode(c);
	     if(x >= 0 && x < 59)
	     {
	    	x = a*x + b;
	    	y = mymod(x, 59);
	    	c = codeToChar(y);
	     }

	       charArray[i] = c;
	    }
	    String ct = new String(charArray);
		 //System.out.println(ct);
		return ct;
	    }
	
	private void affineDecrypt() throws IOException{
		int a, b;
		PrintWriter writer = new PrintWriter("DecryptedFile.txt", "UTF-8");
		FileWriter file = new FileWriter("DecryptedFile.txt");
		String ct = readFile("UserInfo.txt");
		writeTxt(ct);
		String keyString = encKeyBox.getText();
		ct = ct.toUpperCase(Locale.US);
		try{
		      a = Integer.parseInt(keyString);
		    }catch(Exception exc){
		      JOptionPane.showMessageDialog(this, "The keys must be an integer.");
		      return;
		    }
		    String key2String = encKeyBox2.getText();
		    try{
			      b = Integer.parseInt(key2String);
			    }catch(Exception exc){
			      JOptionPane.showMessageDialog(this, "The keys must be an integer.");
			      return;
			    }
		 int ik = multInverse(a, 59);
		 
		 int i;
		    char c;
		    int x, y;
		    char[] charArray = new char[ct.length()];
		    ct = ct.toUpperCase(Locale.US);

		    //Do encryption work here
		    for(i=0; i<ct.length(); i++){
		    	
		      c = ct.charAt(i);
		     x = charToCode(c);
		     if(x >= 0 && x < 59)
		     {
		    	x = ik*(x - b);
		    	y = mymod(x, 59);
		    	c = codeToChar(y);
		     }

		       charArray[i] = c;
		    }
		    file.flush();
		    file.write(charArray);
		    file.close();
		
	}
	
	private String vigenereEncrypt() throws IOException {
		String pt = readFile("UserInfo.txt");
		String keyString = encKeyBox.getText();
		
		 char[] charArray = new char[pt.length()];
		 char[] charArray2 = new char[keyString.length()];
		 
		 for(int i=0; i<keyString.length(); i++){  
		      charArray2[i] = keyString.charAt(i);
		 }
		 int x, y;
		 int r = 0;
		 int k;
		 char c, l;
		 for(int j =0; j<pt.length(); j++){  
		      c = pt.charAt(j);
		      
		      
		      int asc = (int) c;
		      x = asc - 32;
		      if(x >= 0 && x < 59)
		      {
		       
		      l = charArray2[r];
		      //System.out.println(l);
		      int temp = (int) l;
		      k = temp - 32; 
		      y = (x + k) % 59;
		      if(y < 0)
		    	  y += 59;
		      asc = y + 32;
		      c = (char)asc;
		      }
		      charArray[j] = c;  
		      r++;
		      if(r >= keyString.length())
		    	  r = 0;
		    }
		 String ct = new String(charArray);
		 //System.out.println(ct);
		return ct;
		
	}
	
	private void vigenereDecrypt() throws IOException {
		PrintWriter writer = new PrintWriter("DecryptedFile.txt", "UTF-8");
		FileWriter file = new FileWriter("DecryptedFile.txt");
		String ct = readFile("UserInfo.txt");
		writeTxt(ct);
		String keyString = encKeyBox.getText();
		ct = ct.toUpperCase(Locale.US);
		char[] charArray = new char[ct.length()];
		char[] charArray2 = new char[keyString.length()];
		 
	    for(int i=0; i<keyString.length(); i++){  
		     charArray2[i] = keyString.charAt(i);
		     }
		 int x, y;
		 int r = 0;
		 int k;
		 char c, l;
		 for(int j =0; j<ct.length(); j++){  
		      c = ct.charAt(j);
		      
		      
		      int asc = (int) c;
		      x = asc - 32;
		      if(x >= 0 && x < 59)
		      {
		       
		      l = charArray2[r];
		      //System.out.println(l);
		      int temp = (int) l;
		      k = temp - 32; 
		      y = (x - k) % 59;
		      if(y < 0)
		    	  y += 59;
		      asc = y + 32;
		      c = (char)asc;
		      }
		      charArray[j] = c;  
		      r++;
		      if(r >= keyString.length())
		    	  r = 0;
		    }
		 file.flush();
		 file.write(charArray);
	     file.close();
		
	}
	
	int mymod(int x, int n){
		int temp = x % n;
		if(temp < 0)
			temp += n;
	    return temp; //Write this function to return x mod n
	  }

	  int charToCode(char c){
		  int asc = (int) c;
		  int x = asc - 32; 
		  return x;
	  }

	  char codeToChar(int x){
		  int asc = x + 32;
		  char c = (char) asc;
		  return c;
	    
	}
	  
	  int multInverse(int x, int m)
	   {
		   int[] vals = gcd(x, m);
		      int d = vals[0];
		      int a = vals[1];
		      int b = vals[2];
		      while(d < 0)
		    	  d+=26;
		      if (d > 1) 
		    	  return -1; 
		      if(a > 0) 
		    	  return a;
		      return m + a;
	   }
	   
	   int[] gcd(int p, int q) {
		      if (q == 0)
		         return new int[] { p, 1, 0 };
		      int[] vals = gcd(q, p % q);
		      int d = vals[0];
		      int a = vals[2];
		      int b = vals[1] - (p / q) * vals[2];
		      return new int[] { d, a, b };
	   }
}
	

