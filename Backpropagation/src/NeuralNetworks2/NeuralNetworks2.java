package NeuralNetworks2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class NeuralNetworks2 implements ActionListener,MouseListener{
	ArrayList<Double> arr  = new ArrayList<Double>();
	double input[][];//
	double output[][];
	int entries=0;
	int line = 0;
	int dim;
	int ln[];//隱藏層層數及其中神經元個數
	int outputnumber=1;//輸出神經元個數
	double lrate=0.15;
	double mobp =0.8;
	double testSuccess=0;
    double test =0;
    double trainSuccess=0;
    double train =0;
	int end;
	double varies =1;//分幾類
	boolean allTest =false;
	Dimension ScreenSize,FrameSize; 
	JFrame demo = new JFrame("HW");
	JDialog paint = new JDialog(demo,"paint");
    JTextField learn=new JTextField("請輸入學習率",20);//學習率
    JTextField condition=new JTextField("請輸入收斂條件",20);//收斂條件
    JTextField layernum=new JTextField("請輸入隱藏層層數",20);//層數
    JTextField neurnum=new JTextField("各層神經元個數",20);
    JButton enter = new JButton("輸入");
    JButton choose = new JButton("選擇檔案");
    JLabel outputTrainRate = new JLabel();
    JLabel outputTestRate = new JLabel();
    JLabel outputweights = new JLabel();
    File selectedFile;
	DecimalFormat df=new DecimalFormat("#.###");

    int gwidth=1000,gheigh=800,gx,gy;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new NeuralNetworks2();
	}
	public NeuralNetworks2()
	{
		demo.setSize(500,400);
        demo.setResizable(false);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paint.setSize(gwidth, gheigh);
        paint.setResizable(false);
        outputTrainRate.setBounds(0, 200, 500, 40);
        outputTestRate.setBounds(0, 250, 500, 40);
        outputweights.setBounds(0, 300, 500, 40);
        enter.setBounds(0,150,60,30);
        choose.setBounds(100,0,100,20);
        choose.addActionListener(this);
        learn.addMouseListener(this);
        condition.addMouseListener(this);
        layernum.addMouseListener(this);
        neurnum.addMouseListener(this);
        enter.addActionListener(this);
        learn.setBounds(0,0,100,20);
        condition.setBounds(0,25,100,20);
        layernum.setBounds(0,50,100,20);
        neurnum.setBounds(0,75,100,20);
        
        demo.getContentPane().add(learn);
        demo.getContentPane().add(condition);
        demo.getContentPane().add(enter);
        demo.getContentPane().add(choose);
        demo.getContentPane().add(layernum);
        demo.getContentPane().add(neurnum);
        outputTrainRate.setText("訓練辨識率: ");
        outputTestRate.setText("測試辨識率: ");
        //outputweights.setText("鍵結值: "+df.format(p.weights[0])+", "+df.format(p.weights[1])+", "+df.format(p.weights[2]));
        demo.getContentPane().add(outputTrainRate);
        demo.getContentPane().add(outputTestRate);
        demo.getContentPane().add(outputweights);

        demo.getContentPane().setLayout(new BorderLayout());
        FrameSize = paint.getSize();
        ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        paint.setLocation((ScreenSize.width-FrameSize.width)/2,(ScreenSize.height-FrameSize.height)/2);
        gx=(ScreenSize.width-gwidth)/2; 
		gy=(ScreenSize.height-gheigh)/2; 
		
	    

		//System.out.println(gx);
		//System.out.println(gy);
		
        demo.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==learn)
		{
			learn.setText("");
		}
		if(e.getSource()==condition)
		{
			condition.setText("");
		}
		if(e.getSource()==layernum)
		{
			layernum.setText("");
		}
		if(e.getSource()==neurnum)
		{
			neurnum.setText("");
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == choose)
		{
			JFileChooser fileChooser = new JFileChooser();//宣告filechooser 
			int returnValue = fileChooser.showOpenDialog(null);//叫出filechooser 
			if (returnValue == JFileChooser.APPROVE_OPTION) //判斷是否選擇檔案 
			{ 
				
				selectedFile = fileChooser.getSelectedFile();//指派給File 
				if(selectedFile.getName().equals("數字辨識.txt")||selectedFile.getName().equals("2class.txt")||selectedFile.getName().equals("xor.txt")||selectedFile.getName().equals("感知機3.txt")||selectedFile.getName().equals("感知機2.txt")||selectedFile.getName().equals("感知機1.txt")){
					allTest = true;
				}
					
				scan();
				if(allTest == false)
					randomInput();
					
			} 
		}
		if(e.getSource() == enter)
		{
			
			int ln_length =Integer.parseInt(layernum.getText())+2;
			ln = new int[ln_length];
			dim = entries / line;
			ln[0] = dim - outputnumber;
			ln[ln_length-1] = outputnumber;
			for(int i=1;i<ln_length-1;i++)
			{
				String s = neurnum.getText().toString();
				ln[i] = s.charAt(i-1)-'0';

			}
			lrate = Double.parseDouble(learn.getText());
			BPD bp = new BPD(ln,lrate,mobp);
			end = Integer.parseInt(condition.getText());
			
	        
	        
	        
	        for(int n=0;n<end;n++)
	        {//train
	        	if(allTest == true)
	        	{
	        		for(int i=0;i<input.length;i++)
		            {
	        			
		                bp.train(input[i], output[i]);
		            }
	        	}
	        	else
	        	{
	        		for(int i=0;i<input.length*2/3;i++)
		            {
		                bp.train(input[i], output[i]);
		            }
	        	}
	        }
	        
	        if(allTest == true)
	        {
	        	for(int j=0;j<input.length;j++)
		        {//test
	        		test++;
		            double[] result = bp.computeOut(input[j]);
		            if(Math.abs(output[j][0]-result[0])<=1/varies){
		            	testSuccess++;
		            }
		            System.out.println(Arrays.toString(input[j])+":"+Arrays.toString(result));
		          
		            
		        }
	        	
	        }
	        else
	        {
	        	for(int j=input.length*2/3;j<input.length;j++)
		        {//test
	        		test++;
		            double[] result = bp.computeOut(input[j]);
		            if(Math.abs(output[j][0]-result[0])<=1/varies){
		            	testSuccess++;
		            }
		            System.out.println(Arrays.toString(input[j])+":"+Arrays.toString(result)+output[j][0]);
		            
		            
		        }
	        	for(int j=0;j<input.length*2/3;j++)
		        {//test
	        		train++;
		            double[] result = bp.computeOut(input[j]);
		            if(Math.abs(output[j][0]-result[0])<=1/varies){
		            	trainSuccess++;
		            }
		            	
		            
		            
		        }
	        	
	        }
	        outputTrainRate.setText("訓練辨識率: " +trainSuccess/train*100 +"%");
	        outputTestRate.setText("測試辨識率: " +testSuccess/test*100 +"%");
	        
	        Paint dpanel= new Paint(input,output,varies);
	        paint.add(dpanel);
	        paint.setVisible(true);
		}
	}

	public void scan()
	{
		Scanner sc = null;
		Scanner sc2 = null;
        try{
              sc = new Scanner(selectedFile);
              sc2 = new Scanner(selectedFile);
          }catch(FileNotFoundException e){  
              e.printStackTrace();  
              System.exit(0);
          }
       
        
        while(sc.hasNextLine()){
        	String s=sc.nextLine();
            line++;
            }
        
        
        
        while(sc2.hasNext()){
        	arr.add(sc2.nextDouble());
        	entries++;
          }

        int k=0;
        int l=dim-outputnumber;
        dim = entries/line;
        input = new double[line][dim-outputnumber];
        for(int i=0;i<line;i++)
        {
        	for(int j=0;j<dim-outputnumber;j++)
        	{
        		if(k%dim==dim-outputnumber)
        			k++;
        		input[i][j] = arr.get(k);
        		
        		k++;
        	}
        }
        
        output = new double[line][outputnumber];
        boolean adjust = false;
        double bvalue=1,svalue=0;
		bvalue = arr.get(dim-outputnumber);
		svalue = arr.get(dim-outputnumber);
        for(int i=0;i<line;i++)
        {
        	for(int j=0;j<outputnumber;j++)
        	{
     
        		if(arr.get(l+dim)>bvalue){
        			adjust =true;
        			bvalue  = arr.get(l+dim);
        		}
        		if(arr.get(l+dim)<svalue){
        			adjust =true;
        			svalue  = arr.get(l+dim);
        		}
       			l= l+dim;
       			
        	}	
        }
        System.out.println(bvalue+" "+svalue);
        
        l=dim-outputnumber;
        for(int i=0;i<line;i++)
        {
        	for(int j=0;j<outputnumber;j++)
        	{
        		if(adjust == true){//正規化
        			output[i][j] = (arr.get(l)-svalue)/(bvalue-svalue);
        			
        		}
        		else
        			output[i][j] = arr.get(l);
    			l= l+dim;
        	}
        	
        }
        boolean isDiff;
        for(int i=1;i<output.length;i++)//檢查有幾類輸出
        {
        	isDiff = true;
        	for(int j=1;j<=i;j++)
        	{
        		
        		if(output[i][0]==output[i-j][0]){
        	        
        			isDiff = false;
        			break;
        		}
        	}
        	if(isDiff == true)
        		varies++;
        }
        System.out.println(varies);
	}
	public void randomInput()//洗牌
	{
	     for(int ii=1;ii<=line;ii++)
	     {
	    	 Random ran = new Random();
		     int i = ran.nextInt(line);
		     int j = ran.nextInt(line);
	    	 double[] tmp = input[i];
		     input[i] = input[j];
		     input[j] = tmp;
		     tmp = output[i];
		     output[i] = output[j];
		     output[j] = tmp;
	     }
	}
}
