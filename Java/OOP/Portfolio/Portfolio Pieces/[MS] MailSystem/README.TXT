Project: Mail System
Author: Charlotte Pierce

This project is part of the material for the Object-Oriented Programming Portfolio
of Charlotte Pierce

This project simulates a simple email system. Mail clients simulate email programs
of different users. If you create two or more email clients, they can send messages
to each other.
This project has been extended to include an Internet class. This allows multiple
MailServer objects to exist within the application. Multiple clients can also
exist, each with one of any MailServers. If a client attempts to send a message
to another client who is on a different server to them, the first client's server
forwards the message to the Internet object, who then filters out the intended 
recipient server and again forwards the message, to the appropriate server.
This simulates the actual function of an e-mail system.

To use this project:
- create an instance of the Internet
- create as many MailServer objects as desired
- create as many MailClient objects as desired, and assign each a MailServer
	- note: the MailClient's e-mail address will be "<name of mailclient>@<name of server>"
- use the 'sendMessage' method of a MailClient to send a message to one of the other MailClients
- use the 'printNextMessage' method of the second MailClient to receive the message.

This project demonstrates the use of object interaction, where objects (MailItem) are passed
between other objects (Internet and MailServer). It also demonstrates the use of good program
structure, where each class is responsible for a specific function, and each method within
each class responsible for one specific task. The project is fully commented using javadoc,
in order to facilitate further extensions.

In order to fulfill the requirements defined for the project, it has been extended to
include e-mail addresses for each client, including the client's name and their MailServer.
It has also been extended to use this e-mail address to filter MailItem's through an Internet
object, so that clients can send messages to other clients that are not using thier server.
The project folder includes a sequence diagram to demonstrate the interaction between objects
in order to achieve this functionality.

The project uses exception handling to output errors in cases such as a MailServer or 
MailClient not existing.

The project also includes testing, using JUnit, for this extended version of the MailSystem.
In addition to testing, the project also includes a file, '[MS]-2 InheritanceExplanation',
which reflects on the ability to refactor the code in the project to use the concept of
inheritance. 