package ru.geekbrains.homeworks;

public class Main {

    static final int size = 10000000;
    static final int h = size / 2;


    public static void main(String[] args) {

        float[] arr = new float[size];
        System.out.println("Время работы первого метода: " + doTheFirstMethod(arr));
        doTheSecondMethod(arr);
    }
    public static long doTheFirstMethod(float[] arr){

        for (int i = 0; i<arr.length; i++){
            arr[i] = 1;
        }
        long time0 = System.currentTimeMillis();

        calculate(arr);
        long time1 = System.currentTimeMillis();
        return (time1 - time0);
    }
    public static void doTheSecondMethod(float[] arr) {
        for (int i = 0; i<arr.length; i++){
            arr[i] = 1;
        }
        long time0 = System.currentTimeMillis();

        float[] arrPart1 = new float[size];
        float[] arrPart2 = new float[size];
        System.arraycopy(arr, 0, arrPart1, 0, size/2);
        System.arraycopy(arr, size/2, arrPart2,size/2, size/2);

            Thread MyThreadTheFirst = new Thread() {
                @Override
                public void run() {
                    synchronized (arrPart1) {
                        super.run();
                        calculate(arrPart1);
                    }
                }
            };

            Thread MyThreadTheSecond = new Thread() {

                @Override
                public void run() {
                    synchronized (arrPart2) {
                        super.run();
                        calculate(arrPart2);
                    }
                }
            };

        MyThreadTheFirst.start();
        MyThreadTheSecond.start();

        System.arraycopy(arrPart1, 0, arr, 0, size/2);
        System.arraycopy(arrPart2, 0, arr, size/2, size/2);

        synchronized (arrPart1) {
            synchronized (arrPart2) {
                long time2 = System.currentTimeMillis();
                System.out.println("Время работы второго метода: " + (time2 - time0));
            }
        }

    }
    private static float[] calculate (float[]arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return arr;
    }
    }


