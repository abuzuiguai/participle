package com.iharding.participle.bp;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by fyeman on 2017/11/22.
 */
public class NeuralNetwork {
    //每个节点(神经元)
    public double[][] nodes;
    //权重计算后 每个节点的偏移量
    public double[][] nodes_offset;
    //节点之间的权重 第三维是下一个节点位置
    public double[][][] nodes_weight;
    //权重偏移量
    public double[][][] nodes_weight_offset;

    private double rate;
    private double mobp;

    public void init(int[] layer, double rate, double mobp) {
        int i = 0, j = 0, k = 0;
        //神经网络层数，包含输入、输出、隐藏层
        int layer_length = layer.length;

        nodes = new double[layer_length][];
        nodes_offset = new double[layer_length][];
        nodes_weight = new double[layer_length][][];
        nodes_weight_offset = new double[layer_length][][];

        for (i = 0; i < layer_length; i++) {
            nodes[i] = new double[layer[i]];
            nodes_offset[i] = new double[layer[i]];
            if (i < layer_length - 1) {
                nodes_weight[i] = new double[layer[i]][layer[i + 1]];
                nodes_weight_offset[i] = new double[layer[i]][layer[i + 1]];
                //初始化权重值
                for (j = 0; j < nodes_weight[i].length; j++) {
                    for (k = 0; k < nodes_weight[i][j].length; k++) {
                        nodes_weight[i][j][k] = Math.random();
//                        System.out.println("nodes_weight[" + i + "][" + j + "][" + k + "] = " + nodes_weight[i][j][k]);
                    }
                }
            }
        }

        this.rate = rate;
        this.mobp = mobp;
    }

    /**
     * 计算输出层
     *
     * @param inputs
     * @return 输出层
     */
    public double[] caculate(double[] inputs, boolean isPrint) {
        int i = 0, j = 0, k = 0;
        //输入层初始化
        for (i = 0; i < inputs.length; i++) {
            nodes[0][i] = inputs[i];
        }
        double weight = 0.0;
        double node_val = 0.0;
        for (i = 1; i < nodes.length; i++) {
            for (j = 0; j < nodes[i].length; j++) {
                node_val = 0.0;
                for (k = 0; k < nodes[i - 1].length; k++) {
                    weight = nodes_weight[i - 1][k][j];
                    node_val += weight * nodes[i - 1][k];
                }
                nodes[i][j] = 1 / (1 + Math.exp(-node_val));
            }
        }
//        if (isPrint) {
            System.out.println(Arrays.toString(nodes[nodes.length - 1]));
//        }
        return nodes[nodes.length - 1];
    }

    public void updateWeight(double[] targets) {
        int i = 0, j = 0;
        //层索引值
        int layer_index = nodes.length - 1;
        //最后一层节点误差值
        for (i = 0; i < nodes[layer_index].length; i++) {
            //平方型误差 求导
            nodes_offset[layer_index][i] = (targets[i] - nodes[layer_index][i]) * nodes[layer_index][i] * (1 - nodes[layer_index][i]);
        }
        //逐层往回计算节点误差值
        double offset = 0.0;
        while (layer_index-- > 0) {
            for (i = 0; i < nodes[layer_index].length; i++) {
                offset = 0.0;
                for (j = 0; j < nodes[layer_index + 1].length; j++) {
                    offset = offset + nodes_offset[layer_index + 1][j] * nodes_weight[layer_index][i][j];
                    //Δw(k+1) = mobp*Δw(k)+(1-mobp)rate*Err*Layer 最小化误差调整权重 动量法调整
                    nodes_weight_offset[layer_index][i][j] = this.mobp * nodes_weight_offset[layer_index][i][j]
                            + (1 - this.mobp) * this.rate * nodes_offset[layer_index + 1][j] * nodes[layer_index][i];
                    //修改权重
                    nodes_weight[layer_index][i][j] += nodes_weight_offset[layer_index][i][j];
                }
                nodes_offset[layer_index][i] = offset * nodes[layer_index][i] * (1 - nodes[layer_index][i]);
            }
        }
    }

    public void train(double[] inputs, double[] targets){
        double[] out = caculate(inputs, false);
        updateWeight(targets);
    }
}
