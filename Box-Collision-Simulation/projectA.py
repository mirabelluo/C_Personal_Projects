
# Project A
# Mirabel Luo, Johns Hopkins University
"""
Created on Fri Sep  1 16:05:56 2023


"""
import math

from simVis1 import visualize




def main():
    
    # initialize parameters
    
    radius =0.15
    dt =.2
    
    # simulation steps
    simSteps = 800
    

    # Default is Ax1xmin= 0,Ax1xmax = 1, Ax1ymin = 0, Ax1ymax = 1
    vis = visualize() 
    

    x1 = 0.2
    y1 = 0.25
    
    v1x = -0.05
    v1y = -0.05
    
    x2 = 0.7
    y2 = 0.7
    
    
    v2x = 0.05
    v2y = 0.05
    
    
    # run simulation
    simulate(simSteps,vis,x1,y1,x2,y2,v1x,v1y,v2x,v2y,dt,radius)
    


# DO NOT CHANGE THIS FUNCTION
def boundary_locations(vis,radius):
    
    """
    Calculate the boundary locations within the visualization window.
    This function calculates the boundary coordinates for a containing box 
    within the visualization window to ensure that particles 
    do not exceed these boundaries.

    Parameters
    ----------
    vis : visualization object
        The visualization object used for determining window boundaries.
    radius : float
        The radius of the particles.

    Returns
    -------
    xLow : float
        The lower bound of the containing box's threshold to hit the vertical left wall.
    yLow : float
        The lower bound of the containing box's threshold to hit the horizontal bottom wall.
    xHigh : float
        The upper bound of the containing box's threshold to hit the vertical right wall.
    yHigh : float
        The upper bound of the containing box's threshold to hit the horizontal top wall.

    """
    
    # Adjust the boundary according to the 
    # circle radius and display window size
    # Assume circles have the same radius.
    xLow = vis.Ax1xmin + radius 
    xHigh = vis.Ax1xmax - radius 
    
    yLow = vis.Ax1ymin + radius 
    yHigh = vis.Ax1ymax - radius 
    
    return xLow, yLow, xHigh, yHigh
    
    


def updateX(x,vx,dt): 
    '''
    Updates the x-coordinate of an object's position 
    based on velcoity over a specific time

    Parameters
    ----------
    x : float
        The current x-coordinate of the object's position
    vx : float
        The velocity of the object in the x-direction
    dt : float
        The time step size for the update

    Returns
    -------
    round(new_x, 3) : float
        The updated x-coordinated rounded to 3 decimal points

    '''
    
    new_x = x + (vx * dt)
    return round(new_x, 3)

def updateY(y,vy,dt):
    '''
    Updates the y-coordinate of an object's position 
    based on velcoity over a specific time

    Parameters
    ----------
    y : float
        The current y-coordinate of the object's position
    vy : float
        The velocity of the object in the y-direction
    dt : float
        The time step size for the update

    Returns
    -------
    round(new_y, 3) : float
        The updated y-coordinated rounded to 3 decimal points
        
    '''
    
    new_y = y + (vy * dt)
    return round(new_y, 3)

def boxCollision(x,y,vx,vy,xLow,xHigh,yLow,yHigh):
    '''
    Checks if a circle hits the walls of a containing box.
    If a collision occurs, it recalculates the object's position
    and velocity based on wall reflections.

    Parameters
    ----------
    x : float
        The x-coordinate of the center position of the circle
    y : float
        The y-coordinate of the center position of the circle
    vx : float
        The velocity of the object in the x-direction
    vy : float
        The velocity of the object in the y-direction
    xLow : float
        The lower x-threshold defining the containing box
    xHigh : float
        the upper x-threshold defining the containing box
    yLow : float
        the lower y-threshold defining the containing box
    yHigh : float
        the upper y-threshold defining the containing box

    Returns
    -------
    (x,y)
        updated position after the collision with the walls
    (vx, vy)
        updated velocity after the collision with the walls

    '''
    
    if x > xHigh:
        x = xHigh
        vx = -vx
    elif x < xLow:
        x = xLow
        vx = -vx
    
    if y > yHigh:
        y = yHigh
        vy = -vy
    elif y < yLow:
        y = yLow
        vy = -vy
    
    return x,y,vx,vy
        
    

def Overlap(x1,y1,radius1,x2,y2,radius2):
    '''
    Determines whether two circles and their respective radii overlap or collide

    Parameters
    ----------
    x1 : float
        The x-coordinate of the center of the first circle
    y1 : float
        The y-coordinate of the center of the first circle
    radius1 : float
        The radius of the first circle
    x2 : float
        The x-coordinate of the center of the second circle
    y2 : float
        The y-coordinate of the center of the second circle
    radius2 : float
        The radius of the second circle

    Returns
    -------
    bool
        Returns 'True' if the circles overlap
        Returns 'False' if the circles do not overlap

    '''
   
    dist_between_centers = get_distance(x1, y1, x2, y2)
    sum_radii = radius1 + radius2
    
    if dist_between_centers < sum_radii:
        return True
    else:
        return False

