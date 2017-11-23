package com.iharding.participle.cfg;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fyeman on 2017/11/23.
 */
public class CSVReader {
    public List<String[]> read(String path, int col_length) {
        List<String[]> data = new ArrayList<String[]>(10);
        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(path);
            int i = 0;
            while (csvReader.readRecord()){
                String[] cols = new String[col_length];
                for (i = 0; i < col_length; i++) {
                    cols[i] = csvReader.get(i);
                }
                data.add(cols);
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
