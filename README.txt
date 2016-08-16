=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Instructions  :=
===================

1. Use the up down arrows to move the spaceship. Avoid the orange and red aliens
   and collect the hearts for health. Click and drag the spaceship to shoot little
   gold stars at the aliens. Avoid shooting stars at the heart.

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. You may copy and paste from your proposal
  document if you did not change the features you are implementing.

  1. 2D array
     I'm using a 2D array to store the aliens. 2D array is a good idea because 
     it's easy to access each alien. Also, the aliens come in waves, so using a 2D
     array, I can keep track of when an alien hits the end of the screen, and replace
     it with a new row of aliens
  2. Collision Detectors
     I'm using the collision detector to check if my spaceship/boulder collides with the
     Aliens. Firstly, I used the bounding box method to check if the two objects are within
     range of each other. Then, I check if there's a collision based on the alpha value of 
     the images. This is necessary because the aliens are not regularly shaped objects, 
     so I can't just use a typical bounding box method etc to measure collisions.
  3. Physics
     I use Physics to approximate the movement of the boulder stars. How far back I drag
     the mouse determines the force required to shoot the star forward. The angle of the
     star projectory depends on angle the I drag the mouse back by. The path of the boulder
     star after being released is modelled by the Physics trajactory equation, where the x 
     velocity does not change, but y velocity decreases due to the downward gravity pull.
  4. Inheritance
     Inheritance will be used to represent the 3 different types of aliens, that inherit from 
     the same interface. Since they’re all aliens, they should share common characteristics 
     (e.g. they have a certain level of health), when hit by a star they die (or lose health, 
     depending on their initial health level). 

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Spaceship.java
  That's the class for the spaceship. It includes total health, position and 
  velocity of the spaceship.

  Aliens.java
  This is an interface for all the different aliens, there are 3 different kinds
  of Aliens (the Red Aliens, the Orange Aliens, health)

  Red.java
  The class that represents the meaniest alien. They have 3 health and causes
  10 damage when collides with spaceship

  Orange.java
  The class that represents the wannabe mean alien. They have 2 health and causes
  3 damage when collides with the spaceship.

  Health.java
  They occur rarely, and they have 1 health, and when collected by the ship, gives
  it 3 health.

  Boulder.java
  Creates the boulder object that the spaceship shoots. Contains the position,
  velocity, and acceleration of the boulder.

  GameObj.java
  The super class of all the objects in the game. It contains functions that most
  objects have (e.g. clip() that ensures that the object is within the screen, move()
  that changes the position of the object etc). This class is needed because most objects
  share similar characteristics (e.g. have a position and movement)

  GameCourt.java
  Does most of the logic in the game. E.g. checks if an object intersects with another,
  creates boulders, move the aliens, create aliens in random positions etc

  Game.java
  Creates the overall layout of the game etc and has the main function to run it

- Revisit your proposal document. What components of your plan did you end up
  keeping? What did you have to change? Why?

  I ended up keeping most components of my plan, with the health and the different
  aliens etc

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  I had a lot of problem with the collision detector. It seems that some pictures
  I used had weird alpha values and the collision detector wouldn't be detected.
  I ended up having to manipulate the image to ensure that the background is entirely
  transpoarent.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I felt that most functionality is separated pretty well. Each file had its own 
  function. I tried to make the private state encapulated well. E.g. health of the 
  aliens are private variables and can only be changed using the function hit() that 
  decrements the health and a boolean that checks if the health is zero to see if the 
  alien is dead. I'll probably not refactor

