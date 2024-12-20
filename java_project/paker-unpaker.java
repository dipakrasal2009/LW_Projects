//MarvellousMain.java

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;

class MarvellousLogin extends Template implements ActionListner, Runnable
{
    JButten SUBMIT;
    JLabel label1,label2,label3,TopLabel;
    final JTextField text1,text2;
    private static int attemp = 3;

    MarvellousLogin()
    {
        TopLabel = new JLabel();
        TopLabel.setHorizontalAlignment(SwingContants.CENTER);
        TopLabel.setText("Matrvellous Paker Unpacker : Login");
        TopLabel.setForeground(Colour.blue);

        Dimension topsize = TopLable.getPreferredSize();
        TopLabel.setBoundes(50,40,topsize.width,topsize.heigth);
        _header.add(TopLabel);

        label1 = new JLabel();
        label1.setText("Username : ");
        label1.setForeground(Colour.white);

        Dimension size = label1.getPreferredSize();

        label1.setBoundes(50,135,size.width,size.height);
        label1.setHorizontalAlignment(SwingContants.CENTER);

        text1 = new JTextField(15);
        Dimension tsize = text1.getPreferredSize();
        text1.setBoundes(200,135,tsize.width,tsize.height);

        text1.setToolTipText("ENTER USERNAME");

        label2 = new JLabel();
        label2.setText("Password:");
        label2.setBoundes(50,175,size.width,size.height);
        label2.setForeground(Colour.white);
        label2.setHorizontalAlignment(SwingContants.CENTER);

        text2 = new JPasswordField(15);
        text2.setBoundes(200,175,tsize.width,tsize.height);

        text2.setToolTipText("ENTER PASSWORD");

        text2.addFocusListener(FocusListener()
        {
            public void  focusGained(FocusEvent e)
            {

            }
            public void focusLost(FocusEvent e)
            {
                label3.setText("");
            }
        });

        SUBMIT = new JButten("SUBMIT");
        SUBMIT.setHorizontalAlignment(SwingContants.CENTER);

        Dimension ssize = SUBMIT.getPreferredSize();
        
        SUBMIT.setBoundes(50,220,ssize.width,ssize.height);

        label3 = new JLabel();
        label3.setText("");

        Dimension l3size = label3.getPreferredSize();

        label3.setForeground(Colour.RED);
        label3.setBoundes(50,250,l3size.width,l3size.heigth);

        Thread t = new Thread(this);
        t.start();

        _content.add(label1);
        _content.add(text1);

        _content.add(label2);
        _content.add(text2);

        _content.add(label3);
        _content.add(SUBMIT);

        pack();
        validate();

        ClockHome();
        setVisible(true);
        this.setSize(500,500);
        this.setResizable(false);
           setLocationRelativeTo(false);
           SUBMIT.addActionListener(this);
    }

    public boolean Validate(String Username,String Password)
    {
        if((Username.length()<8)||(Password.lenght()<8))
            return false;
        else
            return true;
    }

    public void actionPerformed(ActionEvent ae)
    {
        String value1 = text1.getText();
        String value2 = text2.getText();

        if( ae.getSource()==exit )
        {
            this.setVisible(false);
            System.exit(0);
        }

        if(ae.getSource()==minimize)
        {
            this.setState(this.ICONIFISD);
        }
        
        if(ae.getSource()==SUBMIT)
        {
            if(Validate(value1,value2) == false)
            {
                text1.setText("");
                text2.setText("");
                JOptionPane.showMassageDialog(this,"Short username","Marvellous Packer Unpacker",JOptionPane.ERROR_MASSAGE);  
            }
            if(value1.equals("MarvellousAdmin")&& value2.equals("MarvellousAdmin"))
            {
                NextPage page = new NextPage(value1);
                this.setVisible(false);
                page.pack();
                page.setVisible(true);
                page.setSize(500,500);
            }
            else
            {
                attemp--;

                if(attemp == 0)
                {
                    JOptionPane.showMassageDialog(this,"Number of attempts finish","Marvellous Packer Unpacker",JOptionPane.ERROR_MASSAGE);
                    this.dispose();
                    System.exit(0);
                }

                JOptionPane.showMassageDialog(this,"incorrect login or password",
                "Error",JOptionPane.ERROR_MASSASGE);
            }
        }
    }

