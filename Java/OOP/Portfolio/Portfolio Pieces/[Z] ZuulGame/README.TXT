Project: World of Zuul
Author: Charlotte Pierce

This project is part of the material for the Object-Oriented Programming portfolio
of Charlotte Pierce.

This application originated as a text-based adventure game, which has been extended
to include a graphical user interface. The game places players in the 'World of Zuul',
a game where Zuul, an evil scientist, is threatening to take over the world. It is up
to the player to infiltrate Zuul's laboratory facilities, find the key to his master
control room, and shut down his operations.

When playing the game, the player can move around the facility, pick up and drop items,
use potions to restore their health, and attack monsters (using items they have picked up).

To use this application, run the main method in the Game class. Then use the buttons on
the GUI to navigate through the game.

This application demonstrates many Object-Oriented concepts and principles. Demonstrating
encapsulation, each class hides it's implementation from all other classes. The application
also demonstrates cohesion, as each class is responsible for one function, and each method
within each class is responsible for one specific task. The structure of the project was
designed with the goal of making classes as loosely coupled as possible, so that the
project can be more easily extended or modified in the future. In another effort to assist
further extensions on the project, every class has been extensively documented using javadoc
style. 

The project demonstrates some more advanced Object-Oriented concepts, such as inheritance.
This is used by the IntelligentBeing and Room classes, each of which is extended
(IntelligentBeing into Monster and Player classes; Room into TransporterRoom). The 
IntelligentBeing class also demonstrates the use of an abstract class (IntelligentBeing),
which is used to stop direct instances of the class from being created, and only allow
its subclasses to be instantiated.

Polymorphism is also used in the project, where instances of the TransporterRoom class
are assigned to the static type of Room, in order for it to be used without the need
to design special methods which perform idential functions (i.e. when an exit points 
to a TransporterRoom, the TransporterRoom is treated as an instance of Room, rather
than specifically a TransporterRoom).

The application fulfills the criteria of the portfolio, first by implementing the
extensions of incorporating an inventory, and the ability to pick up and drop items
as was suggested. It also includes the extension of adding health and strength fields
to every monster and player (which was refactored to be included in IntelligentBeing),
which defines the damage that monster/player does in combat. As such, the application
was also extended to include combat, and potions which restore health.

The project was then extended to incorporate a GUI, which enables players to navigate
the game using buttons, rather than typing in textual commands. These buttons perform
functions with the use of anonymous inner classes being used as event listeners.

Finally, the project was included to allow the player to save and load their game.
The relevant details regarding the player's game (i.e. their inventory, the current
room, the player's health) are stored in a text file in whichever directory the 
game file is located. The player is invited to name this file. Should the player wish
to load a previous game, they just have to select 'Load' from the 'File' menu, and
type in the name of the file they saved to. The loading methods assume that the 
file being loaded is stored in the same directory as the game file, unless the user
specifically types in a concrete address. When loaded, the game changes the value of
the player's health and current moves to those stored, and reinstates the inventory
and current room that the player had/was in when they saved the game.

Exception handling is also included in this version of the project, primarily to avoid
the issue of a null pointer being returned when the player is asked to specify an item
to drop or pick up, or a monster to attack. Due to the exception handling, instead of
returning an error and terminating the execution of the application, the game instead
displays a message to the player indicating their lack of choice, and continues running
as normal.