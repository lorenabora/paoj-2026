package com.pao.project.cabinet_medical.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class ServiciuAudit
{
    private static ServiciuAudit instance;
    private static final String FISIER_AUDIT = "audit.csv";
    private final ReentrantLock lock = new ReentrantLock();

    private ServiciuAudit() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FISIER_AUDIT, true))) {
            java.io.File f = new java.io.File(FISIER_AUDIT);
            if (f.length() == 0) {
                writer.write("nume_actiune,timestamp");
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Eroare initializare audit: " + e.getMessage());
        }
    }

    public static ServiciuAudit getInstance() {
        if (instance == null) {
            instance = new ServiciuAudit();
        }
        return instance;
    }

    public void logheaza(String numeActiune) {
        lock.lock();
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FISIER_AUDIT, true))) {
            writer.write(numeActiune + "," + LocalDateTime.now());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Eroare scriere audit: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