    public void run()
    {
        for(;;)
        {
            if(text2.isFocusOwner())
            {
                if(Toolkit.getDefaultToolkit().getLookingKeyState(KeyEvent.VK_CAPS_LOCK))
                {
                    text2.setToolTipText("warning : CAPS LOCK is on");
                }
                else
                    text2.setToolTipText("");

                if((text2.getText()).length()<8)
                    label3.setText("Week Password");    
                else
                    label3.setText("");
            }   
        }
    }
}

class Marvellous main
{
    public static void main(String Arg[])
    {
        try
        {
            MarvellousLogin frame=new MarvellousLogin();
            frame.setVisible(true);
        }
        catch
        {
            JOptionPane.showMassageDialog(null,e.getMassage());}
        }
}

// Template.java

import javax.swing.*;
import javax.swing.plaf.basic.basic.BasicBorders;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

Class ClockLable extends JLabel implements ActionListener
{
    String type;
    SimpleDateFormat sdf;

    public ClockLable(String type)
    {
        this.type = type;
        setForeground(Colour.green);

        switch(type)
        {
            case "date" :sdf = new SimpleDateFormat("  MMMM dd yyyy");
                setFont(new Font("sans-serif",Font.PLAIN,12));
                setHorizontalAlignment(SwingContants.LEFT);
                break;
            case "time" : new SimpleDateFormat("hh:mm:ss a");
                setFont(new Font("sans-serif",Font.PLAIN,40));
                setHorizontalAlignment(SwingContants.CENTER);
                break;
            case "day": sdf = new SimpleDateFormat("EEEE ");
                setFont(new Font("cans-serif",Font.PLAIN,16));
                setHorizontalAlignment(SwingContants.RIGHT);
                break;
        }
        
        Timer t = new Timer(1000,this);
        t.start();
    }

    public void actionPerformed(ActionEvent ae)
    {
        Date d = new Date();
        setText(sdf.format(d));
    }
}


class Template extends JFrame implements Serializable,ActionListener
{
    JPanel_header;
    JPanel_content;
    JPanel_top;

    ClockLable dayLable;
    ClockLable timeLable;
    ClockLable dateLable;

    JButten minimize,exit;

