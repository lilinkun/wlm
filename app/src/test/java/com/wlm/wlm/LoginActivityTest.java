package com.wlm.wlm;

import android.app.Activity;

import com.wlm.wlm.activity.LoginActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    @Test
    public void titleIsCorrect() throws Exception{
        Activity activity = Robolectric.setupActivity(LoginActivity.class);
        assertTrue(activity.getTitle().toString().equals("adsadsa"));
    }
}
