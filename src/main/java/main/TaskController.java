package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository repository;

    @RequestMapping("/")
    public String currentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "yyyy-MM-dd hh:ss:ms" );
        return LocalDateTime.now ( ).format ( formatter );
    }

    @PostMapping(
            value = "/tasks",
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<String> addTask (@RequestBody Task task) {

        repository.save ( task );
        return ResponseEntity.status ( HttpStatus.CREATED ).
                body ("Task " + task.getTittle ( ) + " save");
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<String> getTask (@PathVariable Integer id) {

        Optional <Task> optionalTask = repository.findById ( id );
        if(optionalTask.isEmpty ( )) {
            return ResponseEntity.status ( HttpStatus.NOT_FOUND ).
                    body ( "Task with in id " + id + " not found." );
        }
        return new ResponseEntity(optionalTask.get (), HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<String> getAllTasks () {
        Iterable<Task> taskIterable = repository.findAll ();
        List<Task> taskList =
                StreamSupport.stream ( taskIterable.spliterator (), false ).
                        collect( Collectors.toList());

        return new ResponseEntity(taskList, HttpStatus.OK);
    }

    @PatchMapping(
            value = "/tasks/{id}",
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<String> updateTask(@PathVariable int id, @RequestBody Task task) {

        Optional<Task> optionalTask =  repository.findById ( id );
        if(optionalTask.isEmpty ( )){
            return ResponseEntity.status ( HttpStatus.NOT_FOUND ).
                    body ( "Task with in id " + id + " not found." );
        }
        repository.delete ( optionalTask.get ( ) );
        repository.save ( task );
        return ResponseEntity.status ( HttpStatus.OK ).body ( "Task with id " + id + " is update" );

    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable  Integer id) {

        Optional <Task> optionalTask = repository.findById ( id );
        if(optionalTask.isEmpty ( )) {
            return ResponseEntity.status ( HttpStatus.NOT_FOUND ).
                    body ( "Task with in id " + id + " not found." );
        }
        repository.delete ( optionalTask.get ( ) );
        return ResponseEntity.status ( HttpStatus.OK ).body ( "Task with id " + id + " is deleted" );
    }

}
