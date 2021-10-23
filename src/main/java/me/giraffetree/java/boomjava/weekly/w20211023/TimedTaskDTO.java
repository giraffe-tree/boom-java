package me.giraffetree.java.boomjava.weekly.w20211023;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author giraffetree
 * @date 2021/10/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TimedTaskDTO {
    /**
     * 线程id
     */
    private Long id;
    /**
     * 被加入线程池的时间戳 毫秒
     */
    private Long putToThreadPoolTimestamp;
    /**
     * 实际开始执行时间戳 毫秒
     */
    private Long executeTimestamp;
    /**
     * 实际执行完成的时间戳 毫秒
     */
    private Long executeCompleteTimestamp;
    /**
     * 从线程池取出结果的时间戳 毫秒
     * 线程池执行完成,然后返回主线程结果的时间, 需要主线程记录
     * 如果没有结果, 则该值为空
     */
    private Long taskOutTimestamp;
}