def get_unit_direction(x1,y1,x2,y2):
    
    '''
    Calculates the unit direction vector between two points
    
    Parameters
    ----------
    x1 : float
        The x-coordinate of the first point
    y1 : float
        The y-coordiante of the first point
    x2 : float
        The x-coordinate of the second point
    y2 : float
        The y-coordinate of the second point

    Returns
    -------
    unit_tuple : tuple
        The unit direction value in the form (x_unit, y_unit), rounded to 3 decimals
    '''
    x_unit = 0
    y_unit = 0
    
    if (x1 == x2) and (y1 == y2):
        
        x_unit = math.nan
        y_unit = math.nan
    
    
    else: 
         x_unit = (x1 - x2) / (get_distance(x1, y1, x2, y2))
         y_unit = (y1 - y2) / (get_distance(x1, y1, x2, y2))
         
    
    return round(x_unit, 3), round(y_unit, 3)



def get_distance(x1,y1,x2,y2):
    
    '''
    Calculates the euclidean distance between two points 
    represented by their coordinates

    Parameters
    ----------
    x1 : float
        The x-coordinate of the first point
    y1 : float
        The y-coordiante of the first point
    x2 : float
        The x-coordinate of the second point
    y2 : float
        The y-coordinate of the second point

    Returns
    -------
    round(distance, 3) : float
        The distance between the two points rounded to 3 decimal points
    '''
    
    distance = math.sqrt(math.pow(x2 - x1, 2) + math.pow(y2 - y1, 2))
    return round(distance, 3)
    

def dot_product(x1,y1,x2,y2):
    
    '''
    Calculatess the dot product of two 2D vectors 

    Parameters
    ----------
    x1 : float
        x component of the first vector
    y1 : float
        y component of the first vector
    x2 : float
        x component of the second vector
    y2 : float
        y component of the second vector

    Returns
    -------
    result : float
        The dot product of the two vectors
    '''
    
    result = (x1 * x2) + (y1 * y2)
    return result

def update_collision_velocity(x1,y1,v1x,v1y, x2,y2,v2x,v2y):
    '''
    Calculates the updated velocities of two objects 
    after a collision based on their initial positions and 
    and velocities

    Parameters
    ----------
    x1 : float
        The x-coordinate of the center of the first circle
    y1 : float
        The y-coordinate of the center of the first circle
    v1x : float
        The x component of the first circle's current velocity
    v1y : float
        The y component of the first circle's current velocity
    x2 : float
        The x-coordinate of the center of the second circle
    y2 : float
        The y-coordinate of the center of the second circle
    v2x : float
        The x component of the second circle's current velocity
    v2y : float
        The y component of the second circle's current velocity

    Returns
    -------
    new_v1x: float
        The x component of the first circle updated velocity after collision
    new_v1y: float
        The y component of the first circle updated velocity after collision
    new_v2x: float
        The x component of the first circle updated velocity after collision
    new_v2y: float
        The y component of the first circle updated velocity after collision

    '''
    #if part of statement accounts for denominator of equation being 0
    if math.pow(get_distance(x1, y1, x2, y2), 2) == 0:
        new_v1x = -v1x
    else:
        new_v1x = v1x - ((((v1x - v2x) * (x1 - x2)) + ((v1y - v2y) * (y1 - y2)))/ math.pow(get_distance(x1, y1, x2, y2), 2) * (x1 - x2))
    
    
    if math.pow(get_distance(x1, y1, x2, y2), 2) == 0:
        new_v1y = -v1y
    else:
        new_v1y = v1y - ((((v1x - v2x) * (x1 - x2)) + ((v1y - v2y) * (y1 - y2)))/ math.pow(get_distance(x1, y1, x2, y2), 2) * (y1 - y2))
   
    if math.pow(get_distance(x1, y1, x2, y2), 2) == 0:
        new_v2x = -v2x
    else:
        new_v2x = v2x - ((((v2x - v1x) * (x2 - x1)) + ((v2y - v1y) * (y2 - y1)))/ math.pow(get_distance(x1, y1, x2, y2), 2) * (x2 - x1))
    
    if  math.pow(get_distance(x1, y1, x2, y2), 2) == 0:
        new_v2y = -v2y
    else:
        new_v2y = v2y - ((((v2x - v1x) * (x2 - x1)) + ((v2y - v1y) * (y2 - y1)))/ math.pow(get_distance(x1, y1, x2, y2), 2) * (y2 - y1))
    
    return round(new_v1x, 3), round(new_v1y, 3), round(new_v2x, 3), round(new_v2y, 3)

