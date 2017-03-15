package com.veripark.imkb.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Kemal on 1/13/16.
 */
public class Period {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({DAILY, WEEKLY, MONTHLY})

    public @interface Type {}

    public static final String DAILY ="Day";
    public static final String WEEKLY = "Week";
    public static final String MONTHLY = "Month";
}
