package me.giraffetree.java.boomjava.utils.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2019/12/5
 */
@Slf4j
public class Log4j2Test {
    private final static Marker OTHER = MarkerFactory.getMarker("other");

    public static void main(String[] args) {
        long l1 = System.currentTimeMillis();
        int loopCount = 100_000;
        int count = loopCount;
        int size = 10;
        while (count > 0) {
            writeMultipleUsersLog(size);
            count--;
        }

        long l2 = System.currentTimeMillis();
        System.out.println(String.format("size:%d loopCount:%d cost: %dms", size, loopCount, l2 - l1));
    }


    public static void addRandomLog() {
        int num = ThreadLocalRandom.current().nextInt(0, 1000);
        if (num < 950) {
            ThreadContext.put("logFileName", "David");
            log.info("current: {}", num);
        } else {
            log.error("current: {}", num);
        }
    }

    public static void writeMultipleUsersLog(int size) {
        for (int i = 0; i < size; i++) {
            String userName = "user" + i;
            ThreadContext.put("logFileName", userName);
            int num = ThreadLocalRandom.current().nextInt(0, 1000);
            if (num < 950) {
                log.info("current: {}", num);
            } else {
                log.info(OTHER, "current: {}", num);
            }
        }
    }

}
