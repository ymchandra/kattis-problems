package com.project.southern.tours.backend.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SpamFilter {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int k = Integer.parseInt(scan.nextLine());
        if (k >= 1 && k <= 100) {
            int f = 0, l = 0;
            double overallSuccessRate = 0.0;
            String input = scan.nextLine();
            if (input.length() >= k && input.length() <= 1000000) {
                List<Integer> sequence = Arrays.asList(input.split(""))
                        .stream()
                        .map(Integer::parseInt).collect(Collectors.toList());
                for (int i = 0; i <= sequence.size() - k; i++) {
                    for (int j = k + i; j <= sequence.size() && i <= j; j++) {
                        List<Integer> subSequence = sequence.subList(i, j);
                        int subSequenceLength = subSequence.size();
                        double successfullyMarkedEmails = (double) Collections.frequency(subSequence, 1);
                        double successRateFactor = successfullyMarkedEmails / (double) subSequenceLength;
                        if (successRateFactor > overallSuccessRate) {
                            overallSuccessRate = successRateFactor;
                            f = i;
                            l = subSequenceLength;
                        }
                        if (overallSuccessRate == 1.0) {
                            break;
                        }
                    }
                    if (overallSuccessRate == 1.0) {
                        break;
                    }
                }
                f++;
                System.out.println(f + " " + l);
            }
        }
        scan.close();
    }
}
