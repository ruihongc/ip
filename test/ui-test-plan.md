# Mochi UI Test Plan

This plan records the test cases used to verify Mochi's text output.
Run them with the `test-ui` skill (`.codex/skills/test-ui/test-ui.ps1`).
Machine-readable input/expected file pairs for each case are in `test/cases/`.

> **Personality (A-Personality):** Mochi is a friendly cat assistant. All user
> messages use a playful cat voice: greetings are new with `nya~` and `Purr~`,
> errors are prefixed `Nya-no!!!` instead of `OOPS!!!`, and count lines read
> `You now have N tasks~` / `You have N tasks left nya~` with correct
> singular/plural wording.

> **GUI (Level-10):** The application now launches a JavaFX GUI via the
> `mochi.gui.Launcher` entry point. The text UI below is preserved and still
> exercised by these test cases. The GUI reuses the same responses through
> `Mochi.getResponse`, covered by `MochiTest`.
>
> **GUI styling (A-BetterGui):** The JavaFX window is resizable (with a small
> minimum size) and styled by `view/main.css`. User and Mochi bubbles differ in
> colour and font size (13px vs 15px) and their profile pictures differ in size,
> so the two speakers have visually distinct bubbles. Error replies that start
> with `Nya-no!!!` are highlighted with a red bubble. The GUI is not exercised
> by the text-UI cases; it is verified by a manual smoke test on launch.

## Test 1: Greeting and exit

- **Aim**: Verify Mochi greets the user in its cat voice and says goodbye on `bye`.
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 2: Add all task types and list

- **Aim**: Verify `todo`, `deadline`, and `event` tasks are added and shown correctly in `list`.
  Dates are stored as `LocalDate` and displayed in `MMM d yyyy` format.
- **Inputs**:
  ```
  todo borrow book
  deadline return book /by 2019-12-02
  event project meeting /from 2019-10-15 /to 2019-10-16
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] borrow book
   You now have 1 task in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [D][ ] return book (by: Dec 2 2019)
   You now have 2 tasks in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)
   You now have 3 tasks in the list~
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
   1.[T][ ] borrow book
   2.[D][ ] return book (by: Dec 2 2019)
   3.[E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] read book
   You now have 1 task in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] return book
   You now have 2 tasks in the list~
  ____________________________________________________________
  ____________________________________________________________
   Purr~ I've marked this task as done:
     [T][X] return book
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
   1.[T][ ] read book
   2.[T][X] return book
  ____________________________________________________________
  ____________________________________________________________
   OK~ I've unmarked this task:
     [T][ ] return book
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
   1.[T][ ] read book
   2.[T][ ] return book
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 4: Deadline with ISO date

- **Aim**: Verify deadline dates are parsed from `yyyy-mm-dd` and displayed as `MMM d yyyy`.
- **Inputs**:
  ```
  deadline do homework /by 2019-10-15
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [D][ ] do homework (by: Oct 15 2019)
   You now have 1 task in the list~
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
   1.[D][ ] do homework (by: Oct 15 2019)
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] read book
   You now have 1 task in the list~
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! I'm sorry, but I don't know what that means :-(
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! The description of a todo cannot be empty.
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! Please give a task number, e.g., mark 2
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! There is no task number 5 in the list.
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! Please add the deadline with /by, e.g., deadline return book /by Sunday
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! Please add the start time with /from, e.g., event project meeting /from Mon 2pm /to 4pm
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! Please add the end time with /to, e.g., event project meeting /from Mon 2pm /to 4pm
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
   1.[T][ ] read book
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! The description of a todo cannot be empty.
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! The description of a deadline cannot be empty.
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! The description of an event cannot be empty.
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 8: Delete a task

- **Aim**: Verify `delete N` removes the task, renumbers the rest, and reports the new count.
  Includes deadlines and events with ISO dates.
- **Inputs**:
  ```
  todo read book
  deadline return book /by 2019-06-06
  event project meeting /from 2019-08-06 /to 2019-08-07
  todo join sports club
  todo borrow book
  list
  delete 3
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] read book
   You now have 1 task in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [D][ ] return book (by: Jun 6 2019)
   You now have 2 tasks in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [E][ ] project meeting (from: Aug 6 2019 to: Aug 7 2019)
   You now have 3 tasks in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] join sports club
   You now have 4 tasks in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] borrow book
   You now have 5 tasks in the list~
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
   1.[T][ ] read book
   2.[D][ ] return book (by: Jun 6 2019)
   3.[E][ ] project meeting (from: Aug 6 2019 to: Aug 7 2019)
   4.[T][ ] join sports club
   5.[T][ ] borrow book
  ____________________________________________________________
  ____________________________________________________________
   Nya~ I've removed this task:
     [E][ ] project meeting (from: Aug 6 2019 to: Aug 7 2019)
   You have 4 tasks left nya~
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
   1.[T][ ] read book
   2.[D][ ] return book (by: Jun 6 2019)
   3.[T][ ] join sports club
   4.[T][ ] borrow book
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 9: Invalid delete commands

- **Aim**: Verify `delete` rejects an out-of-range, zero, non-numeric, or missing
  task number, and that the task list is left unchanged.
- **Inputs**:
  ```
  todo read book
  delete 2
  delete 0
  delete abc
  delete
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] read book
   You now have 1 task in the list~
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! There is no task number 2 in the list.
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! There is no task number 0 in the list.
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! Please give a task number, e.g., delete 3
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! Please give a task number, e.g., delete 3
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
   1.[T][ ] read book
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 10: Deadline date formatting

