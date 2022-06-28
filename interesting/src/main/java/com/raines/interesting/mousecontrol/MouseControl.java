package com.raines.interesting.mousecontrol;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 控制鼠标一直点击某个地方
 */
public class MouseControl {

    public static void main(String[] args) {
        int i=5000;
        try {
            Thread.sleep(i);
            Robot robot = new Robot();
            int n = 10000;
            while (n-- > 0){
                robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
