package com.xudean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * create table spider
 * (
 *     task_id         int auto_increment,
 *     task_start_time varchar,
 *     task_end_time   varchar,
 *     task_status     varchar,
 *     task_store_path varchar,
 *     PRIMARY KEY (`task_id`)
 * );
 */
@Data
public class SpiderItem {
    @JsonProperty("task_id")
    private Long taskId;
    @JsonProperty("task_start_time")
    private String taskStartTime;
    @JsonProperty("task_end_time")
    private String taskEndTime;
    @JsonProperty("task_status")
    private String taskStatus;
    @JsonProperty("task_store_path")
    private String taskStorePath;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskStorePath() {
        return taskStorePath;
    }

    public void setTaskStorePath(String taskStorePath) {
        this.taskStorePath = taskStorePath;
    }
}
