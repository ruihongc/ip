# Mochi UI Test Plan

This plan records the test cases used to verify Mochi's text output.
Run them with the `test-ui` skill (`.codex/skills/test-ui/test-ui.ps1`).
Machine-readable input/expected file pairs for each case are in `test/cases/`.

## Test 1: Greeting and exit

- **Aim**: Verify Mochi greets the user and says goodbye on `bye`.
- **Inputs**:
  ```
  bye
  ```
- **Expected output**:
  ```
  ____________________________________________________________
      __  ___           __    _
     /  |/  /___  _____/ /_  (_)
    / /|_/ / __ \/ ___/ __ \/ /
   / /  / / /_/ / /__/ / / / /
  /_/  /_/\____/\___/_/ /_/_/
  Hello! I'm Mochi.
  What can I do for you?
  ____________________________________________________________
  Bye. Hope to see you again soon!
  ____________________________________________________________
  ```

## Test 2: Add all task types and list

- **Aim**: Verify `todo`, `deadline`, and `event` tasks are added and shown correctly in `list`.
- **Inputs**:
  ```
  todo borrow book
  deadline return book /by Sunday
  event project meeting /from Mon 2pm /to 4pm
  list
  bye
  ```
- **Expected output**:
  ```
  ____________________________________________________________
      __  ___           __    _
     /  |/  /___  _____/ /_  (_)
    / /|_/ / __ \/ ___/ __ \/ /
   / /  / / /_/ / /__/ / / / /
  /_/  /_/\____/\___/_/ /_/_/
  Hello! I'm Mochi.
  What can I do for you?
  ____________________________________________________________
  ____________________________________________________________
   Got it. I've added this task:
     [T][ ] borrow book
   Now you have 1 tasks in the list.
  ____________________________________________________________
  ____________________________________________________________
   Got it. I've added this task:
     [D][ ] return book (by: Sunday)
   Now you have 2 tasks in the list.
  ____________________________________________________________
  ____________________________________________________________
   Got it. I've added this task:
     [E][ ] project meeting (from: Mon 2pm to: 4pm)
   Now you have 3 tasks in the list.
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list:
   1.[T][ ] borrow book
   2.[D][ ] return book (by: Sunday)
   3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
  ____________________________________________________________
  ____________________________________________________________
  Bye. Hope to see you again soon!
  ____________________________________________________________
  ```

## Test 3: Mark and unmark

- **Aim**: Verify `mark` and `unmark` update a task's done status.
- **Inputs**:
  ```
  todo read book
  todo return book
  mark 2
  list
  unmark 2
  list
  bye
  ```
- **Expected output**:
  ```
  ____________________________________________________________
      __  ___           __    _
     /  |/  /___  _____/ /_  (_)
    / /|_/ / __ \/ ___/ __ \/ /
   / /  / / /_/ / /__/ / / / /
  /_/  /_/\____/\___/_/ /_/_/
  Hello! I'm Mochi.
  What can I do for you?
  ____________________________________________________________
  ____________________________________________________________
   Got it. I've added this task:
     [T][ ] read book
   Now you have 1 tasks in the list.
  ____________________________________________________________
  ____________________________________________________________
   Got it. I've added this task:
     [T][ ] return book
   Now you have 2 tasks in the list.
  ____________________________________________________________
  ____________________________________________________________
   Nice! I've marked this task as done:
     [T][X] return book
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list:
   1.[T][ ] read book
   2.[T][X] return book
  ____________________________________________________________
  ____________________________________________________________
   OK, I've marked this task as not done yet:
     [T][ ] return book
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list:
   1.[T][ ] read book
   2.[T][ ] return book
  ____________________________________________________________
  ____________________________________________________________
  Bye. Hope to see you again soon!
  ____________________________________________________________
  ```

## Test 4: Deadline with arbitrary time text

- **Aim**: Verify deadline times are treated as plain strings (no date parsing).
- **Inputs**:
  ```
  deadline do homework /by no idea :-p
  list
  bye
  ```
