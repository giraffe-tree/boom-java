package me.giraffetree.java.boomjava.weekly.w20211023;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务统计
 *
 * @author GiraffeTree
 * @date 2021/10/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatisticsDTO {

    /**
     * 放进线程池到实际执行完成所消耗的平均时间
     * 包含队列等待时间
     */
    private Double averageCost;
    /**
     * 实际执行消耗的平均时长
     */
    private Double realAverageCost;

}
