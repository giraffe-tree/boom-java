package me.giraffetree.java.boomjava.concurrent.thread.communication;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author GiraffeTree
 * @date 2020-05-01
 */
public class PipedTest {

        public static void main(String[] args) throws IOException {
            PipedWriter out = new PipedWriter();
            PipedReader in = new PipedReader();
            out.connect(in);
            Thread t1 = new Thread(new Print(in), "in");
            t1.start();
            try {
//            int receive;
//            while ((receive = System.in.read()) != -1){
//                out.write(receive);
//            }
                int loop = 100;
                while (loop-- > 0) {
                    out.write(loop);
                }
            } catch (IOException e) {

            }

    }

    private static class Print implements Runnable {

        private PipedReader pipedReader;

        public Print(PipedReader pipedReader) {
            this.pipedReader = pipedReader;
        }

        @Override
        public void run() {
            int receive;
            try {
                while ((receive = pipedReader.read()) != -1) {
                    System.out.print(receive);
                }
            } catch (IOException e) {

            }
        }
    }

}