    public Template()
    {
        setDefaultCloseOperation(javax.swing.WindowConstant.DISPOSE_ON_CLOSE);
        GridBagLayout grid = new GridBagLayout();
        setLayout(grid);

        _top = new JPanel();
        _top.setBackground(Colour.Light_GRAY);

        _top.setLayout(null);

                getContentPane().add(_top,new GridBagConstraints(0,0,1,1,1,5,GridBagConstraints.BASELINE,GridBagConstraints.BOTH,new Inests(0,0,0,0),0,0));

        _header = new JPanel();
        _header.setLayout(null);

        header.setBackground(Colour.white);
        
                getContentPane().add(_header,new GridBagConstraints(0,1,1,1,1,20,GridBagConstraints.BASELINE,GridBagConstraints.BOTH,new Inests(0,0,0,0),0,0));

        _content = new JPanel();
        _content.setLayout(null);
        _content.setBackground(new Colour(0,50,120));
        JScrollPane jsp = new JScrollPane(_content,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.SetVirticalScrollBarPolicy(ScrollPaneConstant.VARTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(jsp,new GridBagConstraints(0,2,1,1,1,75,GridBagConstraints.BASELINE,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
        setTital("Marvellous Packer Unpacker");

        Clock();
        CloseAndmin();
    }

    void CloseAndmin()
    {
        minimize = new JButten("-");
        minimize.setBackground(Colour.LIGHT_GRAY);
        minimize.setBoundes(MAXIMIZED_HORIZ,0,45,20 );

        exit = new JButten("X");
        exit.setHorizontalAlignment(SwingContants.CENTER);
        exit.setBackground(Colour.LIGHT_GRAY);
        exit.setHorizontolTextPosition(0);
        exit.setBoundes(MAXIMIZED_HORIZ+45,0,45,20);

        _top.add(minimize);
        _top.add(exit);

        exit.addActionListener(this);
        minimize.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == exit )
        {
            this.setVisible(false);
            System.exit(0);
        }

        if(ae.getSource() == minimize)
        {
            setState(JFrame.ICONIFISD);
        }
    }

    void Clock ()
    {
        dateLable = new ClockLable("date");
        timeLable = new ClockLable("time");
        dayLable = new ClockLable("day");

        dateLable.setForeground(Colour.blue);
        timeLable.setForeground(Colour.blue);
        dayLable.setForeground(Colour.blue);

        dayLable.setFont(new Font("Century",Font.BOLD,15));

        dayLable.setBoundes(700,10,200,100);

        dayLable.setFont(new Font("Century",Font.BOLD,15));

        dayLable.setBoundes(800,-40,200,100);

        timeLable.setFont(new Font("Century",Font.BOLD,15));

        timeLable.setBoundes(760,-15,200,100);

        _header.add(dateLable);
        _header.add(timeLable);
        _header.add(dayLable);
    }

    void ClockHome()
    {
        dateLable = new ClockLable("date");
        timeLable = new ClockLable("time");
        dayLable = new ClockLable("day");

        dateLable.setForeground (Colour.blue);
        timeLable.setForeground (Colour.blue);
        dayLable.setForeground (Colour.blue);

        dayLable.setFont(new Font("Century",Font.BOLD,15));
        dayLable.setBoundes(200,20,200,100);

        dateLable.setFont(new Font("Century",Font.BOLD,15));
        dateLable.setBoundes(300,-40,200,100);

        timeLable.setFont(new Font("Century",Font.BOLD,15));
        timeLable.setBoundes(260,-10,200,100);

        _header.add(dateLable);
        _header.add(timeLable);
        _header.add(dayLable);
    }
}

// Next page.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NextPage extends Template implements ActionListener
{
    JLabel lable;
    JButten pack , unpack;

    NextPage(String value)
    {
        setDefaultCloseOperation(WindowConstant.DISPOSE_ON_CLOSE);

        lable = new JLabel("Welcome: " +value);
        Dimension size = lable.getPreferredSize();
        lable.setBoundes(40,50,size.width + 60,size.height);
        lable.setFont(new Font("Century",Font.BOLD,17));
        lable.setForeground(Colour.blue);

        pack= new JButten("Pack Files");
        Dimension bsize = pack.getPreferredSize();
        pack.setBoundes(100,100,bsize.width,bsize.height);
        pack.addActionListener(this);

        unpacker = new JButten("Unpack Files");
        Dimension b2size = unpack.getPreferredSize();
        unpack.setBoundes(300,100,b2size.width,b2size.height);
        unpack.addActionListener(this);

        _header.add(lable);
        _content.add(pack);
        _content.add(unpack);

        ClockHome();
        this.setSize(600,600);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == exit)
        {
            this.setVisible(false);
            System.exit(0);
        }
        if(ae.getSource() == minimize)
        {
            this.setState(this.ICONIFISD);
        }
        if(ae.getSource() == pack)
        {
            this.setVisible(false);
            try
            {
                MarvellousPackFront obj = new MarvellousPackFront();
            }
            catch(Exception e){}
        }
        if(ae.getSource() == unpack)
        {
            this.setVisible(false);
            MarvellousUnpackerFront obj = new MarvellousUnpackerFront();
        }
    }
}

//Marvellous PackFront.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarvellousPackFront extends Template implements ActionListener
{
    JButten SUBMIT ,PREVIOUS;
    JLabel label1,label2,title;
    final JTextField text1,text2;

    public MarvellousPackFront()
    {
        setDefaultCloseOperation(WindowConstant.DISPOSE_ON_CLOSE);

        title = new JLabel("Marvellous Packing Port");
        Dimension size = title.getPreferredSize();
        title.setBoundes(40,50,size.width + 60,size.height);
        title.setFont(new Font("Century",Font.BOLD,17));
        title.setForeground(Colour.blue);

        label1 = new JLabel();
        label1.setText("Directory name");
        label1.setForeground(Colour.white);
        label1.setBoundes(350,50,size.width,size.height);

        text1 = new JTextField(15);
        Dimention tsize = text1.getPreferredSize();
        text1.setBoundes(500,50,tsize.width,tsize.height);
        text1.setToolTipText("enter the name of Directory ");

        label2 = new JLabel();
        label2.setText("Destination file name");
        label2.setForeground(Colour.white);
        label2.setBoundes(350,100,size.width +60,size.height);

        text2 = new JTextField(15);
        text2.setBoundes(500,100,tsize.width,tsize.height);
        text2.setToolTipText("Enter Destination File Name");

        SUBMIT = new JButten("SUBMIT");
        Dimention bsize = SUBMIT.getPreferredSize();
        SUBMIT.setBoundes(350,200,bsize.width,bsize.height);
        SUBMIT.addActionListener(this);

        PREVIOUS = new JButten("PREVIOUS");
        Dimention b2size = PREVIOUS.getPreferredSize();
        PREVIOUS.setBoundes(500,200,b2size.width,b2size.height);
        PREVIOUS.addActionListener(this);

        _header.add(title);
        _content.add(label1);
        _content.add(label2);
        _content.add(text1);
        _content.add(text2);
        _content.add(SUBMIT);
        _content.add(PREVIOUS);

        this.setSize(1000,400);
        this.setResizable(false);
        this.setVisible(true);
        text1.requestFocusInWindow();
    }

    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == exit)
        {
            this.setVisible(false);
            System.exit(0);
        }

        if(ae.getSource() == minimize)
        {
            this.setState(this.ICONIFISD);

        }
        
        if(ae.getSource() == SUBMIT)
        {
            try
            {
                MarvellousPacker obj = new MarvellousPacker(text1.getText(),text2.getText());
                this.dispose();
                NextPage t = new NextPage("MarvellousAdmin");
            }
            catch(Exception e){}c v
        }
        if(ae.getSource()== PREVIOUS)
        {
            this.setVisible(false);
            this.dispose();
            NextPage t = new NextPage("MarvellousAdmin");
        }
    }    
}

