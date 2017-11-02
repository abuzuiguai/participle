package com.iharding.participle;

import com.iharding.participle.core.Lexeme;
import com.iharding.participle.dijkstra.DijkstraArithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */
public class DijkstraTest {
    public static void main(String[] args) {
        char[] chars = new char[]{'在','北','京','大','学','生','活','区','喝','进','口','红','酒'};

        List<Lexeme> lexemeList = new ArrayList<>();

        Lexeme lexeme = new Lexeme(0, 1);
        lexeme.setText("在");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(1, 1);
        lexeme.setText("北");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(1, 2);
        lexeme.setText("北京");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(1, 4);
        lexeme.setText("北京大学");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(2, 1);
        lexeme.setText("京");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(3, 1);
        lexeme.setText("大");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(3, 2);
        lexeme.setText("大学");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(3, 3);
        lexeme.setText("大学生");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(4, 1);
        lexeme.setText("学");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(4, 2);
        lexeme.setText("学生");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(5, 1);
        lexeme.setText("生");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(5, 2);
        lexeme.setText("生活");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(5, 3);
        lexeme.setText("生活区");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(6, 1);
        lexeme.setText("活");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(7, 1);
        lexeme.setText("区");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(8, 1);
        lexeme.setText("喝");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(8, 2);
        lexeme.setText("喝进");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(9, 1);
        lexeme.setText("进");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(9, 2);
        lexeme.setText("进口");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(10, 1);
        lexeme.setText("口");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(10, 2);
        lexeme.setText("口红");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(11, 1);
        lexeme.setText("红");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(11, 2);
        lexeme.setText("红酒");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);

        lexeme = new Lexeme(12, 1);
        lexeme.setText("酒");
        lexeme.setThreshold(1);
        lexemeList.add(lexeme);


        DijkstraArithmetic dijkstra = new DijkstraArithmetic();
        dijkstra.dijkstra(lexemeList, chars);
    }
}
