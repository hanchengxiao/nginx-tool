package com.ohan.tool.nginx.type;

public enum GlobalBlockConfigKey {
    /**
     * Specify the running user and group for the Nginx worker process
     * user username [groupname];
     */
    USER("user"),
    /**
     * Include other configuration files to improve configuration modularity
     * include path;
     */
    INCLUDE("include"),
    /**
     * Specify the error log file
     * error_log path [level];
     */
    ERROR_LOG("error_log"),
    /**
     * Specify the file to which the process ID is written
     * pid path;
     */
    PID("pid"),
    /**
     * Specify the working directory for the Nginx worker process
     * working_directory path;
     */
    WORKING_DIRECTORY("working_directory"),
    /**
     * Specify the number of worker processes to run
     * worker_processes number;
     */
    WORKER_PROCESSES("worker_processes"),
    /**
     * Bind work processes to specific CPU cores to optimize performance
     * worker_cpu_affinity cpumask [cpumask...];
     */
    WORKER_CPU_AFFINITY("worker_cpu_affinity"),
    /**
     * Specify the maximum size of the core dump file
     * worker_rlimit_core size;
     */
    WORKER_RLIMIT_CORE("worker_rlimit_core"),
    /**
     * Specify the maximum number of open file descriptors
     * worker_rlimit_nofile number;
     */
    WORKER_RLIMIT_NOFILE("worker_rlimit_nofile"),
    /**
     * Specify the path of Nginx lock file (for multi master process scenarios)
     * lock_file path;
     */
    LOCK_FILE("lock_file"),
    /**
     * Keep or set environment variables
     * env variable;
     * env variable=value;
     */
    ENV("env"),
    ;
    private final String key;

    GlobalBlockConfigKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
