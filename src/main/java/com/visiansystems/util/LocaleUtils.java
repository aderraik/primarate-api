package com.visiansystems.util;

import java.util.*;

public class LocaleUtils {

    private static List<Locale> validLocales = new ArrayList<>();

    private static HashSet<String> countryCodeSet = new HashSet<>();

    /**
     * Get all valid locales, which includes a valid currency.
     */
    public static List<Locale> getValidLocales() {

        if (validLocales.isEmpty()) {
            Locale[] locales = Locale.getAvailableLocales();

            // Remove duplicates
            for (Locale locale : locales) {

                try {
                    String code = locale.getISO3Country();

                    if (!countryCodeSet.contains(code)) {
                        validLocales.add(locale);
                        countryCodeSet.add(code);
                    }
                }
                catch (MissingResourceException | IllegalArgumentException ignored) {
                }
            }

            // Sort
            if (validLocales.size() > 0) {
                Collections.sort(validLocales, new Comparator<Locale>() {
                    @Override
                    public int compare(final Locale object1, final Locale object2) {
                        return object1.getISO3Country().compareTo(object2.getISO3Country());
                    }
                });
            }
        }

        return validLocales;
    }

    /**
     * Check if a countryCode produces a valid locale.
     */
    public static boolean isValidLocale(String countryCode) {
        if (countryCodeSet.isEmpty()) {
            getValidLocales();
        }
        return countryCodeSet.contains(countryCode);
    }
}
