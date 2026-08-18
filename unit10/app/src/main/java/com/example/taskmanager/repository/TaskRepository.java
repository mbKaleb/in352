package com.example.taskmanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taskmanager.model.Task.Status;
import com.example.taskmanager.model.Task.Priority;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findByUser(User user);
    List<Task> findByUserId(String id);
    List<Task> findByUserAndStatus(User user, Status status);
    List<Task> findByUserAndPriority(User user, Priority priority);

    Page<Task> findByUser(User user, Pageable pageable);
    Page<Task> findByUserAndStatus(User user, Status status, Pageable pageable);
    Page<Task> findByUserAndPriority(User user, Priority priority, Pageable pageable);

}