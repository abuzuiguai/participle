package com.iharding.participle;

import com.iharding.participle.bp.BpDeep;
import com.iharding.participle.bp.NeuralNetwork;
import com.iharding.participle.cfg.CSVReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fyeman on 2017/11/22.
 */
public class BPTest {
    public static void main(String[] args) {
        BPTest test = new BPTest();
//        test.number();
        test.start();
    }

    public void number() {
        NeuralNetwork bp = new NeuralNetwork();
        bp.init(new int[]{2, 10, 2}, 0.15, 0.8);

        //设置样本数据，对应上面的4个二维坐标数据
        double[][] data = new double[][]{{1, 2}, {2, 2}, {1, 1}, {2, 1}};
        //设置目标数据，对应4个坐标数据的分类
        double[][] target = new double[][]{{1, 0}, {0, 1}, {0, 1}, {1, 0}};

        //迭代训练5000次
        for (int n = 0; n < 5000; n++)
            for (int i = 0; i < data.length; i++)
                bp.train(data[i], target[i]);

        //根据训练结果来检验样本数据
        for (int j = 0; j < data.length; j++) {
            double[] result = bp.caculate(data[j], false);
            System.out.println(Arrays.toString(data[j]) + ":" + Arrays.toString(result));
        }

        //根据训练结果来预测一条新数据的分类
        double[] x = new double[]{3, 1};
        double[] result = bp.caculate(x, false);
        System.out.println(Arrays.toString(x) + ":" + Arrays.toString(result));
    }

    public void start() {
        CSVReader reader = new CSVReader();
        List<String[]> train_data = reader.read("E:\\git\\participle\\src\\main\\resources\\mnist_train.csv", 784);
        List<String[]> train_label = reader.read("E:\\git\\participle\\src\\main\\resources\\mnist_train_labels.csv", 1);
        List<double[]> inputs = convertInputs(train_data);
        List<double[]> targets = convertLabels(train_label);

        NeuralNetwork bp = new NeuralNetwork();
        bp.init(new int[]{784, 10, 10, 10}, 0.15, 0.8);
        for (int i = 0; i < inputs.size(); i++) {
            bp.train(inputs.get(i), targets.get(i));
        }

        List<String[]> text_data = reader.read("E:\\git\\participle\\src\\main\\resources\\textdata.csv", 784);
        inputs = convertInputs(text_data);
        for (int i = 0; i < inputs.size(); i++) {
//            System.out.println(Arrays.toString(inputs.get(i)));
//            double[] result = bp.caculate(inputs.get(i), true);
//            System.out.println(Arrays.toString(result));
        }

//        List<String[]> text_label = reader.read("E:\\git\\participle\\src\\main\\resources\\textresult.csv", 1);
    }

    public List<double[]> convertInputs(List<String[]> train_data) {
        List<double[]> inputs = new ArrayList<>(10);
        int i, j;
        for (i = 0; i < train_data.size(); i++) {
            double[] input = new double[784];
            for (j = 0; j < input.length; j++) {
                input[j] = Double.parseDouble(train_data.get(i)[j]);
            }
            inputs.add(input);
        }

        return inputs;
    }

    public List<double[]> convertLabels(List<String[]> train_label) {
        List<double[]> list = new ArrayList<>(10);
        int i;
        for (i = 0; i < train_label.size(); i++) {
            double[] target = null;
            switch (train_label.get(i)[0]) {
                case "0":
                    target = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
                    break;
                case "1":
                    target = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
                    break;
                case "2":
                    target = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
                    break;
                case "3":
                    target = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
                    break;
                case "4":
                    target = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
                    break;
                case "5":
                    target = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
                    break;
                case "6":
                    target = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
                    break;
                case "7":
                    target = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0};
                    break;
                case "8":
                    target = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
                    break;
                case "9":
                    target = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
                    break;
            }
            list.add(target);
        }

        return list;
    }
}
