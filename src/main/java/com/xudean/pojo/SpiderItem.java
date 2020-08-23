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
}
