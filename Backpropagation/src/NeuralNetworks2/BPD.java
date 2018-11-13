package NeuralNetworks2;

import java.util.Random;
public class BPD{
    public double[][] layer;//[layer][node] = value of node
    public double[][] layerErr;//same as above
    public double[][][] layer_weight;//[layer][node][next node] = Wji
    public double[][][] layer_weight_delta;//
    public double mobp;//慣性項
    public double rate;//learn rate
  
    public BPD(int[] layernum, double rate, double mobp){//layernum.length是層數,layer[i]= 第i層節點數
        this.mobp = mobp;
        this.rate = rate;
        layer = new double[layernum.length][];
        layerErr = new double[layernum.length][];
        layer_weight = new double[layernum.length][][];
        layer_weight_delta = new double[layernum.length][][];
        Random random = new Random();
        for(int l=0;l<layernum.length;l++)
        {
            layer[l]=new double[layernum[l]];//各層層數
            layerErr[l]=new double[layernum[l]];//同上
            if(l+1<layernum.length)
            {//倒數第一二層
                layer_weight[l]=new double[layernum[l]+1][layernum[l+1]];//前面的+1是閥值，後面的是代表下層節點
                layer_weight_delta[l]=new double[layernum[l]+1][layernum[l+1]];
                for(int j=0;j<layernum[l]+1;j++)
                    for(int i=0;i<layernum[l+1];i++)
                        layer_weight[l][j][i]=random.nextDouble();
            }   
        }
    }
    //forward
    public double[] computeOut(double[] in){
        for(int l=1;l<layer.length;l++){
            for(int j=0;j<layer[l].length;j++){
                double v = layer_weight[l-1][layer[l-1].length][j];
                for(int i=0;i<layer[l-1].length;i++){
                	if(l==1)
                		layer[l-1][i]=in[i];
                    v += layer_weight[l-1][i][j]*layer[l-1][i];//Vj(n) = sigma(Wji(n)*Yi(n)) 
                }
                layer[l][j]=1/(1+Math.exp(-v));//sigmoid
            }
        }
        return layer[layer.length-1];
    }
    //backward
    public void updateWeight(double[] tg){
        int l=layer.length-1;//layer陣列是0~layer.length-1
        for(int j=0;j<layerErr[l].length;j++)
            layerErr[l][j]=layer[l][j]*(1-layer[l][j])*(tg[j]-layer[l][j]);//輸出層的layerError

        while(l-- > 0)//由後往前
        {
            for(int j=0;j<layerErr[l].length;j++)
            {
                double z = 0.0;
                for(int i=0;i<layerErr[l+1].length;i++)
                {
                	if(z+1>0)//		output層 第i個神經元
                		z = layerErr[l+1][i]*layer_weight[l][j][i];//先算output那層，z = sigma(LayerErr*weight
                	else //當z變成-1或更小時做修正
                		z = 0;
                    layer_weight_delta[l][j][i]= mobp*layer_weight_delta[l][j][i]+rate*layerErr[l+1][i]*layer[l][j];
                    layer_weight[l][j][i]+=layer_weight_delta[l][j][i];
                    if(j==layerErr[l].length-1)//那層最下面的node也就是閥值
                    {
                    	//因為我們預設閥值都為-1
                        layer_weight_delta[l][j+1][i]= mobp*layer_weight_delta[l][j+1][i]+rate*layerErr[l+1][i];
                        layer_weight[l][j+1][i]+=layer_weight_delta[l][j+1][i];
                    }
                }
                layerErr[l][j]=z*layer[l][j]*(1-layer[l][j]);
            }
        }
    }

    public void train(double[] in, double[] tg){
        double[] out = computeOut(in);
        updateWeight(tg);
    }
}