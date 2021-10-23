package me.giraffetree.java.boomjava.weekly.w20211023;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author GiraffeTree
 * @date 2021/10/23
 */
public class TaskUtils {

    private static ConcurrentHashMap<Long, TimedTaskDTO> CACHE = new ConcurrentHashMap<>(10000);

    public static TaskStatisticsDTO statistics() {
        int count = 0;
        long totalCost = 0L;
        long totalRealCost = 0L;
        for (Map.Entry<Long, TimedTaskDTO> entry : CACHE.entrySet()) {
            TimedTaskDTO value = entry.getValue();
            totalCost += value.getExecuteCompleteTimestamp() - value.getPutToThreadPoolTimestamp();
            totalRealCost += value.getExecuteCompleteTimestamp() - value.getExecuteTimestamp();
            count++;
        }
        if (count == 0) {
            return new TaskStatisticsDTO();
        }

        return new TaskStatisticsDTO(totalCost / (double) count, totalRealCost / (double) count);
    }

    /**
     * 获取一个执行时长 为 taskTime 毫秒的任务
     *
     * @param id                       任务id , 用来标识每个任务
     * @param putToThreadPoolTimestamp 加进任务队列的时间
     * @param taskTime                 任务执行时长 单位 毫秒
     * @return Runnable
     */
    public static Runnable getTask(Long id, long putToThreadPoolTimestamp, long taskTime) {
        return () -> {
            TimedTaskDTO task = null;
            try {
                long startTimestamp = System.currentTimeMillis();
                TimedTaskDTO.TimedTaskDTOBuilder timedTaskBuilder = TimedTaskDTO.builder()
                        .id(id)
                        .executeTimestamp(startTimestamp)
                        .putToThreadPoolTimestamp(putToThreadPoolTimestamp);
                task = timedTaskBuilder.build();
                if (taskTime <= 0L) {
                    return;
                }
                try {
                    Thread.sleep(taskTime);
                } catch (InterruptedException e) {
                    // do nothing
                }
            } finally {
                if (task != null) {
                    task.setExecuteCompleteTimestamp(System.currentTimeMillis());
                    CACHE.put(id, task);
                }
            }


        };
    }


}
