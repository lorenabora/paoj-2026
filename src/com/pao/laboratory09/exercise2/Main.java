package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
// TODO: Implementează conform Readme.md
        Scanner scanner = new Scanner(System.in);
        new File("output").mkdir();
        if(!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        String[] status = {"PENDING", "PROCESSED", "REJECTED"};
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))){
            for(int i=0; i<n; i++){
                int id = scanner.nextInt();
                double suma = scanner.nextDouble();
                String data = scanner.next(); String tip = scanner.next();

                dos.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array());
                dos.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(suma).array());

                byte[] dataBytes = new byte[10];
                byte[] rawData = data.getBytes();
                System.arraycopy(rawData, 0, dataBytes, 0, Math.min(rawData.length, 10));
                for( int j=rawData.length; j<10; j++){
                    dataBytes[j]=(byte) ' ';
                }
                dos.write(dataBytes);
                dos.writeByte(tip.equals("CREDIT")?0:1);//0 credit 1 debit
                dos.writeByte(0);//0 pending
                dos.write(new byte[8]);
            }
        }
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        try(RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String comanda = scanner.next();
                switch (comanda) {
                    case "READ":
                        if (scanner.hasNextInt()) {
                            int idx = scanner.nextInt();
                            readAndPrint(raf, idx, status);
                        }
                        break;
                    case "UPDATE":
                        if (scanner.hasNextInt() && scanner.hasNext()) {
                            int idx = scanner.nextInt();
                            String statusNou = scanner.next();
                            byte statusVal = 0;
                            for (int i = 0; i < status.length; i++) {
                                if (status[i].equals(statusNou)) {
                                    statusVal = (byte) i;
                                    break;
                                }
                            }
                            raf.seek((long) idx * RECORD_SIZE + 23);
                            raf.writeByte(statusVal);
                            System.out.println("Updated [" + idx + "]: " + statusNou);
                        }
                        break;
                    case "PRINT_ALL":
                        for (int i = 0; i < n; i++) {
                            readAndPrint(raf, i, status);
                        }
                        break;
                }
            }
        }
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>

//        System.out.println("TODO: implementează exercițiul 2");
    }
    private static void readAndPrint(RandomAccessFile raf, int idx, String[] status) throws IOException{
        byte[] buffer = new byte[RECORD_SIZE];
        raf.seek((long) idx * RECORD_SIZE);
        raf.readFully(buffer);

        ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);

        int id = bb.getInt(0);
        double suma = bb.getDouble(4);

        byte[] dataBytes = new byte[10];
        bb.position(12);
        bb.get(dataBytes);
        String data = new String(dataBytes).trim();

        byte tipByte = bb.get(22);
        String tip = (tipByte == 0) ? "CREDIT" : "DEBIT";

        byte statusByte = bb.get(23);
        String statusN = status[statusByte];

        System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s\n",
                idx, id, data, tip, suma, statusN);
    }
}
