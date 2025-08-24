Week 4 Project — Exercise Tracker

Context

I lost a bet with a friend and he challenged me that for the next 52 weeks, during weekends I need to create from scratch a new project.

The rules are the following:
- Each Friday night, me and him will talk about what project I need to do.
- Mostly he will decide for me, but I'm allowed to suggest and work on my own ideas if he agrees on.
- Once the project is decided, he will tell me if I'm allowed to work Saturday and Sunday, or only Sunday.
(Surely this won't backfire at some point by underestimating a project, right? 😅)

For this week we decided on an Exercise Tracker to help me follow my routine easier, being allowed to work on it only Sunday.

About the project

The main framework used is Swing to create the UI and its components.

The application represents each day as a JList component. Tasks are stored as individual Item objects within these lists. The application saves data in JSON format in the /saves directory.

Features

- Create Sheets on which you can add as many days as you want. For each day can add as many exercises as you want. For each Sheet the structure looks like: Sheet<List<Day<List<Item>>>>
- Every Item object has 3 editable fields: name, link and description, each one can be left empty.
- Every field including the Sheet/Day name can be renamed

Note: While designed as an exercise tracker, this application can be used for other purposes beyond that.

I will post two examples with this project:
- One being my personal home exercise routine.
- One an empty example.