- **Aim**: Verify a deadline date given as `yyyy-mm-dd` is displayed as `MMM d yyyy`.
- **Inputs**:
  ```
  deadline return book /by 2019-12-02
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [D][ ] return book (by: Dec 2 2019)
   You now have 1 task in the list~
  ____________________________________________________________
  ____________________________________________________________
   Here are the tasks in your list nya~
   1.[D][ ] return book (by: Dec 2 2019)
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 11: Invalid date format

- **Aim**: Verify that non-ISO dates (e.g., `Sunday`, `Mon`) are rejected with a
  helpful error message.
- **Inputs**:
  ```
  deadline return book /by Sunday
  event meeting /from Mon /to Tue
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! The date must be in yyyy-mm-dd format, e.g., 2019-10-15
  ____________________________________________________________
  ____________________________________________________________
   Nya-no!!! The dates must be in yyyy-mm-dd format, e.g., 2019-10-15
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 12: Find tasks by keyword

- **Aim**: Verify `find` returns tasks whose description contains the keyword.
- **Inputs**:
  ```
  todo read book
  deadline return book /by 2019-12-02
  event project meeting /from 2019-10-15 /to 2019-10-16
  find book
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] read book
   You now have 1 task in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [D][ ] return book (by: Dec 2 2019)
   You now have 2 tasks in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)
   You now have 3 tasks in the list~
  ____________________________________________________________
  ____________________________________________________________
   Here are the matching tasks nya~
   1.[T][ ] read book
   2.[D][ ] return book (by: Dec 2 2019)
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 13: Find with no results

- **Aim**: Verify `find` with a keyword that matches no tasks shows an empty list.
- **Inputs**:
  ```
  todo read book
  find swim
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this task nya~
     [T][ ] read book
   You now have 1 task in the list~
  ____________________________________________________________
  ____________________________________________________________
   Here are the matching tasks nya~
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 14: Notes (D-Notes extension)

- **Aim**: Verify `note`, `notes`, `delete-note`, and `find-note` manage the
  note list and are persisted independently of the task list.
- **Inputs**:
  ```
  note waist size is 32
  note movie to watch: interstellar
  note buy milk
  notes
  delete-note 2
  notes
  find-note milk
  find-note pizza
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this note nya~
     waist size is 32
   You now have 1 note in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this note nya~
     movie to watch: interstellar
   You now have 2 notes in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this note nya~
     buy milk
   You now have 3 notes in the list~
  ____________________________________________________________
  ____________________________________________________________
   Here are your notes nya~
   1.waist size is 32
   2.movie to watch: interstellar
   3.buy milk
  ____________________________________________________________
  ____________________________________________________________
   Nya~ I've removed this note:
     movie to watch: interstellar
   You have 2 notes left nya~
  ____________________________________________________________
  ____________________________________________________________
   Here are your notes nya~
   1.waist size is 32
   2.buy milk
  ____________________________________________________________
  ____________________________________________________________
   Here are the matching notes nya~
   1.buy milk
  ____________________________________________________________
  ____________________________________________________________
   Here are the matching notes nya~
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```

## Test 15: Places (D-Places extension)

- **Aim**: Verify `place`, `places`, `view-place`, `delete-place`, and
  `find-place` manage the place list and are persisted independently of the
  task and note lists.
- **Inputs**:
  ```
  place hawkerlicious /d best hokkien mee
  place cafes /d good wifi for studying
  place gym max /d closes at 10pm
  places
  view-place 2
  delete-place 3
  places
  find-place wifi
  find-place zzz
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
  Nya~ Hello! I'm Mochi!
  I'll help you keep your life organised~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this place nya~
     hawkerlicious
   You now have 1 place in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this place nya~
     cafes
   You now have 2 places in the list~
  ____________________________________________________________
  ____________________________________________________________
   Got it! I've added this place nya~
     gym max
   You now have 3 places in the list~
  ____________________________________________________________
  ____________________________________________________________
   Here are the places in your list nya~
   1.hawkerlicious
   2.cafes
   3.gym max
  ____________________________________________________________
  ____________________________________________________________
   Here are the details of cafes nya~
   1.good wifi for studying
  ____________________________________________________________
  ____________________________________________________________
   Nya~ I've removed this place:
     gym max
   You have 2 places left nya~
  ____________________________________________________________
  ____________________________________________________________
   Here are the places in your list nya~
   1.hawkerlicious
   2.cafes
  ____________________________________________________________
  ____________________________________________________________
   Here are the matching places nya~
   1.cafes
  ____________________________________________________________
  ____________________________________________________________
   Here are the matching places nya~
  ____________________________________________________________
  ____________________________________________________________
  Purr~ See you next time!
  ____________________________________________________________
  ```