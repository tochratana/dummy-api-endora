package space.endora.dummy.features.task.controller;

import space.endora.dummy.features.task.dto.TaskDto;
import space.endora.dummy.features.task.service.TaskService;
import space.endora.dummy.features.task.service.impl.CleanupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class TaskController {

    private final TaskService taskService;
    private final CleanupService cleanupService;

    @PostMapping
    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<TaskDto.TaskResponse> createTask(@Valid @RequestBody TaskDto.CreateTaskRequest request) {
        TaskDto.TaskResponse response = taskService.createTask(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID", description = "Retrieves a task by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskDto.TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskDto.TaskResponse> getTask(
            @Parameter(description = "Task ID", required = true)
            @PathVariable Long id) {
        TaskDto.TaskResponse response = taskService.getTaskById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all tasks with pagination", description = "Retrieves all tasks with pagination and sorting options")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
            content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<TaskDto.TaskResponse>> getAllTasks(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "desc")
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TaskDto.TaskResponse> responses = taskService.getAllTasks(pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated tasks", description = "Retrieves tasks with pagination (alternative endpoint)")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
            content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<TaskDto.TaskResponse>> getAllTasksPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "desc")
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TaskDto.TaskResponse> responses = taskService.getAllTasks(pageable);

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Updates an existing task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskDto.TaskResponse> updateTask(
            @Parameter(description = "Task ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TaskDto.UpdateTaskRequest request) {

        TaskDto.TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a task", description = "Partially updates a task (PATCH)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task patched successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskDto.TaskResponse> patchTask(
            @Parameter(description = "Task ID", required = true)
            @PathVariable Long id,
            @RequestBody TaskDto.PatchTaskRequest request) {

        TaskDto.TaskResponse response = taskService.patchTask(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task", description = "Deletes a task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Task ID", required = true)
            @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Check if task exists", description = "Checks whether a task exists by its ID")
    @ApiResponse(responseCode = "200", description = "Task existence status retrieved",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    public ResponseEntity<Boolean> taskExists(
            @Parameter(description = "Task ID", required = true)
            @PathVariable Long id) {
        boolean exists = taskService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/limits")
    @Operation(summary = "Get task limits", description = "Retrieves current task limits and usage statistics")
    @ApiResponse(responseCode = "200", description = "Task limits retrieved successfully")
    public ResponseEntity<Map<String, Object>> getTaskLimits() {
        int currentCount = taskService.getCurrentDailyTaskCount();
        int remaining = taskService.getRemainingTasksForToday();
        boolean canCreate = taskService.canCreateTask();

        Map<String, Object> limits = Map.of(
                "currentDailyTaskCount", currentCount,
                "remainingTasksToday", remaining,
                "canCreateTask", canCreate,
                "maxTasksPerDay", 100
        );

        return ResponseEntity.ok(limits);
    }

    @PostMapping("/cleanup/manual")
    @Operation(summary = "Perform manual cleanup", description = "Triggers manual cleanup of expired task records")
    @ApiResponse(responseCode = "200", description = "Manual cleanup completed successfully")
    public ResponseEntity<Map<String, String>> performManualCleanup() {
        cleanupService.performManualCleanup();
        return ResponseEntity.ok(Map.of("message", "Manual cleanup completed successfully"));
    }

    @GetMapping("/cleanup/status")
    @Operation(summary = "Get cleanup status", description = "Retrieves the current cleanup status and statistics")
    @ApiResponse(responseCode = "200", description = "Cleanup status retrieved successfully")
    public ResponseEntity<CleanupService.CleanupStatus> getCleanupStatus() {
        CleanupService.CleanupStatus status = cleanupService.getCleanupStatus();
        return ResponseEntity.ok(status);
    }
}