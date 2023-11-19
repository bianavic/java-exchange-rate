package org.edu.fabs.exchangerate.utils;

public class Constants {

    public static final String BASE_CODE = "BRL";
    public static final String TARGET_CODE = "EUR";
    public static final String MOCKED_SUPPORTED_CURRENCIES_RESPONSE = "{\n" +
            " \"result\":\"success\",\n" +
            " \"documentation\":\"https://www.exchangerate-api.com/docs\",\n" +
            " \"terms_of_use\":\"https://www.exchangerate-api.com/terms\",\n" +
            " \"time_last_update_unix\":1700265601,\n" +
            " \"time_last_update_utc\":\"Sat, 18 Nov 2023 00:00:01 +0000\",\n" +
            " \"time_next_update_unix\":1700352001,\n" +
            " \"time_next_update_utc\":\"Sun, 19 Nov 2023 00:00:01 +0000\",\n" +
            " \"base_code\":\"BRL\",\n" +
            " \"conversion_rates\":{\n" +
            "  \"BRL\":1,\n" +
            "  \"AED\":0.7547,\n" +
            "  \"AFN\":14.4188,\n" +
            "  \"ALL\":19.8540,\n" +
            "  \"AMD\":82.5834,\n" +
            "  \"ANG\":0.3678,\n" +
            "  \"AOA\":171.4318,\n" +
            "  \"ARS\":72.7481,\n" +
            "  \"AUD\":0.3154,\n" +
            "  \"AWG\":0.3678,\n" +
            "  \"AZN\":0.3488,\n" +
            "  \"BAM\":0.3698,\n" +
            "  \"BBD\":0.4110,\n" +
            "  \"BDT\":22.7326,\n" +
            "  \"BGN\":0.3698,\n" +
            "  \"BHD\":0.07727,\n" +
            "  \"BIF\":582.2385,\n" +
            "  \"BMD\":0.2055,\n" +
            "  \"BND\":0.2761,\n" +
            "  \"BOB\":1.4133,\n" +
            "  \"BSD\":0.2055,\n" +
            "  \"BTN\":17.1122,\n" +
            "  \"BWP\":2.7541,\n" +
            "  \"BYN\":0.6567,\n" +
            "  \"BZD\":0.4110,\n" +
            "  \"CAD\":0.2820,\n" +
            "  \"CDF\":511.8065,\n" +
            "  \"CHF\":0.1824,\n" +
            "  \"CLP\":182.0715,\n" +
            "  \"CNY\":1.4797,\n" +
            "  \"COP\":836.7076,\n" +
            "  \"CRC\":108.3980,\n" +
            "  \"CUP\":4.9319,\n" +
            "  \"CVE\":20.8500,\n" +
            "  \"CZK\":4.6280,\n" +
            "  \"DJF\":36.5212,\n" +
            "  \"DKK\":1.4116,\n" +
            "  \"DOP\":11.6171,\n" +
            "  \"DZD\":27.5934,\n" +
            "  \"EGP\":6.3554,\n" +
            "  \"ERN\":3.0825,\n" +
            "  \"ETB\":11.5013,\n" +
            "  \"EUR\":0.1891,\n" +
            "  \"FJD\":0.4673,\n" +
            "  \"FKP\":0.1654,\n" +
            "  \"FOK\":1.4116,\n" +
            "  \"GBP\":0.1653,\n" +
            "  \"GEL\":0.5539,\n" +
            "  \"GGP\":0.1654,\n" +
            "  \"GHS\":2.4639,\n" +
            "  \"GIP\":0.1654,\n" +
            "  \"GMD\":13.3208,\n" +
            "  \"GNF\":1756.5732,\n" +
            "  \"GTQ\":1.5994,\n" +
            "  \"GYD\":43.0556,\n" +
            "  \"HKD\":1.6025,\n" +
            "  \"HNL\":5.0452,\n" +
            "  \"HRK\":1.4247,\n" +
            "  \"HTG\":27.3552,\n" +
            "  \"HUF\":71.3910,\n" +
            "  \"IDR\":3175.9838,\n" +
            "  \"ILS\":0.7653,\n" +
            "  \"IMP\":0.1654,\n" +
            "  \"INR\":17.1122,\n" +
            "  \"IQD\":268.9153,\n" +
            "  \"IRR\":8680.9312,\n" +
            "  \"ISK\":28.9546,\n" +
            "  \"JEP\":0.1654,\n" +
            "  \"JMD\":31.7731,\n" +
            "  \"JOD\":0.1457,\n" +
            "  \"JPY\":30.6537,\n" +
            "  \"KES\":31.2773,\n" +
            "  \"KGS\":18.3406,\n" +
            "  \"KHR\":846.1867,\n" +
            "  \"KID\":0.3162,\n" +
            "  \"KMF\":93.0263,\n" +
            "  \"KRW\":266.0721,\n" +
            "  \"KWD\":0.06325,\n" +
            "  \"KYD\":0.1712,\n" +
            "  \"KZT\":95.1715,\n" +
            "  \"LAK\":4208.6379,\n" +
            "  \"LBP\":3082.4579,\n" +
            "  \"LKR\":67.2069,\n" +
            "  \"LRD\":39.0246,\n" +
            "  \"LSL\":3.7555,\n" +
            "  \"LYD\":0.9943,\n" +
            "  \"MAD\":2.0817,\n" +
            "  \"MDL\":3.6655,\n" +
            "  \"MGA\":933.2941,\n" +
            "  \"MKD\":11.6633,\n" +
            "  \"MMK\":428.8068,\n" +
            "  \"MNT\":702.5658,\n" +
            "  \"MOP\":1.6506,\n" +
            "  \"MRU\":8.0753,\n" +
            "  \"MUR\":9.0561,\n" +
            "  \"MVR\":3.1552,\n" +
            "  \"MWK\":346.9814,\n" +
            "  \"MXN\":3.5291,\n" +
            "  \"MYR\":0.9619,\n" +
            "  \"MZN\":13.0679,\n" +
            "  \"NAD\":3.7555,\n" +
            "  \"NGN\":200.4183,\n" +
            "  \"NIO\":7.4749,\n" +
            "  \"NOK\":2.2348,\n" +
            "  \"NPR\":27.3795,\n" +
            "  \"NZD\":0.3425,\n" +
            "  \"OMR\":0.07901,\n" +
            "  \"PAB\":0.2055,\n" +
            "  \"PEN\":0.7788,\n" +
            "  \"PGK\":0.7598,\n" +
            "  \"PHP\":11.4258,\n" +
            "  \"PKR\":59.1867,\n" +
            "  \"PLN\":0.8281,\n" +
            "  \"PYG\":1504.9517,\n" +
            "  \"QAR\":0.7480,\n" +
            "  \"RON\":0.9402,\n" +
            "  \"RSD\":22.1781,\n" +
            "  \"RUB\":18.4139,\n" +
            "  \"RWF\":263.2753,\n" +
            "  \"SAR\":0.7706,\n" +
            "  \"SBD\":1.7308,\n" +
            "  \"SCR\":2.8921,\n" +
            "  \"SDG\":91.9768,\n" +
            "  \"SEK\":2.1703,\n" +
            "  \"SGD\":0.2761,\n" +
            "  \"SHP\":0.1654,\n" +
            "  \"SLE\":4.5684,\n" +
            "  \"SLL\":4568.3937,\n" +
            "  \"SOS\":117.5259,\n" +
            "  \"SRD\":7.8545,\n" +
            "  \"SSP\":218.5249,\n" +
            "  \"STN\":4.6327,\n" +
            "  \"SYP\":2633.7429,\n" +
            "  \"SZL\":3.7555,\n" +
            "  \"THB\":7.2047,\n" +
            "  \"TJS\":2.2492,\n" +
            "  \"TMT\":0.7189,\n" +
            "  \"TND\":0.6362,\n" +
            "  \"TOP\":0.4834,\n" +
            "  \"TRY\":5.8985,\n" +
            "  \"TTD\":1.3618,\n" +
            "  \"TVD\":0.3162,\n" +
            "  \"TWD\":6.5058,\n" +
            "  \"TZS\":514.0524,\n" +
            "  \"UAH\":7.4296,\n" +
            "  \"UGX\":772.7845,\n" +
            "  \"USD\":0.2055,\n" +
            "  \"UYU\":8.1525,\n" +
            "  \"UZS\":2516.8645,\n" +
            "  \"VES\":7.2906,\n" +
            "  \"VND\":4974.1047,\n" +
            "  \"VUV\":24.7179,\n" +
            "  \"WST\":0.5646,\n" +
            "  \"XAF\":124.0350,\n" +
            "  \"XCD\":0.5548,\n" +
            "  \"XDR\":0.1551,\n" +
            "  \"XOF\":124.0350,\n" +
            "  \"XPF\":22.5645,\n" +
            "  \"YER\":51.1183,\n" +
            "  \"ZAR\":3.7637,\n" +
            "  \"ZMW\":4.7491,\n" +
            "  \"ZWL\":1185.0861\n" +
            " }\n" +
            "}";

