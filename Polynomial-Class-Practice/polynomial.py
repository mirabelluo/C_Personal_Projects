class Polynomial:
    
    def __init__(self, c_list, in_order=True):
        '''
        Initializes the class, Polynomial's, attributes

        Parameters
        ----------
        c_list : list
            Accepts a list of coefficients
        in_order : boolean
            Set to true a default. True if c_list is in reverse order it will be printed in.

        Returns
        -------
        None.

        '''
        self.c_list = c_list
        
        #deals with a c_list that is all 0
        if all(i == 0 for i in c_list):
            c_list = [0]
        
        #removes ONLY leading 0's
        else:
            i = 0
            while c_list[i] == 0:
                c_list.pop(i)
                
        #checks and corrects order of coefficients  
        if in_order == True:
            coeffs = list(reversed(c_list))
        elif in_order == False:
            coeffs = c_list
        
        degree = len(coeffs) - 1
        
        self.coeffs = coeffs
        self.in_order = in_order
        self.degree = degree
      
    def __str__(self):
        '''
        Overrides the str object so that it returns given coeffs in polynomial form

        Returns
        -------
        poly : str
            Polynomial form of given coeffs

        '''
        poly = ''
        deg_count = self.degree
        
        #get last index
        last_index = len(self.c_list) - 1
        
        
        #expresses polynomial in form 'a(0)z**n +...+ a(n-1)z + a(n)'
        if len(self.coeffs) == 1:
            return str(self.coeffs[0])
        else:
            for c in range(len(self.c_list)):
                if c != last_index and self.c_list[c] != 0:
                    poly += f'{self.c_list[c]}x^{deg_count} + '
                    deg_count -= 1
                if c == last_index and self.c_list[c] != 0:
                    poly += f'{self.c_list[c]}'
                    deg_count -= 1
                #appends '.' when coefficient is 0
                if c == last_index and self.c_list[c] == 0:
                    poly += '0'
                    deg_count -= 1
                elif self.c_list[c] == 0 and c != last_index:
                    poly += ''
                    deg_count -= 1
         
        #solves for issue of negative numbers
        if "+ -" in poly:
            poly = poly.replace('+ -', '- ')
        #below linesremoves 0s from polynomial
        if "+ 0" in poly:
            poly = poly.replace('+ 0', '')
        if " 0" in poly:
            poly = poly.replace(' 0', '')
        
        
        return poly
    
    def at(self, c):
        '''
        Takes a number c and evaluates a polynomial at that number

        Parameters
        ----------
        c : int/float
            number polynomial is to be evaluated at

        Returns
        -------
        evaluate : int/float
            value of the polynomial when x = c

        '''
        deg_count = self.degree
        evaluate = 0
        
        for x in self.c_list:
            if x != self.c_list[-1]:
                evaluate += x * (c ** deg_count)
                deg_count -= 1
            #evalues constant
            if x == self.c_list[-1]:
                evaluate += x
                deg_count -= 1
        return evaluate 
    
    def __eq__(self, other):
        '''
        Checks for equality between two polynomials

        Parameters
        ----------
        other : Polynomial
            second polynomial the first is compared to

        Returns
        -------
        bool
            True if two polynomials are equal, False if they are not

        '''
        if self.coeffs == other.coeffs:
            return True
        else:
            return False
    
    def __ne__(self, other):
        '''
        Checks for equality between two polynomials

        Parameters
        ----------
        other : Polynomial
            second polynomial the first is compared to

        Returns
        -------
        bool
            False if two polynomials are equal, True if they are not

        '''
        if self.coeffs != other.coeffs:
            return True
        else:
            return False
    
    def __add__(self, other):
        '''
        Overloads addition so that two polynomials or a polynomial and a number can be summed together

        Parameters
        ----------
        other : int/float or Polynomial
            object to be added to the first polynomial

        Returns
        -------
        Polynomial(new_clist) : Polynomial
            The final sum

        '''
        new_clist = []
        
        if type(other) == Polynomial:
            #case when both polynomials are 0
            if self.coeffs == [0] and other.coeffs == [0]:
                new_clist = [0]
            else:
                for i in range(len(self.coeffs)):
                    if i <= len(other.c_list) - 1:
                        new_clist.append(self.coeffs[i] + other.coeffs[i])
                    else:
                        new_clist.append(self.coeffs[i])
                #corrects for the case of them one polynomial having more terms than the other
                if len(other.coeffs) > len(self.coeffs):
                    for i in range(len(self.coeffs), len(other.coeffs)):
                        new_clist.append(other.coeffs[i])
                new_clist = list(reversed(new_clist))
            
            
        if type(other) == int or type(other) == float:
            
            new_clist = self.c_list
            new_clist[-1] = self.c_list[-1] + other
        
        
        return Polynomial(new_clist)
    
    def __sub__(self, other):
        '''
        Overloads subtraction so that two polynomials or a polynomial and a number can be subtracted from one another

        Parameters
        ----------
        other : int/float or Polynomial
            object to be subtracted from the first polynomial

        Returns
        -------
        Polynomial(new_clist) : Polynomial
            The final difference

        '''
        new_clist = []
        
        if type(other) == Polynomial:
            
            #degree of first polynomial is greater than second
            if len(self.c_list) > len(other.c_list):
                
                for i in range(len(other.c_list)):
                    new_clist.append(self.c_list[i] - other.c_list[i])
                
                for x in self.c_list[len(other.c_list + 1)::]:
                    new_clist.append(x)
            #degree of second polynomial is greater than first  
            elif len(self.c_list) < len(other.c_list):
                
                for i in range(len(self.c_list)):
                    new_clist.append(self.c_list[i] - other.c_list[i])
                
            
                for x in other.c_list[len(self.c_list + 1)::]:
                    new_clist.append(x) 
            
            #degree of both polynomials are equal
            elif len(self.c_list) == len(other.c_list):
                
                new_clist = [a - b for a, b in zip(self.c_list, other.c_list)]
            
        if type(other) == int or type(other) == float:
            
            new_clist = self.c_list
            new_clist[-1] = self.c_list[-1] - other
        
        return Polynomial(new_clist)
    
    def __mul__(self, other):
        '''
        Overloads multiplication so that two polynomials or a polynomial and a number can be multiplied together

        Parameters
        ----------
        other : int/float or Polynomial
            object to be multiplied to the first polynomial

        Returns
        -------
        Polynomial(new_clist) : Polynomial
            The final product

        '''
        newer_clist = []
        
        if type(other) == int or type(other) == float:
        
            newer_clist = [x * other for x in self.c_list]
        
    
        if type(other) == Polynomial:
            #creates a list with a length equal to the length of the product list
            newer_clist = [0] * ((len(self.c_list) + (len(other.c_list) - 1)))
            
            #multiplies every term of the first polynomial by the term of the second polynomial
            for a in range(len(self.c_list)):
                
                for b in range(len(other.c_list)):
                    newer_clist[a + b] += self.c_list[a] * other.c_list[b]
                    
        return Polynomial(newer_clist)
    
    def __pow__(self, n):
        '''
        Raises a polynomial to the nth power (positive powers only)

        Parameters
        ----------
        n : int
            what power the polynomial is raised to

        Returns
        -------
        newpoly : Polynomial
            resulting raised polynomial

        '''
        newpoly = Polynomial(self.c_list)
        
        #to the 0th power
        if n == 0:
            newpoly = Polynomial([1])
        
        #to the 1st power
        if n == 1:
            newpoly = Polynomial(self.c_list)
        
        #to any power other than 0 or 1   
        elif n > 1:
            while n != 1:
                newpoly *= Polynomial(self.c_list)
                n -= 1
        
        
        return newpoly
    
    def __or__(self, other):
        '''
        This function reads f|g as f(g(x)) and returns the composite function

        Parameters
        ----------
        other : Polynomial
            g(x), the polynomial to be taken as the 'x' term of f(x)

        Returns
        -------
        newpoly : Polynomial
            Composite polynomial of f(g(x))

        '''
        
        newpoly = Polynomial([0])
        deg_count = 0
        
        for x in range(len(self.coeffs)):
            #plugs in g(x) in each term of f(x) containing a variable
            if x != 0:
               newpoly += ((other ** deg_count) * self.coeffs[x])
               deg_count += 1   
            #adds constant of f(x) polynomial
            elif x == 0:
               newpoly += self.coeffs[x]
               deg_count += 1 
         
        return newpoly
        
    def derivative(self, m):
        '''
        Takes the mth derivative of the polynomial

        Parameters
        ----------
        m : int
            how many times the derivative must be taken

        Returns
        -------
        - : Polynomial
            some polynomial representation of the nth derivative of the input polynomial

        '''
        
        exponent = self.degree
        
        copy = self.c_list.copy()
        
        #checks if m is 0
        if m == 0:
            return self.c_list
        #when taking a derivative greater than the degree, the result is 0
        elif m > self.degree:
            return Polynomial([0])
        else:
        #loops until nth derivative
            for i in range(m):
                new_clist = [copy[i] * (exponent - i) for i in range(exponent)]
                copy = new_clist
                exponent -= 1
        
            return Polynomial(copy)
        
        
