package com.braintreepayments.api.models;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.braintreepayments.api.exceptions.ErrorWithResponse;

import static com.braintreepayments.testutils.FixturesHelper.stringFromFixture;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ThreeDSecureAuthenticationResponseTest {

    @Test(timeout = 1000)
    @SmallTest
    public void fromJson_parsesCorrectly() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                stringFromFixture("three_d_secure/authentication_response.json"));

        assertEquals("11", authResponse.getCard().getLastTwo());
        assertTrue(authResponse.getCard().getThreeDSecureInfo().isLiabilityShifted());
        assertTrue(authResponse.getCard().getThreeDSecureInfo().isLiabilityShiftPossible());
        assertTrue(authResponse.isSuccess());
    }

    @Test(timeout = 1000)
    @SmallTest
    public void fromException_parsesCorrectly() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse
                .fromException("Error!");

        assertFalse(authResponse.isSuccess());
        assertEquals("Error!", authResponse.getException());
    }

    @Test(timeout = 1000)
    @SmallTest
    public void getErrors_returnsErrorString() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                stringFromFixture("three_d_secure/authentication_response_with_error.json"));
        ErrorWithResponse errors = new ErrorWithResponse(0, authResponse.getErrors());

        assertNull(authResponse.getCard());
        assertFalse(authResponse.isSuccess());
        assertEquals("Failed to authenticate, please try a different form of payment", errors.getMessage());
    }

    @Test(timeout = 1000)
    @SmallTest
    public void isParcelable() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                stringFromFixture("three_d_secure/authentication_response.json"));

        Intent intent = new Intent().putExtra("auth-response", authResponse);
        ThreeDSecureAuthenticationResponse parsedAuthResponse = intent.getParcelableExtra("auth-response");

        assertEquals(authResponse.getCard().getLastTwo(), parsedAuthResponse.getCard().getLastTwo());
        assertEquals(authResponse.getCard().getThreeDSecureInfo().isLiabilityShifted(),
                parsedAuthResponse.getCard().getThreeDSecureInfo().isLiabilityShifted());
        assertEquals(authResponse.getCard().getThreeDSecureInfo().isLiabilityShiftPossible(),
                parsedAuthResponse.getCard().getThreeDSecureInfo().isLiabilityShiftPossible());
        assertEquals(authResponse.isSuccess(), parsedAuthResponse.isSuccess());
        assertEquals(authResponse.getException(), parsedAuthResponse.getException());
    }

    @Test(timeout = 1000)
    @SmallTest
    public void exceptionsAreParcelable() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse
                .fromException("Error!");

        Intent intent = new Intent().putExtra("auth-response", authResponse);
        ThreeDSecureAuthenticationResponse parsedAuthResponse = intent.getParcelableExtra("auth-response");

        assertEquals(authResponse.isSuccess(), parsedAuthResponse.isSuccess());
        assertEquals(authResponse.getException(), parsedAuthResponse.getException());
    }
}