- **Expected output**:
  ```
  ____________________________________________________________
      __  ___           __    _
     /  |/  /___  _____/ /_  (_)
    / /|_/ / __ \/ ___/ __ \/ /
   / /  / / /_/ / /__/ / / / /
  /_/  /_/\____/\___/_/ /_/_/
  Hello! I'm Mochi.
  What can I do for you?
  ____________________________________________________________
  ____________________________________________________________
   Got it. I've added this task:
     [D][ ] do homework (by: no idea :-p)
   Now you have 1 tasks in the list.
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list:
   1.[D][ ] do homework (by: no idea :-p)
  ____________________________________________________________
  ____________________________________________________________
  Bye. Hope to see you again soon!
  ____________________________________________________________
  ```

## Test 5: Empty list

- **Aim**: Verify `list` on an empty task list produces no task lines.
- **Inputs**:
  ```
  list
  bye
  ```
- **Expected output**:
  ```
  ____________________________________________________________
      __  ___           __    _
     /  |/  /___  _____/ /_  (_)
    / /|_/ / __ \/ ___/ __ \/ /
   / /  / / /_/ / /__/ / / / /
  /_/  /_/\____/\___/_/ /_/_/
  Hello! I'm Mochi.
  What can I do for you?
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list:
  ____________________________________________________________
  ____________________________________________________________
  Bye. Hope to see you again soon!
  ____________________________________________________________
  ```

## Test 6: Invalid commands (interleaved with valid ones)

- **Aim**: Verify Mochi rejects unknown commands and malformed mark/deadline/event
  commands with specific error messages, and that invalid commands do not corrupt
  the stored tasks. Positive and negative commands are interleaved.
- **Inputs**:
  ```
  todo read book
  blah
  todo
  mark abc
  mark 5
  deadline return book
  event project meeting
  event project meeting /from Mon 2pm
  list
  bye
  ```
- **Expected output**:
  ```
  ____________________________________________________________
      __  ___           __    _
     /  |/  /___  _____/ /_  (_)
    / /|_/ / __ \/ ___/ __ \/ /
   / /  / / /_/ / /__/ / / / /
  /_/  /_/\____/\___/_/ /_/_/
  Hello! I'm Mochi.
  What can I do for you?
  ____________________________________________________________
  ____________________________________________________________
   Got it. I've added this task:
     [T][ ] read book
   Now you have 1 tasks in the list.
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! I'm sorry, but I don't know what that means :-(
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! The description of a todo cannot be empty.
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! Please give a task number, e.g., mark 2
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! There is no task number 5 in the list.
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! Please add the deadline with /by, e.g., deadline return book /by Sunday
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! Please add the start time with /from, e.g., event project meeting /from Mon 2pm /to 4pm
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! Please add the end time with /to, e.g., event project meeting /from Mon 2pm /to 4pm
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list:
   1.[T][ ] read book
  ____________________________________________________________
  ____________________________________________________________
  Bye. Hope to see you again soon!
  ____________________________________________________________
  ```

## Test 7: Empty task descriptions

- **Aim**: Verify each task type rejects a missing description.
- **Inputs**:
  ```
  todo
  deadline
  event
  list
  bye
  ```
- **Expected output**:
  ```
  ____________________________________________________________
      __  ___           __    _
     /  |/  /___  _____/ /_  (_)
    / /|_/ / __ \/ ___/ __ \/ /
   / /  / / /_/ / /__/ / / / /
  /_/  /_/\____/\___/_/ /_/_/
  Hello! I'm Mochi.
  What can I do for you?
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! The description of a todo cannot be empty.
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! The description of a deadline cannot be empty.
  ____________________________________________________________
  ____________________________________________________________
   OOPS!!! The description of an event cannot be empty.
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list:
  ____________________________________________________________
  ____________________________________________________________
  Bye. Hope to see you again soon!
  ____________________________________________________________
  ```

