package com.santamaria.flappykek.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Nicolò Santamaria on 28/01/17.
 */

public class GameStateManager {
    private Stack<State> states;

    public GameStateManager () {
        states = new Stack<State>();
    }

    public void push (State state) {
        states.push(state);
    }

//    public void pop (boolean needsDispose) {
//        if (needsDispose)
//            states.pop().dispose();
//        else
//            states.pop();
//    }

    public void pop() {
        states.pop().dispose();
    }

    public void set (State state) {
        states.pop().dispose();
        states.push(state);
        return;
    }

    public void update (float deltaTime) {
        states.peek().update(deltaTime);
    }

    public void render (SpriteBatch sb) {
        states.peek().render(sb);
    }
}
