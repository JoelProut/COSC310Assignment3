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

# Additions:
1) Added a GUI to Thebo. the GUI improves the look of the application.

![image](https://user-images.githubusercontent.com/43254182/55665565-f5dd4080-57f6-11e9-9527-c970cc6870d7.png)

2) Added an extra topic. Thebo can also act as a friend as well as a therapist if you just need someone to talk to. The user can switch back and forth between topics mid conversation.

![image](https://user-images.githubusercontent.com/43254182/55665619-d0046b80-57f7-11e9-9da8-9f53e03cedf6.png)


3) Unexpected Response - If thebo gets a response it wasnt expecting it checks the sentiment and parrots back part of your sentence expressing either sympathy, excitement or curiosity.

4) POS tagging - Implemented POS tagging and recognition from stanfords CoreNLP library. Its used to extract specific parts of the sentence such as the nouns depending on what the user said.

5) Named entity recognition - Implemented NER from stanfrords CoreNLP library. Used to get the users name, and in combination with sentiment analysis if the user expresses liking or disliking locations or people.

The following image demenstrates and the output of an unexpected response through the use of POS tagging. The unexpectedResponse method finds verbs, adjectives, nouns, people and places to determine how to respond. Recognizing the users name is also done through Named entity recognition, which can also recognize location names and is used throughout the core response logic.

![image](https://user-images.githubusercontent.com/43254182/55666096-45733a80-57fe-11e9-8f2b-75e1b714158b.png)

6) Sentiment analysis - Changes the response depending on the sentiment expresses. For example if the user is talking about school and the input returns a positive sentiment Thebo will ask something along the lines of "What do you like about school?" while if the sentiment was negative it would express sympathy.

![image](https://user-images.githubusercontent.com/43254182/55666248-b4519300-5800-11e9-98a6-a69ffa618604.png)
