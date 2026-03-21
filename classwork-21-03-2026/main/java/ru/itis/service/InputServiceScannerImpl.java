package ru.itis.service;

import ru.itis.InputService;

import java.util.Scanner;

public class InputServiceScannerImpl implements InputService {
    @Override
    public String inputFio() {
        Scanner sc = new Scanner(System.in);
        System.out.println("������� ���� �. �. �:");
        String input = sc.nextLine();
        System.out.println("��������� \"" + input + "\".");
        return input;
    }
}
