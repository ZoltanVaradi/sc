package hu.zoltan.varadi.supercharge.test.util;

import java.time.LocalDateTime;

public class RealTimeService implements TimeService {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
