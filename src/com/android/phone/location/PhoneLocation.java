/*
 * Copyright (C) 2011 Lysting Huang http://lytsing.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.phone.location;

public final class PhoneLocation {

    private static String _location;

    private static String _phone;

    /**
     * Get Location, format: 0755,广东深圳
     * @param num 
     * @return
     */
    public static synchronized String getLocationFromPhone(String num) {

        if (num == null) {
            return null;
        }

        if (num.equals(_phone)) {
            return _location;
        }

        _phone = num;
        _location = getPhoneLocationJni(num);

        return _location;
    }

    /**
     * Get City Name
     * @param num
     * @return
     */
    public static String getCityFromPhone(String num) {
        return _getPosFromPhone(num, 1);
    }

    /**
     * Get Code
     * @param num
     * @return
     */
    public static String getCodeFromPhone(String num) {
        return _getPosFromPhone(num, 0);
    }

    private static String _getPosFromPhone(String num, int i) {
        String s = getLocationFromPhone(num);
        String[] loc = null;

        // TODO: code review
        if (s != null) {
            loc = s.split(",");

            if (loc.length == 2) {
                return loc[i];
            }
        }

        return null;
    }

    static native String getPhoneLocationJni(String num);

    static {
        System.loadLibrary("phoneloc-jni");
    }
}

