package com.osiris.ahi;

import com.osiris.ahi.organs.brain.Brain;

public class Body {
    private Brain brain;

    public Body() throws Exception {
        this(new Brain());
    }

    public Body(Brain brain) {
        this.brain = brain;
    }

    public Brain getBrain() {
        return brain;
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }
}
