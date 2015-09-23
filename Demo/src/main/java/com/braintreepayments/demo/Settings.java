package com.braintreepayments.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

@SuppressWarnings("com.braintreepayments.beta")
public class Settings {

    protected static final String ENVIRONMENT = "environment";

    private static final String SANDBOX_BASE_SERVER_URL = "https://braintree-demo.herokuapp.com";
    //private static final String SANDBOX_BASE_SERVER_URL = "localhost:5000";

    private static final String PRODUCTION_BASE_SERVER_URL = "https://executive-sample-merchant.herokuapp.com";

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static int getEnvironment(Context context) {
        return getPreferences(context).getInt(ENVIRONMENT, 0);
    }

    public static void setEnvironment(Context context, int environment) {
        getPreferences(context)
                .edit()
                .putInt(ENVIRONMENT, environment)
                .apply();
    }

    public static String getSandboxUrl() {
        return SANDBOX_BASE_SERVER_URL;
    }

    public static String getEnvironmentUrl(Context context) {
        int environment = getEnvironment(context);
        if (environment == 0) {
            return SANDBOX_BASE_SERVER_URL;
        } else if (environment == 1) {
            return PRODUCTION_BASE_SERVER_URL;
        } else {
            return "";
        }
    }

    public static String getCustomerId(Context context) {
        return getPreferences(context).getString("customer", null);
    }

    public static String getThreeDSecureMerchantAccountId(Context context) {
        if (isThreeDSecureEnabled(context) && getEnvironment(context) == 1) {
            return "test_AIB";
        } else {
            return null;
        }
    }

    public static boolean isAndroidPayBillingAgreement(Context context) {
        return getPreferences(context).getBoolean("android_pay_billing_agreement", false);
    }

    public static boolean isAndroidPayShippingAddressRequired(Context context) {
        return getPreferences(context).getBoolean("android_pay_require_shipping_address", false);
    }

    public static boolean isAndroidPayPhoneNumberRequired(Context context) {
        return getPreferences(context).getBoolean("android_pay_require_phone_number", false);
    }

    public static boolean isPayPalAddressScopeRequested(Context context) {
        return getPreferences(context).getBoolean("paypal_request_address_scope", false);
    }

    public static boolean isThreeDSecureEnabled(Context context) {
        return getPreferences(context).getBoolean("enable_three_d_secure", false);
    }

    public static boolean isThreeDSecureRequired(Context context) {
        return getPreferences(context).getBoolean("require_three_d_secure", true);
    }
}
