import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
class gui implements ActionListener{
	JFrame f=null;
	JButton bconvert=null;
	JTextField tfrom=null,tto=null,tstatus=null;
	JLabel llanguage,lfrom,lto,lstatus;
	gui(){
		f=new JFrame("SUBTITLE CONVERTER");

		llanguage=new JLabel("Select language");
		lfrom=new JLabel("From");
		lto=new JLabel("To");
		lstatus=new JLabel("Status");

		tfrom=new JTextField();
		tto=new JTextField();
		tstatus=new JTextField();
		

		bconvert=new JButton("CONVERT");


		llanguage.setBounds(200,50,200,30);
		lfrom.setBounds(200,80,80,30);
		lto.setBounds(320,80,80,30);
		lstatus.setBounds(50,180,100,30);

		tfrom.setBounds(200,130,80,30);
		tto.setBounds(320,130,80,30);
		tstatus.setBounds(200,180,200,30);

		bconvert.setBounds(50,130,100,30);

		f.add(llanguage);
		f.add(lfrom);
		f.add(lto);
		f.add(lstatus);

		f.add(tfrom);
		f.add(tto);
		f.add(tstatus);

		f.add(bconvert);

		f.setSize(500,300);
		f.setLayout(null);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bconvert.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==bconvert){
			tstatus.setText("Converting...");
			try{
			FileReader inp=new FileReader(tfrom.getText()+".srt");
			PrintWriter out=new PrintWriter(tto.getText()+".srt","UTF-8");
			Scanner sc=new Scanner(inp);
			String line=null;
			int flag=0;
			
int c=0;
while((line=sc.nextLine())!=null) {
	flag=0;
	if(line.compareTo("")==0)
		out.println(line);
	else
	{
		if(isNumeric(line.substring(0,1)))
			out.println(line);
		else{
			do{
				try{
					out.println(translateText(line,tfrom.getText(),tto.getText()));
				}catch(Exception err){
					continue;
				}
				flag=1;
			}while(flag==0);
			Thread.sleep(500);
		}

	}
	
	c++;
	if(c==50)
		break;
		
	
	}	
	inp.close();
out.close();
tstatus.setText("CONVERTED");
}catch(Exception ex){
	System.out.println(ex.getMessage());
}




	}	//if
}

	static String translateText(String txt,String source,String target)throws Exception{
		StringBuilder text=new StringBuilder("");
		for(int i=0;i<txt.length();i++)
		{
			if(txt.charAt(i)==' ')
				text.append("%20");
			else
				text.append(Character.toString(txt.charAt(i)));
		}
		StringBuilder params=new StringBuilder("");
		params.append("https://www.googleapis.com/language/translate/v2?key=AIzaSyCjhidhDka3I8Z6Nz586xwjJkis5tkSgMkLWI&q=");
		params.append(text.toString());
		params.append("&source=");
		params.append(source);
		params.append("&target=");
		params.append(target);
		URL url = new URL(params.toString());
		URLConnection con = url.openConnection();
		InputStream is =con.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder translated=new StringBuilder("");
		while ((line = br.readLine()) != null)
			translated.append(line);
		line=translated.toString();
		return line.substring(57,line.length()-11);
	}
	public static boolean isNumeric(String str)  
	{  
		if(str.length()<1)
			return false;
  		try  
  		{  
    		int d = Integer.parseInt(str);  
 		}catch(NumberFormatException nfe)  
  			{  
    			return false;  
 			}  
				return true;  
	}


}
class app{

	public static void main(String[] args) {
		gui obj=new gui();
	}
}
