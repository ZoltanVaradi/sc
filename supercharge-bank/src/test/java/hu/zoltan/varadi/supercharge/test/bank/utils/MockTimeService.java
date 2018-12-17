package hu.zoltan.varadi.supercharge.test.bank.utils;

import hu.zoltan.varadi.supercharge.test.util.TimeService;

import java.time.LocalDateTime;

public class MockTimeService implements TimeService {

    public LocalDateTime mockTime = null;

    @Override
    public LocalDateTime now() {
        return mockTime != null ? mockTime : LocalDateTime.now();
    }
}
