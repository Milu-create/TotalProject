package com.totalproject;

import java.util.TimerTask;

public class Rule_for_point extends TimerTask {
    User user;
        public void run() {
            user = Login.user;
            user.setPoint(user.getPoint()+1);
        }
}
