package com.agms.automation_service.util;

public class RuleEngineUtil {

    public static String checkTemperature(double temp, double min, double max) {

        if (temp > max) {
            return "TURN_FAN_ON";
        }

        if (temp < min) {
            return "TURN_HEATER_ON";
        }

        return "NORMAL";
    }
}