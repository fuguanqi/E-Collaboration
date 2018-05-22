package com.ec.controller.resp;

import com.ec.entity.Phase;
import com.ec.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class RespPhaseInfo {
    private Phase phase;
    private List<Object> tasks = new ArrayList<Object>();

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public List<Object> getTasks() {
        return tasks;
    }

    public void setTasks(List<Object> tasks) {
        this.tasks = tasks;
    }
}
