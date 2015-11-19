package click.rmx.debug.server.service;

import click.rmx.debug.server.model.Log;

import java.util.List;

/**
 * Created by bilbowm (Max Bilbow) on 19/11/2015.
 */
@FunctionalInterface
public interface LogCallback {

    void invoke(Log log, List<Throwable> errors);
}
