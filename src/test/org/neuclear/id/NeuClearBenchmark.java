package org.neuclear.id;

import org.neuclear.id.auth.AuthenticationTicket;
import org.neuclear.commons.crypto.signers.InvalidPassphraseException;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.id.builders.AuthenticationTicketBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Dec 19, 2003
 * Time: 10:49:36 PM
 * To change this template use Options | File Templates.
 */
public class NeuClearBenchmark implements Runnable{
    private static final int RUNS = 100;
    private static final int THREADS = 10;

    public NeuClearBenchmark() throws InvalidPassphraseException {
        signer=new TestCaseSigner();

    }
    public static void main(String args[]){
        try {
            NeuClearBenchmark bench=new NeuClearBenchmark();
            bench.startTimer();
            for (int i=0;i<THREADS;i++)
                new Thread(bench).start();


        } catch (InvalidPassphraseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    public void startTimer(){
        start=System.currentTimeMillis();
        startMemory=Runtime.getRuntime().totalMemory();
    }
    long start=0;
    long end=0;
    long startMemory=0;
    long endMemory=0;

    public synchronized void finished(){
        if (++count==THREADS) {
            long end=System.currentTimeMillis();
            long endMemory=Runtime.getRuntime().totalMemory();
            //long postGC=Ru
            System.out.println("Amount of runs:"+RUNS*THREADS);
            System.out.println("Total Time: "+(end-start)/1000+" seconds");
            System.out.println("Time per Transaction: "+(end-start)/(THREADS+RUNS)+" ms.");
            System.out.println("Memory Increase: "+(endMemory-startMemory));
            System.out.println("Memory Increase per Transaction: "+(endMemory-startMemory)/(THREADS+RUNS));
            notifyAll();
        }
//        Thread.currentThread().destroy();

    }
    public void run()  {
        try {
            for (int i=0;i<RUNS;i++) {

                AuthenticationTicketBuilder builder=new AuthenticationTicketBuilder("neu://alice@test","neu://test","http://test.com");
                AuthenticationTicket ticket=(AuthenticationTicket) builder.convert("neu://bob@test",signer);
            }
            finished();
        } catch (InvalidNamedObjectException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
    private Signer signer;
    private int count=0;
}