    public static final String MOCKED_PAIR_CONVERSION_RESPONSE = "{\"result\":\"success\",\"documentation\":\"https://www.exchangerate-api.com/docs\",\"terms_of_use\":\"https://www.exchangerate-api.com/terms\",\"time_last_update_unix\":1700265601,\"time_last_update_utc\":\"Sat, 18 Nov 2023 00:00:01 +0000\",\"time_next_update_unix\":1700352001,\"time_next_update_utc\":\"Sun, 19 Nov 2023 00:00:01 +0000\",\"base_code\":\"BRL\",\"target_code\":\"EUR\",\"conversion_rate\":0.1891}";

    public final static String MOCKED_PAIR_AMOUNT_RESPONSE = "{\"result\":\"success\",\"documentation\":\"https://www.exchangerate-api.com/docs\",\"terms_of_use\":\"https://www.exchangerate-api.com/terms\",\"time_last_update_unix\":1700265601,\"time_last_update_utc\":\"Sat, 18 Nov 2023 00:00:01 +0000\",\"time_next_update_unix\":1700352001,\"time_next_update_utc\":\"Sun, 19 Nov 2023 00:00:01 +0000\",\"base_code\":\"BRL\",\"target_code\":\"EUR\",\"conversion_rate\":0.1891,\"conversion_result\":189.1}";

    public final static String MOCKED_SUPPORTED_CURRENCIES_RESPONSE_404 = "{\n" +
            "  \"result\":\"error\",\n" +
            "  \"documentation\":\"https://www.exchangerate-api.com/docs\",\n" +
            "  \"terms-of-use\":\"https://www.exchangerate-api.com/terms\",\n" +
            "  \"error-type\":\"unsupported-code\"\n" +
            "}";

}
