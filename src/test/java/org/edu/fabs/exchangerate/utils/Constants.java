package org.edu.fabs.exchangerate.utils;

public class Constants {

    public static final String BASE_CODE = "BRL";
    public static final String TARGET_CODE = "EUR";
    public static final String MOCKED_SUPPORTED_CURRENCIES_RESPONSE = "{\n" +
            " \"result\":\"success\",\n" +
            " \"documentation\":\"https://www.exchangerate-api.com/docs\",\n" +
            " \"terms_of_use\":\"https://www.exchangerate-api.com/terms\",\n" +
            " \"time_last_update_unix\":1700179201,\n" +
            " \"time_last_update_utc\":\"Fri, 17 Nov 2023 00:00:01 +0000\",\n" +
            " \"time_next_update_unix\":1700265601,\n" +
            " \"time_next_update_utc\":\"Sat, 18 Nov 2023 00:00:01 +0000\",\n" +
            " \"base_code\":\"BRL\",\n" +
            " \"conversion_rates\":{\n" +
            "  \"BRL\":1,\n" +
            "  \"AED\":0.7553,\n" +
            "  \"AFN\":14.5786,\n" +
            "  \"ALL\":19.9241,\n" +
            "  \"AMD\":82.7050,\n" +
            "  \"ANG\":0.3681,\n" +
            "  \"AOA\":172.7050,\n" +
            "  \"ARS\":72.6997,\n" +
            "  \"AUD\":0.3175,\n" +
            "  \"AWG\":0.3681,\n" +
            "  \"AZN\":0.3472,\n" +
            "  \"BAM\":0.3710,\n" +
            "  \"BBD\":0.4113,\n" +
            "  \"BDT\":22.7956,\n" +
            "  \"BGN\":0.3710,\n" +
            "  \"BHD\":0.07733,\n" +
            "  \"BIF\":582.2385,\n" +
            "  \"BMD\":0.2057,\n" +
            "  \"BND\":0.2773,\n" +
            "  \"BOB\":1.4054,\n" +
            "  \"BSD\":0.2057,\n" +
            "  \"BTN\":17.1142,\n" +
            "  \"BWP\":2.7647,\n" +
            "  \"BYN\":0.6591,\n" +
            "  \"BZD\":0.4113,\n" +
            "  \"CAD\":0.2825,\n" +
            "  \"CDF\":511.8065,\n" +
            "  \"CHF\":0.1828,\n" +
            "  \"CLP\":183.1510,\n" +
            "  \"CNY\":1.4906,\n" +
            "  \"COP\":837.9212,\n" +
            "  \"CRC\":107.9239,\n" +
            "  \"CUP\":4.9360,\n" +
            "  \"CVE\":20.9150,\n" +
            "  \"CZK\":4.6378,\n" +
            "  \"DJF\":36.5516,\n" +
            "  \"DKK\":1.4142,\n" +
            "  \"DOP\":11.5590,\n" +
            "  \"DZD\":27.5496,\n" +
            "  \"EGP\":6.3609,\n" +
            "  \"ERN\":3.0850,\n" +
            "  \"ETB\":11.5013,\n" +
            "  \"EUR\":0.1895,\n" +
            "  \"FJD\":0.4671,\n" +
            "  \"FKP\":0.1659,\n" +
            "  \"FOK\":1.4142,\n" +
            "  \"GBP\":0.1658,\n" +
            "  \"GEL\":0.5548,\n" +
            "  \"GGP\":0.1659,\n" +
            "  \"GHS\":2.4785,\n" +
            "  \"GIP\":0.1659,\n" +
            "  \"GMD\":13.4740,\n" +
            "  \"GNF\":1769.7676,\n" +
            "  \"GTQ\":1.5917,\n" +
            "  \"GYD\":43.0556,\n" +
            "  \"HKD\":1.6050,\n" +
            "  \"HNL\":5.0197,\n" +
            "  \"HRK\":1.4291,\n" +
            "  \"HTG\":27.3552,\n" +
            "  \"HUF\":71.4224,\n" +
            "  \"IDR\":3204.1523,\n" +
            "  \"ILS\":0.7774,\n" +
            "  \"IMP\":0.1659,\n" +
            "  \"INR\":17.1139,\n" +
            "  \"IQD\":268.9153,\n" +
            "  \"IRR\":8750.3986,\n" +
            "  \"ISK\":29.0781,\n" +
            "  \"JEP\":0.1659,\n" +
            "  \"JMD\":31.5682,\n" +
            "  \"JOD\":0.1458,\n" +
            "  \"JPY\":31.1162,\n" +
            "  \"KES\":31.1550,\n" +
            "  \"KGS\":18.3637,\n" +
            "  \"KHR\":846.1867,\n" +
            "  \"KID\":0.3176,\n" +
            "  \"KMF\":93.3162,\n" +
            "  \"KRW\":265.9788,\n" +
            "  \"KWD\":0.06305,\n" +
            "  \"KYD\":0.1714,\n" +
            "  \"KZT\":94.9296,\n" +
            "  \"LAK\":4192.2900,\n" +
            "  \"LBP\":3085.0289,\n" +
            "  \"LKR\":67.6337,\n" +
            "  \"LRD\":38.7537,\n" +
            "  \"LSL\":3.7724,\n" +
            "  \"LYD\":0.9943,\n" +
            "  \"MAD\":2.0759,\n" +
            "  \"MDL\":3.6685,\n" +
            "  \"MGA\":933.2941,\n" +
            "  \"MKD\":11.6734,\n" +
            "  \"MMK\":426.6649,\n" +
            "  \"MNT\":701.6913,\n" +
            "  \"MOP\":1.6531,\n" +
            "  \"MRU\":8.0753,\n" +
            "  \"MUR\":9.0625,\n" +
            "  \"MVR\":3.1375,\n" +
            "  \"MWK\":349.2326,\n" +
            "  \"MXN\":3.5531,\n" +
            "  \"MYR\":0.9644,\n" +
            "  \"MZN\":13.1687,\n" +
            "  \"NAD\":3.7724,\n" +
            "  \"NGN\":185.6492,\n" +
            "  \"NIO\":7.4370,\n" +
            "  \"NOK\":2.2346,\n" +
            "  \"NPR\":27.3828,\n" +
            "  \"NZD\":0.3436,\n" +
            "  \"OMR\":0.07908,\n" +
            "  \"PAB\":0.2057,\n" +
            "  \"PEN\":0.7848,\n" +
            "  \"PGK\":0.7553,\n" +
            "  \"PHP\":11.4610,\n" +
            "  \"PKR\":58.8639,\n" +
            "  \"PLN\":0.8302,\n" +
            "  \"PYG\":1500.4819,\n" +
            "  \"QAR\":0.7486,\n" +
            "  \"RON\":0.9432,\n" +
            "  \"RSD\":22.2401,\n" +
            "  \"RUB\":18.3745,\n" +
            "  \"RWF\":263.1891,\n" +
            "  \"SAR\":0.7713,\n" +
            "  \"SBD\":1.7219,\n" +
            "  \"SCR\":2.6819,\n" +
            "  \"SDG\":91.9768,\n" +
            "  \"SEK\":2.1773,\n" +
            "  \"SGD\":0.2772,\n" +
            "  \"SHP\":0.1659,\n" +
            "  \"SLE\":4.5722,\n" +
            "  \"SLL\":4572.2041,\n" +
            "  \"SOS\":117.5259,\n" +
            "  \"SRD\":7.8545,\n" +
            "  \"SSP\":220.0369,\n" +
            "  \"STN\":4.6472,\n" +
            "  \"SYP\":2625.9776,\n" +
            "  \"SZL\":3.7724,\n" +
            "  \"THB\":7.3011,\n" +
            "  \"TJS\":2.2449,\n" +
            "  \"TMT\":0.7197,\n" +
            "  \"TND\":0.6390,\n" +
            "  \"TOP\":0.4834,\n" +
            "  \"TRY\":5.8999,\n" +
            "  \"TTD\":1.3618,\n" +
            "  \"TVD\":0.3176,\n" +
            "  \"TWD\":6.5612,\n" +
            "  \"TZS\":517.9306,\n" +
            "  \"UAH\":7.4595,\n" +
            "  \"UGX\":778.3072,\n" +
            "  \"USD\":0.2057,\n" +
            "  \"UYU\":8.1118,\n" +
            "  \"UZS\":2523.0658,\n" +
            "  \"VES\":7.2868,\n" +
            "  \"VND\":4967.5938,\n" +
            "  \"VUV\":24.7179,\n" +
            "  \"WST\":0.5643,\n" +
            "  \"XAF\":124.4217,\n" +
            "  \"XCD\":0.5553,\n" +
            "  \"XDR\":0.1552,\n" +
            "  \"XOF\":124.4217,\n" +
            "  \"XPF\":22.6348,\n" +
            "  \"YER\":50.8595,\n" +
            "  \"ZAR\":3.7726,\n" +
            "  \"ZMW\":4.7831,\n" +
            "  \"ZWL\":1187.0918\n" +
            " }\n" +
            "}";

