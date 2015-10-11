package nl.ordina.jtech.bigdata.myo.collector.view;

import com.airhacks.afterburner.views.FXMLView;

import javax.annotation.PostConstruct;

/**
 * Created by pieter on 9/18/2015.
 */
public class MainApplicationView extends FXMLView {

    @PostConstruct
    public void init() {
        System.out.println("Tower.init()");
    }
}
