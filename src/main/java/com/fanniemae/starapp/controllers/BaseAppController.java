package com.fanniemae.starapp.controllers;

import com.fanniemae.starapp.exceptions.handler.AppErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseAppController {

    @Autowired
    protected AppErrorHandler errorHandler;
}
