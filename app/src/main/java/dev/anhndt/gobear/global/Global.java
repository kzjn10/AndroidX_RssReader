package dev.anhndt.gobear.global;

public class Global {

    public class BundleParams {
        public static final String STEP = "STEP";
    }

    public class ExtrasParams {
        public static final String TITLE = "TITLE";
        public static final String LINK = "LINK";
        public static final String COVER = "COVER";
        public static final String PUBLISH_TIME = "PUBLISH_TIME";
    }

    public class CacheKey {
        public static final String REMEMBER_ME = "REMEMBER_ME";
    }

    public enum ScreenState {
        SHOW_DATA, LOADING, NO_CONNECTION, NO_DATA, REFRESHING
    }

    public class HolderType {
        public static final int NEWS = 1;
        public static final int UNKNOWN = 2;
    }

}
