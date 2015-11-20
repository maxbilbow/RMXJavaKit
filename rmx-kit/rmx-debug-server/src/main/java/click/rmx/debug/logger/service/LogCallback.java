package click.rmx.debug.logger.service;

import click.rmx.debug.logger.model.Log;

import java.util.List;

/**
 * Created by bilbowm (Max Bilbow) on 19/11/2015.
 */
@FunctionalInterface
public interface LogCallback {

    void invoke(Log log, List<Throwable> errors);
}
