package com.iharding.participle.dijkstra;

import com.iharding.participle.core.Lexeme;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 *
 * 最短路径求值
 */
public class DijkstraArithmetic {
    //节点最短路径长度
    Map<Integer, Float> node_min_length_map = new HashMap<Integer, Float>(10, 0.8f);
    //节点最短路径
    Map<Integer, int[]> node_min_route_map = new HashMap<>(10, 0.8f);
    //已計算節點
    List<Integer> caled_node_list = new ArrayList<Integer>(10);
    //未计算节点
    List<Integer> caled_node_list_not = new ArrayList<>(10);
    //节点信息，矩阵存储
    float[][] nodes;
    //词元字符集
    char[] word_chars;
    //节点间默认距离为最大值
    private float length_default = Float.MAX_VALUE;

    private void init(List<Lexeme> lexemeList, char[] word_chars) {
        this.word_chars = word_chars;
        int length = word_chars.length;
        //矩阵长度以词元字符集个数+1建立, 其中最后一个是追加的结束词元
        nodes = new float[length + 1][length + 1];
        //初始化
        node_min_length_map.clear();
        node_min_route_map.clear();
        caled_node_list.clear();
        caled_node_list_not.clear();

        int i = 0;
        int lexemeLength = lexemeList.size();
        Lexeme lexeme;
        for (; i < length + 1; i++) {
            //初始化矩阵最大值
            Arrays.fill(nodes[i], length_default);
            //所有节点都未计算
            caled_node_list_not.add(i);
            //初始化每个节点最短路径长度
            node_min_length_map.put(i, length_default);
        }
        //出发节点设置长度为0，最短路径为-1(起点)
        int[] route = new int[]{-1};
        node_min_route_map.put(0, route);
        node_min_length_map.put(0, 0f);
        //词元长度初始化，矩阵横坐标为词元起始位置(offset)，纵坐标为词元结束位置(offset + length)，
        //两点之间连线值为即为用来计算最短路径，暂时用词元频次来作为两点之间的路径值
        for (i = 0; i < lexemeLength; i++) {
            lexeme = lexemeList.get(i);
            //有词元的初始化覆盖
            nodes[lexeme.getOffset()][lexeme.getOffset() + lexeme.getLength()] = lexeme.getThreshold();
        }
    }

    public void dijkstra(List<Lexeme> lexemeList, char[] word_chars) {
        this.init(lexemeList, word_chars);
        int cur_position = 0;
        while (caled_node_list_not.size() > 1) {
            cur_position = this.lookforPosition(cur_position);

            int last_node = word_chars.length - 1;

            List<Integer> routeList = new ArrayList<Integer>(10);
            int[] route = node_min_route_map.get(cur_position);
            int length = route.length;
            if (length > 1) {
                int i = 0;
                for (i = 1; i < length; i++) {
                    routeList.add(route[i]);
                }
                routeList.add(cur_position);
            }

            System.out.println(StringUtils.join(routeList, ","));
        }

//        int last_node = word_chars.length - 1;
//
//        List<Integer> routeList = new ArrayList<Integer>(10);
//        int[] route = node_min_route_map.get(last_node);
//        int length = route.length;
//        if (length > 1) {
//            int i = 0;
//            for (i = 1; i < length; i++) {
//                routeList.add(route[i]);
//            }
//            routeList.add(last_node);
//        }
//
//        System.out.println(StringUtils.join(routeList, ","));
    }

    private int lookforPosition(int cur_position) {
        float cur_node_min_length = node_min_length_map.get(cur_position);
        int[] cur_node_min_route = node_min_route_map.get(cur_position);
        //从当前节点出发的相连两个节点间距离，如果不相连则为length_default
        float relation_node_length;
        int i = 0;
        for (; i < nodes[cur_position].length; i++) {
            relation_node_length = nodes[cur_position][i];
            if (!caled_node_list.contains(i) && i != cur_position) {
                //替换相连节点最短路径值
                this.replaceNodeMinLength(cur_node_min_length, cur_node_min_route, relation_node_length, cur_position, i);
            }
        }
        caled_node_list.add(cur_position);
        caled_node_list_not.remove(Integer.valueOf(cur_position));
        //查找所有未计算节点的最短路径值，返回最小节点
        return lookforNotCaledPosition();
    }

    private void replaceNodeMinLength(float cur_node_min_length, int[] cur_node_min_route, float relation_node_length,
                                      int cur_position, int cur_position_relation) {
        //如该节点最短路径不是最小，则进行替换，使节点始终保持最小节点值
        if (relation_node_length != length_default) { //过滤非相连节点
            //替换最短路径和替换路径长度
            if ((cur_node_min_length + relation_node_length) < node_min_length_map.get(cur_position_relation)) {
                node_min_length_map.put(cur_position_relation, cur_node_min_length + relation_node_length);
                //更新节点最短路径
                int[] tmp_route = new int[cur_node_min_route.length + 1];
                System.arraycopy(cur_node_min_route, 0, tmp_route, 0, tmp_route.length - 1);
                tmp_route[tmp_route.length - 1] = cur_position;
                node_min_route_map.put(cur_position_relation, tmp_route);
            }
        }
    }

    private int lookforNotCaledPosition() {
        float minLength = length_default;
        int next_position = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Float> entry : node_min_length_map.entrySet()) {
            if (caled_node_list_not.contains(entry.getKey()) && entry.getValue() < minLength) {
                minLength = entry.getValue();
                next_position = entry.getKey();
            }
        }
        return next_position;
    }
}
