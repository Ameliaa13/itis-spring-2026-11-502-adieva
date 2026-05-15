package ru.itis.service;

import ru.itis.SystemPropertiesService;

public class SystemPropertiesServiceImpl implements SystemPropertiesService {

    private static final String NO_INFO = "<<<NO-INFO>>>";
    @Override
    public String getOsName() {
        return System.getProperty("os.name", NO_INFO);
    }

    @Override
    public String getOsArch() {
        return System.getProperty("os.arch", NO_INFO);
    }

    @Override
    public String getOsVersion() {
        return System.getProperty("os.version", NO_INFO);
    }

    @Override
    public String getUserName() {
        return System.getProperty("user.name", NO_INFO);
    }
}
