# PDA Calculator
![PDA Calculator](/../screenshoot/screenshoots/usage1.jpg?raw=true)

Pushdown Automaton (PDA) implementation for validating calculator syntax in Java.

## Intro
### What is PDA?
Pushdown Automata (PDA) is one type of automata that involves stack. PDA is more powerful than _state machines_.

### What is this project about?
In this project, we made a PDA Calculator that can validate and calculate inputs from users. Our PDA Calculator can open an external PDA files and use them. 

### PDA in this project
![PDA](/../screenshoot/screenshoots/pda.jpg?raw=true)

**Description:**

Q0 : Handles "(" or "numbers"
Q1 : Handles mathematics expression with "(" inside the expression (not in beginning).
Q2 : Handles mathematics operators (eg + / * -)
Q3 : Final state (empty stack)

**PDA Notations:**

![PDA](/../screenshoot/screenshoots/function.jpg?raw=true)

Converted PDA into text file (used as program input):
```
^(Q0,(,$)={(Q0,X$)}
^(Q0,(,X)={(Q0,XX)}
^(Q0,-,X)={(Q1,YX)}
^(Q0,+,X)={(Q1,ZX)}
^(Q1,N,Y)={(Q1,~)}
^(Q1,N,Z)={(Q1,~)}
^(Q0,N,X)={(Q2,X)}
^(Q0,N,$)={(Q2,$)}
^(Q2,),X)={(Q2,~)}
^(Q2,+,$)={(Q0,$)}
^(Q2,-,$)={(Q0,$)}
^(Q2,*,$)={(Q0,$)}
^(Q2,/,$)={(Q0,$)}
^(Q2,+,X)={(Q0,X)}
^(Q2,-,X)={(Q0,X)}
^(Q2,*,X)={(Q0,X)}
^(Q2,/,X)={(Q0,X)}
^(Q1,),X)={(Q2,~)}
^(Q2,~,$)={(Q3,$)}

```

This input PDA can be found in this project as `PDA INPUT.txt` 

## Build Information
This project is made by using Netbeans IDE. You may need to use netbeans to open/build this project properly.

## Screen Shoots
User gave correct input:

![Correct Input](/../screenshoot/screenshoots/usage1.jpg?raw=true)

User gave bad input:

![Bad Input](/../screenshoot/screenshoots/usage2.JPG?raw=true)

## License
MIT License