// Marvellois Packer.java

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MarvellousPacker
{
    FileOutputStream outstream = null;

    String ValidExit[] = {".txt",".c",".java",".cpp"};

    public MarvellousPacker(String src,String Dest)throws Exception
    {
        String Magic = "Marvellous11";
        byte arr[] = Magic.getBytes();
        File outfile = new File(dest);

        File infile = null;
        outstream = new FileOutputStream(Dest);
        outstream.write(arr,0,arr.length);

        File folder = new File(src);

        System.setProperty("user.dir",src);

        listAllFile(src);
    }

    public void listAllFile(String path)
    {
        try
        (Stream<Path> paths = Files.walk(Paths.get(path)))
        {
            paths.forEach(filePath ->
                        {
                            if(Files.isRegulerFile(filePath))
                            {
                                try
                                {
                                    String name = filePath.getFileName().toString();
                                    String ext = name.substring(name.LastIndexOf("."));

                                    List<String> list = Arrays.asList(ValidExt);

                                    if(list.contains(ext))
                                    {
                                        File file = new File(filePath.getFileName().toString());

                                        Pack(file.getAbsolutePath());
                                    }
                                }
                                catch(Exception e)
                                {
                                    System.put.println(e);
                                }
                            }
                        });
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    public void Pack(String filePath)
    {
        FileInputStream instream = null;

        try
        {
            byte[] buffer = new byte[1024];

            int length;

            byte temp[] = new byte[100];

            File fobj = new File(filePath);

            String Header = filePath+""+fobj.length();

            for(int i = Header.length(); i < 100; i++)
                Header += "";

            temp = Header.getBytes();

            instream = new FileInputStream(filePath);

            outstream.write(temp,0,temp.length);

            while((length = instream.read(buffer)) > 0)
            {
                outstream.write(buffer,0,length);
            }

            instream.colse();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

//MarvelousUnpackeFront.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class InvalidFileException extends Exception
{
    public InvalidFileException(String str)
    {
        super(str);
    }
}

public class MarvellousUnpackerFront extends Template implements ActionListener
{
    JButten SUBMIT ,PREVIOUS;
    JLabel label1,label2,title;
    final JTextField text1;

    public MarvellousUnpackFront()
    {
        setDefaultCloseOperation(WindowConstant.DISPOSE_ON_CLOSE);

        title = new JLabel("Unpacking Portal");
        Dimension size = title.getPreferredSize();
        title.setBoundes(40,50,size.width + 60,size.height);
        title.setFont(new Font("Century",Font.BOLD,17));
        title.setForeground(Colour.blue);

        label1 = new JLabel();
        label1.setText("File name");
        label1.setForeground(Colour.white);
        label1.setBoundes(350,50,size.width,size.height);

        text1 = new JTextField(15);
        Dimention tsize = text1.getPreferredSize();
        text1.setBoundes(500,50,tsize.width,tsize.height);
        text1.setToolTipText("enter the name of Directory ");

        SUBMIT = new JButten("Extract Here");
        Dimention bsize = SUBMIT.getPreferredSize();
        SUBMIT.setBoundes(350,200,bsize.width,bsize.height);
        SUBMIT.addActionListener(this);

        PREVIOUS = new JButten("PREVIOUS");
        Dimention b2size = PREVIOUS.getPreferredSize();
        PREVIOUS.setBoundes(500,200,b2size.width,b2size.height);
        PREVIOUS.addActionListener(this);

        _header.add(title);
        _content.add(label1);
        _content.add(text1);
        _content.add(SUBMIT);
        _content.add(PREVIOUS);

        this.setSize(1000,400);
        this.setResizable(false);
        this.setVisible(true);
        text1.requestFocusInWindow();
    }

    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == exit)
        {
            this.setVisible(false);
            System.exit(0);
        }

        if(ae.getSource() == minimize)
        {
            this.setState(this.ICONIFISD);

        }
        
        if(ae.getSource() == SUBMIT)
        {
            try
            {
                MarvellousPacker obj = new MarvellousPacker(text1.getText());
                this.dispose();
                NextPage t = new NextPage("admin");
            }
            catch(InvalidFileException obj)
            {
                this.setVisible(false);
                this.dispose();

                JOptionPane.showMassageDialog(this, "Invalid Packed File",
                                "Error",JOptionPane.ERROR_MASSAGE);

                NextPage t = new NextPage("MarvellousAdmin");
            }
            catch(Exception e)
            {}
        }
        if(ae.getSource()== PREVIOUS)
        {
            this.setVisible(false);
            this.dispose();
            NextPage t = new NextPage("admin");
        }
    }    
}

//MarvellousUnpacker.java

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MarvellousUnpacker
{
    FileOutputStream outstream = null;

    public MarvellousUnpacker(string src) throws Exception
    {
        unpacker(src);
    }

    public void unpacker(String filePath)throws Exception
    {
        try
        {
            FileInputStream instream = new FileInputStream(filePath);

            byte hedaer[] = new byte[100];
            int length  = 0;

            byte Magic[] = new byte[12];
            instream.read(Magic,0,Magic.length);

            String Magicstr = new String(Magic);
            
            if(!Magicstr.equals("Marvellous11"))
            {
                throw new InvalidFileException("invalid Package file format");
            }

            while((length = instream.read(header,0,100)) > 0)
            {
                String str = new String(header);

                String ext = str.substring(str.LastIndexOf("/"));
                ext = ext.substring(1);

                String[] words= ext.split("\\s");

                String filename = words[0];

                int size = Integer.parseInt(words[1]);

                byte arr[] = new byte[size];

                instream.read(arr,0,size);

                FileOutputStream fout = new FileOutputStream(filename);
                fout.write(arr,0,size);
            }
        }
        catch(InvalidFileException obj)
        {
            throws new InvalidFileException("Invalid Packe File Format");
        }
        catch(Exception e)
        {}
    }
}