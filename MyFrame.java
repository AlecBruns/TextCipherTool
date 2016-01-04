import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class MyFrame
  extends JFrame
{
  private JTextField encKeyBox;
  private JTextField encKeyBox2;
  private JTextField fileLoc;
  private JButton encryptButton;
  private JButton decryptButton;
  private JButton affineButton;
  private JButton caesarButton;
  private JButton vigenereButton;
  private JMenu menu;
  private JMenuBar menuBar;
  private String cipher = "caesar";
  
  public MyFrame()
  {
    setSize(600, 300);
    setTitle("Text File Cipher");
    setDefaultCloseOperation(3);
    setVisible(true);
    
    final Container container = getContentPane();
    container.setLayout(new GridBagLayout());
    final GridBagConstraints c = new GridBagConstraints();
    
    final JPanel p1 = new JPanel();
    c.gridx = 1;
    c.gridy = 2;
    
    container.add(p1, c);
    JPanel p2 = new JPanel();
    c.gridx = 0;
    c.gridy = 3;
    container.add(p2, c);
    JPanel p3 = new JPanel();
    c.gridx = 0;
    c.gridy = 1;
    c.fill = 2;
    
    container.add(p3, c);
    JPanel p4 = new JPanel();
    c.gridx = 2;
    c.gridy = 3;
    container.add(p4, c);
    JPanel p5 = new JPanel();
    c.gridx = 1;
    c.gridy = 1;
    
    container.add(p5, c);
    
    final JPanel p6 = new JPanel();
    
    JPanel p7 = new JPanel();
    c.gridx = 2;
    c.gridy = 1;
    container.add(p7, c);
    
    JPanel p8 = new JPanel();
    c.gridx = 1;
    c.gridy = 0;
    container.add(p8, c);
    
    this.encKeyBox = new JTextField("0", 5);
    this.encKeyBox2 = new JTextField("0", 3);
    this.fileLoc = new JTextField("Enter Here", 20);
    this.affineButton = new JButton("Affine Cipher");
    p3.add(this.affineButton);
    this.caesarButton = new JButton("Caesar Cipher");
    p5.add(this.caesarButton);
    this.vigenereButton = new JButton("Vigenere Cipher");
    p7.add(this.vigenereButton);
    p1.setLayout(new FlowLayout());
    p1.add(new Label("Encryption Key"));
    p1.add(this.encKeyBox);
    this.encryptButton = new JButton("Encrypt");
    p2.add(this.encryptButton);
    this.decryptButton = new JButton("Decrypt");
    p4.add(this.decryptButton);
    p8.add(new Label("File Location"));
    p8.add(this.fileLoc);
    
    this.encryptButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          if (MyFrame.this.cipher.equals("caesar")) {
            MyFrame.this.writeTxt(MyFrame.access$1(MyFrame.this));
          } else if (MyFrame.this.cipher.equals("affine")) {
            MyFrame.this.writeTxt(MyFrame.access$3(MyFrame.this));
          } else {
            MyFrame.this.writeTxt(MyFrame.access$4(MyFrame.this));
          }
          JOptionPane.showMessageDialog(null, "File is Encrypted");
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
      }
    });
    this.decryptButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          if (MyFrame.this.cipher.equals("caesar")) {
            MyFrame.this.caesarDecrypt();
          } else if (MyFrame.this.cipher.equals("affine")) {
            MyFrame.this.affineDecrypt();
          } else {
            MyFrame.this.vigenereDecrypt();
          }
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "File is Decrypted");
      }
    });
    this.affineButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MyFrame.this.setVisible(false);
        MyFrame.this.cipher = "affine";
        c.gridx = 0;
        c.gridy = 2;
        container.add(p1, c);
        c.gridx = 2;
        c.gridy = 2;
        container.add(p6, c);
        p6.add(MyFrame.this.encKeyBox2);
        
        MyFrame.this.setVisible(true);
      }
    });
    this.caesarButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MyFrame.this.setVisible(false);
        MyFrame.this.cipher = "caesar";
        c.gridx = 1;
        c.gridy = 2;
        container.add(p1, c);
        p1.add(MyFrame.this.encKeyBox);
        p6.add(MyFrame.this.encKeyBox2);
        p6.remove(MyFrame.this.encKeyBox2);
        MyFrame.this.setVisible(true);
      }
    });
    this.vigenereButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MyFrame.this.cipher = "vigenere";
        MyFrame.this.setVisible(false);
        c.gridx = 1;
        c.gridy = 2;
        container.add(p1, c);
        p1.add(MyFrame.this.encKeyBox);
        p6.add(MyFrame.this.encKeyBox2);
        p6.remove(MyFrame.this.encKeyBox2);
        MyFrame.this.setVisible(true);
      }
    });
    setVisible(true);
  }
  
  private static String readFile(String file)
    throws IOException
  {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line = null;
    StringBuilder stringBuilder = new StringBuilder();
    String ls = System.getProperty("line.separator");
    while ((line = reader.readLine()) != null)
    {
      stringBuilder.append(line);
      stringBuilder.append(ls);
    }
    return stringBuilder.toString();
  }
  
  private String caesarEncrypt()
    throws IOException
  {
    String pt = readFile(this.fileLoc.getText());
    String keyString = this.encKeyBox.getText();
    pt = pt.toUpperCase(Locale.US);
    try
    {
      ek = Integer.parseInt(keyString);
    }
    catch (Exception exc)
    {
      int ek;
      return "ERROR";
    }
    int ek;
    char[] charArray = new char[pt.length()];
    for (int i = 0; i < pt.length(); i++)
    {
      char c = pt.charAt(i);
      
      int asc = c;
      int x = asc - 32;
      if ((x >= 0) && (x < 59))
      {
        int y = (x + ek) % 59;
        if (y < 0) {
          y += 59;
        }
        asc = y + 32;
        c = (char)asc;
      }
      charArray[i] = c;
    }
    String ct = new String(charArray);
    
    return ct;
  }
  
  private void writeTxt(String s)
    throws IOException
  {
    FileWriter file = new FileWriter(this.fileLoc.getText());
    
    char[] charArray = new char[s.length()];
    for (int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      charArray[i] = c;
    }
    file.flush();
    file.write(charArray);
    file.close();
  }
  
  private void caesarDecrypt()
    throws IOException
  {
    PrintWriter writer = new PrintWriter("DecryptedFile.txt", "UTF-8");
    FileWriter file = new FileWriter("DecryptedFile.txt");
    String ct = readFile(this.fileLoc.getText());
    writeTxt(ct);
    String keyString = this.encKeyBox.getText();
    ct = ct.toUpperCase(Locale.US);
    try
    {
      ek = Integer.parseInt(keyString);
    }
    catch (Exception exc)
    {
      int ek;
      return;
    }
    int ek;
    char[] charArray = new char[ct.length()];
    for (int i = 0; i < ct.length(); i++)
    {
      char c = ct.charAt(i);
      
      int asc = c;
      int x = asc - 32;
      if ((x >= 0) && (x < 59))
      {
        int y = (x - ek) % 59;
        if (y < 0) {
          y += 59;
        }
        asc = y + 32;
        c = (char)asc;
      }
      charArray[i] = c;
    }
    file.flush();
    file.write(charArray);
    file.close();
  }
  
  private String affineEncrypt()
    throws IOException
  {
    String pt = readFile(this.fileLoc.getText());
    String keyString = this.encKeyBox.getText();
    try
    {
      a = Integer.parseInt(keyString);
    }
    catch (Exception exc)
    {
      int a;
      JOptionPane.showMessageDialog(this, "The keys must be an integer.");
      return "";
    }
    int a;
    String key2String = this.encKeyBox2.getText();
    try
    {
      b = Integer.parseInt(key2String);
    }
    catch (Exception exc)
    {
      int b;
      JOptionPane.showMessageDialog(this, "The keys must be an integer.");
      return "";
    }
    int b;
    char[] charArray = new char[pt.length()];
    pt = pt.toUpperCase(Locale.US);
    for (int i = 0; i < pt.length(); i++)
    {
      char c = pt.charAt(i);
      int x = charToCode(c);
      if ((x >= 0) && (x < 59))
      {
        x = a * x + b;
        int y = mymod(x, 59);
        c = codeToChar(y);
      }
      charArray[i] = c;
    }
    String ct = new String(charArray);
    
    return ct;
  }
  
  private void affineDecrypt()
    throws IOException
  {
    PrintWriter writer = new PrintWriter("DecryptedFile.txt", "UTF-8");
    FileWriter file = new FileWriter("DecryptedFile.txt");
    String ct = readFile(this.fileLoc.getText());
    writeTxt(ct);
    String keyString = this.encKeyBox.getText();
    ct = ct.toUpperCase(Locale.US);
    try
    {
      a = Integer.parseInt(keyString);
    }
    catch (Exception exc)
    {
      int a;
      JOptionPane.showMessageDialog(this, "The keys must be an integer."); return;
    }
    int a;
    String key2String = this.encKeyBox2.getText();
    try
    {
      b = Integer.parseInt(key2String);
    }
    catch (Exception exc)
    {
      int b;
      JOptionPane.showMessageDialog(this, "The keys must be an integer."); return;
    }
    int b;
    int ik = multInverse(a, 59);
    
    char[] charArray = new char[ct.length()];
    ct = ct.toUpperCase(Locale.US);
    for (int i = 0; i < ct.length(); i++)
    {
      char c = ct.charAt(i);
      int x = charToCode(c);
      if ((x >= 0) && (x < 59))
      {
        x = ik * (x - b);
        int y = mymod(x, 59);
        c = codeToChar(y);
      }
      charArray[i] = c;
    }
    file.flush();
    file.write(charArray);
    file.close();
  }
  
  private String vigenereEncrypt()
    throws IOException
  {
    String pt = readFile(this.fileLoc.getText());
    String keyString = this.encKeyBox.getText();
    
    char[] charArray = new char[pt.length()];
    char[] charArray2 = new char[keyString.length()];
    for (int i = 0; i < keyString.length(); i++) {
      charArray2[i] = keyString.charAt(i);
    }
    int r = 0;
    for (int j = 0; j < pt.length(); j++)
    {
      char c = pt.charAt(j);
      
      int asc = c;
      int x = asc - 32;
      if ((x >= 0) && (x < 59))
      {
        char l = charArray2[r];
        
        int temp = l;
        int k = temp - 32;
        int y = (x + k) % 59;
        if (y < 0) {
          y += 59;
        }
        asc = y + 32;
        c = (char)asc;
      }
      charArray[j] = c;
      r++;
      if (r >= keyString.length()) {
        r = 0;
      }
    }
    String ct = new String(charArray);
    
    return ct;
  }
  
  private void vigenereDecrypt()
    throws IOException
  {
    PrintWriter writer = new PrintWriter("DecryptedFile.txt", "UTF-8");
    FileWriter file = new FileWriter("DecryptedFile.txt");
    String ct = readFile(this.fileLoc.getText());
    writeTxt(ct);
    String keyString = this.encKeyBox.getText();
    ct = ct.toUpperCase(Locale.US);
    char[] charArray = new char[ct.length()];
    char[] charArray2 = new char[keyString.length()];
    for (int i = 0; i < keyString.length(); i++) {
      charArray2[i] = keyString.charAt(i);
    }
    int r = 0;
    for (int j = 0; j < ct.length(); j++)
    {
      char c = ct.charAt(j);
      
      int asc = c;
      int x = asc - 32;
      if ((x >= 0) && (x < 59))
      {
        char l = charArray2[r];
        
        int temp = l;
        int k = temp - 32;
        int y = (x - k) % 59;
        if (y < 0) {
          y += 59;
        }
        asc = y + 32;
        c = (char)asc;
      }
      charArray[j] = c;
      r++;
      if (r >= keyString.length()) {
        r = 0;
      }
    }
    file.flush();
    file.write(charArray);
    file.close();
  }
  
  int mymod(int x, int n)
  {
    int temp = x % n;
    if (temp < 0) {
      temp += n;
    }
    return temp;
  }
  
  int charToCode(char c)
  {
    int asc = c;
    int x = asc - 32;
    return x;
  }
  
  char codeToChar(int x)
  {
    int asc = x + 32;
    char c = (char)asc;
    return c;
  }
  
  int multInverse(int x, int m)
  {
    int[] vals = gcd(x, m);
    int d = vals[0];
    int a = vals[1];
    int b = vals[2];
    while (d < 0) {
      d += 26;
    }
    if (d > 1) {
      return -1;
    }
    if (a > 0) {
      return a;
    }
    return m + a;
  }
  
  int[] gcd(int p, int q)
  {
    if (q == 0) {
      return new int[] { p, 1 };
    }
    int[] vals = gcd(q, p % q);
    int d = vals[0];
    int a = vals[2];
    int b = vals[1] - p / q * vals[2];
    return new int[] { d, a, b };
  }
}