    public static final String MOCKED_PAIR_CONVERSION_RESPONSE = "{" +
            "\"result\":\"success\"," +
            "\"documentation\":\"https://www.exchangerate-api.com/docs\"," +
            "\"terms_of_use\":\"https://www.exchangerate-api.com/terms\"," +
            "\"time_last_update_unix\":1700179201," +
            "\"time_last_update_utc\":\"Fri, 17 Nov 2023 00:00:01 +0000\"," +
            "\"time_next_update_unix\":1700265601," +
            "\"time_next_update_utc\":\"Sat, 18 Nov 2023 00:00:01 +0000\"," +
            "\"base_code\":\"BRL\"," +
            "\"target_code\":\"EUR\"," +
            "\"conversion_rate\":0.1895" +
            "}";

    public final static String MOCKED_PAIR_AMOUNT_RESPONSE = "{" +
            "\"result\":\"success\"," +
            "\"documentation\":\"https://www.exchangerate-api.com/docs\"," +
            "\"terms_of_use\":\"https://www.exchangerate-api.com/terms\"," +
            "\"time_last_update_unix\":1700179201," +
            "\"time_last_update_utc\":\"Fri, 17 Nov 2023 00:00:01 +0000\"," +
            "\"time_next_update_unix\":1700265601," +
            "\"time_next_update_utc\":\"Sat, 18 Nov 2023 00:00:01 +0000\"," +
            "\"base_code\":\"BRL\"," +
            "\"target_code\":\"EUR\"," +
            "\"conversion_rate\":0.1895," +
            "\"conversion_result\":189.5" +
            "}";

}