if __name__ == "__main__":
    '''
    #Task 1 Sub-task I
    p = Polynomial([6,0,2])
    print('Coefficients: ',p.coeffs)
    
    print(type(p.coeffs))
    
    print('Degree: ',p.degree)
    print(type(p.degree))
    #Task 1 Sub-task II
    q = Polynomial([6,1,2], in_order = False)
    print('Coefficients:', q.coeffs)
    #Task 1 Sub-task III
    p = Polynomial([0, 0, 6,1,2])
    q = Polynomial([0, 0])
    print(p.coeffs)
    print(q.coeffs)
    
    #Task 2
    p = Polynomial([0, 4, 1, 1])
    q = Polynomial([-7,1,-2])
    print('Polynomial p:', p)
    print('Polynomial q:', q)
    
    #Task 3
    p = Polynomial([6,0,2])
    print('Polynomial f(x): ', p)
    print('f(x) at x = 2: ',p.at(2))
    
    #Task 4
    p = Polynomial([6,0,2])
    q = Polynomial([0, 6, 0 , 2])
    r = Polynomial([2])

    print('Polynomial p: ',p)
    print('Polynomial q: ',q)
    print('Polynomial r: ',r)

    print('p == p :',p == p)
    print('p == q :',p == q)
    print('p != r :',p != r)
    print('p != q :',p != q)
    
    #Task 5
    p = Polynomial([6,0,2])
    q = Polynomial([7,1,2])

    r = p + q
    t = p + 5

    print('Polynomial p: ',p)
    print('Polynomial q: ',q)
    print('p + q: ',r)
    
    print(type(r))
    print('p + 5: ', t)
    print(type(t))
    
    #Task 6
    p = Polynomial([6,0,2])
    q = Polynomial([7,1,2])
    z = Polynomial([6,0,2])

    r = p - q
    t = q - z

    print('Polynomial p: ',p)
    print('Polynomial q: ',q)
    print('Polynomial z: ',z)

    print('p - q: ',r)
    print(type(r))

    print('q - z: ',t)
    print(type(t))

    print('p - z: ',p - z)
    print('p - 5: ',p - 5)
    
    #Task 7
    p = Polynomial([6,0,2])
    q = Polynomial([7,1,2])

    r = p * q

    print('Polynomial p: ',p)
    print('Polynomial q: ',q)

    print('p * q: ',r)
    print(type(r))
    
    print('p * 5: ',p * 5)
    
    #Task 8
    p = Polynomial([6,2])
    
    x = p ** 0
    y = p ** 1
    z = p ** 2

    print('Polynomial p: ',p)
    print('Results')
    print('Case 1 p^0: ',x)
    print('Case 2 p^1: ',y)
    print('Case 3 p^2: ',z)

    print(type(x))
    
    #Task 9
    f = Polynomial([0, 3, 2, 7])
    g = Polynomial([0, 1, 1])
    
    a = Polynomial([0, 0, 1, 2, 7])
    b = Polynomial([0, 1, 0])
    
    
    
    print('Composition: ', f|g)
    print('Composition: ', a|b)
    
    #Task 10
    p = Polynomial([6,0,2])
    
    print('Polynomial p: ',p)

    print('First derivative: ', p.derivative(1))
    print('Second derivative: ', p.derivative(2))
    print('Third derivative: ', p.derivative(3))
    print('Fourth derivative: ', p.derivative(4))

    print(type(p.derivative(4)))
    '''
