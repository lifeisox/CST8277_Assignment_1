# CST8277_Assignment_1

Assignment 1: Multithreading

• Due Date: see Blackboard for due date.
• Demo required in your own lab period.
• Read the requirements carefully, if your program does not meet the requirements expect a loss of marks for the assignment, even though your program code runs without errors.

Purpose
After completing this assignment, you will have achieved the following:
1. You will have modified a program to implement multithreading.
2. You will have experience in implementing a solution to the Producer/Consumer problem.
3. You will have experience in how to test this type of program (animated and multithreaded).

Part 1: Basic Multithreading
Become familiar with the given bouncing sprite Java program. The user clicks the mouse inside the window to start a blue sprite bouncing inside the window. This is an animated program, which runs an infinite loop to repeatedly re-draw the sprite in the panel about 25 times per second, which we call the frame rate. In Swing 2D graphics, calling repaint causes the system to invoke the paintComponent method with the appropriate Graphics object parameter. In the paintComponent method, the programmer uses that Graphics object to draw things on the screen. Inside the paintComponent method, the call to super.paintComponent clears the screen (so the programmer can draw the new frame). Each time the sprite is drawn (one frame), it has moved a little bit, so as we watch this process in real time, the sprite appears to move. This is exactly the same principle used in a Hollywood "moving picture" (the standard Hollywood frame rate is 24 frames per second). The program as you received it uses two threads: 1. the main thread of the program that ultimately runs the animation loop (redrawing the window about 25 times per second), and 2. the Event Dispatch Thread that runs the mouse handler code whenever the mouse is clicked in the frame (to create an additional sprite).

1. THIS MAY TAKE TIME, but it will save you time later: Read the code of the given program to understand how it works, and add comments (both implementation comments and Javadoc comments, as necessary) that would have made it easier for you to understand the program more quickly. Ask your Professor for help with sections that you find difficult. You are encouraged to use debugging methods (make experimental changes, insert debugging lines of code, use a debugger) to help you understand how given program works.

2. Modify the program so that the Sprite class implements the Runnable interface and the sprite moves itself (25 times per second, which is the original frame rate) with its own thread of execution (the invocation of the sprite's move method will no longer belong in the main animation loop of the SpritePanel).

3. Modify the program so that each additional mouse click adds an additional bouncing sprite rather than replacing the previous sprite.

4. Run the jvisualvm program included with the JDK to confirm that each sprite on the screen has its own thread of execution. (Your Professor has demonstrated how to use this program in the Lecture and/or Lab class.)

Part 2: Synchronizing Threads
The goal of this part is for you to practice using the wait and notifyAll methods to synchronize threads. You will use similar techniques to the "Producer/Consumer Relationship with Synchronization" example in the Multithreading chapter of the Deitel textbook.

1. It is recommended that you read and understand the "Producer/Consumer Relationship with Synchronization" example in the Deitel textbook, and refer to the lecture slides on the Producer/Consumer problem.

2. Enhance the program to draw a circle, centered inside the main window, drawn with an outline (use a Graphics object method call within paintComponent of class SpritePanel). The sprites will move as they did before, and they will enter and exit from this circle as they move across the main window and cross the circle boundaries. Implement the following constraints:

    a. Only four sprites can be moving within the boundaries of the circle at a time. Any additional sprites entering the circle or created inside will freeze until one of the four currently moving sprites leaves the circle.
    b. A moving sprite exiting the circle will freeze unless there are at least 2 other sprites in the circle (a sprite cannot leave the circle with less than or equal to 2 sprites inside)
If it helps, you can think of the circle as a dark room with two flashlights. A sprite can move inside (enter) the circle only if there is at least one flashlight available. A sprite can leave the room only if both flashlights are in use.

It is intended that you apply the solution to the synchronized Producer/Consumer Relationship. That will work if you consider the sprites that are entering the circle to be consumers, and those that are leaving the circle to be producers (of vacancies, flashlights, or whatever). The reverse also works, if you consider the sprites that are entering the circle to be producers of "occupants", and the sprites that are leaving the circle to be consumers of "occupants".

HINTS:
1. A sprite can determine it is entering the circle if its status was outside the circle boundaries in the previous frame, but in the current frame, it is within the boundaries -- and similarly for exiting the circle.

2. Create all sprites with an initial status of being outside the circle, even if the new sprite is physically inside the circle. Those sprites that are created within the boundaries will immediately make their attempt to enter the circle and freeze in place if they cannot enter (think of flying by airplane to a city in a different country -- you enter the country at that city rather than at the border). Remember, you're supposed to be learning about synchronizing threads in a Producer/Consumer context: if you're getting bogged down with animation problems, ask your Professor for help.

3. Look up an algorithm for detecting that a circle is inside a circle and document your source using IEEE reference style in your code in the programmer comments above the collision detection method you create. Important! Most of the math assumes the x, y coordinates are in the center of the circle. Java places the x, y coordinates in the upper left corner (Your circle (oval) size is defined by a rectangle shape in the Java graphics system). Adjust your values accordingly.

You can use a tutorial website e.g. https://gamedevelopment.tutsplus.com/tutorials/when-worlds-collide-simulating-circle-circle-collisions--gamedev-769
Or something from StackOverflow (cite all authors participating in the discussion) http://stackoverflow.com/questions/9486520/finding-if-a-circle-is-inside-another-circle
Failing to cite your source may result in a score of zero for the assignment.
