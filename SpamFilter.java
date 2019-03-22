package com.project.southern.tours.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class SpamFilter {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int k = Integer.parseInt(scan.nextLine());
        if (k >= 1 && k <= 100) {
            int f = 0, l = 0;
            double overallSuccessRate = 0.0;
            String input = scan.nextLine();
            if (input.length() >= k && input.length() <= 1000000) {
                String[] inputArray = input.split("");
                final Integer[] ints = new Integer[inputArray.length];
                for (int i = 0; i < inputArray.length; i++) {
                    ints[i] = Integer.parseInt(inputArray[i]);
                }
                List<SuccessRate> successRates = new ArrayList<>();
                getSubsequences(ints, successRates, k);
                for (SuccessRate successRate : successRates) {
                    Map.Entry entry = successRate.getSuccessFactor();
                    Double highestSuccess = (Double) entry.getKey();
                    if (highestSuccess > overallSuccessRate) {
                        overallSuccessRate = highestSuccess;
                        f = successRate.getIndex();
                        l = (int) entry.getValue();
                    }
                }
                f = f + 1;
                System.out.println(f + " " + l);
            }
        }
        scan.close();
    }

    private static void getSubsequences(Integer[] input, List<SuccessRate> successRates, int maxLength) {
        int n = input.length;
        for (int i = 0; i < n; i++) {
            SuccessRate successRate = new SuccessRate();
            for (int j = i; j < n; j++) {
                List<Integer> subSequence = Arrays.asList(input).subList(i, j + 1);
                if (subSequence.size() >= maxLength) {
                    double successfullyMarkedEmails = (double) subSequence.parallelStream().filter(integer -> integer == 1).count();
                    double successRateFactor = successfullyMarkedEmails / (double) subSequence.size();
                    successRate.addSuccessFactor(successRateFactor, subSequence.size());
                }
            }
            if (successRate.hasSuccessFactor()) {
                successRate.setIndex(i);
                successRates.add(successRate);
            }
        }
    }

    static class SuccessRate {
        Integer index;
        Map<Double, Integer> successFactor = new HashMap<>();

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Map.Entry getSuccessFactor() {
            TreeMap sortedMap = new TreeMap(Collections.reverseOrder());
            sortedMap.putAll(successFactor);
            return sortedMap.firstEntry();
        }

        public void addSuccessFactor(Double successRate, Integer length) {
            this.successFactor.put(successRate, length);
        }

        public boolean hasSuccessFactor() {
            return this.successFactor.keySet().size() > 0;
        }
    }
}