def circleCollision(x1,y1,radius1,v1x, v1y, x2,y2,radius2,v2x,v2y):
    '''
    Simulates the collision and response of two circular objects in a 2D space.
    It calculates the new positions and velocities of the two objects after a collision

    Parameters
    ----------
    x1 : float
        The x-coordinate of the center of the first circle
    y1 : float
        The y-coordinate of the center of the first circle
    radius1 : float
        The radius of the first circular object.
    v1x : float
        The x component of the first circle's current velocity
    v1y : float
        The y component of the first circle's current velocity
    x2 : float
        The x-coordinate of the center of the second circle
    y2 : float
        The y-coordinate of the center of the second circle
    radius2 : float
        The radius of the second circular object
    v2x : float
        The x component of the second circle's current velocity
    v2y : float
        The y component of the second circle's current velocity

    Returns
    -------
    new_x : float
        The newly calculated x-coordinate of the first circle
    new_y : float
        The newly calculated y-coordinate of the first circle
    v1x: float
        The x component of the first circle updated velocity after collision
    v1y: float
        The y component of the first circle updated velocity after collision
    x2 : float
        The x-coordinate of the center of the second circle
    y2 : float
        The y-coordinate of the center of the second circle
    v2x: float
        The x component of the first circle updated velocity after collision
    v2y: float
        The y component of the first circle updated velocity after collision
    
    '''  
    if Overlap(x1, y1, radius1, x2, y2, radius2) == True:
        
        if x1 != x2 and y1 != y2:
        
            v1x, v1y, v2x, v2y = update_collision_velocity(x1, y1, v1x, v1y, x2, y2, v2x, v2y)
        
            sum_radii = radius1 + radius2
            
            x_unit, y_unit = get_unit_direction(x1, y1, x2, y2)
            
            #distance center of circle 1 must be moved
            move = (math.fabs((get_distance(x1, y1, x2, y2) - sum_radii)))
       
            x1 = x1 + (move * x_unit)
            y1 = y1 + (move * y_unit)
            
            
        else:
        
            v1x, v1y, v2x, v2y = update_collision_velocity(x1, y1, v1x, v1y, x2, y2, v2x, v2y)
        
            sum_radii = radius1 + radius2
       
            x1 = x1 + (sum_radii * 0.707)
            y1 = y1 + (sum_radii * 0.707)
        
        #  displcalculatesacement needed to separate the objects
    
    
    return round(x1, 3), round(y1, 3), round(v1x, 3), round(v1y, 3), round(x2, 3), round(y2, 3), round(v2x, 3), round(v2y, 3)


# DON NOT CHANGE THIS FUNCTION
def simulate(simSteps,vis,x1,y1,x2,y2,v1x,v1y,v2x,v2y,dt,radius):
    
    """
    Run a particle simulation for a specified number of time steps.

    Parameters
    ----------
    simSteps : int
        Number of time steps to run the simulation.
    vis : visualize
        An instance of the 'visualize' class for visualization.
    x1 : float
        Initial x-coordinate of the center of circle 1.
    y1 : float
        Initial y-coordinate of the center of circle 1.
    x2 : float
        Initial x-coordinate of the center of circle 2.
    y2 : float
        Initial y-coordinate of the center of circle 2.
    v1x : float
        Initial x-component of the velocity of circle 1.
    v1y : float
        Initial y-component of the velocity of circle 1.
    v2x : float
        Initial x-component of the velocity of circle 2.
    v2y : float
        Initial y-component of the velocity of circle 2.
    dt : float
        Time step size for the simulation.
    radius : float
        Radius of both particles.

    Returns
    -------
    None

    """
    
    # This function runs a two-particle simulation for a specified number of 
    # time steps. It updates the positions of two circles and checks for 
    # their collisions with both the container boundary and each other. 
    # The simulation is visualized using the'visualize' class instance 
    # provided as 'vis'.
    
    
    # Identify the boundary location for the containing window
    # Assumption: both particles have the same radius.
    xLow,yLow,xHigh, yHigh = boundary_locations(vis,radius) 
    
    for i in range(simSteps):
        
            
    
    
        # update x, y position of particles
        x1 = updateX(x1,v1x,dt)
        y1 = updateY(y1,v1y,dt)
        
        x2 = updateX(x2,v2x,dt)
        y2 = updateY(y2,v2y,dt)
            
        
                    
        # Check for box and circle collisions
        
        x1,y1,v1x,v1y = boxCollision(x1,y1,v1x,v1y,xLow,xHigh,yLow,yHigh)
        
        
        
        x2,y2,v2x,v2y = boxCollision(x2,y2,v2x,v2y,xLow,xHigh,yLow,yHigh)
        
        
        x1,y1,v1x,v1y, x2,y2,v2x,v2y = circleCollision\
                                      (x1,y1,radius,v1x, \
                                        v1y, x2,y2,radius,v2x,v2y)
                    
        
                    
        # draw circles
        vis.circle(x1, y1, radius, 0)
        vis.circle(x2, y2, radius, 1)
        
                    
        # pause plots and clear window axis 1
        vis.plotPause()
        
        
        vis.axis1Clear()
    
    # redraw circles after last iteration
    vis.circle(x1, y1, radius, 0)
    vis.circle(x2, y2, radius, 1)
        
if __name__ == '__main__': 
    
    # Call the main function to excute the simulation
    # For testing purpose comment main() and call another 
    # function.
    main()
    
    
    
    