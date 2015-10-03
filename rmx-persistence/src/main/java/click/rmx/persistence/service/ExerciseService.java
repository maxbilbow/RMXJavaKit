package click.rmx.persistence.service;


import click.rmx.persistence.model.Activity;
import click.rmx.persistence.model.Exercise;

import java.util.List;

public interface ExerciseService extends EntityService<Exercise> {
    List<Activity> findAllActivities();
}