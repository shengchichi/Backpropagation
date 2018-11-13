package NeuralNetworks2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class Paint extends JPanel{
	double in[][];
	double out[][];
	double var;
	Color s[] ={Color.RED, Color.BLACK,Color.BLUE,Color.GREEN,Color.CYAN,Color.ORANGE,Color.GRAY};
	public Paint(double in[][],double out[][],double var)
	{
		this.in = in;
		this.out = out;
		this.var = var;
		
	}

	 protected void paintComponent(Graphics g)
	  {
		 super.paintComponent(g);
		 g.setColor(Color.BLACK);
		 Graphics2D g2 = (Graphics2D) g;
		 g2.translate(500, 400);
		 g2.draw(new Line2D.Double(-250.0,0.0,250.0,0.0));
		 g2.draw(new Line2D.Double(0.0,250.0,0.0,-250.0));
		 g2.draw(new Line2D.Double(-250.0,.0,250.0,0.0));
		 g2.setColor(Color.RED);
		 for(int i =0;i<in.length;i++)
		 {
			 Ellipse2D.Double shape = new Ellipse2D.Double(80*in[i][0],-80*in[i][1], 5.0, 5.0);
			 for(int j=1;j<=var;j++)
			 {
				 if(out[i][0]>=(j-1)/(var-1)&&out[i][0]<j/(var-1))
				 {
					 g2.setColor(s[j]);
					 g2.fill(shape);
				 }
			
			 }
			 
		 }
	  }
}
