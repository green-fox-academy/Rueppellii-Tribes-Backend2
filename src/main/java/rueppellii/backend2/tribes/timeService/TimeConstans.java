package rueppellii.backend2.tribes.timeService;

import java.util.concurrent.TimeUnit;

public final class TimeConstans {

    private static final Long DEFAULT_OBJECT_CREATING_TIME = TimeUnit.MINUTES.toSeconds(10);
    private static final Long DEFAULT_OBJECT_UPGRADING_TIME = TimeUnit.MINUTES.toSeconds(8);

    public static Long getDefaultObjectCreatingTime() {
        return DEFAULT_OBJECT_CREATING_TIME;
    }

    public static Long getDefaultObjectUpgradingTime() {
        return DEFAULT_OBJECT_UPGRADING_TIME;
    }
}

