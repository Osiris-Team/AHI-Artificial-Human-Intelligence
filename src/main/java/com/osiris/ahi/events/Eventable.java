/*
 *  Copyright Osiris Team
 *  All rights reserved.
 *
 *  This software is licensed work.
 *  Please consult the file "LICENSE" for details.
 */

package com.osiris.ai.events;

public interface Eventable<E> {
    void runOnEvent(E e);
}
