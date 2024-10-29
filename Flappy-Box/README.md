# Homework 1 Discussion

## Part I: Object-Oriented Programming Concepts

1. **Class**: an object blueprint that can be used to instantiate objects. Pipe, FlappyBox, and Block are all examples of classes in this project. 


2. **Object**: an individual identifiable item, unit, or entity with a defined role (attributes or methods) in some problem domain. Objects are instances of a class, such as 'flappyBox' in 'FlappyBox' class or 'pipe' in 'Pipe' class.


3. **Encapsulation**: the bundling of related fields and methods together into a single unit, such as a class, restricting access to specific units in that object. In Block, the private fields 'topLeftX' and 'topLeftY' are encapsulated in the class and cannot be accessed from the outside.


4. **Abstraction**: the hiding of complex implementation from the user while showing just the essential or necessary features of an object. An example of this would be the method runGameLoop() as calling it runs the game without revealing the individual components needed to start or continue a game.


5. **Data type**: a specification of the kinds of data an object can hold, such as integers, characters, or objects. An example of a datatype would be an integer, which can be seen in the variable 'score'; 'score' can only hold integer values.


6. **Composite data type**: a data type that is composed of other data types, either primitive or reference types. It allows for the grouping of multiple elements into a single unit, such as in classes or arrays. For instance, 'List< Pipe >' is a composite data type in which 'pipes' is a list of objects in the 'Pipe' class.  


7. **Method**: a function defined in a class that has a specific purpose or action. An example of this would be 'jump()' in 'FlappyBox' class.


8. **Constructor**: a method that is called when an object is instantiated, initializing the specific object. An example would be "public FallingBox(double x, double y, Color color)" the constructor for the 'FallingBox' class, initializing the color and position of some object of the FallingBox class.


9. **Instance variable**: a variable defined in a class that each instantiated object of that class has its own separate copy of the variable. An example would be the private integer 'x' in FlappyBox. 'x' is an instance variable representing the x-coordinate of the object.


10. **Local variable**: a variable defined in a block or unit of code, such as a method, that is only accessible in that block. The boolean 'xoverlap' and 'yoverlap' are local variables in the method 'intersects()' in the 'Box' class.


11. **Parameter**: a variable in the method declaration that the method accepts as input. For example, in the method declaration the FlappyBox constructor takes in the parameters 'x' and 'y'.


12. **Return type**: the data type of the value the method returns. For example, the method 'handleCollisions()' has a return type of boolean as it either returns 'true' or 'false' depending on whether the flappy box collides with a pipe.


13. **Inheritance**: a mechanism that allows one class to inherit properties and methods from another class. The class 'Box' inherits the fields and methods associated to its base class, 'Sprite'.


14. **Type Hierarchy**: the arrangement of classes and associated subclasses in such a way that a subclass inherits from its base class. The type hierarchy for this project is: Block > Sprite > Box > FallingBox > FlappyBox, while Pipe also inherits from Box.


15. **Apparent type**: the type specified in a variable's declaration. For example, the declaration 'private FlappyBox flappyBox;' means that flappyBox has the apparent type FlappyBox.


16. **Actual type**: the type that the object actually references during runtime. An example of this would be the instantiation 'flappyBox = new FlappyBox(200, GameConstant.CANVAS_HEIGHT - 50);' which denotes 'flappyBox' as having the actual type 'FlappyBox'.


17. **Is-a relationship**: a relationship where the subclass is a baseclass, a model for inheritance. For example, 'FallingBox' extends 'Box', thus 'FallingBox' is a 'Box'.


18. **Has-a relationship**: a relationship where a class contains an instance of another class. For example, the 'Game' class contains an instance of the 'FlappyBox' class. Thus, 'Game' has a 'FlappyBox'.


19. **Method overloading**: when two or more methods in a class have the same name, but different parameters. An example of this is 'setCanvasSize()' and 'setCanvasSize(int canvasWidth, int canvasHeight)' in the class 'StdDraw'. Both methods have the same name, but accept different parameters and would be executed based off of which parameter specification is called.


20. **Method overriding**: when a subclass provides a different implementation of a method that is in the base class, overriding the implementation of this specific method in the base class. An example of this would be the 'move()' method in the 'FallingBox' class overrides the 'move()' method in the 'Sprite' class. 


21. **Static polymorphism**: polymorphism demonstrated during compiler-time. The Java compiler will decide what method should be executed or what type overloaded. An example of this is in line 29 of the 'Game' class, 'StdDraw.setCanvasSize(GameConstant.CANVAS_WIDTH, GameConstant.CANVAS_HEIGHT);'. The 'StdDraw' class has multiple methods named 'setCanvasSize', but during compile-time it is determined which method would be called based on the number and type of arguments on this line.


22. **Dynamic polymorphism**: polymorphism demonstrated during run-time when the method that will be executed is determined based on the actual object type. This allows subclasses to provide their own implementation of a method, which is then appropriately called. An example of this is 'flappyBox.move()'. During run time, it is dynamically determined that the move() method being called is that defined in the 'FallingBox' class.

## Part II: Data Structures

ArrayList: an ArrayList is used in the game to store and manage pipes. The specific List 'pipes' declared with the actual type ArrayList allows for easy management of the creation and removal of pipes as they move off the screen. They also create an organized storage of multiple objects in the 'Pipe' class, making implementation and management easier. 

FlappyBox: a representation of the player's avatar or controlled character in the game. The class packages in an organized form, or encapsulates, the functions and attributes of the character, such as its general rendering, position, and movement. This keeps the code clean and modular, as all the logic related to the user-accessed avatar is confined to this class.

Game: the Game class sets up the general game environment, initializes game objects like flappyBox and pipes, and handles the game state, like the score and whether the game is over. All game control is centralized in this class, making the code more organized and user-friendly.  

## Part III: Algorithm Example

handleCollisions(): the method 'handleCollisions()' in the 'Game' class is an important algorithm which detects whether a 'FlappyBox' object has collided with a 'Pipe' object. The method iterates through each 'Pipe' object and checks if the 'FlappyBox' object intersects with any pipe using the method 'intersects()', returning true if an intersection is detected, signalling a collision. The method is called in 'runGameLoop()' and terminates the game should it detect a collision (return true). Detecting if the FlappyBox object has collided with a Pipe object is crucial to the game play and is one way to determine if a player loses. It also ensures a collision is detected immediately, making the game more responsive.