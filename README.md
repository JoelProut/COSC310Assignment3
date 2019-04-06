# COSC310Assignment3

# Summary
Thebo is a therapy and companion chat bot that takes the users input and uses stanfords CoreNLP library to analysie the parts of speach, named entities, and sentiments to respond in an intellegent way. To run Thebo simple run the chatBox class located in the gui package

# Class Breakdown

# ConvoBot Classes
1) Patient - The patient object holds the users personal information.

2) Thebo - Chat logic, inputs are passed to Thebo from the chatBox and Thebo returns an appropriate response. Thebo uses topics from the topic package.

# gui
The gui package contains the code for the graphical user interface of Thebo as well as event listeners to pass inputs to the main chat logic classes.

# Language Processing
The languageProcessing package contains the code for implementing stanfords coreNLP library. It takes in a string and finds the Parts of Speach, Named Entitys, Lemmas, and the sentiment of the input.

# Class Structure

![UML diagram](https://github.com/hmehain/COSC-310-Group-6/blob/master/chatbot%20UML%202.jpg)

[WBS](https://docs.google.com/spreadsheets/d/1r-X-w9h50yBHoTPS1j0cAH8LC9jF8CfqRzDWSvb3xO8/edit?usp=sharing)
