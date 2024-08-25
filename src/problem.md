Problem Statement
Develop a TODO task tracking application which allows its users to maintain TODO lists. TODO tasks in the list can have a deadline and tags associated with them for easier grouping and management. You can demonstrate the working of the application using a driver program. There is no requirement to use a database for persistence (instead, use memory to store data) or write a web service.
Features
Users should be able to update the TODO list at any point in time - add, modify and remove tasks
A task can be marked as completed and once it is completed, it is automatically removed from the TODO list. Tasks can also be added to appear in the TODO list at a future date.
Users should be able to see an activity log which describes additions, modifications and removals of tasks from the TODO list during a particular time period.
Users should also be able to see statistics around how many tasks were added, completed and spilled over the deadline during a particular period of time.
Implementation requirements
Your solution should implement the following functions. Feel free to use the representation for objects you deem fit for the problem and the provided use cases. The functions are ordered in the decreasing order of importance (highest to lowest). We understand that you may not be able to complete the implementation for all the functions listed here. So try to implement them in the order in which they are declared down below.
addTask(task)
getTask(taskId) -> a task
modifyTask(task)
removeTask(taskId)
listTasks(...) -> a list of tasks which match the given filter ordered based on a defined sort criteria
getStatistics(optional[timePeriod]) -> statistics for the given time period (if supplied)
getActivityLog(optional[timePeriod]) -> activity log for the given time period (if supplied)

Things to keep in mind
You are only allowed to use in-memory data structures.
You are NOT allowed to use any databases.
You are NOT required to have a full-fledged web service or APIs exposed.
You are NOT allowed to use any other third-party libraries apart from the standard ones that are part of the language runtime.
Please ensure you submit your solution on time even if it is not complete. A partial solution submitted on time is better than a complete solution submitted after time.
Spend time on implementing the core features first and then proceed to implement the optional features.
Writing test cases to prove the correctness of your code will be appreciated.
You are required to demonstrate the working of your solution during code review.
A driver program with a main class that simulates the above operations is enough. No need to create any GUI or CLI to make the application interactive.
Evaluation Criteria
Separation of concerns
Abstractions
Application of OO design principles
Testability
Extensibility
Code readability
Language proficiency
[execution time limit] 10 seconds
[memory limit] 1 GB
