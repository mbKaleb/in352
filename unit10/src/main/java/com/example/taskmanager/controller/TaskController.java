package com.example.taskmanager.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.Task.Priority;
import com.example.taskmanager.model.Task.Status;
import com.example.taskmanager.model.TaskForm;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Principal principal, Model model,
                                @RequestParam(required = false) Status status,
                                @RequestParam(required = false) Priority priority) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));

        List<Task> tasks;
        if (status != null) {
            tasks = taskRepository.findByUserAndStatus(user, status);
        } else if (priority != null) {
            tasks = taskRepository.findByUserAndPriority(user, priority);
        } else {
            tasks = taskRepository.findByUser(user);
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("status", status);
        model.addAttribute("priority", priority);

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
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("showErrorModal", true);
            return "taskform";
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));

        Task task = new Task(user, taskForm.getTitle(), taskForm.getDescription(),
                taskForm.getDueDate(), taskForm.getPriority());
        taskRepository.save(task);

        log.info("User '{}' created task '{}' (id={})", user.getUsername(), task.getTitle(), task.getId());

        redirectAttributes.addFlashAttribute("successMessage",
                "✔ Task \"" + task.getTitle() + "\" was created!");
        return "redirect:/dashboard";
    }

    @GetMapping("/tasks/{id}/edit")
    public String showEditForm(Principal principal, @PathVariable String id, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        Task task = taskRepository.findById(id).orElseThrow();
        if (!task.getUser().getUsername().equals(principal.getName())) {
            return "redirect:/dashboard";
        }

        TaskForm taskForm = new TaskForm();
        taskForm.setTitle(task.getTitle());
        taskForm.setDescription(task.getDescription());
        taskForm.setDueDate(task.getDueDate());
        taskForm.setPriority(task.getPriority());

        model.addAttribute("taskForm", taskForm);
        model.addAttribute("taskId", id);
        return "taskform";
    }

    @PostMapping("/tasks/{id}")
    public String updateTask(Principal principal, @PathVariable String id,
                              @Valid @ModelAttribute("taskForm") TaskForm taskForm,
                              BindingResult result,
                              Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("taskId", id);
            return "taskform";
        }

        Task task = taskRepository.findById(id).orElseThrow();
        if (!task.getUser().getUsername().equals(principal.getName())) {
            return "redirect:/dashboard";
        }

        task.setTitle(taskForm.getTitle());
        task.setDescription(taskForm.getDescription());
        task.setDueDate(taskForm.getDueDate());
        task.setPriority(taskForm.getPriority());
        taskRepository.save(task);

        log.info("User '{}' updated task '{}' (id={})", principal.getName(), task.getTitle(), task.getId());

        return "redirect:/dashboard";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(Principal principal, @PathVariable String id) {
        if (principal == null) {
            return "redirect:/login";
        }

        Task task = taskRepository.findById(id).orElseThrow();
        if (task.getUser().getUsername().equals(principal.getName())) {
            taskRepository.delete(task);
            log.info("User '{}' deleted task '{}' (id={})", principal.getName(), task.getTitle(), task.getId());
        } else {
            log.warn("User '{}' attempted to delete task (id={}) owned by another user", principal.getName(), id);
        }

        return "redirect:/dashboard";
    }
}