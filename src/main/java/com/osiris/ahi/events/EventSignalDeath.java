/*
 *  Copyright Osiris Team
 *  All rights reserved.
 *
 *  This software is licensed work.
 *  Please consult the file "LICENSE" for details.
 */

package com.osiris.ai.events;

import com.osiris.ai.Signal;

import java.sql.Timestamp;

public class EventSignalDeath {
    private Signal signal;
    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public EventSignalDeath(Signal signal) {
        this.signal = signal;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
