package com.bugsnag.android.mazerunner.scenarios

import android.app.Activity
import android.content.Context
import com.bugsnag.android.Bugsnag
import com.bugsnag.android.Configuration
import com.bugsnag.android.createCustomHeaderDelivery

/**
 * Sends a session which is cached on disk to Bugsnag, then sent on a separate launch,
 * using a custom API client which modifies the request.
 */
internal class CustomClientSessionFlushScenario(config: Configuration,
                                                context: Context) : Scenario(config, context) {
    init {
        config.autoTrackSessions = false

        if (context is Activity) {
            eventMetaData = context.intent.getStringExtra("EVENT_METADATA")
            if ("online" == eventMetaData) {
                config.delivery = createCustomHeaderDelivery()
            } else {
                disableAllDelivery(config)
            }
        }
    }

    override fun run() {
        super.run()

        Bugsnag.startSession()
    }
}
