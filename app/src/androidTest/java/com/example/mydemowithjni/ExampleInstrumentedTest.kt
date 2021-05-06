package com.example.mydemowithjni

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mydemowithjni.activity.LearnActivity
import com.example.mydemowithjni.activity.StartActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mydemowithjni", appContext.packageName)
    }
    @get:Rule var activityScenarioRule = ActivityScenarioRule(LearnActivity::class.java)
    @Test
    fun testEvent(){
//        val scenario = activityScenarioRule.scenario
        // 要创建被测 Activity
        val scenario = launch(LearnActivity::class.java)
        // 更改被测Activity的生命周期状态
        // scenario.moveToState(Lifecycle.State.CREATED)
        // Finishes the managed activity and cleans up device's state.
        // scenario.close()
        // 确定当前的Activity状态
        // scenario.onActivity { activity ->
        //     startActivity(activity, Intent(activity, StartActivity::class.java), null)
        // }
        // val originalActivityState = scenario.state
        // 重新创建Activity
        // scenario.recreate()
        // 触发Activity中的操作
        onView(withId(R.id.startBtn)).perform(click())
    }

}