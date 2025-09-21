package com.bcp.training;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ExpenseValidator {

//    private static final boolean DEBUG_EMABLED = true;
    @ConfigProperty(name = "debug-enabled", defaultValue = "false")
    boolean debugEnabled;

//    private static final int RANGE_HIGH = 1000;
    @ConfigProperty(name = "range-high")
    int targetRangeHigh;

//    private static final int RANGE_LOW = 250;
    @ConfigProperty(name = "range-low")
    int targetRangeLow;

    public void debugRanges() {
//        System.out.println("Range - High: " + RANGE_HIGH);
        System.out.println("Range - High: " + targetRangeHigh);
//        System.out.println("Range - Low: " + RANGE_LOW);
        System.out.println("Range - Low: " + targetRangeLow);
    }

    public boolean isValidAmount(int amount) {
        if (debugEnabled) {
            debugRanges();
        }

//        return amount >= RANGE_LOW && amount <= RANGE_HIGH;
        return amount >= targetRangeLow && amount <= targetRangeHigh;
    }
}
