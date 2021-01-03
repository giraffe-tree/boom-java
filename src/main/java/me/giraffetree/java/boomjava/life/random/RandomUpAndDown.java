package me.giraffetree.java.boomjava.life.random;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * @author GiraffeTree
 * @date 2020-10-12
 */
public class RandomUpAndDown {

    public static void main(String[] args) {
        double initialMoney = 100;
        double amplitude = 0.1;
        int[] timeArr = {100, 1000, 2000, 4000, 5000, 6000, 7000, 8000, 10000, 20000};
        for (int i = 0; i < timeArr.length; i++) {
            int times = timeArr[i];
            List<Double> tradingData = manyPeopleTrading(10000, initialMoney, times, amplitude);
            DoubleSummaryStatistics collect = tradingData.stream().collect(Collectors.summarizingDouble(x -> x));
            double average = collect.getAverage();
            double max = collect.getMax();
            System.out.println(String.format("times:%d", times));
            System.out.println(String.format("平均:%.4f 最大:%.4f", average, max));
        }

    }

    private static void writeToFile(List<Double> list, double initialMoney, int times, double amplitude) {
        File file = new File(
                String.format("./initial_%.1f-times_%d-amplitude_%.2f.csv", initialMoney, times, amplitude));
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            List<String> data = list.stream().map(x -> String.format("%.4f,", x)).collect(Collectors.toList());
            FileUtils.writeLines(file, data);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static List<Double> manyPeopleTrading(int numberOfPeople, double initialMoney, int times, double amplitude) {
        ArrayList<Double> list = new ArrayList<>(numberOfPeople);
        for (int i = 0; i < numberOfPeople; i++) {
            double afterTransaction = transaction(initialMoney, times, amplitude);
            list.add(afterTransaction);
        }
        return list;
    }

    /**
     * 交易
     *
     * @param initialMoney 初始资金
     * @param times        交易次数
     * @param amplitude    每次交易涨跌幅度
     * @return 最后资金
     */
    private static double transaction(double initialMoney, int times, double amplitude) {
        // 随机上涨 下跌 amplitude 振幅

        for (int i = 0; i < times; i++) {
            boolean up = Math.random() > 0.5;
            if (up) {
                initialMoney = (1 + amplitude) * initialMoney;
            } else {
                initialMoney = (1 - amplitude) * initialMoney;
            }
        }

        return initialMoney;
    }


}
