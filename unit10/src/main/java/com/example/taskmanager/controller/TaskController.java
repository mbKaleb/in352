package com.example.taskmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskForm;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));

        List<Task> tasks = taskRepository.findByUser(user);
        model.addAttribute("tasks", tasks);

        return "dashboard";
    }

    @GetMapping("/tasks/new")
    public String showTaskForm(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        model.addAttribute("taskForm", new TaskForm());
        return "taskform";
    }

    @PostMapping("/tasks")
    public String createTask(Principal principal,
                              @Valid @ModelAttribute("taskForm") TaskForm taskForm,
                              BindingResult result) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "taskform";
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));

        Task task = new Task(user, taskForm.getTitle(), taskForm.getDescription(),
                taskForm.getDueDate(), taskForm.getPriority());
        taskRepository.save(task);

        return "redirect:/dashboard";
    }
}